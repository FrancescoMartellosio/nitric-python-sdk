from __future__ import annotations

import os
import warnings
from typing import List

from antlr4 import CommonTokenStream, InputStream
from antlr4.error.ErrorListener import ErrorListener

from nitric.proto.analyticsservice.v1 import Pipeline
from nitric.resources.dsl.validator import PipelineValidator


class ParseError(Exception):
    """Raised when the declarative DSL has syntax errors."""

    def __init__(self, errors: List[str]):
        self.errors = errors
        super().__init__("Pipeline parse failed:\n" + "\n".join(errors))


class _ErrorCollector(ErrorListener):
    """ANTLR error listener that collects all errors into a list."""

    def __init__(self):
        super().__init__()
        self.errors: List[str] = []

    def syntaxError(self, recognizer, offending_symbol, line, col, msg, e):
        self.errors.append(f"Line {line}:{col} — {msg}")


def parse(source: str) -> Pipeline:
    """
    Parse a declarative pipeline definition and return a Pipeline proto.

    Accepts either a DSL string or a path to a .pipeline file.
    Runs both syntactic (ANTLR grammar) and semantic (validator) checks.
    Semantic warnings are emitted via Python's warnings module.

    Args:
        source: Declarative DSL text, or path to a .pipeline file.

    Returns:
        A validated Pipeline proto ready to submit to the analytics service.

    Raises:
        ParseError: if the DSL has syntax errors.
        ValueError: if the pipeline fails semantic validation.

    Examples:
        # From string
        pipeline = parse('''
            FROM STREAM temperature_readings
            WHERE temperature > 25.0
            INTO STREAM hot_rooms
        ''')

        # From file
        pipeline = parse('/pipelines/hot_rooms.pipeline')
    """
    # Support file paths transparently
    if os.path.isfile(source):
        with open(source, "r", encoding="utf-8") as f:
            source = f.read()

    pipeline = _parse_declarative(source)

    # Semantic validation after parsing
    validator = PipelineValidator()
    result = validator.validate(pipeline)

    # Surface warnings before raising so callers see them
    for w in result.warnings:
        warnings.warn(str(w), stacklevel=3)

    result.raise_if_invalid()

    return pipeline


def _parse_declarative(source: str) -> Pipeline:
    """
    Run the ANTLR lexer + parser and compile the AST to a Pipeline proto.
    Does NOT run semantic validation — call parse() for the full pipeline.
    """
    from nitric.resources.dsl.generated.declarative.DeclarativeLexer import DeclarativeLexer
    from nitric.resources.dsl.generated.declarative.DeclarativeParser import DeclarativeParser
    from nitric.resources.dsl.declarative_compiler import DeclarativeCompiler

    stream = InputStream(source)
    lexer = DeclarativeLexer(stream)
    tokens = CommonTokenStream(lexer)
    parser = DeclarativeParser(tokens)

    errors = _ErrorCollector()
    lexer.removeErrorListeners()
    parser.removeErrorListeners()
    lexer.addErrorListener(errors)
    parser.addErrorListener(errors)

    tree = parser.pipeline()

    if errors.errors:
        raise ParseError(errors.errors)

    return DeclarativeCompiler().compile(tree)
