from __future__ import annotations

from nitric.resources.dsl.expression_compiler import (
    ExpressionCompiler,
    BUILTIN_AGG_FUNCTIONS,
)
from nitric.proto.analyticsservice.v1 import (
    Pipeline,
    PipelineMode,
    Source,
    StreamSource,
    TableSource,
    Sink,
    StreamSink,
    TableSink,
    Step,
    Filter,
    Project,
    Window,
    Aggregate,
    AggExpr,
    Join,
    MapToState,
    MapToData,
    ColumnExpr,
    StateOperation,
    StreamOperator,
)


class DeclarativeCompiler:
    """
    Walks the ANTLR parse tree produced by the Declarative grammar
    and builds a Pipeline proto message.

    Each visit method corresponds to a grammar rule alternative
    (the # label in the .g4 file) and appends the appropriate
    proto Step or sets source/sink on the pipeline.

    This class does NOT import the generated ANTLR visitor base class
    directly — it uses duck-typed visit dispatch so it can be tested
    without running ANTLR code generation first.
    """

    def __init__(self):
        self._pipeline = Pipeline()
        self._expr = ExpressionCompiler()
        self._mode = PipelineMode.DATA
        self._state_key = None

    def compile(self, ctx) -> Pipeline:
        self._visit(ctx)
        return self._pipeline

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
        self._set_mode(PipelineMode.DATA)
        self._pipeline.source.CopyFrom(Source(stream=StreamSource(name=self._name(ctx.name()))))

    def _visit_FromKVContext(self, ctx):
        self._set_mode(PipelineMode.STATE)
        self._pipeline.source.CopyFrom(Source(table=TableSource(name=self._name(ctx.name()))))

    def _visit_FromTimeseriesDataContext(self, ctx):
        self._set_mode(PipelineMode.DATA)
        self._pipeline.source.CopyFrom(
            Source(
                table=TableSource(
                    name=self._name(ctx.name()),
                    format="timeseries",
                )
            )
        )

    def _visit_FromTimeseriesStateContext(self, ctx):
        self._set_mode(PipelineMode.STATE)
        self._pipeline.source.CopyFrom(
            Source(
                table=TableSource(
                    name=self._name(ctx.name()),
                    format="timeseries",
                )
            )
        )

    # ── Sinks ─────────────────────────────────────────────────────────────────

    def _visit_IntoStreamContext(self, ctx):
        self._pipeline.sink.CopyFrom(Sink(stream=StreamSink(name=self._name(ctx.name()))))

    def _visit_IntoKVContext(self, ctx):
        self._pipeline.sink.CopyFrom(Sink(table=TableSink(name=self._name(ctx.name()))))

    def _visit_IntoTimeseriesDataContext(self, ctx):
        self._pipeline.sink.CopyFrom(
            Sink(
                table=TableSink(
                    name=self._name(ctx.name()),
                    format="timeseries",
                )
            )
        )

    def _visit_IntoTimeseriesStateContext(self, ctx):
        self._pipeline.sink.CopyFrom(
            Sink(
                table=TableSink(
                    name=self._name(ctx.name()),
                    format="timeseries",
                )
            )
        )

    # ── WHERE ─────────────────────────────────────────────────────────────────

    def _visit_WhereDataContext(self, ctx):
        self._add_filter(ctx.whereClause())

    def _visit_WhereStateContext(self, ctx):
        self._add_filter(ctx.whereClause())

    def _add_filter(self, where_ctx):
        self._pipeline.steps.append(Step(filter=Filter(predicate=self._expr.compile(where_ctx.expression()))))

    # ── SELECT ────────────────────────────────────────────────────────────────

    def _visit_SelectDataContext(self, ctx):
        self._add_project(ctx.selectClause())

    def _visit_SelectStateContext(self, ctx):
        self._add_project(ctx.selectClause())

    def _add_project(self, select_ctx):
        project = Project()

        for sel in select_ctx.selectExpr():
            ctx_type = type(sel).__name__

            if ctx_type == "StarSelectContext":
                # SELECT * — no-op, pass all columns through unchanged
                continue

            if ctx_type == "AliasedSelectContext":
                project.columns.append(
                    ColumnExpr(
                        output_column=self._name(sel.name()),
                        transform=self._expr.compile(sel.expression()),
                    )
                )

            elif ctx_type == "ColumnSelectContext":
                col_name = self._name(sel.name())
                project.columns.append(
                    ColumnExpr(
                        input_column=col_name,
                        output_column=col_name,
                    )
                )

        if project.columns:
            self._pipeline.steps.append(Step(project=project))

    # ── WINDOW + GROUP BY ─────────────────────────────────────────────────────

    def _visit_WindowGroupByContext(self, ctx):
        w = ctx.windowClause()
        self._pipeline.steps.append(
            Step(
                window=Window(
                    duration=self._unquote(w.duration.text),
                    time_column=self._name(w.timeCol),
                    slide=self._unquote(w.slide.text) if w.slide else "",
                )
            )
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
            func = ae.aggFunc().getText().upper()
            if func not in BUILTIN_AGG_FUNCTIONS:
                raise ValueError(f"Unknown aggregation function: '{func}'. " f"Valid: {sorted(BUILTIN_AGG_FUNCTIONS)}")

            if ae.STAR() is not None:
                col = "*"
            elif ae.expression() is not None:
                col = ae.expression().getText()
            else:
                col = "*"

            agg.aggs.append(
                AggExpr(
                    function=func,
                    column=col,
                    output_name=self._name(ae.name()),
                )
            )

        # STATE mode constraint — group_by must not be state key
        if self._mode == PipelineMode.STATE and self._state_key:
            if self._state_key in list(agg.group_by):
                raise ValueError(
                    f"GROUP BY must not include the state key "
                    f"'{self._state_key}' in STATE mode. "
                    f"Each group would contain exactly one row. "
                    f"Group by a non-key column, or omit GROUP BY "
                    f"for global aggregation across all state entries."
                )

        self._pipeline.steps.append(Step(aggregate=agg))

    # ── JOIN ──────────────────────────────────────────────────────────────────

    def _visit_JoinDataContext(self, ctx):
        self._add_join(ctx.joinClause())

    def _visit_JoinStateContext(self, ctx):
        self._add_join(ctx.joinClause())

    def _add_join(self, join_ctx):
        ctx_type = type(join_ctx).__name__

        if ctx_type == "ExplicitJoinContext":
            join_type = join_ctx.joinType().getText().lower().replace("_", " ")
            left_key, right_key = self._extract_join_keys(join_ctx.expression())
            self._pipeline.steps.append(
                Step(
                    join=Join(
                        right_source=self._name(join_ctx.name()),
                        left_key=left_key,
                        right_key=right_key,
                        join_type=join_type,
                    )
                )
            )

        elif ctx_type == "EnrichJoinContext":
            left_key, right_key = self._extract_join_keys(join_ctx.expression())
            self._pipeline.steps.append(
                Step(
                    join=Join(
                        right_source=self._name(join_ctx.name()),
                        left_key=left_key,
                        right_key=right_key,
                        join_type="left",
                    )
                )
            )

    def _extract_join_keys(self, expr_ctx) -> tuple[str, str]:
        """
        Extract left_key and right_key from ON left_col = right_col.
        Only simple column = column equality is valid in ON clauses.
        """
        ctx_type = type(expr_ctx).__name__
        if ctx_type != "CompExprContext":
            raise ValueError("JOIN ON clause must be a simple equality: " "ON left_column = right_column")
        left_ctx = expr_ctx.expression(0)
        right_ctx = expr_ctx.expression(1)

        if type(left_ctx).__name__ != "ColRefContext":
            raise ValueError(f"Left side of JOIN ON must be a column name, " f"got: {left_ctx.getText()}")
        if type(right_ctx).__name__ != "ColRefContext":
            raise ValueError(f"Right side of JOIN ON must be a column name, " f"got: {right_ctx.getText()}")

        return (
            self._name(left_ctx.name()),
            self._name(right_ctx.name()),
        )

    # ── MAP TO STATE ──────────────────────────────────────────────────────────

    def _visit_CrossToStateContext(self, ctx):
        self._state_key = self._name(ctx.keyCol)

        self._pipeline.steps.append(
            Step(
                map_to_state=MapToState(
                    key_column=self._name(ctx.keyCol),
                    value_column=self._name(ctx.valueCol),
                    operation=self._parse_state_op(ctx.stateOp().getText()),
                )
            )
        )

        self._mode = PipelineMode.STATE
        # Continue visiting children (the stateClause* that follow)
        self._visit_children(ctx)

    # ── MAP TO DATA ───────────────────────────────────────────────────────────

    def _visit_CrossToDataContext(self, ctx):
        operator = self._parse_stream_operator(ctx.streamOperator().getText())

        if operator == StreamOperator.RSTREAM:
            schedule = ""
            if ctx.schedule() is not None:
                schedule = self._unquote(ctx.schedule().STRING().getText())
            mtd = MapToData(operator=operator, schedule=schedule)
        else:
            # ISTREAM and DSTREAM never have a schedule
            mtd = MapToData(operator=operator)

        self._pipeline.steps.append(Step(map_to_data=mtd))
        self._mode = PipelineMode.DATA
        self._state_key = None
        self._visit_children(ctx)

    # ── Helpers ───────────────────────────────────────────────────────────────

    def _set_mode(self, mode: PipelineMode):
        self._mode = mode
        self._pipeline.mode = mode

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
            raise ValueError(f"Unknown state operation: '{text}'. " f"Valid: {list(mapping.keys())}")
        return result

    def _parse_stream_operator(self, text: str) -> StreamOperator:
        mapping = {
            "ISTREAM": StreamOperator.ISTREAM,
            "DSTREAM": StreamOperator.DSTREAM,
            "RSTREAM": StreamOperator.RSTREAM,
        }
        result = mapping.get(text.upper())
        if result is None:
            raise ValueError(f"Unknown stream operator: '{text}'. " f"Valid: ISTREAM, DSTREAM, RSTREAM")
        return result
