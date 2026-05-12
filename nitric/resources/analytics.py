from __future__ import annotations

from typing import Any, Dict, List, Optional

_UNSET: Any = object()  # sentinel to distinguish "not provided" from None
from grpclib import GRPCError

from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.channel import ChannelManager

from nitric.proto.analyticsservice.v1 import (
    AnalyticsServiceStub,
    ExecuteRequest,
    PlanResponse,
    Subgraph,
    Node,
    Edge,
    Schedule,
    PipelineMode,
    Source,
    Sink,
    StreamSource,
    KvSource,
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
    Expression,
    ComparisonExpr,
    CompareOp,
    LogicalExpr,
    LogicalOperator,
    FunctionCallExpr,
    ArithmeticExpr,
    ArithmeticOp,
    BetweenExpr,
    IsNullExpr,
    ColumnRef,
    Literal,
    StateOperation,
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
# Operator lookup tables
# =============================================================================

_COMPARE_OPS: Dict[str, CompareOp] = {
    "==": CompareOp.EQ,
    "!=": CompareOp.NEQ,
    ">": CompareOp.GT,
    "<": CompareOp.LT,
    ">=": CompareOp.GTE,
    "<=": CompareOp.LTE,
}

_ARITH_OPS: Dict[str, ArithmeticOp] = {
    "+": ArithmeticOp.ADD,
    "-": ArithmeticOp.SUBTRACT,
    "*": ArithmeticOp.MULTIPLY,
    "/": ArithmeticOp.DIVIDE,
}

_JOIN_TYPES: Dict[str, JoinType] = {
    "inner": JoinType.INNER,
    "left": JoinType.LEFT,
    "left_semi": JoinType.LEFT_SEMI,
    "full": JoinType.FULL,
    "full_outer": JoinType.FULL_OUTER,
}

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

_MAP_TO_DATA_STRATEGIES: Dict[str, MapToDataStrategy] = {
    "SNAPSHOT": MapToDataStrategy.SNAPSHOT,
    "CDC": MapToDataStrategy.CDC,
    "PERIODIC": MapToDataStrategy.PERIODIC,
}


# =============================================================================
# Expr — fluent expression builder
# =============================================================================
#
# Wraps an Expression proto and supports Python operators and built-in
# function method chains so expressions can be written naturally.
#
# Quick reference:
#   col("temperature") > 25.0
#   col("temperature").round(2) > 25.0
#   (col("temperature") > 20.0) & (col("unit") == "C")
#   col("label").upper().as_("label_upper")
#   (col("temperature") * 1.8 + 32).as_("temp_f")
#   col("temperature").between(20.0, 30.0)
#   col("room_id").is_not_null()


class Expr:
    """
    Fluent expression builder. Wraps an Expression proto and supports
    Python operators and built-in function method chains so expressions
    can be written naturally without string quoting.
    """

    def __init__(self, proto: Expression):
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
        return Expr(Expression(is_null=IsNullExpr(operand=self._proto, negated=False)))

    def is_not_null(self) -> Expr:
        """IS NOT NULL — true if column value is not null."""
        return Expr(Expression(is_null=IsNullExpr(operand=self._proto, negated=True)))

    def between(self, lower, upper) -> Expr:
        """
        BETWEEN lower AND upper — true if value is in [lower, upper].

        Example:
            col("temperature").between(20.0, 30.0)
        """
        return Expr(
            Expression(
                between=BetweenExpr(
                    value=self._proto,
                    lower=_to_expr(lower)._proto,
                    upper=_to_expr(upper)._proto,
                )
            )
        )

    # ── Alias — for use in derive() ───────────────────────────────────────────

    def as_(self, alias: str) -> _AliasedExpr:
        """
        Assign an output column name. Required for non-trivial expressions
        when used in derive().

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
        return id(self)

    def __repr__(self) -> str:
        return f"Expr({self._proto})"


class _AliasedExpr:
    """Result of expr.as_('name'). Used by derive() to name output columns."""

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
    if isinstance(value, Expr):
        return value
    return Expr(_make_literal(value))


def _cmp(left: Expr, op: str, right) -> Expr:
    return Expr(
        Expression(
            comparison=ComparisonExpr(
                left=left._proto,
                operator=_COMPARE_OPS[op],
                right=_to_expr(right)._proto,
            )
        )
    )


def _arith(left: Expr, op: str, right) -> Expr:
    return Expr(
        Expression(
            arithmetic=ArithmeticExpr(
                left=left._proto,
                operator=_ARITH_OPS[op],
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


def _parse_schedule(s: str) -> Schedule:
    """Parse a schedule string into a Schedule proto (cron or interval)."""
    parts = s.strip().split()
    if len(parts) == 5:
        return Schedule(cron=s)
    return Schedule(interval=s)


# =============================================================================
# Public helpers — imported by users
# =============================================================================


def col(name: str) -> Expr:
    """
    Reference a column by name.

    Returns an Expr that supports Python operators, built-in function chains,
    and .as_() for aliasing in derive().

    Examples:
        col("temperature") > 25.0
        col("temperature").round(2).as_("temp_rounded")
        (col("temperature") * 1.8 + 32).as_("temp_f")
        col("label").upper()
        col("room_id").is_not_null()
        (col("temperature") > 20.0) & (col("unit") == "C")
        col("temperature").between(20.0, 30.0)
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


def agg(
    output_name: str,
    function: "AggFunc | str",
    column: "Expr | str",
) -> AggExpr:
    """
    Build an AggExpr for use in aggregate().

    function: AggFunc enum or string ("AVG", "SUM", "COUNT", etc.)
    column:   col() expression or column name string

    Examples:
        agg("avg_temp",   AggFunc.AVG,   col("temperature"))
        agg("room_count", AggFunc.COUNT, col("room_id"))
        agg("max_temp",   AggFunc.MAX,   col("temperature"))
        agg("total",      AggFunc.SUM,   col("energy_wh"))
        agg("avg_temp",   "AVG",         "temperature")   # string shorthand
    """
    if isinstance(function, str):
        func = _AGG_FUNCS.get(function.upper())
        if func is None:
            raise ValueError(f"Unknown aggregation function: '{function}'. " f"Valid: {sorted(_AGG_FUNCS)}")
    else:
        func = function

    if isinstance(column, str):
        input_expr = Expression(column_ref=ColumnRef(name=column))
    else:
        input_expr = column.build()

    return AggExpr(output_name=output_name, function=func, input=input_expr)


# =============================================================================
# SubgraphBuilder — top-level graph builder
# =============================================================================


class SubgraphBuilder:
    """
    Builds a Subgraph — a DAG of Nodes connected by Edges.

    Obtain via AnalyticsRef.subgraph("name").
    Add source nodes via source_stream() or source_kv(), which return
    a NodeBuilder for chaining downstream operations.

    Example:
        graph = ref.subgraph("hot-rooms")
        await (
            graph.source_stream("temperature-readings")
            .filter(col("temperature") > 25.0)
            .to_stream("hot-rooms-output")
            .execute()
        )

        # Fanout to two sinks
        graph = ref.subgraph("fanout")
        filtered = graph.source_stream("input").filter(col("temperature") > 25.0)
        filtered.to_stream("hot-output")
        filtered.to_kv("hot-state")
        await graph.execute()
    """

    def __init__(self, name: str, stub: AnalyticsServiceStub):
        self._name = name
        self._stub = stub
        self._nodes: List[Node] = []
        self._edges: List[Edge] = []
        self._counters: Dict[str, int] = {}
        self._hints: Hints = Hints()

    # ── Internal helpers ──────────────────────────────────────────────────────

    def _new_id(self, prefix: str) -> str:
        n = self._counters.get(prefix, 0)
        self._counters[prefix] = n + 1
        return f"{prefix}-{n}"

    def _add_node(self, node: Node) -> None:
        self._nodes.append(node)

    def _add_edge(self, from_id: str, to_id: str, mode: PipelineMode) -> None:
        self._edges.append(Edge(from_node=from_id, to_node=to_id, mode=mode))

    # ── Source entry points ───────────────────────────────────────────────────

    def source_stream(self, topic: str) -> NodeBuilder:
        """
        Add a stream source node (Kafka topic) in DATA mode.

        Example:
            graph.source_stream("temperature-readings")
        """
        node_id = self._new_id("source")
        self._add_node(
            Node(
                id=node_id,
                source=Source(stream=StreamSource(topic=topic), mode=PipelineMode.DATA),
            )
        )
        return NodeBuilder(self, node_id, PipelineMode.DATA)

    def source_kv(self, store: str) -> NodeBuilder:
        """
        Add a KV source node in STATE mode.

        Example:
            graph.source_kv("room-metadata")
        """
        node_id = self._new_id("source")
        self._add_node(
            Node(
                id=node_id,
                source=Source(kv=KvSource(store=store), mode=PipelineMode.STATE),
            )
        )
        return NodeBuilder(self, node_id, PipelineMode.STATE)

    # ── Hints ─────────────────────────────────────────────────────────────────

    def with_engine(self, engine: EnginePreference) -> SubgraphBuilder:
        """Force a specific execution engine."""
        self._hints.engine = engine
        return self

    def with_watermarks(self, watermarks: Dict[str, str]) -> SubgraphBuilder:
        """
        Set watermark delays keyed by time column name.
        Required for window steps and stream-stream joins.

        Example:
            .with_watermarks({"event_time": "30 seconds"})
        """
        for k, v in watermarks.items():
            self._hints.watermarks[k] = v
        return self

    def with_shuffle_partitions(self, n: int) -> SubgraphBuilder:
        """Set the number of Spark shuffle partitions."""
        self._hints.shuffle_partitions = n
        return self

    def with_checkpoint_base(self, path: str) -> SubgraphBuilder:
        """Set the base directory for Spark streaming checkpoints."""
        self._hints.checkpoint_base = path
        return self

    def with_partition_column(self, column: str) -> SubgraphBuilder:
        """Partition Spark batch output by this column."""
        self._hints.partition_column = column
        return self

    def with_starting_offsets(self, offsets: str) -> SubgraphBuilder:
        """
        Set Kafka starting offsets for batch pipelines.
        'earliest' reads full topic history (default for batch).
        'latest' reads only new messages from now.
        """
        self._hints.starting_offsets = offsets
        return self

    def with_refresh(self, strategy: str, schedule: str = "") -> SubgraphBuilder:
        """Set the materialized view refresh strategy."""
        self._hints.refresh_strategy = strategy
        self._hints.refresh_schedule = schedule
        return self

    def with_indexes(self, enabled: bool = True) -> SubgraphBuilder:
        """Create indexes on the materialized view output."""
        self._hints.create_indexes = enabled
        return self

    # ── Terminal operations ───────────────────────────────────────────────────

    def _build(self) -> Subgraph:
        return Subgraph(name=self._name, nodes=self._nodes, edges=self._edges)

    async def execute(self) -> str:
        """
        Submit the subgraph for execution and return the job ID.

        For streaming subgraphs the job runs indefinitely.
        For batch subgraphs this returns after the job completes.
        """
        req = ExecuteRequest(subgraph=self._build(), hints=self._hints)
        try:
            response = await self._stub.execute(req)
            if response.error:
                raise RuntimeError(f"Pipeline execution failed: {response.error}")
            return response.job_id
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    async def plan(self) -> PlanResponse:
        """
        Dry-run: validate the subgraph and return the routing decision.

        Returns a PlanResponse with:
          - selected_engine: which engine would execute this subgraph
          - reason: why that engine was selected
          - physical_plan: step-by-step translation for the selected engine
          - warnings: non-fatal issues
          - error: non-empty if the subgraph would be rejected
        """
        req = ExecuteRequest(subgraph=self._build(), hints=self._hints)
        try:
            return await self._stub.plan(req)
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err


# =============================================================================
# NodeBuilder — fluent node chaining
# =============================================================================


class NodeBuilder:
    """
    Represents a node in the dataflow graph.

    Each step method creates a new downstream Node + Edge in the parent
    SubgraphBuilder and returns a new NodeBuilder targeting that node,
    allowing unlimited method chaining.

    Sink methods (to_stream, to_kv) return the parent SubgraphBuilder
    so you can call .execute() or add more source branches.
    """

    def __init__(
        self,
        graph: SubgraphBuilder,
        node_id: str,
        mode: PipelineMode,
        state_key: Optional[str] = None,
    ):
        self._graph = graph
        self._node_id = node_id
        self._mode = mode
        self._state_key = state_key

    def _step_node(
        self,
        prefix: str,
        step: Step,
        out_mode: Optional[PipelineMode] = None,
        out_state_key: Any = _UNSET,
    ) -> NodeBuilder:
        """Create a single-parent step node downstream of this node."""
        node_id = self._graph._new_id(prefix)
        step.id = node_id
        self._graph._add_node(Node(id=node_id, step=step))
        self._graph._add_edge(self._node_id, node_id, self._mode)
        return NodeBuilder(
            self._graph,
            node_id,
            out_mode if out_mode is not None else self._mode,
            self._state_key if out_state_key is _UNSET else out_state_key,
        )

    # ── Steps ─────────────────────────────────────────────────────────────────

    def filter(self, expr: Expr) -> NodeBuilder:
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
                f"Use col('name') > value, or col('name') == 'value', etc."
            )
        return self._step_node("filter", Step(filter=Filter(predicate=expr.build())))

    def derive(self, *exprs: _AliasedExpr) -> NodeBuilder:
        """
        Compute new or transformed columns row by row. No cross-row logic.

        Valid in both DATA and STATE mode.
        Every argument must have an alias — use .as_('name').

        Examples:
            .derive(
                col("temperature").round(2).as_("temp_c"),
                (col("temperature") * 1.8 + 32).as_("temp_f"),
                col("label").upper().as_("label_upper"),
                col("event_time").year().as_("year"),
            )
        """
        derive_exprs = []
        for e in exprs:
            if not isinstance(e, _AliasedExpr):
                raise TypeError(
                    f"derive() expects AliasedExpr arguments (use expr.as_('name')), " f"got {type(e).__name__}."
                )
            derive_exprs.append(DeriveExpr(transform=e.expr.build(), output_column=e.alias))
        return self._step_node("derive", Step(derive=Derive(expressions=derive_exprs)))

    def select(self, *columns: str) -> NodeBuilder:
        """
        Project to a specific subset of columns by name.

        Valid in both DATA and STATE mode.
        Use derive() instead for transformations or renaming.

        Examples:
            .select("room_id", "temperature", "event_time")
            .select("key", "value")
        """
        return self._step_node("select", Step(select=Select(columns=list(columns))))

    def window(
        self,
        duration: str,
        time_column: str,
        slide: str = "",
    ) -> NodeBuilder:
        """
        Group events into time-bounded buckets.

        Valid in DATA mode only — state has no event time axis.
        Must be immediately followed by aggregate().
        Declare watermarks via SubgraphBuilder.with_watermarks({time_column: delay}).

        Examples:
            .window("1 minute", "event_time")
            .window("10 minutes", "event_time", slide="2 minutes")
        """
        if self._mode == PipelineMode.STATE:
            raise ValueError(
                "window() is invalid in STATE mode. "
                "State has no event time axis. "
                "Use map_to_data() to cross back to DATA first."
            )
        return self._step_node(
            "window",
            Step(window=Window(duration=duration, time_column=time_column, slide=slide)),
        )

    def aggregate(
        self,
        group_by: List[str],
        aggs: List[AggExpr],
    ) -> NodeBuilder:
        """
        Collapse rows into groups and compute summary values per group.

        In STATE mode: group_by must not include the state key column.
        Empty group_by = global aggregation (produces one output row).

        Examples:
            .aggregate(["room_id"], [agg("avg_temp", AggFunc.AVG, col("temperature"))])
            .aggregate([], [agg("global_avg", AggFunc.AVG, col("temperature"))])
            .aggregate(["window", "room_id"], [
                agg("avg_temp",   AggFunc.AVG,   col("temperature")),
                agg("max_temp",   AggFunc.MAX,   col("temperature")),
                agg("room_count", AggFunc.COUNT, col("room_id")),
            ])
        """
        if self._mode == PipelineMode.STATE and self._state_key:
            if self._state_key in group_by:
                raise ValueError(
                    f"aggregate() group_by must not include the state key "
                    f"'{self._state_key}' in STATE mode. "
                    f"Each group would contain exactly one row — "
                    f"any aggregation function would be a no-op."
                )
        return self._step_node(
            "aggregate",
            Step(aggregate=Aggregate(group_by=group_by, aggs=aggs)),
        )

    def join(
        self,
        right: NodeBuilder,
        left_key: str,
        right_key: str,
        join_type: "JoinType | str" = JoinType.INNER,
    ) -> NodeBuilder:
        """
        Combine this node's data with another NodeBuilder by matching keys.

        Two edges are added: one from this node (left) and one from the
        right NodeBuilder. Both must belong to the same SubgraphBuilder.

        join_type: JoinType enum or string ("inner", "left", "left_semi",
                   "full", "full_outer")

        Examples:
            readings = graph.source_stream("temperature-readings")
            metadata = graph.source_kv("room-metadata")
            joined = readings.join(metadata, "room_id", "id", JoinType.LEFT)

            # stream-stream join (both DATA mode)
            events = graph.source_stream("events")
            clicks = graph.source_stream("clicks")
            joined = events.join(clicks, "user_id", "user_id", JoinType.INNER)
        """
        if isinstance(join_type, str):
            jt = _JOIN_TYPES.get(join_type.lower())
            if jt is None:
                raise ValueError(f"Unknown join type: '{join_type}'. " f"Valid: {list(_JOIN_TYPES)}")
        else:
            jt = join_type

        node_id = self._graph._new_id("join")
        step = Step(
            id=node_id,
            join=Join(
                left_key=left_key,
                right_key=right_key,
                join_type=jt,
                right_source_mode=right._mode,
            ),
        )
        self._graph._add_node(Node(id=node_id, step=step))
        self._graph._add_edge(self._node_id, node_id, self._mode)
        self._graph._add_edge(right._node_id, node_id, right._mode)
        return NodeBuilder(self._graph, node_id, self._mode)

    def map_to_state(
        self,
        key_column: str,
        value_column: str,
        operation: StateOperation,
    ) -> NodeBuilder:
        """
        Boundary crossing: DATA → STATE.

        All steps after this operate in STATE mode.
        Invalid if the node is already in STATE mode.

        Examples:
            .map_to_state("room_id", "temperature", StateOperation.REPLACE)
            .map_to_state("room_id", "energy_wh",   StateOperation.INCREMENT)
            .map_to_state("room_id", "temperature", StateOperation.MAXIMUM)
        """
        if self._mode == PipelineMode.STATE:
            raise ValueError(
                "map_to_state() is invalid — already in STATE mode. "
                "Cannot cross DATA→STATE twice without map_to_data() in between."
            )
        return self._step_node(
            "map-to-state",
            Step(
                map_to_state=MapToState(
                    key_column=key_column,
                    value_column=value_column,
                    operation=operation,
                )
            ),
            out_mode=PipelineMode.STATE,
            out_state_key=key_column,
        )

    def map_to_data(
        self,
        strategy: "MapToDataStrategy | str",
        schedule: str = "",
    ) -> NodeBuilder:
        """
        Boundary crossing: STATE → DATA.

        strategy:
          SNAPSHOT — emit full state once as a bounded dataset
          CDC      — emit each state change as an event (insert or delete)
          PERIODIC — emit full state on a recurring schedule

        schedule: cron expression or interval string, required for PERIODIC.
          Examples: "0 6 * * *" (cron), "1 hour" (interval)

        Examples:
            .map_to_data(MapToDataStrategy.CDC)
            .map_to_data(MapToDataStrategy.SNAPSHOT)
            .map_to_data(MapToDataStrategy.PERIODIC, "0 * * * *")
            .map_to_data("PERIODIC", "1 hour")   # string shorthand
        """
        if self._mode == PipelineMode.DATA:
            raise ValueError("map_to_data() is invalid — already in DATA mode.")

        if isinstance(strategy, str):
            strat = _MAP_TO_DATA_STRATEGIES.get(strategy.upper())
            if strat is None:
                raise ValueError(f"Unknown strategy: '{strategy}'. Valid: SNAPSHOT, CDC, PERIODIC")
        else:
            strat = strategy

        if strat == MapToDataStrategy.PERIODIC and not schedule:
            import warnings

            warnings.warn(
                "map_to_data(PERIODIC) without a schedule will emit on every "
                "micro-batch. Pass a cron or interval schedule. "
                "Example: .map_to_data(MapToDataStrategy.PERIODIC, '0 * * * *')",
                stacklevel=2,
            )

        sched = _parse_schedule(schedule) if schedule else Schedule()
        return self._step_node(
            "map-to-data",
            Step(map_to_data=MapToData(strategy=strat, schedule=sched)),
            out_mode=PipelineMode.DATA,
            out_state_key=None,
        )

    # ── Sinks ─────────────────────────────────────────────────────────────────

    def to_stream(self, topic: str) -> SubgraphBuilder:
        """
        Write output to a stream sink (e.g. Kafka topic).

        Returns the SubgraphBuilder so you can chain .execute() or add
        more branches (fanout) before calling execute().

        Example:
            .to_stream("hot-rooms-output").execute()
        """
        node_id = self._graph._new_id("sink")
        self._graph._add_node(
            Node(
                id=node_id,
                sink=Sink(stream=StreamSink(topic=topic), mode=self._mode),
            )
        )
        self._graph._add_edge(self._node_id, node_id, self._mode)
        return self._graph

    def to_kv(self, store: str) -> SubgraphBuilder:
        """
        Write output to a KV store sink.

        Returns the SubgraphBuilder so you can chain .execute() or add
        more branches (fanout) before calling execute().

        Example:
            .to_kv("room-state").execute()
        """
        node_id = self._graph._new_id("sink")
        self._graph._add_node(
            Node(
                id=node_id,
                sink=Sink(kv=KvSink(store=store), mode=self._mode),
            )
        )
        self._graph._add_edge(self._node_id, node_id, self._mode)
        return self._graph


# =============================================================================
# AnalyticsRef — runtime reference
# =============================================================================


class AnalyticsRef:
    """
    Runtime reference to the analytics service for subgraph submission.

    Obtained via analytics_service("name").allow().
    """

    def __init__(self, name: str):
        self._name = name
        self._channel = ChannelManager.get_channel()
        self._stub = AnalyticsServiceStub(channel=self._channel)

    def subgraph(self, name: str) -> SubgraphBuilder:
        """
        Start building an analytics subgraph (DAG of nodes and edges).

        Returns a SubgraphBuilder. Add source nodes via source_stream() or
        source_kv(), then chain operations on the returned NodeBuilder.

        Examples:
            # Simple linear pipeline
            graph = ref.subgraph("hot-rooms")
            await (
                graph.source_stream("temperature-readings")
                .filter(col("temperature") > 25.0)
                .to_stream("hot-rooms-output")
                .execute()
            )

            # Join stream with KV lookup
            graph = ref.subgraph("enrichment")
            readings = graph.source_stream("temperature-readings")
            metadata = graph.source_kv("room-metadata")
            await (
                readings.join(metadata, "room_id", "id", JoinType.LEFT)
                .filter(col("temperature") > 25.0)
                .to_stream("enriched-output")
                .execute()
            )

            # Fanout: one source feeds two sinks
            graph = ref.subgraph("fanout")
            filtered = graph.source_stream("input").filter(col("temperature") > 25.0)
            filtered.to_stream("hot-output")
            filtered.to_kv("hot-state")
            await graph.execute()

            # With hints
            graph = ref.subgraph("windowed-avg")
            await (
                graph
                .with_watermarks({"event_time": "30 seconds"})
                .with_engine(EnginePreference.SPARK_STREAMING)
                .source_stream("temperature-readings")
                .window("1 minute", "event_time")
                .aggregate(["room_id"], [agg("avg_temp", AggFunc.AVG, col("temperature"))])
                .to_stream("room-averages")
                .execute()
            )
        """
        return SubgraphBuilder(name=name, stub=self._stub)


# =============================================================================
# Resource
# =============================================================================


class AnalyticsService(Resource):
    """An AnalyticsService resource used for deployment."""

    def __init__(self, name: str):
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
        """Return a runtime reference for submitting subgraphs."""
        return AnalyticsRef(self.name)


# =============================================================================
# Entry point
# =============================================================================


def analytics_service(name: str) -> AnalyticsService:
    """
    Define an Analytics Service resource in a Nitric application.

    Example:
        from nitric.resources.analytics import analytics_service, col, agg, AggFunc, JoinType
        svc = analytics_service("my-analytics")
        ref = svc.allow()

        # Linear pipeline
        graph = ref.subgraph("hot-rooms")
        await (
            graph.source_stream("temperature_readings")
            .filter(col("temperature") > 25.0)
            .to_stream("hot_rooms")
            .execute()
        )

        # Join
        graph = ref.subgraph("enrichment")
        readings = graph.source_stream("temperature_readings")
        metadata = graph.source_kv("room_metadata")
        await (
            readings.join(metadata, "room_id", "id", JoinType.LEFT)
            .to_stream("enriched")
            .execute()
        )
    """
    return Nitric._create_resource(AnalyticsService, name)
