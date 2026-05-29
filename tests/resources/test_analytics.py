#
# Copyright (c) 2021 Nitric Technologies Pty Ltd.
#
# This file is part of Nitric Python 3 SDK.
# See https://github.com/nitrictech/python-sdk for further info.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import pytest
from unittest import IsolatedAsyncioTestCase, TestCase
from unittest.mock import AsyncMock, MagicMock

from grpclib import GRPCError, Status

from nitric.exception import UnknownException
from nitric.proto.analyticsservice.v1 import (
    AggFunc,
    Aggregate,
    AggExpr,
    ArithmeticOp,
    BetweenExpr,
    CompareOp,
    ComparisonExpr,
    Derive,
    DeriveExpr,
    Edge,
    ExecuteRequest,
    ExecuteResponse,
    Expression,
    FieldDef,
    FieldType,
    Filter,
    Hints,
    IsNullExpr,
    Join,
    JoinType,
    KvSink,
    KvSource,
    Literal,
    LogicalExpr,
    LogicalOperator,
    MapToData,
    MapToDataStrategy,
    MapToState,
    Node,
    PipelineMode,
    PlanResponse,
    Schedule,
    Select,
    Sink,
    Source,
    StateOperation,
    Step,
    StreamSink,
    StreamSource,
    Subgraph,
    Window,
    ColumnRef,
    EnginePreference,
    ArithmeticExpr,
    FunctionCallExpr,
)
from nitric.resources.analytics import (
    SubgraphBuilder,
    NodeBuilder,
    Expr,
    _AliasedExpr,
    col,
    lit,
    agg,
)

# pylint: disable=protected-access,missing-function-docstring,missing-class-docstring


# ── Helpers ───────────────────────────────────────────────────────────────────

_SCHEMA = [FieldDef(name="x", type=FieldType.DOUBLE)]
_STREAM_SCHEMA = [
    FieldDef(name="room_id", type=FieldType.STRING),
    FieldDef(name="temperature", type=FieldType.DOUBLE),
    FieldDef(name="event_time", type=FieldType.TIMESTAMP),
]
_KV_SCHEMA = [
    FieldDef(name="id", type=FieldType.STRING),
    FieldDef(name="building", type=FieldType.STRING),
    FieldDef(name="floor", type=FieldType.LONG),
]


def _stub(job_id: str = "job-1", error: str = "") -> MagicMock:
    """Return a mock AnalyticsServiceStub with pre-configured async methods."""
    s = MagicMock()
    s.execute = AsyncMock(return_value=ExecuteResponse(job_id=job_id, error=error))
    s.plan = AsyncMock(return_value=PlanResponse(selected_engine=EnginePreference.SPARK_STREAMING))
    return s


def _graph(name: str = "test") -> SubgraphBuilder:
    """Return a SubgraphBuilder with a fresh mock stub."""
    return SubgraphBuilder(name, _stub())


# =============================================================================
# Expr — expression builder
# =============================================================================


class TestExprComparisons(TestCase):
    def test_gt(self):
        expr = col("temperature") > 25.0
        self.assertEqual(
            expr.build(),
            Expression(
                comparison=ComparisonExpr(
                    left=Expression(column_ref=ColumnRef(name="temperature")),
                    operator=CompareOp.GT,
                    right=Expression(literal=Literal(double_val=25.0)),
                )
            ),
        )

    def test_lt(self):
        expr = col("temperature") < 0.0
        self.assertEqual(expr.build().comparison.operator, CompareOp.LT)

    def test_eq(self):
        expr = col("unit") == "C"
        self.assertEqual(expr.build().comparison.operator, CompareOp.EQ)
        self.assertEqual(expr.build().comparison.right.literal.string_val, "C")

    def test_ne(self):
        expr = col("status") != "disabled"
        self.assertEqual(expr.build().comparison.operator, CompareOp.NEQ)

    def test_gte(self):
        expr = col("x") >= 10
        self.assertEqual(expr.build().comparison.operator, CompareOp.GTE)
        self.assertEqual(expr.build().comparison.right.literal.int_val, 10)

    def test_lte(self):
        expr = col("x") <= 10
        self.assertEqual(expr.build().comparison.operator, CompareOp.LTE)


class TestExprLogical(TestCase):
    def test_and(self):
        expr = (col("temperature") > 20.0) & (col("unit") == "C")
        result = expr.build()
        self.assertEqual(result.logical.operator, LogicalOperator.AND)
        self.assertEqual(len(result.logical.operands), 2)

    def test_or(self):
        expr = (col("temperature") > 30.0) | (col("temperature") < 0.0)
        self.assertEqual(expr.build().logical.operator, LogicalOperator.OR)

    def test_not(self):
        expr = ~(col("status") == "disabled")
        result = expr.build()
        self.assertEqual(result.logical.operator, LogicalOperator.NOT)
        self.assertEqual(len(result.logical.operands), 1)

    def test_chained_and(self):
        expr = (col("a") > 0) & (col("b") > 0) & (col("c") > 0)
        # Left-associative: ((a>0 AND b>0) AND c>0)
        self.assertEqual(expr.build().logical.operator, LogicalOperator.AND)


class TestExprArithmetic(TestCase):
    def test_mul_add(self):
        expr = col("temperature") * 1.8 + 32.0
        result = expr.build()
        self.assertEqual(result.arithmetic.operator, ArithmeticOp.ADD)
        self.assertEqual(result.arithmetic.left.arithmetic.operator, ArithmeticOp.MULTIPLY)

    def test_sub(self):
        expr = col("x") - 5
        self.assertEqual(expr.build().arithmetic.operator, ArithmeticOp.SUBTRACT)

    def test_div(self):
        expr = col("x") / 2
        self.assertEqual(expr.build().arithmetic.operator, ArithmeticOp.DIVIDE)

    def test_radd(self):
        expr = 32.0 + col("temperature")
        self.assertEqual(expr.build().arithmetic.operator, ArithmeticOp.ADD)
        self.assertEqual(expr.build().arithmetic.left.literal.double_val, 32.0)

    def test_rmul(self):
        expr = 1.8 * col("temperature")
        self.assertEqual(expr.build().arithmetic.operator, ArithmeticOp.MULTIPLY)


class TestExprSpecialForms(TestCase):
    def test_is_null(self):
        expr = col("room_id").is_null()
        result = expr.build()
        self.assertEqual(result.is_null.negated, False)
        self.assertEqual(result.is_null.operand.column_ref.name, "room_id")

    def test_is_not_null(self):
        expr = col("room_id").is_not_null()
        result = expr.build()
        self.assertEqual(result.is_null.negated, True)

    def test_between(self):
        expr = col("temperature").between(20.0, 30.0)
        result = expr.build()
        self.assertEqual(result.between.lower.literal.double_val, 20.0)
        self.assertEqual(result.between.upper.literal.double_val, 30.0)
        self.assertEqual(result.between.value.column_ref.name, "temperature")

    def test_upper(self):
        expr = col("unit").upper()
        result = expr.build()
        self.assertEqual(result.function.name, "UPPER")
        self.assertEqual(result.function.args[0].column_ref.name, "unit")

    def test_round(self):
        expr = col("temperature").round(2)
        result = expr.build()
        self.assertEqual(result.function.name, "ROUND")
        self.assertEqual(result.function.args[1].literal.double_val, 2.0)

    def test_cast(self):
        expr = col("x").cast("double")
        self.assertEqual(expr.build().function.name, "CAST")
        self.assertEqual(expr.build().function.args[1].literal.string_val, "double")


class TestExprLiterals(TestCase):
    def test_int_literal(self):
        self.assertEqual(lit(42).build().literal.int_val, 42)

    def test_float_literal(self):
        self.assertEqual(lit(3.14).build().literal.double_val, 3.14)

    def test_string_literal(self):
        self.assertEqual(lit("hello").build().literal.string_val, "hello")

    def test_bool_literal(self):
        self.assertEqual(lit(True).build().literal.bool_val, True)
        self.assertEqual(lit(False).build().literal.bool_val, False)

    def test_invalid_literal_type(self):
        with self.assertRaises(TypeError):
            lit([1, 2, 3])

    def test_as_returns_aliased_expr(self):
        aliased = col("temperature").round(2).as_("temp_rounded")
        self.assertIsInstance(aliased, _AliasedExpr)
        self.assertEqual(aliased.alias, "temp_rounded")

    def test_hash_is_id_based(self):
        a = col("x")
        b = col("x")
        # Different objects → different hashes (even with same value)
        self.assertNotEqual(hash(a), hash(b))
        # Same object → same hash
        self.assertEqual(hash(a), hash(a))


# =============================================================================
# agg() helper
# =============================================================================


class TestAggHelper(TestCase):
    def test_agg_with_enum_and_expr(self):
        result = agg("avg_temp", AggFunc.AVG, col("temperature"))
        self.assertEqual(result.output_name, "avg_temp")
        self.assertEqual(result.function, AggFunc.AVG)
        self.assertEqual(result.input.column_ref.name, "temperature")

    def test_agg_string_shorthand(self):
        result = agg("count", "COUNT", "room_id")
        self.assertEqual(result.function, AggFunc.COUNT)
        self.assertEqual(result.input.column_ref.name, "room_id")

    def test_agg_string_case_insensitive(self):
        result = agg("s", "sum", col("x"))
        self.assertEqual(result.function, AggFunc.SUM)

    def test_agg_invalid_function(self):
        with self.assertRaises(ValueError, msg="should reject unknown function"):
            agg("x", "MEDIAN", col("x"))

    def test_agg_all_functions(self):
        for name, enum in [
            ("SUM", AggFunc.SUM),
            ("COUNT", AggFunc.COUNT),
            ("AVG", AggFunc.AVG),
            ("MAX", AggFunc.MAX),
            ("MIN", AggFunc.MIN),
            ("LAST", AggFunc.LAST),
            ("FIRST", AggFunc.FIRST),
            ("STDDEV", AggFunc.STDDEV),
            ("VARIANCE", AggFunc.VARIANCE),
            ("COLLECT_LIST", AggFunc.COLLECT_LIST),
        ]:
            result = agg("out", name, col("x"))
            self.assertEqual(result.function, enum, f"failed for {name}")


# =============================================================================
# SubgraphBuilder — graph structure
# =============================================================================


class TestSubgraphBuilderStructure(TestCase):
    def test_source_stream_creates_data_node(self):
        graph = _graph()
        nb = graph.source_stream("temperature-readings", _STREAM_SCHEMA)
        sg = graph._build()
        self.assertEqual(len(sg.nodes), 1)
        self.assertEqual(sg.nodes[0].source.stream.topic, "temperature-readings")
        self.assertEqual(sg.nodes[0].source.mode, PipelineMode.DATA)
        self.assertEqual(sg.nodes[0].source.schema, _STREAM_SCHEMA)
        self.assertIsInstance(nb, NodeBuilder)
        self.assertEqual(nb._mode, PipelineMode.DATA)

    def test_source_kv_creates_state_node(self):
        graph = _graph()
        nb = graph.source_kv("room-metadata", _KV_SCHEMA)
        sg = graph._build()
        self.assertEqual(sg.nodes[0].source.kv.store, "room-metadata")
        self.assertEqual(sg.nodes[0].source.mode, PipelineMode.STATE)
        self.assertEqual(sg.nodes[0].source.schema, _KV_SCHEMA)
        self.assertEqual(nb._mode, PipelineMode.STATE)

    def test_linear_pipeline_node_and_edge_count(self):
        graph = _graph("linear")
        graph.source_stream("input", _SCHEMA).filter(col("x") > 0).to_stream("output")
        sg = graph._build()
        # source-0, filter-0, sink-0
        self.assertEqual(len(sg.nodes), 3)
        self.assertEqual(len(sg.edges), 2)
        self.assertEqual(sg.name, "linear")

    def test_linear_pipeline_node_ids(self):
        graph = _graph()
        graph.source_stream("input", _SCHEMA).filter(col("x") > 0).to_stream("output")
        sg = graph._build()
        ids = [n.id for n in sg.nodes]
        self.assertEqual(ids, ["source-0", "filter-0", "sink-0"])

    def test_linear_pipeline_edge_connections(self):
        graph = _graph()
        graph.source_stream("input", _SCHEMA).filter(col("x") > 0).to_stream("output")
        sg = graph._build()
        self.assertEqual(sg.edges[0].from_node, "source-0")
        self.assertEqual(sg.edges[0].to_node, "filter-0")
        self.assertEqual(sg.edges[1].from_node, "filter-0")
        self.assertEqual(sg.edges[1].to_node, "sink-0")

    def test_edge_carries_mode(self):
        graph = _graph()
        graph.source_stream("input", _SCHEMA).filter(col("x") > 0).to_stream("output")
        sg = graph._build()
        self.assertEqual(sg.edges[0].mode, PipelineMode.DATA)
        self.assertEqual(sg.edges[1].mode, PipelineMode.DATA)

    def test_fanout_to_two_sinks(self):
        graph = _graph("fanout")
        filtered = graph.source_stream("input", _SCHEMA).filter(col("x") > 0)
        filtered.to_stream("stream-out")
        filtered.to_kv("kv-out")
        sg = graph._build()
        # source-0, filter-0, sink-0, sink-1
        self.assertEqual(len(sg.nodes), 4)
        self.assertEqual(len(sg.edges), 3)

    def test_fanout_sink_node_contents(self):
        graph = _graph()
        filtered = graph.source_stream("input", _SCHEMA).filter(col("x") > 0)
        filtered.to_stream("stream-out")
        filtered.to_kv("kv-out")
        sg = graph._build()
        sinks = [n for n in sg.nodes if n.sink.stream.topic or n.sink.kv.store]
        self.assertEqual({s.sink.stream.topic or s.sink.kv.store for s in sinks}, {"stream-out", "kv-out"})

    def test_join_creates_two_sources_one_join_node(self):
        graph = _graph("join")
        readings = graph.source_stream("temperature-readings", _STREAM_SCHEMA)
        metadata = graph.source_kv("room-metadata", _KV_SCHEMA)
        readings.join(metadata, "room_id", "id", JoinType.LEFT).to_stream("enriched")
        sg = graph._build()
        # source-0, source-1, join-0, sink-0
        self.assertEqual(len(sg.nodes), 4)
        # two edges into join, one out
        self.assertEqual(len(sg.edges), 3)

    def test_join_both_sources_connect_to_join_node(self):
        graph = _graph()
        readings = graph.source_stream("temperature-readings", _STREAM_SCHEMA)
        metadata = graph.source_kv("room-metadata", _KV_SCHEMA)
        readings.join(metadata, "room_id", "id").to_stream("enriched")
        sg = graph._build()
        to_join = [e for e in sg.edges if e.to_node == "join-0"]
        self.assertEqual(len(to_join), 2)
        from_nodes = {e.from_node for e in to_join}
        self.assertEqual(from_nodes, {"source-0", "source-1"})

    def test_join_node_contents(self):
        graph = _graph()
        readings = graph.source_stream("temperature-readings", _STREAM_SCHEMA)
        metadata = graph.source_kv("room-metadata", _KV_SCHEMA)
        readings.join(metadata, "room_id", "id", JoinType.LEFT).to_stream("out")
        sg = graph._build()
        join_node = next(n for n in sg.nodes if n.id == "join-0")
        self.assertEqual(join_node.step.join.left_key, "room_id")
        self.assertEqual(join_node.step.join.right_key, "id")
        self.assertEqual(join_node.step.join.join_type, JoinType.LEFT)
        self.assertEqual(join_node.step.join.right_source_mode, PipelineMode.STATE)

    def test_join_string_join_type(self):
        graph = _graph()
        left = graph.source_stream("left", _SCHEMA)
        right = graph.source_stream("right", _SCHEMA)
        left.join(right, "k", "k", "inner").to_stream("out")
        sg = graph._build()
        join_node = next(n for n in sg.nodes if n.id == "join-0")
        self.assertEqual(join_node.step.join.join_type, JoinType.INNER)

    def test_multiple_steps_chain(self):
        graph = _graph()
        (
            graph.source_stream("events", _SCHEMA)
            .filter(col("value") > 0)
            .window("1 minute", "event_time")
            .aggregate(["room_id"], [agg("avg", AggFunc.AVG, col("value"))])
            .to_stream("windowed")
        )
        sg = graph._build()
        # source, filter, window, aggregate, sink
        self.assertEqual(len(sg.nodes), 5)
        self.assertEqual(len(sg.edges), 4)
        ids = [n.id for n in sg.nodes]
        self.assertEqual(ids, ["source-0", "filter-0", "window-0", "aggregate-0", "sink-0"])

    def test_map_to_state_changes_mode(self):
        graph = _graph()
        nb = graph.source_stream("events", _SCHEMA).map_to_state("room_id", "temperature", StateOperation.REPLACE)
        self.assertEqual(nb._mode, PipelineMode.STATE)
        self.assertEqual(nb._state_key, "room_id")

    def test_map_to_data_changes_mode_back(self):
        graph = _graph()
        nb = (
            graph.source_stream("events", _SCHEMA)
            .map_to_state("room_id", "temperature", StateOperation.REPLACE)
            .map_to_data(MapToDataStrategy.CDC)
        )
        self.assertEqual(nb._mode, PipelineMode.DATA)
        self.assertIsNone(nb._state_key)

    def test_full_round_trip_data_state_data(self):
        graph = _graph()
        (
            graph.source_stream("events", _SCHEMA)
            .filter(col("value") > 0)
            .map_to_state("room_id", "value", StateOperation.REPLACE)
            .filter(col("value") > 30)
            .map_to_data(MapToDataStrategy.CDC)
            .to_stream("alerts")
        )
        sg = graph._build()
        # source, filter, map-to-state, filter, map-to-data, sink
        self.assertEqual(len(sg.nodes), 6)


# =============================================================================
# NodeBuilder — step node contents
# =============================================================================


class TestNodeBuilderStepContents(TestCase):
    def _get_node(self, sg: Subgraph, node_id: str) -> Node:
        return next(n for n in sg.nodes if n.id == node_id)

    def test_filter_node_predicate(self):
        graph = _graph()
        graph.source_stream("t", _SCHEMA).filter(col("temperature") > 25.0).to_stream("out")
        node = self._get_node(graph._build(), "filter-0")
        pred = node.step.filter.predicate
        self.assertEqual(pred.comparison.operator, CompareOp.GT)
        self.assertEqual(pred.comparison.left.column_ref.name, "temperature")
        self.assertEqual(pred.comparison.right.literal.double_val, 25.0)

    def test_filter_id_matches_node_id(self):
        graph = _graph()
        graph.source_stream("t", _SCHEMA).filter(col("x") > 0).to_stream("out")
        node = self._get_node(graph._build(), "filter-0")
        self.assertEqual(node.step.id, "filter-0")

    def test_select_node_columns(self):
        graph = _graph()
        graph.source_stream("t", _SCHEMA).select("room_id", "temperature").to_stream("out")
        node = self._get_node(graph._build(), "select-0")
        self.assertEqual(list(node.step.select.columns), ["room_id", "temperature"])

    def test_derive_node_expressions(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .derive(
                col("temperature").round(2).as_("temp_c"),
                (col("temperature") * 1.8 + 32.0).as_("temp_f"),
            )
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "derive-0")
        exprs = node.step.derive.expressions
        self.assertEqual(len(exprs), 2)
        self.assertEqual(exprs[0].output_column, "temp_c")
        self.assertEqual(exprs[1].output_column, "temp_f")

    def test_window_node_contents(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .window("1 minute", "event_time", slide="30 seconds")
            .aggregate(["room_id"], [agg("avg", AggFunc.AVG, col("v"))])
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "window-0")
        self.assertEqual(node.step.window.duration, "1 minute")
        self.assertEqual(node.step.window.time_column, "event_time")
        self.assertEqual(node.step.window.slide, "30 seconds")

    def test_aggregate_node_contents(self):
        graph = _graph()
        aggs = [
            agg("avg_temp", AggFunc.AVG, col("temperature")),
            agg("count", AggFunc.COUNT, col("room_id")),
        ]
        graph.source_stream("t", _SCHEMA).aggregate(["room_id"], aggs).to_stream("out")
        node = self._get_node(graph._build(), "aggregate-0")
        self.assertEqual(list(node.step.aggregate.group_by), ["room_id"])
        self.assertEqual(len(node.step.aggregate.aggs), 2)
        self.assertEqual(node.step.aggregate.aggs[0].output_name, "avg_temp")
        self.assertEqual(node.step.aggregate.aggs[0].function, AggFunc.AVG)

    def test_map_to_state_node_contents(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .map_to_state("room_id", "temperature", StateOperation.INCREMENT)
            .to_kv("state")
        )
        node = self._get_node(graph._build(), "map-to-state-0")
        self.assertEqual(node.step.map_to_state.key_column, "room_id")
        self.assertEqual(node.step.map_to_state.value_column, "temperature")
        self.assertEqual(node.step.map_to_state.operation, StateOperation.INCREMENT)

    def test_map_to_data_cdc_node(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .map_to_state("room_id", "v", StateOperation.REPLACE)
            .map_to_data(MapToDataStrategy.CDC)
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "map-to-data-0")
        self.assertEqual(node.step.map_to_data.strategy, MapToDataStrategy.CDC)

    def test_map_to_data_periodic_with_cron(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .map_to_state("room_id", "v", StateOperation.REPLACE)
            .map_to_data(MapToDataStrategy.PERIODIC, "0 * * * *")
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "map-to-data-0")
        self.assertEqual(node.step.map_to_data.strategy, MapToDataStrategy.PERIODIC)
        self.assertEqual(node.step.map_to_data.schedule.cron, "0 * * * *")

    def test_map_to_data_periodic_with_interval(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .map_to_state("room_id", "v", StateOperation.REPLACE)
            .map_to_data(MapToDataStrategy.PERIODIC, "1 hour")
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "map-to-data-0")
        self.assertEqual(node.step.map_to_data.schedule.interval, "1 hour")

    def test_map_to_data_string_strategy(self):
        graph = _graph()
        (
            graph.source_stream("t", _SCHEMA)
            .map_to_state("room_id", "v", StateOperation.REPLACE)
            .map_to_data("CDC")
            .to_stream("out")
        )
        node = self._get_node(graph._build(), "map-to-data-0")
        self.assertEqual(node.step.map_to_data.strategy, MapToDataStrategy.CDC)

    def test_to_stream_sink_topic(self):
        graph = _graph()
        graph.source_stream("t", _SCHEMA).to_stream("hot-output")
        sg = graph._build()
        sink = next(n for n in sg.nodes if n.sink.stream.topic)
        self.assertEqual(sink.sink.stream.topic, "hot-output")

    def test_to_kv_sink_store(self):
        graph = _graph()
        graph.source_kv("src", _KV_SCHEMA).to_kv("dest-store")
        sg = graph._build()
        sink = next(n for n in sg.nodes if n.sink.kv.store)
        self.assertEqual(sink.sink.kv.store, "dest-store")

    def test_to_stream_returns_subgraph_builder(self):
        graph = _graph()
        result = graph.source_stream("t", _SCHEMA).to_stream("out")
        self.assertIsInstance(result, SubgraphBuilder)
        self.assertIs(result, graph)

    def test_to_kv_returns_subgraph_builder(self):
        graph = _graph()
        result = graph.source_kv("s", _KV_SCHEMA).to_kv("dest")
        self.assertIsInstance(result, SubgraphBuilder)
        self.assertIs(result, graph)


# =============================================================================
# NodeBuilder — mode guards
# =============================================================================


class TestNodeBuilderModeGuards(TestCase):
    def test_window_raises_in_state_mode(self):
        graph = _graph()
        nb = graph.source_stream("t", _SCHEMA).map_to_state("room_id", "v", StateOperation.REPLACE)
        with self.assertRaises(ValueError, msg="window() should raise in STATE mode"):
            nb.window("1 minute", "event_time")

    def test_map_to_state_raises_if_already_state(self):
        graph = _graph()
        nb = graph.source_stream("t", _SCHEMA).map_to_state("room_id", "v", StateOperation.REPLACE)
        with self.assertRaises(ValueError):
            nb.map_to_state("room_id", "v", StateOperation.REPLACE)

    def test_map_to_data_raises_if_already_data(self):
        graph = _graph()
        nb = graph.source_stream("t", _SCHEMA)
        with self.assertRaises(ValueError):
            nb.map_to_data(MapToDataStrategy.CDC)

    def test_aggregate_raises_if_group_by_includes_state_key(self):
        graph = _graph()
        nb = graph.source_stream("t", _SCHEMA).map_to_state("room_id", "v", StateOperation.REPLACE)
        with self.assertRaises(ValueError):
            nb.aggregate(["room_id"], [agg("avg", AggFunc.AVG, col("v"))])

    def test_filter_raises_on_non_expr(self):
        graph = _graph()
        nb = graph.source_stream("t")
        with self.assertRaises(TypeError):
            nb.filter("temperature > 25")  # plain string — not an Expr

    def test_derive_raises_on_non_aliased_expr(self):
        graph = _graph()
        nb = graph.source_stream("t")
        with self.assertRaises(TypeError):
            nb.derive(col("x"))  # must use .as_()

    def test_join_raises_on_invalid_join_type_string(self):
        graph = _graph()
        left = graph.source_stream("left")
        right = graph.source_stream("right")
        with self.assertRaises(ValueError):
            left.join(right, "k", "k", "cross")

    def test_map_to_data_raises_on_invalid_strategy_string(self):
        graph = _graph()
        nb = graph.source_stream("t").map_to_state("room_id", "v", StateOperation.REPLACE)
        with self.assertRaises(ValueError):
            nb.map_to_data("RSTREAM")  # old API name


# =============================================================================
# SubgraphBuilder — hints
# =============================================================================


class TestSubgraphBuilderHints(TestCase):
    def test_with_engine(self):
        graph = _graph()
        result = graph.with_engine(EnginePreference.SPARK_STREAMING)
        self.assertIs(result, graph)
        self.assertEqual(graph._hints.engine, EnginePreference.SPARK_STREAMING)

    def test_with_watermarks(self):
        graph = _graph()
        graph.with_watermarks({"event_time": "30 seconds"})
        self.assertEqual(graph._hints.watermarks["event_time"], "30 seconds")

    def test_with_watermarks_merge(self):
        graph = _graph()
        graph.with_watermarks({"a": "10 seconds"})
        graph.with_watermarks({"b": "20 seconds"})
        self.assertEqual(graph._hints.watermarks["a"], "10 seconds")
        self.assertEqual(graph._hints.watermarks["b"], "20 seconds")

    def test_with_shuffle_partitions(self):
        graph = _graph()
        graph.with_shuffle_partitions(200)
        self.assertEqual(graph._hints.shuffle_partitions, 200)

    def test_with_checkpoint_base(self):
        graph = _graph()
        graph.with_checkpoint_base("/checkpoints/my-job")
        self.assertEqual(graph._hints.checkpoint_base, "/checkpoints/my-job")

    def test_with_partition_column(self):
        graph = _graph()
        graph.with_partition_column("event_date")
        self.assertEqual(graph._hints.partition_column, "event_date")

    def test_with_starting_offsets(self):
        graph = _graph()
        graph.with_starting_offsets("earliest")
        self.assertEqual(graph._hints.starting_offsets, "earliest")

    def test_with_refresh(self):
        graph = _graph()
        graph.with_refresh("incremental", "0 * * * *")
        self.assertEqual(graph._hints.refresh_strategy, "incremental")
        self.assertEqual(graph._hints.refresh_schedule, "0 * * * *")

    def test_with_indexes(self):
        graph = _graph()
        graph.with_indexes(True)
        self.assertTrue(graph._hints.create_indexes)

    def test_hints_chain_returns_self(self):
        graph = _graph()
        result = (
            graph.with_engine(EnginePreference.SPARK_BATCH).with_shuffle_partitions(100).with_checkpoint_base("/tmp")
        )
        self.assertIs(result, graph)


# =============================================================================
# SubgraphBuilder — execute() and plan()
# =============================================================================


class TestSubgraphBuilderExecute(IsolatedAsyncioTestCase):
    async def test_execute_calls_stub_with_correct_request(self):
        stub = _stub(job_id="job-42")
        graph = SubgraphBuilder("hot-rooms", stub)
        graph.source_stream("temperature-readings").filter(col("temperature") > 25.0).to_stream("hot-rooms-output")

        job_id = await graph.execute()

        self.assertEqual(job_id, "job-42")
        stub.execute.assert_called_once()
        req: ExecuteRequest = stub.execute.call_args[0][0]
        self.assertEqual(req.subgraph.name, "hot-rooms")
        self.assertEqual(len(req.subgraph.nodes), 3)

    async def test_execute_passes_hints(self):
        stub = _stub()
        graph = SubgraphBuilder("test", stub)
        graph.with_engine(EnginePreference.SPARK_STREAMING).with_shuffle_partitions(64)
        graph.source_stream("t").to_stream("out")

        await graph.execute()

        req: ExecuteRequest = stub.execute.call_args[0][0]
        self.assertEqual(req.hints.engine, EnginePreference.SPARK_STREAMING)
        self.assertEqual(req.hints.shuffle_partitions, 64)

    async def test_execute_raises_on_response_error(self):
        stub = _stub(error="something went wrong")
        graph = SubgraphBuilder("test", stub)
        graph.source_stream("t").to_stream("out")

        with self.assertRaises(RuntimeError, msg="should raise on non-empty error field"):
            await graph.execute()

    async def test_execute_raises_on_grpc_error(self):
        stub = MagicMock()
        stub.execute = AsyncMock(side_effect=GRPCError(Status.UNKNOWN, "grpc error"))
        graph = SubgraphBuilder("test", stub)
        graph.source_stream("t").to_stream("out")

        with self.assertRaises(UnknownException):
            await graph.execute()

    async def test_plan_calls_stub(self):
        stub = _stub()
        graph = SubgraphBuilder("test", stub)
        graph.source_stream("t").to_stream("out")

        response = await graph.plan()

        stub.plan.assert_called_once()
        self.assertEqual(response.selected_engine, EnginePreference.SPARK_STREAMING)

    async def test_plan_raises_on_grpc_error(self):
        stub = MagicMock()
        stub.plan = AsyncMock(side_effect=GRPCError(Status.UNKNOWN, "grpc error"))
        graph = SubgraphBuilder("test", stub)
        graph.source_stream("t").to_stream("out")

        with self.assertRaises(UnknownException):
            await graph.plan()

    async def test_execute_builds_correct_subgraph_proto(self):
        stub = _stub()
        graph = SubgraphBuilder("enrichment", stub)
        readings = graph.source_stream("temperature-readings")
        metadata = graph.source_kv("room-metadata")
        (
            readings.join(metadata, "room_id", "id", JoinType.LEFT)
            .filter(col("temperature") > 25.0)
            .to_stream("enriched-output")
        )

        await graph.execute()

        req: ExecuteRequest = stub.execute.call_args[0][0]
        sg = req.subgraph
        self.assertEqual(sg.name, "enrichment")
        # 4 nodes: source-0, source-1, join-0, filter-0, sink-0
        self.assertEqual(len(sg.nodes), 5)
        self.assertEqual(len(sg.edges), 4)
