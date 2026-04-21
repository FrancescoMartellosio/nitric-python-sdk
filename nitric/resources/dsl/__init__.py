"""
nitric.resources.dsl
~~~~~~~~~~~~~~~~~~~~

Declarative pipeline grammar support for the Nitric Analytics SDK.

The primary entry point is parse():

    from nitric.resources.dsl import parse
    pipeline = parse('FROM STREAM events WHERE temperature > 25.0 INTO STREAM hot')

For direct use from AnalyticsRef, use execute_dsl() or pipeline_from_file()
on the ref object returned by analytics_service("name").allow().
"""

from nitric.resources.dsl.parser import parse, ParseError

__all__ = ["parse", "ParseError"]
