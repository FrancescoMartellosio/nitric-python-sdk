from __future__ import annotations

from typing import Dict, List, Optional

from nitric.resources.dsl.expression_compiler import (
    ExpressionCompiler,
    BUILTIN_AGG_FUNCTIONS,
)
from nitric.proto.analyticsservice.v1 import (
    Subgraph,
    Node,
    Edge,
    Schedule,
    PipelineMode,
    Source,
    StreamSource,
    KvSource,
    Sink,
    StreamSink,
    KvSink,
    Step,
    Filter,
    Derive,
    DeriveExpr,
    Select,
    Window,
    Aggregate,
    AggExpr,
    AggFunc,
    Join,
    JoinType,
    MapToState,
    MapToData,
    MapToDataStrategy,
    StateOperation,
)

_AGG_FUNCS: Dict[str, AggFunc] = {
    "SUM": AggFunc.SUM,
    "COUNT": AggFunc.COUNT,
    "AVG": AggFunc.AVG,
    "MAX": AggFunc.MAX,
    "MIN": AggFunc.MIN,
    "LAST": AggFunc.LAST,
    "FIRST": AggFunc.FIRST,
    "STDDEV": AggFunc.STDDEV,
    "VARIANCE": AggFunc.VARIANCE,
    "COLLECT_LIST": AggFunc.COLLECT_LIST,
}

_JOIN_TYPES: Dict[str, JoinType] = {
    "inner": JoinType.INNER,
    "left": JoinType.LEFT,
    "left_semi": JoinType.LEFT_SEMI,
    "full": JoinType.FULL,
    "full_outer": JoinType.FULL_OUTER,
}


class DeclarativeCompiler:
    """
    Walks the ANTLR parse tree produced by the Declarative grammar
    and builds a Subgraph proto message (DAG of Nodes and Edges).

    The DSL grammar describes a linear pipeline, which is compiled
    into a sequential chain of nodes: one source node, zero or more
    step nodes, and one sink node.

    Each node is assigned a generated ID. Edges connect consecutive
    nodes in declaration order.
    """

    def __init__(self):
        self._nodes: List[Node] = []
        self._edges: List[Edge] = []
        self._counters: Dict[str, int] = {}
        self._expr = ExpressionCompiler()
        self._mode = PipelineMode.DATA
        self._state_key: Optional[str] = None
        self._current_id: Optional[str] = None  # ID of the last node added

    def compile(self, ctx) -> Subgraph:
        """Walk the parse tree rooted at ctx and return the compiled Subgraph."""
        self._visit(ctx)
        return Subgraph(name="", nodes=self._nodes, edges=self._edges)

    # ── ID generation ─────────────────────────────────────────────────────────

    def _new_id(self, prefix: str) -> str:
        n = self._counters.get(prefix, 0)
        self._counters[prefix] = n + 1
        return f"{prefix}-{n}"

    def _append_node(self, node: Node) -> None:
        """Add a node and connect it to the previous node via an edge."""
        if self._current_id is not None:
            self._edges.append(Edge(from_node=self._current_id, to_node=node.id, mode=self._mode))
        self._nodes.append(node)
        self._current_id = node.id

    def _append_step(self, prefix: str, step: Step) -> None:
        node_id = self._new_id(prefix)
        step.id = node_id
        self._append_node(Node(id=node_id, step=step))

    # ── Dispatch ──────────────────────────────────────────────────────────────

    def _visit(self, ctx):
        if ctx is None:
            return
        ctx_type = type(ctx).__name__
        method = getattr(self, f"_visit_{ctx_type}", None)
        if method:
            method(ctx)
        else:
            self._visit_children(ctx)

    def _visit_children(self, ctx):
        if hasattr(ctx, "getChildCount"):
            for i in range(ctx.getChildCount()):
                child = ctx.getChild(i)
                if hasattr(child, "getChildCount"):
                    self._visit(child)

    # ── Pipeline ──────────────────────────────────────────────────────────────

    def _visit_PipelineContext(self, ctx):
        self._visit_children(ctx)

    def _visit_DataPipelineContext(self, ctx):
        self._visit_children(ctx)

    def _visit_StatePipelineContext(self, ctx):
        self._visit_children(ctx)

    # ── Sources ───────────────────────────────────────────────────────────────

    def _visit_FromStreamContext(self, ctx):
        self._mode = PipelineMode.DATA
        node_id = self._new_id("source")
        self._nodes.append(
            Node(
                id=node_id,
                source=Source(
                    stream=StreamSource(topic=self._name(ctx.name())),
                    mode=PipelineMode.DATA,
                ),
            )
        )
        self._current_id = node_id

    def _visit_FromKVContext(self, ctx):
        self._mode = PipelineMode.STATE
        node_id = self._new_id("source")
        self._nodes.append(
            Node(
                id=node_id,
                source=Source(
                    kv=KvSource(store=self._name(ctx.name())),
                    mode=PipelineMode.STATE,
                ),
            )
        )
        self._current_id = node_id

    # ── Sinks ─────────────────────────────────────────────────────────────────

    def _visit_IntoStreamContext(self, ctx):
        node_id = self._new_id("sink")
        self._append_node(
            Node(
                id=node_id,
                sink=Sink(stream=StreamSink(topic=self._name(ctx.name())), mode=self._mode),
            )
        )

    def _visit_IntoKVContext(self, ctx):
        node_id = self._new_id("sink")
        self._append_node(
            Node(
                id=node_id,
                sink=Sink(kv=KvSink(store=self._name(ctx.name())), mode=self._mode),
            )
        )

    # ── WHERE ─────────────────────────────────────────────────────────────────

    def _visit_WhereDataContext(self, ctx):
        self._add_filter(ctx.whereClause())

    def _visit_WhereStateContext(self, ctx):
        self._add_filter(ctx.whereClause())

    def _add_filter(self, where_ctx):
        self._append_step(
            "filter",
            Step(filter=Filter(predicate=self._expr.compile(where_ctx.expression()))),
        )

    # ── SELECT ────────────────────────────────────────────────────────────────

    def _visit_SelectDataContext(self, ctx):
        self._add_select(ctx.selectClause())

    def _visit_SelectStateContext(self, ctx):
        self._add_select(ctx.selectClause())

    def _add_select(self, select_ctx):
        plain_cols: List[str] = []
        derive_exprs: List[DeriveExpr] = []

        for sel in select_ctx.selectExpr():
            ctx_type = type(sel).__name__

            if ctx_type == "StarSelectContext":
                continue  # SELECT * — pass all columns through

            if ctx_type == "AliasedSelectContext":
                derive_exprs.append(
                    DeriveExpr(
                        transform=self._expr.compile(sel.expression()),
                        output_column=self._name(sel.name()),
                    )
                )

            elif ctx_type == "ColumnSelectContext":
                plain_cols.append(self._name(sel.name()))

        if plain_cols:
            self._append_step("select", Step(select=Select(columns=plain_cols)))

        if derive_exprs:
            self._append_step("derive", Step(derive=Derive(expressions=derive_exprs)))

    # ── WINDOW + GROUP BY ─────────────────────────────────────────────────────

    def _visit_WindowGroupByContext(self, ctx):
        w = ctx.windowClause()
        self._append_step(
            "window",
            Step(
                window=Window(
                    duration=self._unquote(w.duration.text),
                    time_column=self._name(w.timeCol),
                    slide=self._unquote(w.slide.text) if w.slide else "",
                )
            ),
        )
        self._add_group_by(ctx.groupByClause())

    def _visit_GroupByDataContext(self, ctx):
        self._add_group_by(ctx.groupByClause())

    def _visit_GroupByStateContext(self, ctx):
        self._add_group_by(ctx.groupByClause())

    def _add_group_by(self, group_ctx):
        agg = Aggregate()

        for name_ctx in group_ctx.name():
            agg.group_by.append(self._name(name_ctx))

        for ae in group_ctx.aggExpr():
            func_text = ae.aggFunc().getText().upper()
            if func_text not in BUILTIN_AGG_FUNCTIONS:
                raise ValueError(
                    f"Unknown aggregation function: '{func_text}'. " f"Valid: {sorted(BUILTIN_AGG_FUNCTIONS)}"
                )
            func = _AGG_FUNCS[func_text]

            if ae.STAR() is not None:
                from nitric.proto.analyticsservice.v1 import Expression, ColumnRef

                input_expr = Expression(column_ref=ColumnRef(name="*"))
            elif ae.expression() is not None:
                input_expr = self._expr.compile(ae.expression())
            else:
                from nitric.proto.analyticsservice.v1 import Expression, ColumnRef

                input_expr = Expression(column_ref=ColumnRef(name="*"))

            agg.aggs.append(
                AggExpr(
                    function=func,
                    input=input_expr,
                    output_name=self._name(ae.name()),
                )
            )

        if self._mode == PipelineMode.STATE and self._state_key:
            if self._state_key in list(agg.group_by):
                raise ValueError(f"GROUP BY must not include the state key " f"'{self._state_key}' in STATE mode.")

        self._append_step("aggregate", Step(aggregate=agg))

    # ── JOIN ──────────────────────────────────────────────────────────────────

    def _visit_JoinDataContext(self, ctx):
        self._add_join(ctx.joinClause())

    def _visit_JoinStateContext(self, ctx):
        self._add_join(ctx.joinClause())

    def _add_join(self, join_ctx):
        ctx_type = type(join_ctx).__name__

        source_ref = join_ctx.sourceRef()
        right_source_id, right_mode = self._add_join_source(source_ref)

        if ctx_type == "ExplicitJoinContext":
            jt_text = join_ctx.joinType().getText().lower()
            jt = _JOIN_TYPES.get(jt_text, JoinType.INNER)
        else:  # EnrichJoinContext
            jt = JoinType.LEFT

        left_key, right_key = self._extract_join_keys(join_ctx.expression())

        left_id = self._current_id
        join_id = self._new_id("join")
        step = Step(
            id=join_id,
            join=Join(
                left_key=left_key,
                right_key=right_key,
                join_type=jt,
                right_source_mode=right_mode,
            ),
        )
        self._nodes.append(Node(id=join_id, step=step))
        self._edges.append(Edge(from_node=left_id, to_node=join_id, mode=self._mode))
        self._edges.append(Edge(from_node=right_source_id, to_node=join_id, mode=right_mode))
        self._current_id = join_id

    def _add_join_source(self, source_ref_ctx) -> tuple:
        """Create a source node for the right side of a join; return (node_id, mode)."""
        ctx_type = type(source_ref_ctx).__name__

        if ctx_type == "JoinStreamContext":
            node_id = self._new_id("source")
            self._nodes.append(
                Node(
                    id=node_id,
                    source=Source(
                        stream=StreamSource(topic=self._name(source_ref_ctx.name())),
                        mode=PipelineMode.DATA,
                    ),
                )
            )
            return node_id, PipelineMode.DATA

        if ctx_type == "JoinKVContext":
            node_id = self._new_id("source")
            self._nodes.append(
                Node(
                    id=node_id,
                    source=Source(
                        kv=KvSource(store=self._name(source_ref_ctx.name())),
                        mode=PipelineMode.STATE,
                    ),
                )
            )
            return node_id, PipelineMode.STATE

        raise ValueError(f"Unknown join source ref type: {ctx_type}")

    def _extract_join_keys(self, expr_ctx) -> tuple:
        ctx_type = type(expr_ctx).__name__
        if ctx_type != "CompExprContext":
            raise ValueError("JOIN ON clause must be a simple equality: ON left_col = right_col")
        left_ctx = expr_ctx.expression(0)
        right_ctx = expr_ctx.expression(1)
        if type(left_ctx).__name__ != "ColRefContext":
            raise ValueError(f"Left side of JOIN ON must be a column name, got: {left_ctx.getText()}")
        if type(right_ctx).__name__ != "ColRefContext":
            raise ValueError(f"Right side of JOIN ON must be a column name, got: {right_ctx.getText()}")
        return self._name(left_ctx.name()), self._name(right_ctx.name())

    # ── MAP TO STATE ──────────────────────────────────────────────────────────

    def _visit_CrossToStateContext(self, ctx):
        self._state_key = self._name(ctx.keyCol)
        self._append_step(
            "map-to-state",
            Step(
                map_to_state=MapToState(
                    key_column=self._name(ctx.keyCol),
                    value_column=self._name(ctx.valueCol),
                    operation=self._parse_state_op(ctx.stateOp().getText()),
                )
            ),
        )
        self._mode = PipelineMode.STATE
        self._visit_children(ctx)

    # ── MAP TO DATA ───────────────────────────────────────────────────────────

    def _visit_CrossToDataContext(self, ctx):
        strategy = self._parse_map_to_data_strategy(ctx.streamOperator().getText())

        schedule = Schedule()
        if ctx.schedule is not None:
            sched_str = self._unquote(ctx.schedule.text)
            parts = sched_str.strip().split()
            if len(parts) == 5:
                schedule = Schedule(cron=sched_str)
            else:
                schedule = Schedule(interval=sched_str)
            # RSTREAM with a schedule becomes PERIODIC
            if strategy == MapToDataStrategy.SNAPSHOT:
                strategy = MapToDataStrategy.PERIODIC

        self._append_step(
            "map-to-data",
            Step(map_to_data=MapToData(strategy=strategy, schedule=schedule)),
        )
        self._mode = PipelineMode.DATA
        self._state_key = None
        self._visit_children(ctx)

    # ── Helpers ───────────────────────────────────────────────────────────────

    def _name(self, ctx) -> str:
        return self._unquote(ctx.getText())

    def _unquote(self, s: str) -> str:
        if s and len(s) >= 2 and s[0] in ('"', "'") and s[-1] == s[0]:
            return s[1:-1]
        return s

    def _parse_state_op(self, text: str) -> StateOperation:
        mapping = {
            "REPLACE": StateOperation.REPLACE,
            "INCREMENT": StateOperation.INCREMENT,
            "DECREMENT": StateOperation.DECREMENT,
            "MAXIMUM": StateOperation.MAXIMUM,
            "MINIMUM": StateOperation.MINIMUM,
            "COLLECT": StateOperation.COLLECT,
        }
        result = mapping.get(text.upper())
        if result is None:
            raise ValueError(f"Unknown state operation: '{text}'. Valid: {list(mapping)}")
        return result

    def _parse_map_to_data_strategy(self, text: str) -> MapToDataStrategy:
        # DSL still uses ISTREAM/DSTREAM/RSTREAM keywords — map to new strategies
        mapping = {
            "ISTREAM": MapToDataStrategy.CDC,
            "DSTREAM": MapToDataStrategy.CDC,
            "RSTREAM": MapToDataStrategy.SNAPSHOT,
            # Allow the new strategy names directly too
            "SNAPSHOT": MapToDataStrategy.SNAPSHOT,
            "CDC": MapToDataStrategy.CDC,
            "PERIODIC": MapToDataStrategy.PERIODIC,
        }
        result = mapping.get(text.upper())
        if result is None:
            raise ValueError(
                f"Unknown stream operator: '{text}'. " f"Valid: ISTREAM, DSTREAM, RSTREAM (or SNAPSHOT, CDC, PERIODIC)"
            )
        return result
