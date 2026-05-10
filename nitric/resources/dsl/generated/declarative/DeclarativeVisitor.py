# Generated from Declarative.g4 by ANTLR 4.13.2
from antlr4 import *
if "." in __name__:
    from .DeclarativeParser import DeclarativeParser
else:
    from DeclarativeParser import DeclarativeParser

# This class defines a complete generic visitor for a parse tree produced by DeclarativeParser.

class DeclarativeVisitor(ParseTreeVisitor):

    # Visit a parse tree produced by DeclarativeParser#pipeline.
    def visitPipeline(self, ctx:DeclarativeParser.PipelineContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#dataPipeline.
    def visitDataPipeline(self, ctx:DeclarativeParser.DataPipelineContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#fromStream.
    def visitFromStream(self, ctx:DeclarativeParser.FromStreamContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#fromTimeseriesData.
    def visitFromTimeseriesData(self, ctx:DeclarativeParser.FromTimeseriesDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#intoStream.
    def visitIntoStream(self, ctx:DeclarativeParser.IntoStreamContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#intoTimeseriesData.
    def visitIntoTimeseriesData(self, ctx:DeclarativeParser.IntoTimeseriesDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#whereData.
    def visitWhereData(self, ctx:DeclarativeParser.WhereDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#selectData.
    def visitSelectData(self, ctx:DeclarativeParser.SelectDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#windowGroupBy.
    def visitWindowGroupBy(self, ctx:DeclarativeParser.WindowGroupByContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#groupByData.
    def visitGroupByData(self, ctx:DeclarativeParser.GroupByDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinData.
    def visitJoinData(self, ctx:DeclarativeParser.JoinDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#statePipeline.
    def visitStatePipeline(self, ctx:DeclarativeParser.StatePipelineContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#fromKV.
    def visitFromKV(self, ctx:DeclarativeParser.FromKVContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#fromTimeseriesState.
    def visitFromTimeseriesState(self, ctx:DeclarativeParser.FromTimeseriesStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#intoKV.
    def visitIntoKV(self, ctx:DeclarativeParser.IntoKVContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#intoTimeseriesState.
    def visitIntoTimeseriesState(self, ctx:DeclarativeParser.IntoTimeseriesStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#whereState.
    def visitWhereState(self, ctx:DeclarativeParser.WhereStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#selectState.
    def visitSelectState(self, ctx:DeclarativeParser.SelectStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#groupByState.
    def visitGroupByState(self, ctx:DeclarativeParser.GroupByStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinState.
    def visitJoinState(self, ctx:DeclarativeParser.JoinStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#crossToState.
    def visitCrossToState(self, ctx:DeclarativeParser.CrossToStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#crossToData.
    def visitCrossToData(self, ctx:DeclarativeParser.CrossToDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#streamOperator.
    def visitStreamOperator(self, ctx:DeclarativeParser.StreamOperatorContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#whereClause.
    def visitWhereClause(self, ctx:DeclarativeParser.WhereClauseContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#selectClause.
    def visitSelectClause(self, ctx:DeclarativeParser.SelectClauseContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#aliasedSelect.
    def visitAliasedSelect(self, ctx:DeclarativeParser.AliasedSelectContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#columnSelect.
    def visitColumnSelect(self, ctx:DeclarativeParser.ColumnSelectContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#starSelect.
    def visitStarSelect(self, ctx:DeclarativeParser.StarSelectContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#windowClause.
    def visitWindowClause(self, ctx:DeclarativeParser.WindowClauseContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#groupByClause.
    def visitGroupByClause(self, ctx:DeclarativeParser.GroupByClauseContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#aggExpr.
    def visitAggExpr(self, ctx:DeclarativeParser.AggExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#explicitJoin.
    def visitExplicitJoin(self, ctx:DeclarativeParser.ExplicitJoinContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#enrichJoin.
    def visitEnrichJoin(self, ctx:DeclarativeParser.EnrichJoinContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinStream.
    def visitJoinStream(self, ctx:DeclarativeParser.JoinStreamContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinKV.
    def visitJoinKV(self, ctx:DeclarativeParser.JoinKVContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinTimeseriesData.
    def visitJoinTimeseriesData(self, ctx:DeclarativeParser.JoinTimeseriesDataContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinTimeseriesState.
    def visitJoinTimeseriesState(self, ctx:DeclarativeParser.JoinTimeseriesStateContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#isNotNullExpr.
    def visitIsNotNullExpr(self, ctx:DeclarativeParser.IsNotNullExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#notExpr.
    def visitNotExpr(self, ctx:DeclarativeParser.NotExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#funcExpr.
    def visitFuncExpr(self, ctx:DeclarativeParser.FuncExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#isNullExpr.
    def visitIsNullExpr(self, ctx:DeclarativeParser.IsNullExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#compExpr.
    def visitCompExpr(self, ctx:DeclarativeParser.CompExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#orExpr.
    def visitOrExpr(self, ctx:DeclarativeParser.OrExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#colRef.
    def visitColRef(self, ctx:DeclarativeParser.ColRefContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#litExpr.
    def visitLitExpr(self, ctx:DeclarativeParser.LitExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#parenExpr.
    def visitParenExpr(self, ctx:DeclarativeParser.ParenExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#arithExpr.
    def visitArithExpr(self, ctx:DeclarativeParser.ArithExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#andExpr.
    def visitAndExpr(self, ctx:DeclarativeParser.AndExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#betweenExpr.
    def visitBetweenExpr(self, ctx:DeclarativeParser.BetweenExprContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#builtinFunc.
    def visitBuiltinFunc(self, ctx:DeclarativeParser.BuiltinFuncContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#aggFunc.
    def visitAggFunc(self, ctx:DeclarativeParser.AggFuncContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#compOp.
    def visitCompOp(self, ctx:DeclarativeParser.CompOpContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#arithOp.
    def visitArithOp(self, ctx:DeclarativeParser.ArithOpContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#joinType.
    def visitJoinType(self, ctx:DeclarativeParser.JoinTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#stateOp.
    def visitStateOp(self, ctx:DeclarativeParser.StateOpContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#strategy.
    def visitStrategy(self, ctx:DeclarativeParser.StrategyContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#typeName.
    def visitTypeName(self, ctx:DeclarativeParser.TypeNameContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#numLit.
    def visitNumLit(self, ctx:DeclarativeParser.NumLitContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#strLit.
    def visitStrLit(self, ctx:DeclarativeParser.StrLitContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#trueLit.
    def visitTrueLit(self, ctx:DeclarativeParser.TrueLitContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#falseLit.
    def visitFalseLit(self, ctx:DeclarativeParser.FalseLitContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DeclarativeParser#name.
    def visitName(self, ctx:DeclarativeParser.NameContext):
        return self.visitChildren(ctx)



del DeclarativeParser