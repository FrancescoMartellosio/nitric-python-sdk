from __future__ import annotations

from grpclib import GRPCError
from nitric.application import Nitric
from nitric.exception import exception_from_grpc_error

from nitric.proto.resources.v1 import (
    ResourceDeclareRequest,
    ResourceIdentifier,
    ResourceType,
    AnalyticsServiceResource,
)
from nitric.resources.resource import Resource


class AnalyticsService(Resource):
    """An AnalyticsService resource used for deployment."""

    def __init__(self, name: str):
        """
        Initialize a new Analytics Service resource.

        :param name: The name of the analytics service
        """
        super().__init__(name)

    async def _register(self) -> None:
        """Register the AnalyticsService resource with the Nitric Resource Server."""
        try:
            await self._resources_stub.declare(
                resource_declare_request=ResourceDeclareRequest(
                    id=self._to_resource_id(),
                    # Empty configuration as per the proto definition
                    analytics_service=AnalyticsServiceResource(),
                )
            )
        except GRPCError as grpc_err:
            raise exception_from_grpc_error(grpc_err) from grpc_err

    def _to_resource_id(self) -> ResourceIdentifier:
        """
        Return the resource identifier for this AnalyticsService resource.

        Internal use only.
        """
        return ResourceIdentifier(name=self.name, type=ResourceType.AnalyticsService)


def analytics_service(name: str) -> AnalyticsService:
    """
    Entry point for defining an Analytics Service in a Nitric application.

    :param name: The name of the analytics service
    """
    return Nitric._create_resource(
        AnalyticsService,
        name,
    )
