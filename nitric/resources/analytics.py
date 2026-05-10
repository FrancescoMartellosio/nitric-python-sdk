from __future__ import annotations

from typing import Dict, List, Optional
from grpclib import GRPCError

from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.channel import ChannelManager

from nitric.proto.analyticsservice.v1 import (
    AnalyticsServiceStub,
    ExecuteRequest,
    PlanResponse,
    Pipeline,
    PipelineMode,
    Source,
    Sink,
    StreamSource,
    KvSource,
    TimeSeriesSource,
    StreamSink,
    KvSink,
    TimeSeriesSink,
    Step,
    Filter,
    Project,
    Window,
    Aggregate,
    AggExpr,
    Join,
    MapToState,
    MapToData,
    Expression,
    ComparisonExpr,
    LogicalExpr,
    LogicalOperator,
    FunctionCallExpr,
    ArithmeticExpr,
    ColumnRef,
    Literal,
    ColumnExpr,
    StateOperation,
    StreamOperator,
    Hints,
    EnginePreference,
)

from nitric.proto.resources.v1 import (
    ResourceDeclareRequest,
    ResourceIdentifier,
    ResourceType,
    AnalyticsServiceResource,
)
from nitric.resources.resource import Resource

# =============================================================================
# Expr — fluent expression builder
# =============================================================================
#
# Replaces the old filter("column", "operator", "value") string API.
# Expressions are Python objects — composable, type-safe, and supporting
# all built-in functions as method chains.
#
# Quick reference:
#   col("temperature") > 25.0
#   col("temperature").round(2) > 25.0
#   (col("temperature") > 20.0) & (col("unit") == "C")
#   col("label").upper().as_("label_upper")          ← alias for project()
#   (col("temperature") * 1.8 + 32).as_("temp_f")   ← alias for project()


class Expr:
    """
    Fluent expression builder. Wraps an Expression proto and supports
    Python operators and built-in function method chains so expressions
    can be written naturally without string quoting.
    """

    def __init__(self, proto: Expression):
        """Wrap an Expression proto."""
        self._proto = proto

    # ── Factory methods ───────────────────────────────────────────────────────

    @classmethod
    def col(cls, name: str) -> Expr:
        """Reference a column by name."""
        return cls(Expression(column_ref=ColumnRef(name=name)))

    @classmethod
    def lit(cls, value) -> Expr:
        """Wrap a Python literal (int, float, str, bool) as an expression."""
        return cls(_make_literal(value))

    # ── Comparison operators ──────────────────────────────────────────────────

    def __eq__(self, other) -> Expr:
        return _cmp(self, "==", other)

    def __ne__(self, other) -> Expr:
        return _cmp(self, "!=", other)

    def __gt__(self, other) -> Expr:
        return _cmp(self, ">", other)

    def __lt__(self, other) -> Expr:
        return _cmp(self, "<", other)

    def __ge__(self, other) -> Expr:
        return _cmp(self, ">=", other)

    def __le__(self, other) -> Expr:
        return _cmp(self, "<=", other)

    # ── Logical operators ─────────────────────────────────────────────────────

    def __and__(self, other: Expr) -> Expr:
        return Expr(
            Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.AND,
                    operands=[self._proto, _to_expr(other)._proto],
                )
            )
        )

    def __or__(self, other: Expr) -> Expr:
        return Expr(
            Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.OR,
                    operands=[self._proto, _to_expr(other)._proto],
                )
            )
        )

    def __invert__(self) -> Expr:
        return Expr(
            Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.NOT,
                    operands=[self._proto],
                )
            )
        )

    # ── Arithmetic operators ──────────────────────────────────────────────────

    def __add__(self, other) -> Expr:
        return _arith(self, "+", other)

    def __radd__(self, other) -> Expr:
        return _arith(_to_expr(other), "+", self)

    def __sub__(self, other) -> Expr:
        return _arith(self, "-", other)

    def __rsub__(self, other) -> Expr:
        return _arith(_to_expr(other), "-", self)

    def __mul__(self, other) -> Expr:
        return _arith(self, "*", other)

    def __rmul__(self, other) -> Expr:
        return _arith(_to_expr(other), "*", self)

    def __truediv__(self, other) -> Expr:
        return _arith(self, "/", other)

    def __rtruediv__(self, other) -> Expr:
        return _arith(_to_expr(other), "/", self)

    # ── Built-in scalar functions ─────────────────────────────────────────────

    def upper(self) -> Expr:
        """UPPER(column) — convert string to uppercase."""
        return _fn("UPPER", self)

    def lower(self) -> Expr:
        """LOWER(column) — convert string to lowercase."""
        return _fn("LOWER", self)

    def round(self, decimals: int) -> Expr:
        """ROUND(column, decimals) — round to N decimal places."""
        return _fn("ROUND", self, Expr.lit(float(decimals)))

    def abs(self) -> Expr:
        """ABS(column) — absolute value."""
        return _fn("ABS", self)

    def floor(self) -> Expr:
        """FLOOR(column) — round down to nearest integer."""
        return _fn("FLOOR", self)

    def ceil(self) -> Expr:
        """CEIL(column) — round up to nearest integer."""
        return _fn("CEIL", self)

    def cast(self, type_name: str) -> Expr:
        """CAST(column, type) — cast to a different type (double, long, string, etc.)."""
        return _fn("CAST", self, Expr.lit(type_name))

    def coalesce(self, default) -> Expr:
        """COALESCE(column, default) — return default if column is null."""
        return _fn("COALESCE", self, _to_expr(default))

    def length(self) -> Expr:
        """LENGTH(column) — string length."""
        return _fn("LENGTH", self)

    def substring(self, start: int, length: int) -> Expr:
        """SUBSTRING(column, start, length) — extract substring."""
        return _fn("SUBSTRING", self, Expr.lit(float(start)), Expr.lit(float(length)))

    def concat(self, other) -> Expr:
        """CONCAT(column, other) — concatenate two strings."""
        return _fn("CONCAT", self, _to_expr(other))

    def to_timestamp(self, fmt: str) -> Expr:
        """TO_TIMESTAMP(column, format) — parse string as timestamp."""
        return _fn("TO_TIMESTAMP", self, Expr.lit(fmt))

    def date_format(self, fmt: str) -> Expr:
        """DATE_FORMAT(column, format) — format timestamp as string."""
        return _fn("DATE_FORMAT", self, Expr.lit(fmt))

    def year(self) -> Expr:
        """YEAR(column) — extract year from timestamp."""
        return _fn("YEAR", self)

    def month(self) -> Expr:
        """MONTH(column) — extract month from timestamp."""
        return _fn("MONTH", self)

    def day(self) -> Expr:
        """DAY(column) — extract day from timestamp."""
        return _fn("DAY", self)

    def hour(self) -> Expr:
        """HOUR(column) — extract hour from timestamp."""
        return _fn("HOUR", self)

    def is_null(self) -> Expr:
        """IS NULL — true if column value is null."""
        return _fn("IS_NULL", self)

    def is_not_null(self) -> Expr:
        """IS NOT NULL — true if column value is not null."""
        return _fn("IS_NOT_NULL", self)

    def between(self, lower, upper) -> Expr:
        """
        Sugar for (self >= lower) & (self <= upper).

        Example:
            col("temperature").between(20.0, 30.0)
        """
        return (self >= lower) & (self <= upper)

    # ── Alias — for use in project() ─────────────────────────────────────────

    def as_(self, alias: str) -> _AliasedExpr:
        """
        Assign an output column name. Required for non-trivial expressions
        when used in project().

        Examples:
            col("temperature").round(2).as_("temp_rounded")
            (col("temperature") * 1.8 + 32).as_("temp_f")
            col("label").upper().as_("label_upper")
        """
        return _AliasedExpr(self, alias)

    # ── Internal ──────────────────────────────────────────────────────────────

    def build(self) -> Expression:
        """Return the underlying Expression proto."""
        return self._proto

    def __hash__(self):
        # Required because __eq__ is overridden
        return id(self)

    def __repr__(self) -> str:
        return f"Expr({self._proto})"


class _AliasedExpr:
    """Result of expr.as_('name'). Used by project() to name output columns."""

    def __init__(self, expr: Expr, alias: str):
        self.expr = expr
        self.alias = alias


# ── Expression private helpers ────────────────────────────────────────────────


def _make_literal(value) -> Expression:
    lit = Literal()
    if isinstance(value, bool):
        lit.bool_val = value
    elif isinstance(value, float):
        lit.double_val = value
    elif isinstance(value, int):
        lit.int_val = value
    elif isinstance(value, str):
        lit.string_val = value
    else:
        raise TypeError(
            f"Cannot convert {type(value).__name__} to a literal expression. "
            f"Supported types: int, float, str, bool."
        )
    return Expression(literal=lit)


def _to_expr(value) -> Expr:
    """Convert a raw Python value to an Expr if it is not one already."""
    if isinstance(value, Expr):
        return value
    return Expr(_make_literal(value))


def _cmp(left: Expr, op: str, right) -> Expr:
    return Expr(
        Expression(
            comparison=ComparisonExpr(
                left=left._proto,
                operator=op,
                right=_to_expr(right)._proto,
            )
        )
    )


def _arith(left: Expr, op: str, right) -> Expr:
    return Expr(
        Expression(
            arithmetic=ArithmeticExpr(
                left=left._proto,
                operator=op,
                right=_to_expr(right)._proto,
            )
        )
    )


def _fn(name: str, *args: Expr) -> Expr:
    return Expr(
        Expression(
            function=FunctionCallExpr(
                name=name,
                args=[a._proto for a in args],
            )
        )
    )


# =============================================================================
# Public helpers — imported by users
# =============================================================================


def col(name: str) -> Expr:
    """
    Reference a column by name.

    Returns an Expr that supports Python operators, built-in function chains,
    and .as_() for aliasing in project().

    Examples:
        col("temperature") > 25.0
        col("temperature").round(2).as_("temp_rounded")
        (col("temperature") * 1.8 + 32).as_("temp_f")
        col("label").upper()
        col("room_id").is_not_null()
        (col("temperature") > 20.0) & (col("unit") == "C")
    """
    return Expr.col(name)


def lit(value) -> Expr:
    """
    Wrap a Python literal as an expression.

    Examples:
        lit(25.0)
        lit("celsius")
        lit(True)
    """
    return Expr.lit(value)


def agg(output_name: str, function: str, column: str) -> AggExpr:
    """
    Build an AggExpr for use in aggregate().

    Examples:
        agg("avg_temp",   "AVG",   "temperature")
        agg("room_count", "COUNT", "room_id")
        agg("max_temp",   "MAX",   "temperature")
        agg("total",      "SUM",   "energy_wh")
    """
    return AggExpr(
        output_name=output_name,
        function=function.upper(),
        column=column,
    )


# =============================================================================
# PipelineQuery — fluent builder
# =============================================================================


class PipelineQuery:
    """
    Fluent builder for an analytics pipeline.

    Each method call appends a Step to the pipeline proto and tracks
    the current mode (DATA or STATE) so invalid operations are caught
    immediately with a clear error message rather than at execution time.

    Typical usage:
        job_id = await (
            ref.from_stream("temperature-readings", "hot-rooms")
            .filter(col("temperature") > 25.0)
            .to_stream("hot-rooms-output")
            .execute()
        )
    """

    def __init__(
        self,
        service_name: str,
        pipeline_name: str,
        source: Source,
        mode: PipelineMode,
        stub: AnalyticsServiceStub,
    ):
        """Initialise a pipeline query builder with the given source and mode."""
        self._service_name = service_name
        self._pipeline_name = pipeline_name
        self._source = source
        self._initial_mode = mode  # mode at source — stored for proto
        self._mode = mode  # current mode — changes on boundary crossings
        self._stub = stub
        self._steps: List[Step] = []
        self._sink: Optional[Sink] = None
        self._hints: Hints = Hints()
        self._state_key: Optional[str] = None

    # ── Sink ──────────────────────────────────────────────────────────────────

    def to_stream(self, name: str) -> PipelineQuery:
        """Write output to a stream sink (e.g. Kafka topic). Requires DATA mode."""
        self._sink = Sink(stream=StreamSink(name=name))
        return self

    def to_kv(self, name: str) -> PipelineQuery:
        """Write output to a KV store sink. Requires STATE mode."""
        self._sink = Sink(kv=KvSink(name=name))
        return self

    def to_table(self, name: str, format: str = "") -> PipelineQuery:
        """Write output to a table or file sink."""
        # to_table remains for backwards compatibility with raw table/file sinks
        # Use to_kv() or to_timeseries() for semantic sink types
        self._sink = Sink(kv=KvSink(name=name))
        return self

    def to_timeseries(self, name: str) -> PipelineQuery:
        """Write output to a time series database."""
        self._sink = Sink(
            timeseries=TimeSeriesSink(
                name=name,
                mode=self._mode,  # set at call time — mode may change later if more steps added
            )
        )
        return self

    # ── Hints ─────────────────────────────────────────────────────────────────

    def with_engine(self, engine: EnginePreference) -> PipelineQuery:
        """Force a specific execution engine."""
        self._hints.engine = engine
        return self

    def with_watermarks(self, watermarks: Dict[str, str]) -> PipelineQuery:
        """
        Set watermark delays keyed by time column name.
        Required for window steps and stream-stream joins.

        Example:
            .with_watermarks({"event_time": "30 seconds"})
        """
        for key, value in watermarks.items():
            self._hints.watermarks[key] = value
        return self

    def with_shuffle_partitions(self, n: int) -> PipelineQuery:
        """Set the number of Spark shuffle partitions."""
        self._hints.shuffle_partitions = n
        return self

    def with_checkpoint_base(self, path: str) -> PipelineQuery:
        """Set the base directory for Spark streaming checkpoints."""
        self._hints.checkpoint_base = path
        return self

    def with_partition_column(self, column: str) -> PipelineQuery:
        """Partition Spark batch output by this column."""
        self._hints.partition_column = column
        return self

    def with_starting_offsets(self, offsets: str) -> PipelineQuery:
        """
        Set Kafka starting offsets for batch pipelines.
        'earliest' reads full topic history (default for batch).
        'latest' reads only new messages from now.
        """
        self._hints.starting_offsets = offsets
        return self

    def with_refresh(self, strategy: str, schedule: str = "") -> PipelineQuery:
        """Set the materialized view refresh strategy."""
        self._hints.refresh_strategy = strategy
        self._hints.refresh_schedule = schedule
        return self

    def with_indexes(self, enabled: bool = True) -> PipelineQuery:
        """Create indexes on the materialized view output."""
        self._hints.create_indexes = enabled
        return self

    # ── Steps ─────────────────────────────────────────────────────────────────

    def filter(self, expr: Expr) -> PipelineQuery:
        """
        Keep rows satisfying an expression. Discard the rest.

        Valid in both DATA and STATE mode.
        Chain multiple filter() calls for implicit AND.
        Combine conditions with & (AND), | (OR), ~ (NOT).

        Examples:
            .filter(col("temperature") > 25.0)
            .filter(col("temperature").round(1) > 25.0)
            .filter((col("temperature") > 20.0) & (col("unit") == "C"))
            .filter(col("temperature").between(10.0, 40.0))
            .filter(col("room_id").is_not_null())
            .filter(~(col("status") == "disabled"))
        """
        if not isinstance(expr, Expr):
            raise TypeError(
                f"filter() expects an Expr, got {type(expr).__name__}. "
                f"Use col('name') > value, or col('name') == 'value', etc. "
                f"Example: .filter(col('temperature') > 25.0)"
            )
        self._steps.append(Step(filter=Filter(predicate=expr.build())))
        return self

    def project(self, *columns) -> PipelineQuery:
        """
        Transform, rename, or derive columns row by row. No cross-row logic.

        Valid in both DATA and STATE mode.
        Pass bare col('name') to copy a column unchanged.
        Pass expr.as_('name') for any transformation.

        Examples:
            .project(
                col("room_id"),
                col("temperature").round(2).as_("temp_c"),
                (col("temperature") * 1.8 + 32).as_("temp_f"),
                col("label").upper().as_("label_upper"),
                col("event_time").year().as_("year"),
            )
        """
        project = Project()
        for c in columns:
            if isinstance(c, _AliasedExpr):
                project.columns.append(
                    ColumnExpr(
                        output_column=c.alias,
                        transform=c.expr.build(),
                    )
                )
            elif isinstance(c, Expr):
                # betterproto: check column_ref by testing if name is set
                # instead of HasField("column_ref") which does not exist
                if c._proto.column_ref.name:
                    name = c._proto.column_ref.name
                    project.columns.append(
                        ColumnExpr(
                            input_column=name,
                            output_column=name,
                        )
                    )
                else:
                    raise ValueError(
                        "Expressions in project() must have an alias. "
                        "Use .as_('output_name') to name the output column. "
                        "Example: (col('temperature') * 1.8 + 32).as_('temp_f')"
                    )
            else:
                raise TypeError(
                    f"project() expects Expr or AliasedExpr arguments, "
                    f"got {type(c).__name__}. "
                    f"Use col('name') or expr.as_('alias')."
                )
        self._steps.append(Step(project=project))
        return self

    def window(
        self,
        duration: str,
        time_column: str,
        slide: str = "",
    ) -> PipelineQuery:
        """
        Group events into time-bounded buckets.

        Valid in DATA mode only — state has no event time axis.
        Must be immediately followed by aggregate().
        Declare watermarks via with_watermarks({time_column: delay}).

        Examples:
            .window("1 minute", "event_time")
            .window("10 minutes", "event_time", slide="2 minutes")
        """
        if self._mode == PipelineMode.STATE:
            raise ValueError(
                "window() is invalid in STATE mode. "
                "State has no event time axis — "
                "window requires events arriving over time. "
                "Move window() before map_to_state(), or use "
                "map_to_data() to cross back to DATA first."
            )
        self._steps.append(
            Step(
                window=Window(
                    duration=duration,
                    time_column=time_column,
                    slide=slide,
                )
            )
        )
        return self

    def aggregate(
        self,
        group_by: List[str],
        aggs: List[AggExpr],
    ) -> PipelineQuery:
        """
        Collapse rows into groups and compute summary values per group.

        In STATE mode: group_by must not include the state key column.
        Empty group_by = global aggregation (produces one output row).

        Examples:
            .aggregate(["room_id"], [agg("avg_temp", "AVG", "temperature")])
            .aggregate([], [agg("global_avg", "AVG", "temperature")])
            .aggregate(["window", "room_id"], [
                agg("avg_temp",   "AVG",   "temperature"),
                agg("max_temp",   "MAX",   "temperature"),
                agg("room_count", "COUNT", "room_id"),
            ])
        """
        if self._mode == PipelineMode.STATE and self._state_key:
            if self._state_key in group_by:
                raise ValueError(
                    f"aggregate() group_by must not include the state key "
                    f"'{self._state_key}' in STATE mode. "
                    f"Each group would contain exactly one row — "
                    f"any aggregation function would be a no-op. "
                    f"Group by a non-key column (e.g. 'building_id'), "
                    f"or use an empty group_by for global aggregation "
                    f"across all state entries."
                )
        self._steps.append(Step(aggregate=Aggregate(group_by=group_by, aggs=aggs)))
        return self

    def join(
        self,
        right_source: str,
        left_key: str,
        right_key: str,
        join_type: str = "inner",
    ) -> PipelineQuery:
        """
        Combine the current dataset with another source by matching keys.

        DATA mode: stream-table enrichment or stream-stream join.
        STATE mode: plain table join — no time alignment needed.

        join_type: "inner", "left", or "left_semi"

        Examples:
            .join("room-metadata", "room_id", "id", "left")
            .join("building-state", "building_id", "id", "inner")
        """
        self._steps.append(
            Step(
                join=Join(
                    right_source=right_source,
                    left_key=left_key,
                    right_key=right_key,
                    join_type=join_type,
                )
            )
        )
        return self

    def map_to_state(
        self,
        key_column: str,
        value_column: str,
        operation: StateOperation,
    ) -> PipelineQuery:
        """
        Boundary crossing: DATA → STATE.

        All steps after this operate in STATE mode.
        Invalid if the pipeline is already in STATE mode.

        Examples:
            .map_to_state("room_id", "temperature", StateOperation.REPLACE)
            .map_to_state("room_id", "energy_wh",   StateOperation.INCREMENT)
            .map_to_state("room_id", "temperature", StateOperation.MAXIMUM)
        """
        if self._mode == PipelineMode.STATE:
            raise ValueError(
                "map_to_state() is invalid — pipeline is already in STATE mode. "
                "Cannot cross DATA→STATE twice without map_to_data() in between."
            )
        self._steps.append(
            Step(
                map_to_state=MapToState(
                    key_column=key_column,
                    value_column=value_column,
                    operation=operation,
                )
            )
        )
        self._mode = PipelineMode.STATE
        self._state_key = key_column
        return self

    def map_to_data(
        self,
        operator: StreamOperator,
        schedule: str = "",
    ) -> PipelineQuery:
        """
        Boundary crossing: STATE → DATA using a CQL stream operator.

        IStream (ISTREAM) — emit tuples as they are inserted into the relation.
            Use when you want to react to new or changed state entries.
            Example: room temperature just crossed above threshold.

        DStream (DSTREAM) — emit tuples as they are deleted from the relation.
            Use when you want to react to state entries disappearing.
            Example: room temperature dropped back below threshold.

        RStream (RSTREAM) — emit the full current relation at every evaluation.
            Use when downstream consumers need the complete current picture.
            Optionally pass a cron schedule to limit emission frequency.

        Examples:
            .map_to_data(StreamOperator.ISTREAM)
            .map_to_data(StreamOperator.DSTREAM)
            .map_to_data(StreamOperator.RSTREAM)
            .map_to_data(StreamOperator.RSTREAM, "0 * * * *")
        """
        if self._mode == PipelineMode.DATA:
            raise ValueError("map_to_data() is invalid — pipeline is already in DATA mode.")
        if operator == StreamOperator.STREAM_OPERATOR_UNSPECIFIED:
            raise ValueError(
                "map_to_data() requires a stream operator. " "Use StreamOperator.ISTREAM, DSTREAM, or RSTREAM."
            )
        if operator == StreamOperator.RSTREAM and not schedule:
            import warnings

            warnings.warn(
                "map_to_data(RSTREAM) without a schedule will emit the full "
                "relation on every micro-batch. Pass a cron schedule to limit "
                "emission frequency. "
                "Example: .map_to_data(StreamOperator.RSTREAM, '0 * * * *')",
                stacklevel=2,
            )
        if operator in (StreamOperator.ISTREAM, StreamOperator.DSTREAM) and schedule:
            import warnings

            warnings.warn(
                f"map_to_data({operator.name}) does not use a schedule — " f"the schedule argument will be ignored.",
                stacklevel=2,
            )
            schedule = ""

        self._steps.append(Step(map_to_data=MapToData(operator=operator, schedule=schedule)))
        self._mode = PipelineMode.DATA
        self._state_key = None
        return self

    # ── Terminal operations ───────────────────────────────────────────────────

    async def execute(self) -> str:
        """
        Submit the pipeline for execution and return the job ID.

        For streaming pipelines the job runs indefinitely.
        For batch pipelines this returns after the job completes.
        """
        if self._sink is None:
            raise ValueError("No sink configured. " "Call to_stream(), to_kv(), or to_table() before execute().")
        req = self._build_request()
        try:
            response = await self._stub.execute(req)
            if response.error:
                raise RuntimeError(f"Pipeline execution failed: {response.error}")
            return response.job_id
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    async def plan(self) -> PlanResponse:
        """
        Dry-run: validate the pipeline and return the routing decision.

        Returns a PlanResponse with:
          - selected_engine: which engine would execute this pipeline
          - reason: why that engine was selected
          - physical_plan: step-by-step translation for the selected engine
          - warnings: non-fatal issues
          - error: non-empty if the pipeline would be rejected
        """
        req = self._build_request()
        try:
            return await self._stub.plan(req)
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _build_request(self) -> ExecuteRequest:
        pipeline = Pipeline(
            name=self._pipeline_name,
            mode=self._initial_mode,
            source=self._source,
            steps=self._steps,
            sink=self._sink,
        )
        return ExecuteRequest(
            pipeline=pipeline,
            hints=self._hints,
        )


# =============================================================================
# AnalyticsRef — runtime reference
# =============================================================================


class AnalyticsRef:
    """
    Runtime reference to the analytics service for pipeline submission.

    Obtained via analytics_service("name").allow().
    Provides both the fluent builder API and the declarative DSL API.
    """

    def __init__(self, name: str):
        """Create a runtime reference to the named analytics service."""
        self._name = name
        self._channel = ChannelManager.get_channel()
        self._stub = AnalyticsServiceStub(channel=self._channel)

    # ── Fluent builder entry points ───────────────────────────────────────────

    def from_stream(self, source_name: str, pipeline_name: str) -> PipelineQuery:
        """
        Start a DATA pipeline reading from an event stream (Kafka topic).

        Example:
            await (
                ref.from_stream("temperature_readings", "hot-rooms")
                .filter(col("temperature") > 25.0)
                .to_stream("hot-rooms-output")
                .execute()
            )
        """
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(stream=StreamSource(name=source_name)),
            mode=PipelineMode.DATA,
            stub=self._stub,
        )

    def from_kv(self, source_name: str, pipeline_name: str) -> PipelineQuery:
        """Start a STATE pipeline reading from a KV store."""
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(kv=KvSource(name=source_name)),
            mode=PipelineMode.STATE,
            stub=self._stub,
        )

    def from_table(
        self,
        source_name: str,
        pipeline_name: str,
        mode: PipelineMode = PipelineMode.DATA,
        format: str = "",
    ) -> PipelineQuery:
        """Start a pipeline reading from a table or file (kept for compatibility)."""
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(kv=KvSource(name=source_name)),
            mode=mode,
            stub=self._stub,
        )

    def from_timeseries(
        self,
        source_name: str,
        pipeline_name: str,
        mode: PipelineMode = PipelineMode.DATA,
    ) -> PipelineQuery:
        """Start a pipeline reading from a time series database."""
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(
                timeseries=TimeSeriesSource(
                    name=source_name,
                    mode=mode,
                )
            ),
            mode=mode,
            stub=self._stub,
        )

    # ── Declarative DSL entry points ──────────────────────────────────────────

    async def execute_dsl(
        self,
        source: str,
        hints: Optional[Hints] = None,
    ) -> str:
        """
        Parse and execute a declarative pipeline.

        Accepts either an inline DSL string or a path to a .pipeline file.
        Grammar style is auto-detected — no need to specify.

        Args:
            source: Declarative DSL string or path to a .pipeline file.
            hints:  Optional execution hints (watermarks, engine, etc.)

        Returns:
            Job ID of the started pipeline.

        Raises:
            ParseError: if the DSL has syntax errors.
            ValueError: if the pipeline fails semantic validation.
            RuntimeError: if execution fails.

        Examples:
            # Inline string
            job_id = await ref.execute_dsl('''
                FROM STREAM temperature_readings
                WHERE ROUND(temperature, 1) > 25.0
                  AND UPPER(unit) = 'C'
                INTO STREAM hot_rooms
            ''')

            # With hints
            job_id = await ref.execute_dsl(
                '/pipelines/window_avg.pipeline',
                hints=Hints(watermarks={"event_time": "30 seconds"})
            )
        """
        from nitric.resources.dsl.parser import parse, ParseError

        try:
            pipeline = parse(source)
        except ParseError as e:
            raise RuntimeError("Declarative pipeline parse failed:\n" + "\n".join(e.errors)) from e

        return await self._submit(pipeline, hints)

    async def plan_dsl(
        self,
        source: str,
        hints: Optional[Hints] = None,
    ) -> PlanResponse:
        """
        Parse a declarative pipeline and explain routing without executing.

        Useful for validating a pipeline and understanding which engine
        would be selected before committing to execution.

        Example:
            plan = await ref.plan_dsl('''
                FROM STREAM temperature_readings
                WHERE temperature > 25.0
                INTO STREAM hot_rooms
            ''')
            print(plan.selected_engine)
            print(plan.reason)
            for step in plan.physical_plan:
                print(f"  {step.operation} → {step.physical_translation}")
        """
        from nitric.resources.dsl.parser import parse, ParseError

        try:
            pipeline = parse(source)
        except ParseError as e:
            raise RuntimeError("Declarative pipeline parse failed:\n" + "\n".join(e.errors)) from e

        req = ExecuteRequest(pipeline=pipeline, hints=hints or Hints())
        try:
            return await self._stub.plan(req)
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def pipeline_from_file(self, path: str) -> _DeclarativePipelineBuilder:
        """
        Load a .pipeline file and attach hints before submitting.

        Example:
            job_id = await (
                ref.pipeline_from_file('/pipelines/room_temp.pipeline')
                   .with_watermarks({"event_time": "30 seconds"})
                   .with_engine(EnginePreference.SPARK_STREAMING)
                   .execute()
            )
        """
        return _DeclarativePipelineBuilder(self._stub, path)

    # ── Internal ──────────────────────────────────────────────────────────────

    async def _submit(self, pipeline: Pipeline, hints: Optional[Hints]) -> str:
        req = ExecuteRequest(pipeline=pipeline, hints=hints or Hints())
        try:
            resp = await self._stub.execute(req)
            if resp.error:
                raise RuntimeError(f"Pipeline execution failed: {resp.error}")
            return resp.job_id
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err


# =============================================================================
# _DeclarativePipelineBuilder
# =============================================================================


class _DeclarativePipelineBuilder:
    """
    Wraps a declarative pipeline file or string and lets you attach
    hints before executing. Mirrors the PipelineQuery terminal interface.
    """

    def __init__(self, stub: AnalyticsServiceStub, source: str):
        self._stub = stub
        self._source = source
        self._hints = Hints()

    def with_watermarks(self, watermarks: Dict[str, str]) -> _DeclarativePipelineBuilder:
        for k, v in watermarks.items():
            self._hints.watermarks[k] = v
        return self

    def with_engine(self, engine: EnginePreference) -> _DeclarativePipelineBuilder:
        self._hints.engine = engine
        return self

    def with_shuffle_partitions(self, n: int) -> _DeclarativePipelineBuilder:
        self._hints.shuffle_partitions = n
        return self

    def with_checkpoint_base(self, path: str) -> _DeclarativePipelineBuilder:
        self._hints.checkpoint_base = path
        return self

    def with_starting_offsets(self, offsets: str) -> _DeclarativePipelineBuilder:
        self._hints.starting_offsets = offsets
        return self

    async def execute(self) -> str:
        from nitric.resources.dsl.parser import parse, ParseError

        try:
            pipeline = parse(self._source)
        except ParseError as e:
            raise RuntimeError("Declarative pipeline parse failed:\n" + "\n".join(e.errors)) from e

        req = ExecuteRequest(pipeline=pipeline, hints=self._hints)
        try:
            resp = await self._stub.execute(req)
            if resp.error:
                raise RuntimeError(f"Pipeline execution failed: {resp.error}")
            return resp.job_id
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    async def plan(self) -> PlanResponse:
        from nitric.resources.dsl.parser import parse, ParseError

        try:
            pipeline = parse(self._source)
        except ParseError as e:
            raise RuntimeError("Declarative pipeline parse failed:\n" + "\n".join(e.errors)) from e

        req = ExecuteRequest(pipeline=pipeline, hints=self._hints)
        try:
            return await self._stub.plan(req)
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err


# =============================================================================
# Resource
# =============================================================================


class AnalyticsService(Resource):
    """An AnalyticsService resource used for deployment."""

    def __init__(self, name: str):
        """Declare an analytics service resource with the given name."""
        super().__init__(name)

    async def _register(self) -> None:
        try:
            await self._resources_stub.declare(
                resource_declare_request=ResourceDeclareRequest(
                    id=self._to_resource_id(),
                    analytics_service=AnalyticsServiceResource(),
                )
            )
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _to_resource_id(self) -> ResourceIdentifier:
        return ResourceIdentifier(
            name=self.name,
            type=ResourceType.AnalyticsService,
        )

    def allow(self) -> AnalyticsRef:
        """Return a runtime reference for submitting pipelines."""
        return AnalyticsRef(self.name)


# =============================================================================
# Entry point
# =============================================================================


def analytics_service(name: str) -> AnalyticsService:
    """
    Define an Analytics Service resource in a Nitric application.

    Example:
        from nitric.resources.analytics import analytics_service, col, agg
        svc = analytics_service("my-analytics")
        ref = svc.allow()

        # Fluent
        job_id = await (
            ref.from_stream("temperature_readings", "hot-rooms")
            .filter(col("temperature") > 25.0)
            .to_stream("hot-rooms")
            .execute()
        )

        # Declarative
        job_id = await ref.execute_dsl('''
            FROM STREAM temperature_readings
            WHERE temperature > 25.0
            INTO STREAM hot_rooms
        ''')
    """
    return Nitric._create_resource(AnalyticsService, name)
