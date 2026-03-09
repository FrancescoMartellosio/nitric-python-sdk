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
from __future__ import annotations
from typing import List, Optional, Union  # Added Union

from grpclib import GRPCError
from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.channel import ChannelManager

# Updated imports from your new proto generation
from nitric.proto.spark.v1 import SparkStub, SparkSubmitRequest, SparkExecuteRequest, SparkInstruction
from nitric.proto.resources.v1 import (
    ResourceDeclareRequest,
    ResourceIdentifier,
    ResourceType,
    SparkResource,
)
from nitric.resources.resource import Resource


class SparkQuery:
    """A fluent query builder for Spark operations."""

    def __init__(self, cluster_name: str, table_pattern: str, stub: SparkStub):
        """Initialize a new SparkQuery."""
        self._cluster_name = cluster_name
        self._table_pattern = table_pattern
        self._stub = stub
        self._instructions: List[SparkInstruction] = []

    def filter(self, column: str, operator: str, value: Union[str, int, float]) -> SparkQuery:
        """Add a filter instruction and return self for chaining."""
        self._instructions.append(SparkInstruction(type="FILTER", column=column, operator=operator, value=str(value)))
        return self

    def map(self, column: str, operator: str, value: Optional[Union[str, int, float]] = None) -> SparkQuery:
        """Add a map/transform instruction and return self for chaining."""
        self._instructions.append(
            SparkInstruction(
                type="MAP", column=column, operator=operator, value=str(value) if value is not None else ""
            )
        )
        return self

    async def sum(self, column: str) -> float:
        """Terminal operation: Add sum instruction and execute."""
        self._instructions.append(SparkInstruction(type="SUM", column=column))
        return await self._execute()

    async def save_to(self, target_table: str) -> float:
        """Terminal operation: Add save instruction and execute."""
        self._instructions.append(SparkInstruction(type="SAVE", value=target_table))
        return await self._execute()

    async def _execute(self) -> float:
        """Construct the final request and send it to the provider."""
        req = SparkExecuteRequest(
            cluster_name=self._cluster_name, table_pattern=self._table_pattern, instructions=self._instructions
        )
        try:
            # Note: Ensure the keyword argument matches your generated SparkStub
            response = await self._stub.execute(spark_execute_request=req)
            return response.value
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err


class SparkRef:
    """A reference to a deployed Spark cluster, used for job submission at runtime."""

    def __init__(self, name: str):
        """
        Initialize a new SparkRef.

        :param name: The name of the spark cluster
        """
        self._channel = ChannelManager.get_channel()
        self._stub = SparkStub(channel=self._channel)
        self.name = name

    def from_path(self, table_pattern: str) -> SparkQuery:
        """
        Start a fluent query on a specific Redis key pattern.

        Example: cluster.from_path("sales:raw:*")
        """
        return SparkQuery(self.name, table_pattern, self._stub)

    async def execute(self, table_pattern: str) -> float:
        """Directly invoke the Execute RPC with hardcoded test instructions."""
        # Hardcoding values to test the gRPC bridge
        test_instructions = [
            SparkInstruction(type="FILTER", column="status", operator="eq", value="active"),
            SparkInstruction(type="SUM", column="amount", operator="", value=""),
        ]

        req = SparkExecuteRequest(cluster_name=self.name, table_pattern=table_pattern, instructions=test_instructions)

        try:
            print(f"📡 [SDK] Sending Execute Request for cluster: {self.name}")
            response = await self._stub.execute(req)
            return response.value
        except GRPCError as grpc_err:
            # This is where we will see the 'Unknown Service' if the CLI is blocking it
            raise exception_from_grpc_error(grpc_err) from grpc_err

    async def submit(self, jar_path: str, args: Optional[List[str]] = None) -> str:
        """
        Submit a job to the Spark cluster.

        Return the job_id from the provider.
        """
        req = SparkSubmitRequest(
            cluster_name=self.name,
            job_jar_path=jar_path,
            arguments=args or [],
        )

        try:
            response = await self._stub.submit(spark_submit_request=req)
            return response.job_id
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err


class Spark(Resource):
    """A Spark resource used for deployment."""

    def __init__(
        self,
        name: str,
        workers_per_host: int = 1,
        memory_gb: int = 1,
        cpus_per_worker: int = 1,
    ):
        """
        Initialize a new Spark cluster resource.

        :param name: The name of the spark cluster
        :param workers_per_host: Number of workers per host
        :param memory_gb: Memory per worker in GB
        :param cpus_per_worker: CPU count per worker
        """
        super().__init__(name)
        self.workers_per_host = workers_per_host
        self.memory_gb = memory_gb
        self.cpus_per_worker = cpus_per_worker

    async def _register(self) -> None:
        """Register the Spark resource with the Nitric Resource Server."""
        try:
            await self._resources_stub.declare(
                resource_declare_request=ResourceDeclareRequest(
                    id=self._to_resource_id(),
                    spark=SparkResource(
                        workers_per_host=self.workers_per_host,
                        memory_gb=self.memory_gb,
                        cpus_per_worker=self.cpus_per_worker,
                    ),
                )
            )
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _to_resource_id(self) -> ResourceIdentifier:
        """
        Return the resource identifier for this Spark resource.

        Internal use only.
        """
        return ResourceIdentifier(name=self.name, type=ResourceType.Spark)

    def allow(self) -> SparkRef:
        """
        Return a SparkRef to allow submitting jobs to this cluster.

        This reference can be used to invoke runtime methods like submit.
        """
        return SparkRef(self.name)


def spark(name: str, workers_per_host: int = 1, memory_gb: int = 1, cpus_per_worker: int = 1) -> Spark:
    """
    Entry point for defining a Spark resource in a Nitric application.

    :param name: The name of the spark cluster
    :param workers_per_host: Number of workers per host
    :param memory_gb: Memory per worker in GB
    :param cpus_per_worker: CPU count per worker
    """
    return Nitric._create_resource(
        Spark,
        name,
        workers_per_host=workers_per_host,
        memory_gb=memory_gb,
        cpus_per_worker=cpus_per_worker,
    )
