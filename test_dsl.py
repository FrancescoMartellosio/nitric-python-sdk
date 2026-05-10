"""
Standalone test for the declarative DSL parser.
Runs without the Nitric runtime — parses DSL strings and
inspects the resulting Pipeline proto using betterproto.

Run with:
    cd python-sdk
    python test_dsl.py
"""

import sys
import traceback
import warnings

sys.path.insert(0, ".")

from nitric.resources.dsl.parser import parse, ParseError  # noqa: E402
from nitric.resources.dsl.validator import _step_type  # noqa: E402
from nitric.proto.analyticsservice.v1 import StreamOperator, PipelineMode  # noqa: E402

# ── Helpers ───────────────────────────────────────────────────────────────────

PASS = "\033[92m✓\033[0m"
FAIL = "\033[91m✗\033[0m"

results = {"passed": 0, "failed": 0}


def assert_eq(label: str, actual, expected):
    if actual != expected:
        raise AssertionError(f"  {label}: expected {expected!r}, got {actual!r}")


def step_type(p, index: int) -> str:
    """Return the operation type of the step at the given index."""
    return _step_type(p.steps[index])


def test(name: str, dsl: str, expect_error: bool = False, check=None):
    """
    Parse a DSL string and optionally run a check function on the proto.
    Suppresses the 'no name' warning since test pipelines have no name.
    """
    try:
        with warnings.catch_warnings():
            warnings.simplefilter("ignore")
            pipeline = parse(dsl)

        if expect_error:
            print(f"{FAIL} {name}")
            print(f"     Expected parse/validation error but got a valid pipeline")
            results["failed"] += 1
            return

        if check:
            check(pipeline)

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

    except Exception as e:
        print(f"{FAIL} {name}")
        traceback.print_exc()
        results["failed"] += 1


def show(name: str, dsl: str):
    """Parse and print the resulting proto — useful for inspection."""
    print(f"\n── {name} ──────────────────────────────────────")
    try:
        with warnings.catch_warnings():
            warnings.simplefilter("ignore")
            pipeline = parse(dsl)
        print(pipeline)
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
    check=lambda p: (
        assert_eq("source", p.source.stream.name, "temperature_readings"),
        assert_eq("sink", p.sink.stream.name, "hot_rooms"),
        assert_eq("steps", len(p.steps), 1),
        assert_eq("step0", step_type(p, 0), "filter"),
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
    check=lambda p: (
        assert_eq("steps", len(p.steps), 1),
        assert_eq("step type", step_type(p, 0), "filter"),
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

# ── SELECT / project ──────────────────────────────────────────────────────────

test(
    "SELECT with alias",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    SELECT
      room_id,
      ROUND(temperature, 2) AS temp_c,
      UPPER(unit) AS unit_upper
    INTO STREAM projected_readings
""",
    check=lambda p: (
        assert_eq("steps", len(p.steps), 2),
        assert_eq("project", step_type(p, 1), "project"),
        assert_eq("columns", len(p.steps[1].project.columns), 3),
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
    check=lambda p: (
        assert_eq("steps", len(p.steps), 3),
        assert_eq("window", step_type(p, 1), "window"),
        assert_eq("duration", p.steps[1].window.duration, "1 minute"),
        assert_eq("time_col", p.steps[1].window.time_column, "event_time"),
        assert_eq("aggregate", step_type(p, 2), "aggregate"),
        assert_eq("group_by", list(p.steps[2].aggregate.group_by), ["window", "room_id"]),
        assert_eq("agg count", len(p.steps[2].aggregate.aggs), 3),
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
    check=lambda p: (assert_eq("slide", p.steps[0].window.slide, "2 minutes"),),
)

test(
    "global aggregate no group_by",
    """
    FROM KV room_current_temp
    GROUP BY
      AVG(temperature) AS global_avg
    INTO TIMESERIES global_stats AS STATE
""",
)

# ── JOIN ──────────────────────────────────────────────────────────────────────

test(
    "inner join",
    """
    FROM STREAM temperature_readings
    INNER JOIN room_metadata ON room_id = room_id
    INTO STREAM enriched_readings
""",
    check=lambda p: (
        assert_eq("join type", p.steps[0].join.join_type, "inner"),
        assert_eq("right source", p.steps[0].join.right_source, "room_metadata"),
        assert_eq("left key", p.steps[0].join.left_key, "room_id"),
        assert_eq("right key", p.steps[0].join.right_key, "room_id"),
    ),
)

test(
    "left join enrichment",
    """
    FROM STREAM temperature_readings
    ENRICH room_metadata ON room_id = room_id
    INTO STREAM enriched_readings
""",
    check=lambda p: (assert_eq("join type", p.steps[0].join.join_type, "left"),),
)

test(
    "left_semi join",
    """
    FROM STREAM temperature_readings
    LEFT_SEMI JOIN room_metadata ON room_id = room_id
    INTO STREAM semi_enriched
""",
    check=lambda p: (assert_eq("join type", p.steps[0].join.join_type, "left_semi"),),
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
    check=lambda p: (
        assert_eq("steps", len(p.steps), 2),
        assert_eq("mts", step_type(p, 1), "map_to_state"),
        assert_eq("key col", p.steps[1].map_to_state.key_column, "room_id"),
        assert_eq("val col", p.steps[1].map_to_state.value_column, "temperature"),
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
    check=lambda p: (
        assert_eq("steps", len(p.steps), 2),
        assert_eq("step0", step_type(p, 0), "map_to_state"),
        assert_eq("step1", step_type(p, 1), "filter"),
    ),
)

# ── IStream / DStream / RStream ───────────────────────────────────────────────

test(
    "MAP TO DATA ISTREAM",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA ISTREAM
    INTO STREAM newly_hot_rooms
""",
    check=lambda p: (
        assert_eq("steps", len(p.steps), 3),
        assert_eq("mtd", step_type(p, 2), "map_to_data"),
        assert_eq("operator", p.steps[2].map_to_data.operator, StreamOperator.ISTREAM),
    ),
)

test(
    "MAP TO DATA DSTREAM",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA DSTREAM
    INTO STREAM cooling_rooms
""",
    check=lambda p: (assert_eq("operator", p.steps[2].map_to_data.operator, StreamOperator.DSTREAM),),
)

test(
    "MAP TO DATA RSTREAM",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA RSTREAM
    INTO STREAM hot_room_snapshot
""",
    check=lambda p: (assert_eq("operator", p.steps[2].map_to_data.operator, StreamOperator.RSTREAM),),
)

test(
    "MAP TO DATA RSTREAM with schedule",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    MAP TO DATA RSTREAM ON SCHEDULE '0 * * * *'
    INTO STREAM hourly_snapshot
""",
    check=lambda p: (assert_eq("schedule", p.steps[1].map_to_data.schedule, "0 * * * *"),),
)

# ── Source types ──────────────────────────────────────────────────────────────

test(
    "KV source STATE pipeline",
    """
    FROM KV room_current_temp
    WHERE temperature > 25.0
    INTO KV hot_room_state
""",
    check=lambda p: (
        assert_eq("source", p.source.kv.name, "room_current_temp"),
        assert_eq("sink", p.sink.kv.name, "hot_room_state"),
    ),
)

test(
    "TIMESERIES source AS DATA",
    """
    FROM TIMESERIES sensor_history AS DATA
    WHERE temperature > 0.0
    GROUP BY room_id
      AVG(temperature) AS avg_temp
    INTO TIMESERIES room_averages AS DATA
""",
)

test(
    "TIMESERIES source AS STATE",
    """
    FROM TIMESERIES room_snapshots AS STATE
    WHERE temperature > 25.0
    INTO KV hot_rooms
""",
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
    check=lambda p: (
        assert_eq("steps", len(p.steps), 4),
        assert_eq("step0", step_type(p, 0), "filter"),
        assert_eq("step1", step_type(p, 1), "map_to_state"),
        assert_eq("step2", step_type(p, 2), "filter"),
        assert_eq("step3", step_type(p, 3), "map_to_data"),
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

for jt, expected in [("INNER", "inner"), ("LEFT", "left"), ("LEFT_SEMI", "left_semi")]:
    test(
        f"join type {jt}",
        f"""
        FROM STREAM readings
        {jt} JOIN metadata ON room_id = room_id
        INTO STREAM enriched
    """,
        check=lambda p, e=expected: (assert_eq("join type", p.steps[0].join.join_type, e),),
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
    "aggregate group_by includes state key",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        MAP TO STATE KEY room_id VALUE temperature USING REPLACE
        GROUP BY room_id
          AVG(temperature) AS avg_temp
        INTO KV output
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

test(
    "unknown stream operator",
    expect_error=True,
    dsl="""
        FROM STREAM temperature_readings
        MAP TO STATE KEY room_id VALUE temperature USING REPLACE
        MAP TO DATA CSTREAM
        INTO STREAM output
    """,
)

# =============================================================================
# PROTO INSPECTION
# =============================================================================

print("\n=== PROTO INSPECTION ===\n")

show(
    "filter pipeline proto",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 25.0
    INTO STREAM hot_rooms
""",
)

show(
    "IStream pipeline proto",
    """
    FROM STREAM temperature_readings
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA ISTREAM
    INTO STREAM newly_hot_rooms
""",
)

show(
    "window pipeline proto",
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
    "round trip pipeline proto",
    """
    FROM STREAM temperature_readings
    WHERE temperature > 0.0
    MAP TO STATE KEY room_id VALUE temperature USING REPLACE
    WHERE temperature > 30.0
    MAP TO DATA DSTREAM
    INTO STREAM cooling_rooms
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
