"""
Standalone test for the declarative DSL parser.
Runs without the Nitric runtime — parses DSL strings and
inspects the resulting Subgraph proto.

Run with:
    cd python-sdk
    python test_dsl.py
"""

import sys
import traceback
import warnings

sys.path.insert(0, ".")

from nitric.resources.dsl.parser import parse, ParseError  # noqa: E402
from nitric.resources.dsl.validator import _step_op  # noqa: E402
from nitric.proto.analyticsservice.v1 import (  # noqa: E402
    Subgraph,
    Node,
    PipelineMode,
    JoinType,
    MapToDataStrategy,
)

# ── Helpers ───────────────────────────────────────────────────────────────────

PASS = "\033[92m✓\033[0m"
FAIL = "\033[91m✗\033[0m"

results = {"passed": 0, "failed": 0}


def assert_eq(label: str, actual, expected):
    if actual != expected:
        raise AssertionError(f"  {label}: expected {expected!r}, got {actual!r}")


def classify(sg: Subgraph):
    """
    Split a Subgraph's nodes into (sources, steps, sinks) by graph topology.
    Sources have in-degree 0. Sinks have out-degree 0. Steps are in between.
    """
    has_incoming = {e.to_node for e in sg.edges}
    has_outgoing = {e.from_node for e in sg.edges}
    node_map = {n.id: n for n in sg.nodes}
    sources = [node_map[n.id] for n in sg.nodes if n.id not in has_incoming]
    sinks = [node_map[n.id] for n in sg.nodes if n.id not in has_outgoing]
    steps = [node_map[n.id] for n in sg.nodes if n.id in has_incoming and n.id in has_outgoing]
    return sources, steps, sinks


def step_type_at(sg: Subgraph, index: int) -> str:
    """Return the operation type of the step node at the given index."""
    _, steps, _ = classify(sg)
    return _step_op(steps[index].step)


def test(name: str, dsl: str, expect_error: bool = False, check=None):
    """
    Parse a DSL string and optionally run a check function on the Subgraph.
    Suppresses the 'no name' warning since test pipelines have no name.
    """
    try:
        with warnings.catch_warnings():
            warnings.simplefilter("ignore")
            sg = parse(dsl)

        if expect_error:
            print(f"{FAIL} {name}")
            print(f"     Expected parse/validation error but got a valid subgraph")
            results["failed"] += 1
            return

        if check:
            check(sg)

        print(f"{PASS} {name}")
        results["passed"] += 1

    except (ParseError, ValueError) as e:
        if expect_error:
            short = str(e)[:80].replace("\n", " ")
            print(f"{PASS} {name}  (correctly rejected: {short})")
            results["passed"] += 1
        else:
            print(f"{FAIL} {name}")
            print(f"     {e}")
            results["failed"] += 1

    except Exception:
        print(f"{FAIL} {name}")
        traceback.print_exc()
        results["failed"] += 1


def show(name: str, dsl: str):
    """Parse and print the resulting Subgraph proto — useful for inspection."""
    print(f"\n── {name} ──────────────────────────────────────")
    try:
        with warnings.catch_warnings():
            warnings.simplefilter("ignore")
            sg = parse(dsl)
        sources, steps, sinks = classify(sg)
        print(f"  nodes: {[n.id for n in sg.nodes]}")
        print(f"  edges: {[(e.from_node, e.to_node) for e in sg.edges]}")
        for s in sources:
            print(f"  source: topic={s.source.stream.topic!r} store={s.source.kv.store!r}")
        for s in steps:
            print(f"  step[{s.id}]: {_step_op(s.step)}")
        for s in sinks:
            print(f"  sink: topic={s.sink.stream.topic!r} store={s.sink.kv.store!r}")
    except Exception as e:
        print(f"ERROR: {e}")
    print()


# =============================================================================
# VALID PIPELINES
# =============================================================================

print("\n=== VALID PIPELINES ===\n")

# ── Simple filter ─────────────────────────────────────────────────────────────

test(
    "simple filter DATA→DATA",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 25.0
    INTO STREAM hot_rooms
""",
    check=lambda sg: (
        assert_eq("source topic", classify(sg)[0][0].source.stream.topic, "temperature_readings"),
        assert_eq("sink topic", classify(sg)[2][0].sink.stream.topic, "hot_rooms"),
        assert_eq("step count", len(classify(sg)[1]), 1),
        assert_eq("step0 type", step_type_at(sg, 0), "filter"),
    ),
)

# ── Compound filter ───────────────────────────────────────────────────────────

test(
    "compound filter with AND",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 20.0
      AND room_id IS NOT NULL
    INTO STREAM filtered_readings
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 1),
        assert_eq("step0 type", step_type_at(sg, 0), "filter"),
    ),
)

# ── Built-in functions ────────────────────────────────────────────────────────

test(
    "ROUND in WHERE",
    """
    FROM STREAM temperature_readings
    WHERE ROUND(temperature, 1) > 25.0
    INTO STREAM hot_rooms
""",
)

test(
    "UPPER in WHERE",
    """
    FROM STREAM temperature_readings
    WHERE UPPER(unit) = 'C'
    INTO STREAM filtered
""",
)

test(
    "BETWEEN sugar",
    """
    FROM STREAM temperature_readings
    WHERE temperature BETWEEN 20.0 AND 30.0
    INTO STREAM normal_temp_rooms
""",
)

test(
    "IS NULL check",
    """
    FROM STREAM temperature_readings
    WHERE room_id IS NOT NULL
    INTO STREAM valid_readings
""",
)

# ── SELECT / derive ───────────────────────────────────────────────────────────

test(
    "SELECT plain columns produces select node",
    """
    FROM STREAM temperature_readings
    SELECT room_id, temperature
    INTO STREAM projected
""",
    check=lambda sg: (
        # plain columns → select node
        assert_eq("has select step", any(_step_op(n.step) == "select" for n in classify(sg)[1]), True),
        assert_eq("select cols", classify(sg)[1][0].step.select.columns, ["room_id", "temperature"]),
    ),
)

test(
    "SELECT with aliases produces derive node",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    SELECT
      room_id,
      ROUND(temperature, 2) AS temp_c,
      UPPER(unit) AS unit_upper
    INTO STREAM projected_readings
""",
    check=lambda sg: (
        # expect: filter, select (room_id), derive (temp_c, unit_upper)
        assert_eq("has select", any(_step_op(n.step) == "select" for n in classify(sg)[1]), True),
        assert_eq("has derive", any(_step_op(n.step) == "derive" for n in classify(sg)[1]), True),
        assert_eq(
            "derive exprs",
            len([n for n in classify(sg)[1] if _step_op(n.step) == "derive"][0].step.derive.expressions),
            2,
        ),
    ),
)

test(
    "SELECT *",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    SELECT *
    INTO STREAM pass_through
""",
)

# ── WINDOW + GROUP BY ─────────────────────────────────────────────────────────

test(
    "window aggregation",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    WINDOW('1 minute', event_time)
    GROUP BY window, room_id
      AVG(temperature) AS avg_temp,
      MAX(temperature) AS max_temp,
      COUNT(*) AS reading_count
    INTO STREAM windowed_averages
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 3),  # filter, window, aggregate
        assert_eq("step0", step_type_at(sg, 0), "filter"),
        assert_eq("step1", step_type_at(sg, 1), "window"),
        assert_eq("step2", step_type_at(sg, 2), "aggregate"),
        assert_eq("duration", classify(sg)[1][1].step.window.duration, "1 minute"),
        assert_eq("time_col", classify(sg)[1][1].step.window.time_column, "event_time"),
        assert_eq("group_by", list(classify(sg)[1][2].step.aggregate.group_by), ["window", "room_id"]),
        assert_eq("agg count", len(classify(sg)[1][2].step.aggregate.aggs), 3),
    ),
)

test(
    "sliding window",
    """
    FROM STREAM temperature_readings
    WINDOW('10 minutes', event_time, '2 minutes')
    GROUP BY window, room_id
      AVG(temperature) AS avg_temp
    INTO STREAM sliding_averages
""",
    check=lambda sg: (assert_eq("slide", classify(sg)[1][0].step.window.slide, "2 minutes"),),
)

test(
    "global aggregate no group_by",
    """
    FROM KV room_current_temp
    GROUP BY
      AVG(temperature) AS global_avg
    INTO KV global_stats
""",
)

# ── JOIN ──────────────────────────────────────────────────────────────────────

test(
    "inner join STREAM sources",
    """
    FROM STREAM temperature_readings
    INNER JOIN STREAM room_metadata ON room_id = room_id
    INTO STREAM enriched_readings
""",
    check=lambda sg: (
        # 2 sources, 1 join step, 1 sink = 4 nodes
        assert_eq("node count", len(sg.nodes), 4),
        assert_eq("source count", len(classify(sg)[0]), 2),
        assert_eq("join type", classify(sg)[1][0].step.join.join_type, JoinType.INNER),
        assert_eq("left key", classify(sg)[1][0].step.join.left_key, "room_id"),
        assert_eq("right key", classify(sg)[1][0].step.join.right_key, "room_id"),
    ),
)

test(
    "left join enrichment (ENRICH keyword)",
    """
    FROM STREAM temperature_readings
    ENRICH KV room_metadata ON room_id = room_id
    INTO STREAM enriched_readings
""",
    check=lambda sg: (
        assert_eq("join type", classify(sg)[1][0].step.join.join_type, JoinType.LEFT),
        assert_eq("right mode", classify(sg)[1][0].step.join.right_source_mode, PipelineMode.STATE),
    ),
)

test(
    "left_semi join",
    """
    FROM STREAM temperature_readings
    LEFT_SEMI JOIN STREAM room_metadata ON room_id = room_id
    INTO STREAM semi_enriched
""",
    check=lambda sg: (assert_eq("join type", classify(sg)[1][0].step.join.join_type, JoinType.LEFT_SEMI),),
)

# ── MAP TO STATE ──────────────────────────────────────────────────────────────

test(
    "map to state REPLACE",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    INTO KV room_current_temp
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 2),
        assert_eq("step0", step_type_at(sg, 0), "filter"),
        assert_eq("step1", step_type_at(sg, 1), "map_to_state"),
        assert_eq("key col", classify(sg)[1][1].step.map_to_state.key_column, "room_id"),
        assert_eq("val col", classify(sg)[1][1].step.map_to_state.value_column, "temperature"),
        assert_eq("sink store", classify(sg)[2][0].sink.kv.store, "room_current_temp"),
    ),
)

test(
    "map to state INCREMENT",
    """
    FROM STREAM energy_readings
    MAP TO STATE KEY device_id VALUE energy_wh USING INCREMENT
    INTO KV device_total_energy
""",
)

test(
    "map to state then filter on state",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 25.0
    INTO KV hot_room_state
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 2),
        assert_eq("step0", step_type_at(sg, 0), "map_to_state"),
        assert_eq("step1", step_type_at(sg, 1), "filter"),
    ),
)

# ── MAP TO DATA ───────────────────────────────────────────────────────────────
# ISTREAM and DSTREAM both map to CDC strategy.
# RSTREAM maps to SNAPSHOT (no schedule) or PERIODIC (with schedule).

test(
    "MAP TO DATA ISTREAM → CDC",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA ISTREAM
    INTO STREAM newly_hot_rooms
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 3),
        assert_eq("step2 type", step_type_at(sg, 2), "map_to_data"),
        assert_eq("strategy", classify(sg)[1][2].step.map_to_data.strategy, MapToDataStrategy.CDC),
    ),
)

test(
    "MAP TO DATA DSTREAM → CDC",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA DSTREAM
    INTO STREAM cooling_rooms
""",
    check=lambda sg: (assert_eq("strategy", classify(sg)[1][2].step.map_to_data.strategy, MapToDataStrategy.CDC),),
)

test(
    "MAP TO DATA RSTREAM → SNAPSHOT",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA RSTREAM
    INTO STREAM hot_room_snapshot
""",
    check=lambda sg: (assert_eq("strategy", classify(sg)[1][2].step.map_to_data.strategy, MapToDataStrategy.SNAPSHOT),),
)

test(
    "MAP TO DATA RSTREAM with cron schedule → PERIODIC",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    MAP TO DATA RSTREAM ON SCHEDULE '0 * * * *'
    INTO STREAM hourly_snapshot
""",
    check=lambda sg: (
        assert_eq("strategy", classify(sg)[1][1].step.map_to_data.strategy, MapToDataStrategy.PERIODIC),
        assert_eq("cron", classify(sg)[1][1].step.map_to_data.schedule.cron, "0 * * * *"),
    ),
)

# ── Source types ──────────────────────────────────────────────────────────────

test(
    "KV source STATE pipeline",
    """
    FROM KV room_current_temp
    WHERE temperature > 25.0
    INTO KV hot_room_state
""",
    check=lambda sg: (
        assert_eq("source store", classify(sg)[0][0].source.kv.store, "room_current_temp"),
        assert_eq("sink store", classify(sg)[2][0].sink.kv.store, "hot_room_state"),
    ),
)

# ── Round trip DATA → STATE → DATA ────────────────────────────────────────────

test(
    "full round trip DATA→STATE→DATA",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA ISTREAM
    INTO STREAM critical_alerts
""",
    check=lambda sg: (
        assert_eq("step count", len(classify(sg)[1]), 4),
        assert_eq("step0", step_type_at(sg, 0), "filter"),
        assert_eq("step1", step_type_at(sg, 1), "map_to_state"),
        assert_eq("step2", step_type_at(sg, 2), "filter"),
        assert_eq("step3", step_type_at(sg, 3), "map_to_data"),
    ),
)

# ── All state operations ───────────────────────────────────────────────────────

for op in ["REPLACE", "INCREMENT", "DECREMENT", "MAXIMUM", "MINIMUM", "COLLECT"]:
    test(
        f"state operation {op}",
        f"""
        FROM STREAM readings
        MAP TO STATE KEY device_id VALUE value USING {op}
        INTO KV device_state
    """,
    )

# ── All join types ────────────────────────────────────────────────────────────

for jt, expected_enum in [
    ("INNER", JoinType.INNER),
    ("LEFT", JoinType.LEFT),
    ("LEFT_SEMI", JoinType.LEFT_SEMI),
]:
    test(
        f"join type {jt}",
        f"""
        FROM STREAM readings
        {jt} JOIN STREAM metadata ON room_id = room_id
        INTO STREAM enriched
    """,
        check=lambda sg, e=expected_enum: (assert_eq("join type", classify(sg)[1][0].step.join.join_type, e),),
    )

# =============================================================================
# INVALID PIPELINES
# =============================================================================

print("\n=== INVALID PIPELINES (should be rejected) ===\n")

test(
    "stream source with KV sink (no MAP TO STATE)",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        WHERE temperature > 25.0
        INTO KV hot_rooms
    """,
)

test(
    "KV source with stream sink (no MAP TO DATA)",
    expect_error=True,
    dsl="""
        FROM KV room_current_temp
        WHERE temperature > 25.0
        INTO STREAM hot_rooms
    """,
)

test(
    "WINDOW in STATE mode",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        MAP TO STATE KEY room_id VALUE temperature USING REPLACE
        WINDOW('1 minute', event_time)
        GROUP BY window AVG(temperature) AS avg
        INTO STREAM output
    """,
)

test(
    "MAP TO STATE when already in STATE",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        MAP TO STATE KEY room_id VALUE temperature USING REPLACE
        MAP TO STATE KEY room_id VALUE temperature USING REPLACE
        INTO KV output
    """,
)

test(
    "MAP TO DATA when already in DATA",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        MAP TO DATA ISTREAM
        INTO STREAM output
    """,
)

test(
    "WINDOW without GROUP BY",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        WINDOW('1 minute', event_time)
        WHERE temperature > 25.0
        INTO STREAM output
    """,
)

test(
    "WINDOW as last step",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        WINDOW('1 minute', event_time)
        INTO STREAM output
    """,
)

test(
    "missing INTO clause",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        WHERE temperature > 25.0
    """,
)

test(
    "missing FROM clause",
    expect_error=True,
    dsl="""
        WHERE temperature > 25.0
        INTO STREAM hot_rooms
    """,
)

# =============================================================================
# PROTO INSPECTION
# =============================================================================

print("\n=== PROTO INSPECTION ===\n")

show(
    "filter pipeline",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 25.0
    INTO STREAM hot_rooms
""",
)

show(
    "IStream pipeline (ISTREAM → CDC)",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA ISTREAM
    INTO STREAM newly_hot_rooms
""",
)

show(
    "window pipeline",
    """
    FROM STREAM temperature_readings
    WINDOW('1 minute', event_time)
    GROUP BY window, room_id
      AVG(temperature) AS avg_temp,
      COUNT(*) AS count
    INTO STREAM windowed_averages
""",
)

show(
    "join pipeline",
    """
    FROM STREAM temperature_readings
    ENRICH KV room_metadata ON room_id = room_id
    INTO STREAM enriched
""",
)

# =============================================================================
# Results
# =============================================================================

total = results["passed"] + results["failed"]
print(f"\n{'='*50}")
print(f"Results: {results['passed']}/{total} passed", end="")
if results["failed"] > 0:
    print(f"  ({results['failed']} failed)")
else:
    print("  — all good!")
print()

sys.exit(0 if results["failed"] == 0 else 1)
