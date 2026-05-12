from __future__ import annotations

from dataclasses import dataclass, field
from enum import Enum
from typing import Dict, List, Optional, Set

from nitric.proto.analyticsservice.v1 import (
    Subgraph,
    Node,
    PipelineMode,
    MapToDataStrategy,
    JoinType,
)


class Severity(Enum):
    ERROR = "ERROR"
    WARNING = "WARNING"


@dataclass
class Issue:
    severity: Severity
    message: str
    node_id: str = ""

    def __str__(self) -> str:
        loc = f"node[{self.node_id}]" if self.node_id else "subgraph"
        return f"[{self.severity.value}] {loc}: {self.message}"


@dataclass
class ValidationResult:
    issues: List[Issue] = field(default_factory=list)

    def error(self, message: str, node_id: str = ""):
        self.issues.append(Issue(Severity.ERROR, message, node_id))

    def warning(self, message: str, node_id: str = ""):
        self.issues.append(Issue(Severity.WARNING, message, node_id))

    @property
    def is_valid(self) -> bool:
        return not any(i.severity == Severity.ERROR for i in self.issues)

    @property
    def errors(self) -> List[Issue]:
        return [i for i in self.issues if i.severity == Severity.ERROR]

    @property
    def warnings(self) -> List[Issue]:
        return [i for i in self.issues if i.severity == Severity.WARNING]

    def raise_if_invalid(self):
        if not self.is_valid:
            msg = "\n".join(str(e) for e in self.errors)
            raise ValueError(f"Pipeline validation failed:\n{msg}")


class SubgraphValidator:
    """
    Semantic validation of a Subgraph proto.

    Checks structural integrity and mode constraints by walking nodes
    in topological order (assumed to match declaration order from the
    DSL compiler).
    """

    def validate(self, subgraph: Subgraph) -> ValidationResult:
        result = ValidationResult()

        if subgraph is None:
            result.error("Subgraph is null.")
            return result

        node_map: Dict[str, Node] = {n.id: n for n in subgraph.nodes}

        # ── Edge validity ─────────────────────────────────────────────────────

        for edge in subgraph.edges:
            if edge.from_node not in node_map:
                result.error(f"Edge references unknown from_node '{edge.from_node}'.")
            if edge.to_node not in node_map:
                result.error(f"Edge references unknown to_node '{edge.to_node}'.")

        # ── Classify nodes ────────────────────────────────────────────────────
        # Sources: in-degree 0. Sinks: out-degree 0.

        has_incoming: Set[str] = {e.to_node for e in subgraph.edges}
        has_outgoing: Set[str] = {e.from_node for e in subgraph.edges}

        source_ids: Set[str] = set()
        sink_ids: Set[str] = set()
        step_ids: Set[str] = set()

        for node in subgraph.nodes:
            if not node.id:
                result.error("Node has an empty id.")
                continue
            is_in = node.id in has_incoming
            is_out = node.id in has_outgoing
            if not is_in:
                source_ids.add(node.id)
            if not is_out:
                sink_ids.add(node.id)
            if is_in and is_out:
                step_ids.add(node.id)

        if not source_ids:
            result.error("Subgraph has no source nodes (no nodes with in-degree 0).")
        if not sink_ids:
            result.error("Subgraph has no sink nodes (no nodes with out-degree 0).")

        # ── Propagate modes from sources ──────────────────────────────────────

        node_mode: Dict[str, PipelineMode] = {}

        for nid in source_ids:
            node = node_map[nid]
            mode = PipelineMode.DATA if node.source.stream.topic else PipelineMode.STATE
            node_mode[nid] = mode
            for edge in subgraph.edges:
                if edge.from_node == nid:
                    node_mode[edge.to_node] = mode

        # ── Walk step nodes ───────────────────────────────────────────────────
        # Nodes are in declaration order which is topological for the DSL compiler.

        prev_was_window: Dict[str, bool] = {}

        for node in subgraph.nodes:
            nid = node.id
            if nid not in step_ids:
                continue

            step = node.step
            mode = node_mode.get(nid, PipelineMode.PIPELINE_MODE_UNSPECIFIED)

            # Determine which operation is active
            stype = _step_op(step)

            # Window must be immediately followed by aggregate
            # Track per incoming edge (simplified: track per node in linear order)
            incoming_nodes = [e.from_node for e in subgraph.edges if e.to_node == nid]
            if len(incoming_nodes) == 1:
                prev_node_id = incoming_nodes[0]
                if prev_node_id in step_ids:
                    prev_node = node_map[prev_node_id]
                    if _step_op(prev_node.step) == "window" and stype != "aggregate":
                        result.error(
                            f"WINDOW must be immediately followed by AGGREGATE/GROUP BY, "
                            f"found '{stype.upper()}' instead.",
                            node_id=nid,
                        )

            if stype == "window":
                if mode == PipelineMode.STATE:
                    result.error(
                        "WINDOW is invalid in STATE mode. " "Use MAP TO DATA to cross back to DATA first.",
                        node_id=nid,
                    )

            elif stype == "aggregate":
                if mode == PipelineMode.STATE:
                    # Find the state key from map_to_state upstream (best-effort)
                    pass  # key constraint checked at build time by NodeBuilder

            elif stype == "map_to_state":
                if mode == PipelineMode.STATE:
                    result.error(
                        "MAP TO STATE is invalid — already in STATE mode.",
                        node_id=nid,
                    )
                else:
                    mode = PipelineMode.STATE

            elif stype == "map_to_data":
                if mode == PipelineMode.DATA:
                    result.error(
                        "MAP TO DATA is invalid — already in DATA mode.",
                        node_id=nid,
                    )
                else:
                    if (
                        step.map_to_data.strategy == MapToDataStrategy.PERIODIC
                        and not step.map_to_data.schedule.cron
                        and not step.map_to_data.schedule.interval
                    ):
                        result.warning(
                            "MAP TO DATA PERIODIC has no schedule — " "will emit on every micro-batch.",
                            node_id=nid,
                        )
                    mode = PipelineMode.DATA

            elif stype == "join":
                # Join output mode: DATA wins — if either input is DATA, output is DATA.
                # (DATA+STATE = stream-table enrichment, DATA+DATA = stream-stream join)
                incoming_modes = [
                    node_mode.get(e.from_node, PipelineMode.PIPELINE_MODE_UNSPECIFIED)
                    for e in subgraph.edges
                    if e.to_node == nid
                ]
                if PipelineMode.DATA in incoming_modes:
                    mode = PipelineMode.DATA
                # else STATE+STATE → mode stays STATE

            node_mode[nid] = mode
            for edge in subgraph.edges:
                if edge.from_node == nid:
                    node_mode[edge.to_node] = mode

        # Check for WINDOW as last step node (no aggregate follows)
        for nid in step_ids:
            node = node_map[nid]
            if _step_op(node.step) == "window":
                # Check if any downstream node is an aggregate
                downstream_step_ops = [
                    _step_op(node_map[e.to_node].step)
                    for e in subgraph.edges
                    if e.from_node == nid and e.to_node in step_ids
                ]
                if "aggregate" not in downstream_step_ops:
                    result.error(
                        "WINDOW must be followed by AGGREGATE/GROUP BY.",
                        node_id=nid,
                    )

        # ── Validate sink modes ───────────────────────────────────────────────

        for nid in sink_ids:
            node = node_map[nid]
            incoming = node_mode.get(nid, PipelineMode.PIPELINE_MODE_UNSPECIFIED)

            if node.sink.stream.topic and incoming == PipelineMode.STATE:
                result.error(
                    f"STREAM sink '{node.sink.stream.topic}' requires DATA mode "
                    f"but receives STATE. Add MAP TO DATA before the sink, "
                    f"or change the sink to KV.",
                    node_id=nid,
                )
            elif node.sink.kv.store and incoming == PipelineMode.DATA:
                result.error(
                    f"KV sink '{node.sink.kv.store}' requires STATE mode "
                    f"but receives DATA. Add MAP TO STATE before the sink, "
                    f"or change the sink to STREAM.",
                    node_id=nid,
                )

        return result


def _step_op(step) -> str:
    """Return the operation type string for a Step proto."""
    if step.filter.predicate.to_dict():
        return "filter"
    if step.derive.expressions:
        return "derive"
    if step.select.columns:
        return "select"
    if step.window.duration:
        return "window"
    if step.aggregate.aggs:
        return "aggregate"
    if step.join.left_key:
        return "join"
    if step.map_to_state.key_column:
        return "map_to_state"
    if step.map_to_data.strategy != MapToDataStrategy.MAP_TO_DATA_STRATEGY_UNSPECIFIED:
        return "map_to_data"
    return "unknown"
