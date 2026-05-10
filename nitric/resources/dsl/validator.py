from __future__ import annotations

from dataclasses import dataclass, field
from enum import Enum
from typing import List, Optional

from nitric.proto.analyticsservice.v1 import (
    Pipeline,
    PipelineMode,
    Step,
    Filter,
    Project,
    Window,
    Aggregate,
    Join,
    SourceType,
    MapToState,
    MapToData,
    Source,
    Sink,
    StreamOperator,
)


class Severity(Enum):
    """Severity level for a pipeline validation issue."""

    ERROR = "ERROR"
    WARNING = "WARNING"


@dataclass
class Issue:
    """A single validation issue with a severity, message, and optional step index."""

    severity: Severity
    message: str
    step_index: int = -1

    def __str__(self) -> str:
        loc = f"step[{self.step_index}]" if self.step_index >= 0 else "pipeline"
        return f"[{self.severity.value}] {loc}: {self.message}"


@dataclass
class ValidationResult:
    """Accumulates validation issues found during pipeline checking."""

    issues: List[Issue] = field(default_factory=list)

    def error(self, message: str, step_index: int = -1):
        """Append an error-level issue."""
        self.issues.append(Issue(Severity.ERROR, message, step_index))

    def warning(self, message: str, step_index: int = -1):
        """Append a warning-level issue."""
        self.issues.append(Issue(Severity.WARNING, message, step_index))

    @property
    def is_valid(self) -> bool:
        """Return True if no error-level issues were recorded."""
        return not any(i.severity == Severity.ERROR for i in self.issues)

    @property
    def errors(self) -> List[Issue]:
        """Return all error-level issues."""
        return [i for i in self.issues if i.severity == Severity.ERROR]

    @property
    def warnings(self) -> List[Issue]:
        """Return all warning-level issues."""
        return [i for i in self.issues if i.severity == Severity.WARNING]

    def raise_if_invalid(self):
        """Raise ValueError listing all errors if the result is invalid."""
        if not self.is_valid:
            msg = "\n".join(str(e) for e in self.errors)
            raise ValueError(f"Pipeline validation failed:\n{msg}")


# ── betterproto field presence helpers ───────────────────────────────────────
# betterproto zero-initialises all fields — there is no HasField().
# Presence is detected by checking non-default values.


def _step_type(step: Step) -> str:
    """Return which operation is set on a Step."""
    if step.filter.predicate != type(step.filter.predicate)():
        return "filter"
    if step.project.columns:
        return "project"
    if step.window.duration:
        return "window"
    if step.aggregate.aggs:
        return "aggregate"
    if step.join.right_source:
        return "join"
    if step.map_to_state.key_column:
        return "map_to_state"
    if step.map_to_data.operator != StreamOperator.STREAM_OPERATOR_UNSPECIFIED:
        return "map_to_data"
    return "unknown"


# ── Source presence helpers ───────────────────────────────────────────────────


def _has_stream_source(source: Source) -> bool:
    return bool(source.stream.name)


def _has_kv_source(source: Source) -> bool:
    return bool(source.kv.name)


def _has_timeseries_source(source: Source) -> bool:
    return bool(source.timeseries.name)


def _has_any_source(source: Source) -> bool:
    return _has_stream_source(source) or _has_kv_source(source) or _has_timeseries_source(source)


# ── Sink presence helpers ─────────────────────────────────────────────────────


def _has_stream_sink(sink: Sink) -> bool:
    return bool(sink.stream.name)


def _has_kv_sink(sink: Sink) -> bool:
    return bool(sink.kv.name)


def _has_timeseries_sink(sink: Sink) -> bool:
    return bool(sink.timeseries.name)


def _has_any_sink(sink: Sink) -> bool:
    return _has_stream_sink(sink) or _has_kv_sink(sink) or _has_timeseries_sink(sink)


class PipelineValidator:
    """
    Semantic validation of a Pipeline proto message.

    Catches rules the grammar cannot enforce at parse time:
      - Window must be immediately followed by Aggregate
      - Window is invalid in STATE mode
      - Aggregate group_by must not include state key in STATE mode
      - MapToState is invalid when already in STATE mode
      - MapToData is invalid when already in DATA mode
      - Source type must match declared mode
      - Sink type must match final mode after all steps
    """

    def validate(self, pipeline: Pipeline) -> ValidationResult:
        """Validate the pipeline and return all errors and warnings found."""
        result = ValidationResult()

        if pipeline is None:
            result.error("Pipeline is null.")
            return result

        if not pipeline.name:
            result.warning("Pipeline has no name — " "checkpointing and logging will use an empty string.")

        mode = self._validate_source(pipeline, result)

        state_key: Optional[str] = None
        prev_was_window = False

        for i, step in enumerate(pipeline.steps):
            stype = _step_type(step)

            # Window must be immediately followed by aggregate
            if prev_was_window and stype != "aggregate":
                result.error(
                    f"WINDOW must be immediately followed by AGGREGATE / GROUP BY. "
                    f"Found '{stype.upper()}' at step[{i}] instead.",
                    step_index=i,
                )

            prev_was_window = stype == "window"

            if stype == "filter":
                pass  # valid in both DATA and STATE

            elif stype == "project":
                pass  # valid in both DATA and STATE

            elif stype == "window":
                if mode == PipelineMode.STATE:
                    result.error(
                        "WINDOW is invalid in STATE mode. "
                        "State has no event time axis — "
                        "window requires events arriving over time. "
                        "Move WINDOW before MAP TO STATE, or use "
                        "MAP TO DATA to cross back to DATA first.",
                        step_index=i,
                    )

            elif stype == "aggregate":
                if mode == PipelineMode.STATE and state_key:
                    if state_key in list(step.aggregate.group_by):
                        result.error(
                            f"AGGREGATE GROUP BY includes the state key "
                            f"'{state_key}' in STATE mode. "
                            f"Each group would contain exactly one row — "
                            f"any aggregation function would be a no-op. "
                            f"Group by a non-key column, or omit GROUP BY "
                            f"for global aggregation across all state entries.",
                            step_index=i,
                        )

            elif stype == "join":
                j = step.join

                if not j.right_source:
                    result.error("JOIN has no right_source.", step_index=i)
                if not j.left_key:
                    result.error("JOIN has no left_key.", step_index=i)
                if not j.right_key:
                    result.error("JOIN has no right_key.", step_index=i)

                # Stream-stream join only valid in DATA streaming pipelines
                if j.right_source_type == SourceType.STREAM:
                    if mode == PipelineMode.STATE:
                        result.error(
                            "Cannot join a STREAM source in STATE mode. "
                            "STREAM joins are only valid in DATA pipelines.",
                            step_index=i,
                        )
                    result.warning(
                        "Stream-stream join requires watermarks on both sides. "
                        "Declare watermarks via with_watermarks().",
                        step_index=i,
                    )

                # KV right side = state lookup, valid in both modes
                if j.right_source_type == SourceType.KV:
                    if j.right_source_mode != PipelineMode.STATE:
                        result.error(
                            "KV join source is always STATE mode.",
                            step_index=i,
                        )

                # FULL join only valid when right side is also STATE
                if j.join_type in ("full", "full_outer"):
                    if mode != PipelineMode.STATE:
                        result.error(
                            "FULL JOIN is only valid in STATE mode. " "Both sides must be bounded state stores.",
                            step_index=i,
                        )
                    if j.right_source_type == SourceType.STREAM:
                        result.error(
                            "FULL JOIN is not valid with a STREAM right source. "
                            "Use INNER or LEFT JOIN for stream-stream joins.",
                            step_index=i,
                        )

                # Join type validity
                if mode == PipelineMode.STATE:
                    valid = {"inner", "left", "left_semi", "full", "full_outer"}
                else:
                    valid = {"inner", "left", "left_semi"}

                if j.join_type not in valid:
                    result.error(
                        f"Unknown join_type: '{j.join_type}'. " f"Valid in {mode.name} mode: {sorted(valid)}.",
                        step_index=i,
                    )

            elif stype == "map_to_state":
                if mode == PipelineMode.STATE:
                    result.error(
                        "MAP TO STATE is invalid — "
                        "pipeline is already in STATE mode. "
                        "Cannot cross DATA→STATE twice without "
                        "MAP TO DATA in between.",
                        step_index=i,
                    )
                else:
                    if not step.map_to_state.key_column:
                        result.error(
                            "MAP TO STATE has no key_column.",
                            step_index=i,
                        )
                    if not step.map_to_state.value_column:
                        result.error(
                            "MAP TO STATE has no value_column.",
                            step_index=i,
                        )
                    mode = PipelineMode.STATE
                    state_key = step.map_to_state.key_column

            elif stype == "map_to_data":
                if mode == PipelineMode.DATA:
                    result.error(
                        "MAP TO DATA is invalid — " "pipeline is already in DATA mode.",
                        step_index=i,
                    )
                else:
                    m = step.map_to_data
                    if m.operator == StreamOperator.STREAM_OPERATOR_UNSPECIFIED:
                        result.error(
                            "MAP TO DATA has no stream operator. " "Valid operators: ISTREAM, DSTREAM, RSTREAM.",
                            step_index=i,
                        )
                    if m.operator == StreamOperator.RSTREAM and not m.schedule:
                        result.warning(
                            "MAP TO DATA RSTREAM without a schedule will emit "
                            "the full relation on every micro-batch. "
                            "Consider adding ON SCHEDULE '0 * * * *' to limit "
                            "emission frequency.",
                            step_index=i,
                        )
                    if m.operator in (StreamOperator.ISTREAM, StreamOperator.DSTREAM) and m.schedule:
                        result.warning(
                            f"MAP TO DATA {m.operator.name} does not use a "
                            f"schedule — the schedule field will be ignored. "
                            f"Schedules are only meaningful for RSTREAM.",
                            step_index=i,
                        )
                    mode = PipelineMode.DATA
                    state_key = None

        # Window as last step — no aggregate follows
        if prev_was_window:
            result.error(
                "WINDOW is the last step but must be followed by "
                "AGGREGATE / GROUP BY. Add a GROUP BY clause after WINDOW."
            )

        self._validate_sink(pipeline, mode, result)

        return result

    def _validate_source(self, pipeline: Pipeline, result: ValidationResult) -> PipelineMode:
        source = pipeline.source

        if not _has_any_source(source):
            result.error("Pipeline has no source declared.")
            return pipeline.mode

        # STREAM source → always DATA
        if _has_stream_source(source):
            if pipeline.mode == PipelineMode.STATE:
                result.error(
                    f"STREAM source '{source.stream.name}' cannot start in "
                    f"STATE mode. Streams always produce DATA — they contain "
                    f"events, not current truth. "
                    f"Use KV or TIMESERIES AS STATE for a state source."
                )
            return PipelineMode.DATA

        # KV source → always STATE
        if _has_kv_source(source):
            if pipeline.mode == PipelineMode.DATA:
                result.error(
                    f"KV source '{source.kv.name}' cannot start in DATA mode. "
                    f"KV stores contain current state — one row per key. "
                    f"Use STREAM or TIMESERIES AS DATA for an event source."
                )
            return PipelineMode.STATE

        # TIMESERIES source → mode declared explicitly in the grammar
        # (AS DATA or AS STATE), already set on pipeline.mode by the compiler
        if _has_timeseries_source(source):
            declared = source.timeseries.mode
            if declared == PipelineMode.PIPELINE_MODE_UNSPECIFIED:
                result.error(
                    f"TIMESERIES source '{source.timeseries.name}' has no mode. "
                    f"Declare AS DATA (event history) or AS STATE (current values)."
                )
                return PipelineMode.DATA
            return declared

        return pipeline.mode

    def _validate_sink(
        self,
        pipeline: Pipeline,
        final_mode: PipelineMode,
        result: ValidationResult,
    ):
        sink = pipeline.sink

        if not _has_any_sink(sink):
            result.error("Pipeline has no sink declared.")
            return

        # STREAM sink → requires DATA mode
        if _has_stream_sink(sink):
            if final_mode == PipelineMode.STATE:
                result.error(
                    f"STREAM sink '{sink.stream.name}' requires DATA mode "
                    f"but pipeline ends in STATE mode. "
                    f"Add MAP TO DATA before the sink, or change the sink "
                    f"to KV or TIMESERIES AS STATE."
                )

        # KV sink → requires STATE mode
        if _has_kv_sink(sink):
            if final_mode == PipelineMode.DATA:
                result.error(
                    f"KV sink '{sink.kv.name}' requires STATE mode "
                    f"but pipeline ends in DATA mode. "
                    f"Add MAP TO STATE before the sink, or change the sink "
                    f"to STREAM or TIMESERIES AS DATA."
                )

        # TIMESERIES sink → mode must match final pipeline mode
        if _has_timeseries_sink(sink):
            declared = sink.timeseries.mode
            if declared != PipelineMode.PIPELINE_MODE_UNSPECIFIED and declared != final_mode:
                result.error(
                    f"TIMESERIES sink '{sink.timeseries.name}' declared as "
                    f"{declared.name} but pipeline ends in {final_mode.name}. "
                    f"The sink mode must match the final pipeline mode."
                )

    def _step_name(self, step: Step) -> str:
        return _step_type(step).upper().replace("_", " ")
