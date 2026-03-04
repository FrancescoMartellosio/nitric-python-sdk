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
from typing import List, Optional

from grpclib import GRPCError
from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.channel import ChannelManager

from nitric.proto.spark.v1 import SparkStub, SparkSubmitRequest
from nitric.proto.resources.v1 import (
    ResourceDeclareRequest,
    ResourceIdentifier,
    ResourceType,
    SparkResource,
)
from nitric.resources.resource import Resource


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
