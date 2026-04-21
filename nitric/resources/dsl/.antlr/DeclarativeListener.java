// Generated from /home/viperarossa/poli/thesis/python-sdk/nitric/resources/dsl/Declarative.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DeclarativeParser}.
 */
public interface DeclarativeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#pipeline}.
	 * @param ctx the parse tree
	 */
	void enterPipeline(DeclarativeParser.PipelineContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#pipeline}.
	 * @param ctx the parse tree
	 */
	void exitPipeline(DeclarativeParser.PipelineContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#dataPipeline}.
	 * @param ctx the parse tree
	 */
	void enterDataPipeline(DeclarativeParser.DataPipelineContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#dataPipeline}.
	 * @param ctx the parse tree
	 */
	void exitDataPipeline(DeclarativeParser.DataPipelineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fromStream}
	 * labeled alternative in {@link DeclarativeParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void enterFromStream(DeclarativeParser.FromStreamContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fromStream}
	 * labeled alternative in {@link DeclarativeParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void exitFromStream(DeclarativeParser.FromStreamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fromTimeseriesData}
	 * labeled alternative in {@link DeclarativeParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void enterFromTimeseriesData(DeclarativeParser.FromTimeseriesDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fromTimeseriesData}
	 * labeled alternative in {@link DeclarativeParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void exitFromTimeseriesData(DeclarativeParser.FromTimeseriesDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intoStream}
	 * labeled alternative in {@link DeclarativeParser#dataSink}.
	 * @param ctx the parse tree
	 */
	void enterIntoStream(DeclarativeParser.IntoStreamContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intoStream}
	 * labeled alternative in {@link DeclarativeParser#dataSink}.
	 * @param ctx the parse tree
	 */
	void exitIntoStream(DeclarativeParser.IntoStreamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intoTimeseriesData}
	 * labeled alternative in {@link DeclarativeParser#dataSink}.
	 * @param ctx the parse tree
	 */
	void enterIntoTimeseriesData(DeclarativeParser.IntoTimeseriesDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intoTimeseriesData}
	 * labeled alternative in {@link DeclarativeParser#dataSink}.
	 * @param ctx the parse tree
	 */
	void exitIntoTimeseriesData(DeclarativeParser.IntoTimeseriesDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whereData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereData(DeclarativeParser.WhereDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whereData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereData(DeclarativeParser.WhereDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectData(DeclarativeParser.SelectDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectData(DeclarativeParser.SelectDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code windowGroupBy}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowGroupBy(DeclarativeParser.WindowGroupByContext ctx);
	/**
	 * Exit a parse tree produced by the {@code windowGroupBy}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowGroupBy(DeclarativeParser.WindowGroupByContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupByData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByData(DeclarativeParser.GroupByDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupByData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByData(DeclarativeParser.GroupByDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinData(DeclarativeParser.JoinDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinData}
	 * labeled alternative in {@link DeclarativeParser#dataClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinData(DeclarativeParser.JoinDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#statePipeline}.
	 * @param ctx the parse tree
	 */
	void enterStatePipeline(DeclarativeParser.StatePipelineContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#statePipeline}.
	 * @param ctx the parse tree
	 */
	void exitStatePipeline(DeclarativeParser.StatePipelineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fromKV}
	 * labeled alternative in {@link DeclarativeParser#stateSource}.
	 * @param ctx the parse tree
	 */
	void enterFromKV(DeclarativeParser.FromKVContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fromKV}
	 * labeled alternative in {@link DeclarativeParser#stateSource}.
	 * @param ctx the parse tree
	 */
	void exitFromKV(DeclarativeParser.FromKVContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fromTimeseriesState}
	 * labeled alternative in {@link DeclarativeParser#stateSource}.
	 * @param ctx the parse tree
	 */
	void enterFromTimeseriesState(DeclarativeParser.FromTimeseriesStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fromTimeseriesState}
	 * labeled alternative in {@link DeclarativeParser#stateSource}.
	 * @param ctx the parse tree
	 */
	void exitFromTimeseriesState(DeclarativeParser.FromTimeseriesStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intoKV}
	 * labeled alternative in {@link DeclarativeParser#stateSink}.
	 * @param ctx the parse tree
	 */
	void enterIntoKV(DeclarativeParser.IntoKVContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intoKV}
	 * labeled alternative in {@link DeclarativeParser#stateSink}.
	 * @param ctx the parse tree
	 */
	void exitIntoKV(DeclarativeParser.IntoKVContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intoTimeseriesState}
	 * labeled alternative in {@link DeclarativeParser#stateSink}.
	 * @param ctx the parse tree
	 */
	void enterIntoTimeseriesState(DeclarativeParser.IntoTimeseriesStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intoTimeseriesState}
	 * labeled alternative in {@link DeclarativeParser#stateSink}.
	 * @param ctx the parse tree
	 */
	void exitIntoTimeseriesState(DeclarativeParser.IntoTimeseriesStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whereState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereState(DeclarativeParser.WhereStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whereState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereState(DeclarativeParser.WhereStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectState(DeclarativeParser.SelectStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectState(DeclarativeParser.SelectStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupByState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByState(DeclarativeParser.GroupByStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupByState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByState(DeclarativeParser.GroupByStateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinState(DeclarativeParser.JoinStateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinState}
	 * labeled alternative in {@link DeclarativeParser#stateClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinState(DeclarativeParser.JoinStateContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#crossToState}.
	 * @param ctx the parse tree
	 */
	void enterCrossToState(DeclarativeParser.CrossToStateContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#crossToState}.
	 * @param ctx the parse tree
	 */
	void exitCrossToState(DeclarativeParser.CrossToStateContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#crossToData}.
	 * @param ctx the parse tree
	 */
	void enterCrossToData(DeclarativeParser.CrossToDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#crossToData}.
	 * @param ctx the parse tree
	 */
	void exitCrossToData(DeclarativeParser.CrossToDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(DeclarativeParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(DeclarativeParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectClause(DeclarativeParser.SelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectClause(DeclarativeParser.SelectClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aliasedSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void enterAliasedSelect(DeclarativeParser.AliasedSelectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aliasedSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void exitAliasedSelect(DeclarativeParser.AliasedSelectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void enterColumnSelect(DeclarativeParser.ColumnSelectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void exitColumnSelect(DeclarativeParser.ColumnSelectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code starSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void enterStarSelect(DeclarativeParser.StarSelectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code starSelect}
	 * labeled alternative in {@link DeclarativeParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void exitStarSelect(DeclarativeParser.StarSelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowClause(DeclarativeParser.WindowClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowClause(DeclarativeParser.WindowClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByClause(DeclarativeParser.GroupByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByClause(DeclarativeParser.GroupByClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#aggExpr}.
	 * @param ctx the parse tree
	 */
	void enterAggExpr(DeclarativeParser.AggExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#aggExpr}.
	 * @param ctx the parse tree
	 */
	void exitAggExpr(DeclarativeParser.AggExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explicitJoin}
	 * labeled alternative in {@link DeclarativeParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterExplicitJoin(DeclarativeParser.ExplicitJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explicitJoin}
	 * labeled alternative in {@link DeclarativeParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitExplicitJoin(DeclarativeParser.ExplicitJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code enrichJoin}
	 * labeled alternative in {@link DeclarativeParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterEnrichJoin(DeclarativeParser.EnrichJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code enrichJoin}
	 * labeled alternative in {@link DeclarativeParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitEnrichJoin(DeclarativeParser.EnrichJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isNotNullExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIsNotNullExpr(DeclarativeParser.IsNotNullExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isNotNullExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIsNotNullExpr(DeclarativeParser.IsNotNullExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(DeclarativeParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(DeclarativeParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(DeclarativeParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(DeclarativeParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isNullExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIsNullExpr(DeclarativeParser.IsNullExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isNullExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIsNullExpr(DeclarativeParser.IsNullExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompExpr(DeclarativeParser.CompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompExpr(DeclarativeParser.CompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(DeclarativeParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(DeclarativeParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code colRef}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterColRef(DeclarativeParser.ColRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code colRef}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitColRef(DeclarativeParser.ColRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code litExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLitExpr(DeclarativeParser.LitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code litExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLitExpr(DeclarativeParser.LitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(DeclarativeParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(DeclarativeParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArithExpr(DeclarativeParser.ArithExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArithExpr(DeclarativeParser.ArithExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(DeclarativeParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(DeclarativeParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code betweenExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBetweenExpr(DeclarativeParser.BetweenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code betweenExpr}
	 * labeled alternative in {@link DeclarativeParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBetweenExpr(DeclarativeParser.BetweenExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#builtinFunc}.
	 * @param ctx the parse tree
	 */
	void enterBuiltinFunc(DeclarativeParser.BuiltinFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#builtinFunc}.
	 * @param ctx the parse tree
	 */
	void exitBuiltinFunc(DeclarativeParser.BuiltinFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#aggFunc}.
	 * @param ctx the parse tree
	 */
	void enterAggFunc(DeclarativeParser.AggFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#aggFunc}.
	 * @param ctx the parse tree
	 */
	void exitAggFunc(DeclarativeParser.AggFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(DeclarativeParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(DeclarativeParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#arithOp}.
	 * @param ctx the parse tree
	 */
	void enterArithOp(DeclarativeParser.ArithOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#arithOp}.
	 * @param ctx the parse tree
	 */
	void exitArithOp(DeclarativeParser.ArithOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#joinType}.
	 * @param ctx the parse tree
	 */
	void enterJoinType(DeclarativeParser.JoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#joinType}.
	 * @param ctx the parse tree
	 */
	void exitJoinType(DeclarativeParser.JoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#stateOp}.
	 * @param ctx the parse tree
	 */
	void enterStateOp(DeclarativeParser.StateOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#stateOp}.
	 * @param ctx the parse tree
	 */
	void exitStateOp(DeclarativeParser.StateOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#strategy}.
	 * @param ctx the parse tree
	 */
	void enterStrategy(DeclarativeParser.StrategyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#strategy}.
	 * @param ctx the parse tree
	 */
	void exitStrategy(DeclarativeParser.StrategyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(DeclarativeParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(DeclarativeParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNumLit(DeclarativeParser.NumLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNumLit(DeclarativeParser.NumLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code strLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStrLit(DeclarativeParser.StrLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code strLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStrLit(DeclarativeParser.StrLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTrueLit(DeclarativeParser.TrueLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTrueLit(DeclarativeParser.TrueLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFalseLit(DeclarativeParser.FalseLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLit}
	 * labeled alternative in {@link DeclarativeParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFalseLit(DeclarativeParser.FalseLitContext ctx);
	/**
	 * Enter a parse tree produced by {@link DeclarativeParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(DeclarativeParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DeclarativeParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(DeclarativeParser.NameContext ctx);
}