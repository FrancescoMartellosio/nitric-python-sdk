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

from grpclib import GRPCError
from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error
from nitric.proto.resources.v1 import (
    ResourceDeclareRequest,
    ResourceIdentifier,
    ResourceType,
    SparkResource,  # The specific message from your proto
)
from nitric.resources.resource import Resource


class Spark(Resource):
    """A Spark resource used for deployment."""

    async def _register(self) -> None:
        """Register the Spark resource with the Nitric Resource Server."""
        try:
            # This is the call that triggers the deployment logic in the provider
            await self._resources_stub.declare(
                resource_declare_request=ResourceDeclareRequest(
                    id=self._to_resource_id(),
                    # This must match the field name in your ResourceDeclareRequest proto
                    spark=SparkResource(),
                )
            )
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _to_resource_id(self) -> ResourceIdentifier:
        # ResourceType.Spark must exist in your generated proto enum
        return ResourceIdentifier(name=self.name, type=ResourceType.Spark)


def spark(name: str) -> Spark:
    """Entry point for defining a Spark resource in a Nitric application."""
    return Nitric._create_resource(Spark, name)
