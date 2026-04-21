from __future__ import annotations

from nitric.proto.analyticsservice.v1 import (
    Expression,
    ComparisonExpr,
    LogicalExpr,
    LogicalOperator,
    FunctionCallExpr,
    ArithmeticExpr,
    ColumnRef,
    Literal,
)

# ── Known built-in functions ──────────────────────────────────────────────────

BUILTIN_SCALAR_FUNCTIONS = {
    "UPPER",
    "LOWER",
    "ROUND",
    "ABS",
    "FLOOR",
    "CEIL",
    "CAST",
    "COALESCE",
    "LENGTH",
    "SUBSTRING",
    "CONCAT",
    "TO_TIMESTAMP",
    "DATE_FORMAT",
    "YEAR",
    "MONTH",
    "DAY",
    "HOUR",
    "IS_NULL",
    "IS_NOT_NULL",
}

BUILTIN_AGG_FUNCTIONS = {
    "SUM",
    "COUNT",
    "AVG",
    "MAX",
    "MIN",
    "LAST",
    "FIRST",
    "STDDEV",
    "VARIANCE",
    "COLLECT_LIST",
}


class ExpressionCompiler:
    """
    Converts an ANTLR expression parse tree node into an Expression proto.

    Grammar-agnostic — the expression rules are identical in both the
    functional and declarative grammars, so this class is shared between
    both compilers.

    The context type names (AndExprContext, CompExprContext, etc.) come
    from the ANTLR-generated parser and are matched by string name so
    this class does not need to import the generated code directly.
    """

    def compile(self, ctx) -> Expression:
        ctx_type = type(ctx).__name__

        # ── Logical ───────────────────────────────────────────────────────────

        if ctx_type == "AndExprContext":
            return Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.AND,
                    operands=[
                        self.compile(ctx.expression(0)),
                        self.compile(ctx.expression(1)),
                    ],
                )
            )

        if ctx_type == "OrExprContext":
            return Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.OR,
                    operands=[
                        self.compile(ctx.expression(0)),
                        self.compile(ctx.expression(1)),
                    ],
                )
            )

        if ctx_type == "NotExprContext":
            return Expression(
                logical=LogicalExpr(operator=LogicalOperator.NOT, operands=[self.compile(ctx.expression())])
            )

        # ── Comparison ────────────────────────────────────────────────────────

        if ctx_type == "CompExprContext":
            return Expression(
                comparison=ComparisonExpr(
                    left=self.compile(ctx.expression(0)),
                    operator=ctx.compOp().getText(),
                    right=self.compile(ctx.expression(1)),
                )
            )

        # ── Arithmetic ────────────────────────────────────────────────────────

        if ctx_type == "ArithExprContext":
            return Expression(
                arithmetic=ArithmeticExpr(
                    left=self.compile(ctx.expression(0)),
                    operator=ctx.arithOp().getText(),
                    right=self.compile(ctx.expression(1)),
                )
            )

        # ── BETWEEN — desugars to >= AND <= ───────────────────────────────────

        if ctx_type == "BetweenExprContext":
            subject = self.compile(ctx.expression(0))
            lower = self.compile(ctx.expression(1))
            upper = self.compile(ctx.expression(2))
            return Expression(
                logical=LogicalExpr(
                    operator=LogicalOperator.AND,
                    operands=[
                        Expression(
                            comparison=ComparisonExpr(
                                left=lower,
                                operator=">=",
                                right=subject,
                            )
                        ),
                        Expression(
                            comparison=ComparisonExpr(
                                left=subject,
                                operator="<=",
                                right=upper,
                            )
                        ),
                    ],
                )
            )

        # ── IS NULL / IS NOT NULL ─────────────────────────────────────────────

        if ctx_type == "IsNullExprContext":
            return Expression(function=FunctionCallExpr(name="IS_NULL", args=[self.compile(ctx.expression())]))

        if ctx_type == "IsNotNullExprContext":
            return Expression(function=FunctionCallExpr(name="IS_NOT_NULL", args=[self.compile(ctx.expression())]))

        # ── Built-in function call ─────────────────────────────────────────────

        if ctx_type == "FuncExprContext":
            return self._compile_builtin(ctx.builtinFunc())

        # ── Column reference ──────────────────────────────────────────────────

        if ctx_type == "ColRefContext":
            return Expression(column_ref=ColumnRef(name=self._unquote(ctx.name().getText())))

        # ── Literal ───────────────────────────────────────────────────────────

        if ctx_type == "LitExprContext":
            return self._compile_literal(ctx.literal())

        # ── Parenthesised expression ──────────────────────────────────────────

        if ctx_type == "ParenExprContext":
            return self.compile(ctx.expression())

        raise ValueError(f"Unknown expression context: {ctx_type}. " f"This is a compiler bug — please report it.")

    def _compile_builtin(self, ctx) -> Expression:
        """
        Compile a built-in scalar function call.
        Function name is the first token of the rule (e.g. UPPER, ROUND).
        """
        func_name = ctx.getChild(0).getText().upper()

        if func_name not in BUILTIN_SCALAR_FUNCTIONS:
            raise ValueError(
                f"Unknown built-in function: '{func_name}'. "
                f"Valid scalar functions: {sorted(BUILTIN_SCALAR_FUNCTIONS)}"
            )

        args = []

        # expression() children — the main arguments
        if hasattr(ctx, "expression"):
            exprs = ctx.expression()
            if not isinstance(exprs, list):
                exprs = [exprs] if exprs is not None else []
            for expr in exprs:
                args.append(self.compile(expr))

        # NUMBER tokens — e.g. ROUND(x, 2), SUBSTRING(x, 1, 3)
        if hasattr(ctx, "NUMBER"):
            numbers = ctx.NUMBER()
            if not isinstance(numbers, list):
                numbers = [numbers] if numbers is not None else []
            for n in numbers:
                text = n.getText()
                lit = Literal()
                if "." in text:
                    lit.double_val = float(text)
                else:
                    lit.int_val = int(text)
                args.append(Expression(literal=lit))

        # STRING tokens — e.g. TO_TIMESTAMP(x, 'yyyy-MM-dd')
        if hasattr(ctx, "STRING"):
            strings = ctx.STRING()
            if not isinstance(strings, list):
                strings = [strings] if strings is not None else []
            for s in strings:
                args.append(Expression(literal=Literal(string_val=self._unquote(s.getText()))))

        # typeName — e.g. CAST(x, DOUBLE)
        if hasattr(ctx, "typeName") and ctx.typeName() is not None:
            args.append(Expression(literal=Literal(string_val=ctx.typeName().getText().upper())))

        return Expression(function=FunctionCallExpr(name=func_name, args=args))

    def _compile_literal(self, ctx) -> Expression:
        ctx_type = type(ctx).__name__

        if ctx_type == "NumLitContext":
            text = ctx.NUMBER().getText()
            lit = Literal()
            if "." in text:
                lit.double_val = float(text)
            else:
                lit.int_val = int(text)
            return Expression(literal=lit)

        if ctx_type == "StrLitContext":
            return Expression(literal=Literal(string_val=self._unquote(ctx.STRING().getText())))

        if ctx_type == "TrueLitContext":
            return Expression(literal=Literal(bool_val=True))

        if ctx_type == "FalseLitContext":
            return Expression(literal=Literal(bool_val=False))

        raise ValueError(f"Unknown literal type: {ctx_type}")

    def _unquote(self, s: str) -> str:
        if s and len(s) >= 2 and s[0] in ('"', "'") and s[-1] == s[0]:
            return s[1:-1]
        return s
