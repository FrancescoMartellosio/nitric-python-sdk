from __future__ import annotations

from typing import Dict, List, Optional
from grpclib import GRPCError

from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.channel import ChannelManager

from nitric.proto.analytics.v1 import (
    AnalyticsServiceStub,
    ExecuteRequest,
    PlanResponse,
    Pipeline,
    PipelineMode,
    Source,
    Sink,
    StreamSource,
    TableSource,
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
    Expression,
    ColumnExpr,
    StateOperation,
    MapToDataStrategy,
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

# ─── Fluent pipeline builder ──────────────────────────────────────────────────


class PipelineQuery:
    """Fluent builder for an analytics pipeline."""

    def __init__(
        self,
        service_name: str,
        pipeline_name: str,
        source: Source,
        mode: PipelineMode,
        stub: AnalyticsServiceStub,
    ):
        """
        Initialize a new PipelineQuery.

        Parameters
        ----------
        service_name : str
            The name of the service.
        pipeline_name : str
            The name of the pipeline.
        source : Source
            The source of the data.
        mode : PipelineMode
            The mode of the pipeline.
        stub : AnalyticsServiceStub
            The gRPC stub for the service.

        """
        self._service_name = service_name
        self._pipeline_name = pipeline_name
        self._source = source
        self._mode = mode
        self._stub = stub
        self._steps: List[Step] = []
        self._sink: Optional[Sink] = None
        self._hints: Hints = Hints()

    def to_stream(self, name: str) -> PipelineQuery:
        """Write output to a stream sink (e.g. Kafka topic)."""
        self._sink = Sink(stream=StreamSink(name=name))
        return self

    def to_table(self, name: str, format: str = "") -> PipelineQuery:
        """Write output to a table or file sink."""
        self._sink = Sink(table=TableSink(name=name, format=format))
        return self

    def with_engine(self, engine: EnginePreference) -> PipelineQuery:
        """Force a specific engine instead of auto-routing."""
        self._hints.engine = engine
        return self

    def with_watermarks(self, watermarks: Dict[str, str]) -> PipelineQuery:
        """Set watermark delays keyed by logical source name."""
        self._hints.watermarks.update(watermarks)
        return self

    def with_shuffle_partitions(self, n: int) -> PipelineQuery:
        """Set the number of Spark shuffle partitions."""
        self._hints.shuffle_partitions = n
        return self

    def with_checkpoint_base(self, path: str) -> PipelineQuery:
        """Set the base path for Spark streaming checkpoints."""
        self._hints.checkpoint_base = path
        return self

    def with_partition_column(self, column: str) -> PipelineQuery:
        """Partition Spark batch output by this column."""
        self._hints.partition_column = column
        return self

    def with_adaptive_query(self, enabled: bool = True) -> PipelineQuery:
        """Enable or disable Spark Adaptive Query Execution (batch only)."""
        self._hints.adaptive_query = enabled
        return self

    def with_connection(self, name: str) -> PipelineQuery:
        """Set the logical database connection name for materialized views."""
        self._hints.connection_name = name
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

    def filter(self, column: str, operator: str, value: str) -> PipelineQuery:
        """
        Keep rows satisfying a predicate. Discard the rest.

        Valid in both DATA and STATE mode. Compound predicates: chain multiple
        filter() calls (implicit AND).

        Example:
        -------
        .filter("temperature", "gt", "0.0")
        .filter("building", "eq", "A")

        """
        self._steps.append(
            Step(
                filter=Filter(
                    predicate=Expression(
                        column=column,
                        operator=operator,
                        value=str(value),
                    )
                )
            )
        )
        return self

    def project(self, *columns: ColumnExpr) -> PipelineQuery:
        """
        Transform, rename, or derive columns row by row.

        Valid in both DATA and STATE mode. Use the col() helper to build
        ColumnExpr values.

        Example:
        -------
        .project(
            col("temp_c", "temp_f", "MULTIPLY:1.8"),
            col("room_id", "room_id"),
        )

        """
        self._steps.append(Step(project=Project(columns=list(columns))))
        return self

    def window(
        self,
        duration: str,
        time_column: str,
        slide: str = "",
    ) -> PipelineQuery:
        """Group events into time-bounded buckets."""
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
        """Collapse rows into groups and compute summary values."""
        self._steps.append(
            Step(
                aggregate=Aggregate(
                    group_by=group_by,
                    aggs=aggs,
                )
            )
        )
        return self

    def join(
        self,
        right_source: str,
        left_key: str,
        right_key: str,
        join_type: str = "inner",
    ) -> PipelineQuery:
        """Combine the current dataset with another source by matching keys."""
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
        """Boundary crossing: DATA -> STATE."""
        self._steps.append(
            Step(
                map_to_state=MapToState(
                    key_column=key_column,
                    value_column=value_column,
                    operation=operation,
                )
            )
        )
        return self

    def map_to_data(
        self,
        strategy: MapToDataStrategy,
        schedule: str = "",
    ) -> PipelineQuery:
        """Boundary crossing: STATE -> DATA."""
        self._steps.append(
            Step(
                map_to_data=MapToData(
                    strategy=strategy,
                    schedule=schedule,
                )
            )
        )
        return self

    async def execute(self) -> str:
        """Submit the pipeline for execution."""
        if self._sink is None:
            raise ValueError("No sink configured. Call to_stream() or to_table() before execute().")

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

        Use during development to understand which engine would be
        selected and why, and to surface validation errors early.

        Example:
        -------
        plan = await query.plan()
        print(plan.selected_engine)

        """
        req = self._build_request()
        try:
            return await self._stub.plan(req)
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _build_request(self) -> ExecuteRequest:
        return ExecuteRequest(
            pipeline=Pipeline(
                name=self._pipeline_name,
                mode=self._mode,
                source=self._source,
                steps=self._steps,
                sink=self._sink,
            ),
            hints=self._hints,
        )


# ─── AnalyticsRef ─────────────────────────────────────────────────────────────


class AnalyticsRef:
    """Runtime reference to the analytics service for pipeline submission."""

    def __init__(self, name: str):
        """
        Initialize a new AnalyticsRef.

        Parameters
        ----------
        name : str
            The name of the analytics service.

        """
        self._name = name
        self._channel = ChannelManager.get_channel()
        self._stub = AnalyticsServiceStub(channel=self._channel)

    def from_stream(self, source_name: str, pipeline_name: str) -> PipelineQuery:
        """
        Start a DATA pipeline reading from an event stream.

        Example:
        -------
        ref.from_stream("temperature_readings", "room-temp-pipeline")
           .filter("value", "gt", "0.0")
           .execute()

        """
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(stream=StreamSource(name=source_name)),
            mode=PipelineMode.DATA,
            stub=self._stub,
        )

    def from_table(
        self,
        source_name: str,
        pipeline_name: str,
        mode: PipelineMode = PipelineMode.DATA,
        format: str = "",
    ) -> PipelineQuery:
        """Start a pipeline reading from a table or file."""
        return PipelineQuery(
            service_name=self._name,
            pipeline_name=pipeline_name,
            source=Source(table=TableSource(name=source_name, format=format)),
            mode=mode,
            stub=self._stub,
        )


# ─── Helpers ──────────────────────────────────────────────────────────────────


def col(
    input_column: str,
    output_column: str,
    transform: str = "",
) -> ColumnExpr:
    """
    Build a ColumnExpr for use in project().

    Example:
    -------
    col("temp_c", "temp_f", "MULTIPLY:1.8")
    col("room_id", "room_id")

    """
    return ColumnExpr(
        input_column=input_column,
        output_column=output_column,
        transform=transform,
    )


def agg(
    output_name: str,
    function: str,
    column: str,
) -> AggExpr:
    """
    Build an AggExpr for use in aggregate().

    Example:
    -------
    agg("avg_temp", "AVG", "temperature")
    agg("room_count", "COUNT", "room_id")

    """
    return AggExpr(
        output_name=output_name,
        function=function,
        column=column,
    )


# ─── Resource ─────────────────────────────────────────────────────────────────


class AnalyticsService(Resource):
    """An AnalyticsService resource used for deployment."""

    def __init__(self, name: str):
        """
        Initialize a new AnalyticsService.

        Parameters
        ----------
        name : str
            The name of the analytics service.

        """
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
        """
        Return a runtime reference for submitting pipelines.

        Example:
        -------
        svc = analytics_service("my-analytics")
        ref = svc.allow()

        """
        return AnalyticsRef(self.name)


# ─── Entry point ──────────────────────────────────────────────────────────────


def analytics_service(name: str) -> AnalyticsService:
    """
    Define an Analytics Service resource in a Nitric application.

    Example:
    -------
    from nitric.resources import analytics_service
    svc = analytics_service("my-analytics")

    """
    return Nitric._create_resource(AnalyticsService, name)
