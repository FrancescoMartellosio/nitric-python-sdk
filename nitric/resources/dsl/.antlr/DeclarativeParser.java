// Generated from /home/viperarossa/poli/thesis/python-sdk/nitric/resources/dsl/Declarative.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class DeclarativeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FROM=1, INTO=2, AS=3, ON=4, WHERE=5, SELECT=6, GROUP=7, BY=8, JOIN=9, 
		ENRICH=10, WINDOW=11, SCHEDULE=12, MAP=13, TO=14, STATE=15, DATA=16, KEY=17, 
		VALUE=18, USING=19, STREAM=20, KV=21, TIMESERIES=22, AND=23, OR=24, NOT=25, 
		IS=26, NULL=27, BETWEEN=28, TRUE=29, FALSE=30, INNER=31, LEFT=32, LEFT_SEMI=33, 
		FULL=34, FULL_OUTER=35, REPLACE=36, INCREMENT=37, DECREMENT=38, MAXIMUM=39, 
		MINIMUM=40, COLLECT=41, SNAPSHOT=42, CDC=43, PERIODIC=44, SUM=45, COUNT=46, 
		AVG=47, MAX=48, MIN=49, LAST=50, FIRST=51, STDDEV=52, VARIANCE=53, COLLECT_LIST=54, 
		UPPER=55, LOWER=56, ROUND=57, ABS=58, FLOOR=59, CEIL=60, CAST=61, COALESCE=62, 
		LENGTH=63, SUBSTRING=64, CONCAT=65, TO_TIMESTAMP=66, DATE_FORMAT=67, YEAR=68, 
		MONTH=69, DAY=70, HOUR=71, T_DOUBLE=72, T_LONG=73, T_STRING=74, T_BOOLEAN=75, 
		T_TIMESTAMP=76, EQ=77, NEQ=78, GT=79, LT=80, GTE=81, LTE=82, PLUS=83, 
		MINUS=84, STAR=85, SLASH=86, COMMA=87, LPAREN=88, RPAREN=89, ISTREAM=90, 
		DSTREAM=91, RSTREAM=92, STRING=93, NUMBER=94, ID=95, WS=96, LINE_COMMENT=97, 
		BLOCK_COMMENT=98;
	public static final int
		RULE_pipeline = 0, RULE_dataPipeline = 1, RULE_dataSource = 2, RULE_dataSink = 3, 
		RULE_dataClause = 4, RULE_statePipeline = 5, RULE_stateSource = 6, RULE_stateSink = 7, 
		RULE_stateClause = 8, RULE_crossToState = 9, RULE_crossToData = 10, RULE_streamOperator = 11, 
		RULE_whereClause = 12, RULE_selectClause = 13, RULE_selectExpr = 14, RULE_windowClause = 15, 
		RULE_groupByClause = 16, RULE_aggExpr = 17, RULE_joinClause = 18, RULE_sourceRef = 19, 
		RULE_expression = 20, RULE_builtinFunc = 21, RULE_aggFunc = 22, RULE_compOp = 23, 
		RULE_arithOp = 24, RULE_joinType = 25, RULE_stateOp = 26, RULE_strategy = 27, 
		RULE_typeName = 28, RULE_literal = 29, RULE_name = 30;
	private static String[] makeRuleNames() {
		return new String[] {
			"pipeline", "dataPipeline", "dataSource", "dataSink", "dataClause", "statePipeline", 
			"stateSource", "stateSink", "stateClause", "crossToState", "crossToData", 
			"streamOperator", "whereClause", "selectClause", "selectExpr", "windowClause", 
			"groupByClause", "aggExpr", "joinClause", "sourceRef", "expression", 
			"builtinFunc", "aggFunc", "compOp", "arithOp", "joinType", "stateOp", 
			"strategy", "typeName", "literal", "name"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'FROM'", "'INTO'", "'AS'", "'ON'", "'WHERE'", "'SELECT'", "'GROUP'", 
			"'BY'", "'JOIN'", "'ENRICH'", "'WINDOW'", "'SCHEDULE'", "'MAP'", "'TO'", 
			"'STATE'", "'DATA'", "'KEY'", "'VALUE'", "'USING'", "'STREAM'", "'KV'", 
			"'TIMESERIES'", "'AND'", "'OR'", "'NOT'", "'IS'", "'NULL'", "'BETWEEN'", 
			"'TRUE'", "'FALSE'", "'INNER'", "'LEFT'", "'LEFT_SEMI'", "'FULL'", "'FULL_OUTER'", 
			"'REPLACE'", "'INCREMENT'", "'DECREMENT'", "'MAXIMUM'", "'MINIMUM'", 
			"'COLLECT'", "'SNAPSHOT'", "'CDC'", "'PERIODIC'", "'SUM'", "'COUNT'", 
			"'AVG'", "'MAX'", "'MIN'", "'LAST'", "'FIRST'", "'STDDEV'", "'VARIANCE'", 
			"'COLLECT_LIST'", "'UPPER'", "'LOWER'", "'ROUND'", "'ABS'", "'FLOOR'", 
			"'CEIL'", "'CAST'", "'COALESCE'", "'LENGTH'", "'SUBSTRING'", "'CONCAT'", 
			"'TO_TIMESTAMP'", "'DATE_FORMAT'", "'YEAR'", "'MONTH'", "'DAY'", "'HOUR'", 
			"'DOUBLE'", "'LONG'", "'STRING_TYPE'", "'BOOLEAN'", "'TIMESTAMP'", "'='", 
			"'!='", "'>'", "'<'", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "','", 
			"'('", "')'", "'ISTREAM'", "'DSTREAM'", "'RSTREAM'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "FROM", "INTO", "AS", "ON", "WHERE", "SELECT", "GROUP", "BY", "JOIN", 
			"ENRICH", "WINDOW", "SCHEDULE", "MAP", "TO", "STATE", "DATA", "KEY", 
			"VALUE", "USING", "STREAM", "KV", "TIMESERIES", "AND", "OR", "NOT", "IS", 
			"NULL", "BETWEEN", "TRUE", "FALSE", "INNER", "LEFT", "LEFT_SEMI", "FULL", 
			"FULL_OUTER", "REPLACE", "INCREMENT", "DECREMENT", "MAXIMUM", "MINIMUM", 
			"COLLECT", "SNAPSHOT", "CDC", "PERIODIC", "SUM", "COUNT", "AVG", "MAX", 
			"MIN", "LAST", "FIRST", "STDDEV", "VARIANCE", "COLLECT_LIST", "UPPER", 
			"LOWER", "ROUND", "ABS", "FLOOR", "CEIL", "CAST", "COALESCE", "LENGTH", 
			"SUBSTRING", "CONCAT", "TO_TIMESTAMP", "DATE_FORMAT", "YEAR", "MONTH", 
			"DAY", "HOUR", "T_DOUBLE", "T_LONG", "T_STRING", "T_BOOLEAN", "T_TIMESTAMP", 
			"EQ", "NEQ", "GT", "LT", "GTE", "LTE", "PLUS", "MINUS", "STAR", "SLASH", 
			"COMMA", "LPAREN", "RPAREN", "ISTREAM", "DSTREAM", "RSTREAM", "STRING", 
			"NUMBER", "ID", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Declarative.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DeclarativeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PipelineContext extends ParserRuleContext {
		public DataPipelineContext dataPipeline() {
			return getRuleContext(DataPipelineContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DeclarativeParser.EOF, 0); }
		public StatePipelineContext statePipeline() {
			return getRuleContext(StatePipelineContext.class,0);
		}
		public PipelineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pipeline; }
	}

	public final PipelineContext pipeline() throws RecognitionException {
		PipelineContext _localctx = new PipelineContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pipeline);
		try {
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				dataPipeline();
				setState(63);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				statePipeline();
				setState(66);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataPipelineContext extends ParserRuleContext {
		public DataSourceContext dataSource() {
			return getRuleContext(DataSourceContext.class,0);
		}
		public DataSinkContext dataSink() {
			return getRuleContext(DataSinkContext.class,0);
		}
		public List<DataClauseContext> dataClause() {
			return getRuleContexts(DataClauseContext.class);
		}
		public DataClauseContext dataClause(int i) {
			return getRuleContext(DataClauseContext.class,i);
		}
		public CrossToStateContext crossToState() {
			return getRuleContext(CrossToStateContext.class,0);
		}
		public StateSinkContext stateSink() {
			return getRuleContext(StateSinkContext.class,0);
		}
		public List<StateClauseContext> stateClause() {
			return getRuleContexts(StateClauseContext.class);
		}
		public StateClauseContext stateClause(int i) {
			return getRuleContext(StateClauseContext.class,i);
		}
		public CrossToDataContext crossToData() {
			return getRuleContext(CrossToDataContext.class,0);
		}
		public DataPipelineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataPipeline; }
	}

	public final DataPipelineContext dataPipeline() throws RecognitionException {
		DataPipelineContext _localctx = new DataPipelineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dataPipeline);
		int _la;
		try {
			setState(118);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				dataSource();
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(71);
					dataClause();
					}
					}
					setState(76);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(77);
				dataSink();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				dataSource();
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(80);
					dataClause();
					}
					}
					setState(85);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(86);
				crossToState();
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(87);
					stateClause();
					}
					}
					setState(92);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(93);
				stateSink();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(95);
				dataSource();
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(96);
					dataClause();
					}
					}
					setState(101);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(102);
				crossToState();
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(103);
					stateClause();
					}
					}
					setState(108);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(109);
				crossToData();
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(110);
					dataClause();
					}
					}
					setState(115);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(116);
				dataSink();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataSourceContext extends ParserRuleContext {
		public DataSourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataSource; }
	 
		public DataSourceContext() { }
		public void copyFrom(DataSourceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FromStreamContext extends DataSourceContext {
		public TerminalNode FROM() { return getToken(DeclarativeParser.FROM, 0); }
		public TerminalNode STREAM() { return getToken(DeclarativeParser.STREAM, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public FromStreamContext(DataSourceContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FromTimeseriesDataContext extends DataSourceContext {
		public TerminalNode FROM() { return getToken(DeclarativeParser.FROM, 0); }
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode DATA() { return getToken(DeclarativeParser.DATA, 0); }
		public FromTimeseriesDataContext(DataSourceContext ctx) { copyFrom(ctx); }
	}

	public final DataSourceContext dataSource() throws RecognitionException {
		DataSourceContext _localctx = new DataSourceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dataSource);
		try {
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new FromStreamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(120);
				match(FROM);
				setState(121);
				match(STREAM);
				setState(122);
				name();
				}
				break;
			case 2:
				_localctx = new FromTimeseriesDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(123);
				match(FROM);
				setState(124);
				match(TIMESERIES);
				setState(125);
				name();
				setState(126);
				match(AS);
				setState(127);
				match(DATA);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataSinkContext extends ParserRuleContext {
		public DataSinkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataSink; }
	 
		public DataSinkContext() { }
		public void copyFrom(DataSinkContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntoStreamContext extends DataSinkContext {
		public TerminalNode INTO() { return getToken(DeclarativeParser.INTO, 0); }
		public TerminalNode STREAM() { return getToken(DeclarativeParser.STREAM, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public IntoStreamContext(DataSinkContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntoTimeseriesDataContext extends DataSinkContext {
		public TerminalNode INTO() { return getToken(DeclarativeParser.INTO, 0); }
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode DATA() { return getToken(DeclarativeParser.DATA, 0); }
		public IntoTimeseriesDataContext(DataSinkContext ctx) { copyFrom(ctx); }
	}

	public final DataSinkContext dataSink() throws RecognitionException {
		DataSinkContext _localctx = new DataSinkContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_dataSink);
		try {
			setState(140);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new IntoStreamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(131);
				match(INTO);
				setState(132);
				match(STREAM);
				setState(133);
				name();
				}
				break;
			case 2:
				_localctx = new IntoTimeseriesDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				match(INTO);
				setState(135);
				match(TIMESERIES);
				setState(136);
				name();
				setState(137);
				match(AS);
				setState(138);
				match(DATA);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataClauseContext extends ParserRuleContext {
		public DataClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataClause; }
	 
		public DataClauseContext() { }
		public void copyFrom(DataClauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WindowGroupByContext extends DataClauseContext {
		public WindowClauseContext windowClause() {
			return getRuleContext(WindowClauseContext.class,0);
		}
		public GroupByClauseContext groupByClause() {
			return getRuleContext(GroupByClauseContext.class,0);
		}
		public WindowGroupByContext(DataClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupByDataContext extends DataClauseContext {
		public GroupByClauseContext groupByClause() {
			return getRuleContext(GroupByClauseContext.class,0);
		}
		public GroupByDataContext(DataClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectDataContext extends DataClauseContext {
		public SelectClauseContext selectClause() {
			return getRuleContext(SelectClauseContext.class,0);
		}
		public SelectDataContext(DataClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhereDataContext extends DataClauseContext {
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public WhereDataContext(DataClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinDataContext extends DataClauseContext {
		public JoinClauseContext joinClause() {
			return getRuleContext(JoinClauseContext.class,0);
		}
		public JoinDataContext(DataClauseContext ctx) { copyFrom(ctx); }
	}

	public final DataClauseContext dataClause() throws RecognitionException {
		DataClauseContext _localctx = new DataClauseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dataClause);
		try {
			setState(149);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHERE:
				_localctx = new WhereDataContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				whereClause();
				}
				break;
			case SELECT:
				_localctx = new SelectDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(143);
				selectClause();
				}
				break;
			case WINDOW:
				_localctx = new WindowGroupByContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				windowClause();
				setState(145);
				groupByClause();
				}
				break;
			case GROUP:
				_localctx = new GroupByDataContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(147);
				groupByClause();
				}
				break;
			case ENRICH:
			case INNER:
			case LEFT:
			case LEFT_SEMI:
			case FULL:
			case FULL_OUTER:
				_localctx = new JoinDataContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(148);
				joinClause();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatePipelineContext extends ParserRuleContext {
		public StateSourceContext stateSource() {
			return getRuleContext(StateSourceContext.class,0);
		}
		public StateSinkContext stateSink() {
			return getRuleContext(StateSinkContext.class,0);
		}
		public List<StateClauseContext> stateClause() {
			return getRuleContexts(StateClauseContext.class);
		}
		public StateClauseContext stateClause(int i) {
			return getRuleContext(StateClauseContext.class,i);
		}
		public CrossToDataContext crossToData() {
			return getRuleContext(CrossToDataContext.class,0);
		}
		public DataSinkContext dataSink() {
			return getRuleContext(DataSinkContext.class,0);
		}
		public List<DataClauseContext> dataClause() {
			return getRuleContexts(DataClauseContext.class);
		}
		public DataClauseContext dataClause(int i) {
			return getRuleContext(DataClauseContext.class,i);
		}
		public CrossToStateContext crossToState() {
			return getRuleContext(CrossToStateContext.class,0);
		}
		public StatePipelineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statePipeline; }
	}

	public final StatePipelineContext statePipeline() throws RecognitionException {
		StatePipelineContext _localctx = new StatePipelineContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_statePipeline);
		int _la;
		try {
			setState(199);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				stateSource();
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(152);
					stateClause();
					}
					}
					setState(157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(158);
				stateSink();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				stateSource();
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(161);
					stateClause();
					}
					}
					setState(166);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(167);
				crossToData();
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(168);
					dataClause();
					}
					}
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(174);
				dataSink();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				stateSource();
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(177);
					stateClause();
					}
					}
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(183);
				crossToData();
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571996384L) != 0)) {
					{
					{
					setState(184);
					dataClause();
					}
					}
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(190);
				crossToState();
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571994336L) != 0)) {
					{
					{
					setState(191);
					stateClause();
					}
					}
					setState(196);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(197);
				stateSink();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StateSourceContext extends ParserRuleContext {
		public StateSourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateSource; }
	 
		public StateSourceContext() { }
		public void copyFrom(StateSourceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FromTimeseriesStateContext extends StateSourceContext {
		public TerminalNode FROM() { return getToken(DeclarativeParser.FROM, 0); }
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode STATE() { return getToken(DeclarativeParser.STATE, 0); }
		public FromTimeseriesStateContext(StateSourceContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FromKVContext extends StateSourceContext {
		public TerminalNode FROM() { return getToken(DeclarativeParser.FROM, 0); }
		public TerminalNode KV() { return getToken(DeclarativeParser.KV, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public FromKVContext(StateSourceContext ctx) { copyFrom(ctx); }
	}

	public final StateSourceContext stateSource() throws RecognitionException {
		StateSourceContext _localctx = new StateSourceContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stateSource);
		try {
			setState(210);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new FromKVContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(201);
				match(FROM);
				setState(202);
				match(KV);
				setState(203);
				name();
				}
				break;
			case 2:
				_localctx = new FromTimeseriesStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(204);
				match(FROM);
				setState(205);
				match(TIMESERIES);
				setState(206);
				name();
				setState(207);
				match(AS);
				setState(208);
				match(STATE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StateSinkContext extends ParserRuleContext {
		public StateSinkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateSink; }
	 
		public StateSinkContext() { }
		public void copyFrom(StateSinkContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntoTimeseriesStateContext extends StateSinkContext {
		public TerminalNode INTO() { return getToken(DeclarativeParser.INTO, 0); }
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode STATE() { return getToken(DeclarativeParser.STATE, 0); }
		public IntoTimeseriesStateContext(StateSinkContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntoKVContext extends StateSinkContext {
		public TerminalNode INTO() { return getToken(DeclarativeParser.INTO, 0); }
		public TerminalNode KV() { return getToken(DeclarativeParser.KV, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public IntoKVContext(StateSinkContext ctx) { copyFrom(ctx); }
	}

	public final StateSinkContext stateSink() throws RecognitionException {
		StateSinkContext _localctx = new StateSinkContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stateSink);
		try {
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new IntoKVContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(212);
				match(INTO);
				setState(213);
				match(KV);
				setState(214);
				name();
				}
				break;
			case 2:
				_localctx = new IntoTimeseriesStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(215);
				match(INTO);
				setState(216);
				match(TIMESERIES);
				setState(217);
				name();
				setState(218);
				match(AS);
				setState(219);
				match(STATE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StateClauseContext extends ParserRuleContext {
		public StateClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateClause; }
	 
		public StateClauseContext() { }
		public void copyFrom(StateClauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinStateContext extends StateClauseContext {
		public JoinClauseContext joinClause() {
			return getRuleContext(JoinClauseContext.class,0);
		}
		public JoinStateContext(StateClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhereStateContext extends StateClauseContext {
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public WhereStateContext(StateClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupByStateContext extends StateClauseContext {
		public GroupByClauseContext groupByClause() {
			return getRuleContext(GroupByClauseContext.class,0);
		}
		public GroupByStateContext(StateClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectStateContext extends StateClauseContext {
		public SelectClauseContext selectClause() {
			return getRuleContext(SelectClauseContext.class,0);
		}
		public SelectStateContext(StateClauseContext ctx) { copyFrom(ctx); }
	}

	public final StateClauseContext stateClause() throws RecognitionException {
		StateClauseContext _localctx = new StateClauseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stateClause);
		try {
			setState(227);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHERE:
				_localctx = new WhereStateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				whereClause();
				}
				break;
			case SELECT:
				_localctx = new SelectStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(224);
				selectClause();
				}
				break;
			case GROUP:
				_localctx = new GroupByStateContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(225);
				groupByClause();
				}
				break;
			case ENRICH:
			case INNER:
			case LEFT:
			case LEFT_SEMI:
			case FULL:
			case FULL_OUTER:
				_localctx = new JoinStateContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(226);
				joinClause();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CrossToStateContext extends ParserRuleContext {
		public NameContext keyCol;
		public NameContext valueCol;
		public TerminalNode MAP() { return getToken(DeclarativeParser.MAP, 0); }
		public TerminalNode TO() { return getToken(DeclarativeParser.TO, 0); }
		public TerminalNode STATE() { return getToken(DeclarativeParser.STATE, 0); }
		public TerminalNode KEY() { return getToken(DeclarativeParser.KEY, 0); }
		public TerminalNode VALUE() { return getToken(DeclarativeParser.VALUE, 0); }
		public TerminalNode USING() { return getToken(DeclarativeParser.USING, 0); }
		public StateOpContext stateOp() {
			return getRuleContext(StateOpContext.class,0);
		}
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public CrossToStateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_crossToState; }
	}

	public final CrossToStateContext crossToState() throws RecognitionException {
		CrossToStateContext _localctx = new CrossToStateContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_crossToState);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(MAP);
			setState(230);
			match(TO);
			setState(231);
			match(STATE);
			setState(232);
			match(KEY);
			setState(233);
			((CrossToStateContext)_localctx).keyCol = name();
			setState(234);
			match(VALUE);
			setState(235);
			((CrossToStateContext)_localctx).valueCol = name();
			setState(236);
			match(USING);
			setState(237);
			stateOp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CrossToDataContext extends ParserRuleContext {
		public Token schedule;
		public TerminalNode MAP() { return getToken(DeclarativeParser.MAP, 0); }
		public TerminalNode TO() { return getToken(DeclarativeParser.TO, 0); }
		public TerminalNode DATA() { return getToken(DeclarativeParser.DATA, 0); }
		public StreamOperatorContext streamOperator() {
			return getRuleContext(StreamOperatorContext.class,0);
		}
		public TerminalNode ON() { return getToken(DeclarativeParser.ON, 0); }
		public TerminalNode SCHEDULE() { return getToken(DeclarativeParser.SCHEDULE, 0); }
		public TerminalNode STRING() { return getToken(DeclarativeParser.STRING, 0); }
		public CrossToDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_crossToData; }
	}

	public final CrossToDataContext crossToData() throws RecognitionException {
		CrossToDataContext _localctx = new CrossToDataContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_crossToData);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(MAP);
			setState(240);
			match(TO);
			setState(241);
			match(DATA);
			setState(242);
			streamOperator();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ON) {
				{
				setState(243);
				match(ON);
				setState(244);
				match(SCHEDULE);
				setState(245);
				((CrossToDataContext)_localctx).schedule = match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StreamOperatorContext extends ParserRuleContext {
		public TerminalNode ISTREAM() { return getToken(DeclarativeParser.ISTREAM, 0); }
		public TerminalNode DSTREAM() { return getToken(DeclarativeParser.DSTREAM, 0); }
		public TerminalNode RSTREAM() { return getToken(DeclarativeParser.RSTREAM, 0); }
		public StreamOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_streamOperator; }
	}

	public final StreamOperatorContext streamOperator() throws RecognitionException {
		StreamOperatorContext _localctx = new StreamOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_streamOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			_la = _input.LA(1);
			if ( !(((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & 7L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhereClauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(DeclarativeParser.WHERE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(WHERE);
			setState(251);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectClauseContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(DeclarativeParser.SELECT, 0); }
		public List<SelectExprContext> selectExpr() {
			return getRuleContexts(SelectExprContext.class);
		}
		public SelectExprContext selectExpr(int i) {
			return getRuleContext(SelectExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DeclarativeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DeclarativeParser.COMMA, i);
		}
		public SelectClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectClause; }
	}

	public final SelectClauseContext selectClause() throws RecognitionException {
		SelectClauseContext _localctx = new SelectClauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_selectClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(SELECT);
			setState(254);
			selectExpr();
			setState(259);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(255);
				match(COMMA);
				setState(256);
				selectExpr();
				}
				}
				setState(261);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectExprContext extends ParserRuleContext {
		public SelectExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectExpr; }
	 
		public SelectExprContext() { }
		public void copyFrom(SelectExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColumnSelectContext extends SelectExprContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ColumnSelectContext(SelectExprContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StarSelectContext extends SelectExprContext {
		public TerminalNode STAR() { return getToken(DeclarativeParser.STAR, 0); }
		public StarSelectContext(SelectExprContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AliasedSelectContext extends SelectExprContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public AliasedSelectContext(SelectExprContext ctx) { copyFrom(ctx); }
	}

	public final SelectExprContext selectExpr() throws RecognitionException {
		SelectExprContext _localctx = new SelectExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_selectExpr);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new AliasedSelectContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				expression(0);
				setState(263);
				match(AS);
				setState(264);
				name();
				}
				break;
			case 2:
				_localctx = new ColumnSelectContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(266);
				name();
				}
				break;
			case 3:
				_localctx = new StarSelectContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(267);
				match(STAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WindowClauseContext extends ParserRuleContext {
		public Token duration;
		public NameContext timeCol;
		public Token slide;
		public TerminalNode WINDOW() { return getToken(DeclarativeParser.WINDOW, 0); }
		public TerminalNode LPAREN() { return getToken(DeclarativeParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DeclarativeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DeclarativeParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(DeclarativeParser.RPAREN, 0); }
		public List<TerminalNode> STRING() { return getTokens(DeclarativeParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(DeclarativeParser.STRING, i);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public WindowClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowClause; }
	}

	public final WindowClauseContext windowClause() throws RecognitionException {
		WindowClauseContext _localctx = new WindowClauseContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_windowClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(WINDOW);
			setState(271);
			match(LPAREN);
			setState(272);
			((WindowClauseContext)_localctx).duration = match(STRING);
			setState(273);
			match(COMMA);
			setState(274);
			((WindowClauseContext)_localctx).timeCol = name();
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(275);
				match(COMMA);
				setState(276);
				((WindowClauseContext)_localctx).slide = match(STRING);
				}
			}

			setState(279);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupByClauseContext extends ParserRuleContext {
		public TerminalNode GROUP() { return getToken(DeclarativeParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(DeclarativeParser.BY, 0); }
		public List<AggExprContext> aggExpr() {
			return getRuleContexts(AggExprContext.class);
		}
		public AggExprContext aggExpr(int i) {
			return getRuleContext(AggExprContext.class,i);
		}
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DeclarativeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DeclarativeParser.COMMA, i);
		}
		public GroupByClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupByClause; }
	}

	public final GroupByClauseContext groupByClause() throws RecognitionException {
		GroupByClauseContext _localctx = new GroupByClauseContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_groupByClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(GROUP);
			setState(282);
			match(BY);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STRING || _la==ID) {
				{
				setState(283);
				name();
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(284);
					match(COMMA);
					setState(285);
					name();
					}
					}
					setState(290);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(293);
			aggExpr();
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(294);
				match(COMMA);
				setState(295);
				aggExpr();
				}
				}
				setState(300);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AggExprContext extends ParserRuleContext {
		public AggFuncContext aggFunc() {
			return getRuleContext(AggFuncContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DeclarativeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DeclarativeParser.RPAREN, 0); }
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode STAR() { return getToken(DeclarativeParser.STAR, 0); }
		public AggExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggExpr; }
	}

	public final AggExprContext aggExpr() throws RecognitionException {
		AggExprContext _localctx = new AggExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_aggExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			aggFunc();
			setState(302);
			match(LPAREN);
			setState(305);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
			case TRUE:
			case FALSE:
			case UPPER:
			case LOWER:
			case ROUND:
			case ABS:
			case FLOOR:
			case CEIL:
			case CAST:
			case LENGTH:
			case SUBSTRING:
			case TO_TIMESTAMP:
			case DATE_FORMAT:
			case YEAR:
			case MONTH:
			case DAY:
			case HOUR:
			case LPAREN:
			case STRING:
			case NUMBER:
			case ID:
				{
				setState(303);
				expression(0);
				}
				break;
			case STAR:
				{
				setState(304);
				match(STAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(307);
			match(RPAREN);
			setState(308);
			match(AS);
			setState(309);
			name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinClauseContext extends ParserRuleContext {
		public JoinClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinClause; }
	 
		public JoinClauseContext() { }
		public void copyFrom(JoinClauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EnrichJoinContext extends JoinClauseContext {
		public TerminalNode ENRICH() { return getToken(DeclarativeParser.ENRICH, 0); }
		public SourceRefContext sourceRef() {
			return getRuleContext(SourceRefContext.class,0);
		}
		public TerminalNode ON() { return getToken(DeclarativeParser.ON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public EnrichJoinContext(JoinClauseContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitJoinContext extends JoinClauseContext {
		public JoinTypeContext joinType() {
			return getRuleContext(JoinTypeContext.class,0);
		}
		public TerminalNode JOIN() { return getToken(DeclarativeParser.JOIN, 0); }
		public SourceRefContext sourceRef() {
			return getRuleContext(SourceRefContext.class,0);
		}
		public TerminalNode ON() { return getToken(DeclarativeParser.ON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExplicitJoinContext(JoinClauseContext ctx) { copyFrom(ctx); }
	}

	public final JoinClauseContext joinClause() throws RecognitionException {
		JoinClauseContext _localctx = new JoinClauseContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_joinClause);
		try {
			setState(322);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
			case LEFT:
			case LEFT_SEMI:
			case FULL:
			case FULL_OUTER:
				_localctx = new ExplicitJoinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(311);
				joinType();
				setState(312);
				match(JOIN);
				setState(313);
				sourceRef();
				setState(314);
				match(ON);
				setState(315);
				expression(0);
				}
				break;
			case ENRICH:
				_localctx = new EnrichJoinContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(317);
				match(ENRICH);
				setState(318);
				sourceRef();
				setState(319);
				match(ON);
				setState(320);
				expression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SourceRefContext extends ParserRuleContext {
		public SourceRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceRef; }
	 
		public SourceRefContext() { }
		public void copyFrom(SourceRefContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinTimeseriesStateContext extends SourceRefContext {
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode STATE() { return getToken(DeclarativeParser.STATE, 0); }
		public JoinTimeseriesStateContext(SourceRefContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinStreamContext extends SourceRefContext {
		public TerminalNode STREAM() { return getToken(DeclarativeParser.STREAM, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public JoinStreamContext(SourceRefContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinTimeseriesDataContext extends SourceRefContext {
		public TerminalNode TIMESERIES() { return getToken(DeclarativeParser.TIMESERIES, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DeclarativeParser.AS, 0); }
		public TerminalNode DATA() { return getToken(DeclarativeParser.DATA, 0); }
		public JoinTimeseriesDataContext(SourceRefContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinKVContext extends SourceRefContext {
		public TerminalNode KV() { return getToken(DeclarativeParser.KV, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public JoinKVContext(SourceRefContext ctx) { copyFrom(ctx); }
	}

	public final SourceRefContext sourceRef() throws RecognitionException {
		SourceRefContext _localctx = new SourceRefContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_sourceRef);
		try {
			setState(338);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new JoinStreamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(324);
				match(STREAM);
				setState(325);
				name();
				}
				break;
			case 2:
				_localctx = new JoinKVContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(326);
				match(KV);
				setState(327);
				name();
				}
				break;
			case 3:
				_localctx = new JoinTimeseriesDataContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(328);
				match(TIMESERIES);
				setState(329);
				name();
				setState(330);
				match(AS);
				setState(331);
				match(DATA);
				}
				break;
			case 4:
				_localctx = new JoinTimeseriesStateContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(333);
				match(TIMESERIES);
				setState(334);
				name();
				setState(335);
				match(AS);
				setState(336);
				match(STATE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsNotNullExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IS() { return getToken(DeclarativeParser.IS, 0); }
		public TerminalNode NOT() { return getToken(DeclarativeParser.NOT, 0); }
		public TerminalNode NULL() { return getToken(DeclarativeParser.NULL, 0); }
		public IsNotNullExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExpressionContext {
		public TerminalNode NOT() { return getToken(DeclarativeParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncExprContext extends ExpressionContext {
		public BuiltinFuncContext builtinFunc() {
			return getRuleContext(BuiltinFuncContext.class,0);
		}
		public FuncExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsNullExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IS() { return getToken(DeclarativeParser.IS, 0); }
		public TerminalNode NULL() { return getToken(DeclarativeParser.NULL, 0); }
		public IsNullExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public CompExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(DeclarativeParser.OR, 0); }
		public OrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColRefContext extends ExpressionContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ColRefContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LitExprContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LitExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(DeclarativeParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DeclarativeParser.RPAREN, 0); }
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArithOpContext arithOp() {
			return getRuleContext(ArithOpContext.class,0);
		}
		public ArithExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(DeclarativeParser.AND, 0); }
		public AndExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BetweenExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BETWEEN() { return getToken(DeclarativeParser.BETWEEN, 0); }
		public TerminalNode AND() { return getToken(DeclarativeParser.AND, 0); }
		public BetweenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(341);
				match(NOT);
				setState(342);
				expression(10);
				}
				break;
			case 2:
				{
				_localctx = new FuncExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(343);
				builtinFunc();
				}
				break;
			case 3:
				{
				_localctx = new ColRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(344);
				name();
				}
				break;
			case 4:
				{
				_localctx = new LitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(345);
				literal();
				}
				break;
			case 5:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(346);
				match(LPAREN);
				setState(347);
				expression(0);
				setState(348);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(381);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(379);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
					case 1:
						{
						_localctx = new AndExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(352);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(353);
						match(AND);
						setState(354);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new OrExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(355);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(356);
						match(OR);
						setState(357);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new CompExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(358);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(359);
						compOp();
						setState(360);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new ArithExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(362);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(363);
						arithOp();
						setState(364);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new BetweenExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(366);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(367);
						match(BETWEEN);
						setState(368);
						expression(0);
						setState(369);
						match(AND);
						setState(370);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new IsNullExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(372);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(373);
						match(IS);
						setState(374);
						match(NULL);
						}
						break;
					case 7:
						{
						_localctx = new IsNotNullExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(375);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(376);
						match(IS);
						setState(377);
						match(NOT);
						setState(378);
						match(NULL);
						}
						break;
					}
					} 
				}
				setState(383);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BuiltinFuncContext extends ParserRuleContext {
		public TerminalNode UPPER() { return getToken(DeclarativeParser.UPPER, 0); }
		public TerminalNode LPAREN() { return getToken(DeclarativeParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DeclarativeParser.RPAREN, 0); }
		public TerminalNode LOWER() { return getToken(DeclarativeParser.LOWER, 0); }
		public TerminalNode ROUND() { return getToken(DeclarativeParser.ROUND, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DeclarativeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DeclarativeParser.COMMA, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(DeclarativeParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(DeclarativeParser.NUMBER, i);
		}
		public TerminalNode ABS() { return getToken(DeclarativeParser.ABS, 0); }
		public TerminalNode FLOOR() { return getToken(DeclarativeParser.FLOOR, 0); }
		public TerminalNode CEIL() { return getToken(DeclarativeParser.CEIL, 0); }
		public TerminalNode CAST() { return getToken(DeclarativeParser.CAST, 0); }
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode LENGTH() { return getToken(DeclarativeParser.LENGTH, 0); }
		public TerminalNode SUBSTRING() { return getToken(DeclarativeParser.SUBSTRING, 0); }
		public TerminalNode TO_TIMESTAMP() { return getToken(DeclarativeParser.TO_TIMESTAMP, 0); }
		public TerminalNode STRING() { return getToken(DeclarativeParser.STRING, 0); }
		public TerminalNode DATE_FORMAT() { return getToken(DeclarativeParser.DATE_FORMAT, 0); }
		public TerminalNode YEAR() { return getToken(DeclarativeParser.YEAR, 0); }
		public TerminalNode MONTH() { return getToken(DeclarativeParser.MONTH, 0); }
		public TerminalNode DAY() { return getToken(DeclarativeParser.DAY, 0); }
		public TerminalNode HOUR() { return getToken(DeclarativeParser.HOUR, 0); }
		public BuiltinFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtinFunc; }
	}

	public final BuiltinFuncContext builtinFunc() throws RecognitionException {
		BuiltinFuncContext _localctx = new BuiltinFuncContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_builtinFunc);
		try {
			setState(471);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UPPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(384);
				match(UPPER);
				setState(385);
				match(LPAREN);
				setState(386);
				expression(0);
				setState(387);
				match(RPAREN);
				}
				break;
			case LOWER:
				enterOuterAlt(_localctx, 2);
				{
				setState(389);
				match(LOWER);
				setState(390);
				match(LPAREN);
				setState(391);
				expression(0);
				setState(392);
				match(RPAREN);
				}
				break;
			case ROUND:
				enterOuterAlt(_localctx, 3);
				{
				setState(394);
				match(ROUND);
				setState(395);
				match(LPAREN);
				setState(396);
				expression(0);
				setState(397);
				match(COMMA);
				setState(398);
				match(NUMBER);
				setState(399);
				match(RPAREN);
				}
				break;
			case ABS:
				enterOuterAlt(_localctx, 4);
				{
				setState(401);
				match(ABS);
				setState(402);
				match(LPAREN);
				setState(403);
				expression(0);
				setState(404);
				match(RPAREN);
				}
				break;
			case FLOOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(406);
				match(FLOOR);
				setState(407);
				match(LPAREN);
				setState(408);
				expression(0);
				setState(409);
				match(RPAREN);
				}
				break;
			case CEIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(411);
				match(CEIL);
				setState(412);
				match(LPAREN);
				setState(413);
				expression(0);
				setState(414);
				match(RPAREN);
				}
				break;
			case CAST:
				enterOuterAlt(_localctx, 7);
				{
				setState(416);
				match(CAST);
				setState(417);
				match(LPAREN);
				setState(418);
				expression(0);
				setState(419);
				match(COMMA);
				setState(420);
				typeName();
				setState(421);
				match(RPAREN);
				}
				break;
			case LENGTH:
				enterOuterAlt(_localctx, 8);
				{
				setState(423);
				match(LENGTH);
				setState(424);
				match(LPAREN);
				setState(425);
				expression(0);
				setState(426);
				match(RPAREN);
				}
				break;
			case SUBSTRING:
				enterOuterAlt(_localctx, 9);
				{
				setState(428);
				match(SUBSTRING);
				setState(429);
				match(LPAREN);
				setState(430);
				expression(0);
				setState(431);
				match(COMMA);
				setState(432);
				match(NUMBER);
				setState(433);
				match(COMMA);
				setState(434);
				match(NUMBER);
				setState(435);
				match(RPAREN);
				}
				break;
			case TO_TIMESTAMP:
				enterOuterAlt(_localctx, 10);
				{
				setState(437);
				match(TO_TIMESTAMP);
				setState(438);
				match(LPAREN);
				setState(439);
				expression(0);
				setState(440);
				match(COMMA);
				setState(441);
				match(STRING);
				setState(442);
				match(RPAREN);
				}
				break;
			case DATE_FORMAT:
				enterOuterAlt(_localctx, 11);
				{
				setState(444);
				match(DATE_FORMAT);
				setState(445);
				match(LPAREN);
				setState(446);
				expression(0);
				setState(447);
				match(COMMA);
				setState(448);
				match(STRING);
				setState(449);
				match(RPAREN);
				}
				break;
			case YEAR:
				enterOuterAlt(_localctx, 12);
				{
				setState(451);
				match(YEAR);
				setState(452);
				match(LPAREN);
				setState(453);
				expression(0);
				setState(454);
				match(RPAREN);
				}
				break;
			case MONTH:
				enterOuterAlt(_localctx, 13);
				{
				setState(456);
				match(MONTH);
				setState(457);
				match(LPAREN);
				setState(458);
				expression(0);
				setState(459);
				match(RPAREN);
				}
				break;
			case DAY:
				enterOuterAlt(_localctx, 14);
				{
				setState(461);
				match(DAY);
				setState(462);
				match(LPAREN);
				setState(463);
				expression(0);
				setState(464);
				match(RPAREN);
				}
				break;
			case HOUR:
				enterOuterAlt(_localctx, 15);
				{
				setState(466);
				match(HOUR);
				setState(467);
				match(LPAREN);
				setState(468);
				expression(0);
				setState(469);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AggFuncContext extends ParserRuleContext {
		public TerminalNode SUM() { return getToken(DeclarativeParser.SUM, 0); }
		public TerminalNode COUNT() { return getToken(DeclarativeParser.COUNT, 0); }
		public TerminalNode AVG() { return getToken(DeclarativeParser.AVG, 0); }
		public TerminalNode MAX() { return getToken(DeclarativeParser.MAX, 0); }
		public TerminalNode MIN() { return getToken(DeclarativeParser.MIN, 0); }
		public TerminalNode LAST() { return getToken(DeclarativeParser.LAST, 0); }
		public TerminalNode FIRST() { return getToken(DeclarativeParser.FIRST, 0); }
		public TerminalNode STDDEV() { return getToken(DeclarativeParser.STDDEV, 0); }
		public TerminalNode VARIANCE() { return getToken(DeclarativeParser.VARIANCE, 0); }
		public TerminalNode COLLECT_LIST() { return getToken(DeclarativeParser.COLLECT_LIST, 0); }
		public AggFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggFunc; }
	}

	public final AggFuncContext aggFunc() throws RecognitionException {
		AggFuncContext _localctx = new AggFuncContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_aggFunc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(473);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 35993612646875136L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompOpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(DeclarativeParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(DeclarativeParser.NEQ, 0); }
		public TerminalNode GT() { return getToken(DeclarativeParser.GT, 0); }
		public TerminalNode LT() { return getToken(DeclarativeParser.LT, 0); }
		public TerminalNode GTE() { return getToken(DeclarativeParser.GTE, 0); }
		public TerminalNode LTE() { return getToken(DeclarativeParser.LTE, 0); }
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			_la = _input.LA(1);
			if ( !(((((_la - 77)) & ~0x3f) == 0 && ((1L << (_la - 77)) & 63L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithOpContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(DeclarativeParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(DeclarativeParser.MINUS, 0); }
		public TerminalNode STAR() { return getToken(DeclarativeParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(DeclarativeParser.SLASH, 0); }
		public ArithOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithOp; }
	}

	public final ArithOpContext arithOp() throws RecognitionException {
		ArithOpContext _localctx = new ArithOpContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_arithOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(477);
			_la = _input.LA(1);
			if ( !(((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & 15L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinTypeContext extends ParserRuleContext {
		public TerminalNode INNER() { return getToken(DeclarativeParser.INNER, 0); }
		public TerminalNode LEFT() { return getToken(DeclarativeParser.LEFT, 0); }
		public TerminalNode LEFT_SEMI() { return getToken(DeclarativeParser.LEFT_SEMI, 0); }
		public TerminalNode FULL() { return getToken(DeclarativeParser.FULL, 0); }
		public TerminalNode FULL_OUTER() { return getToken(DeclarativeParser.FULL_OUTER, 0); }
		public JoinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinType; }
	}

	public final JoinTypeContext joinType() throws RecognitionException {
		JoinTypeContext _localctx = new JoinTypeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_joinType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 66571993088L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StateOpContext extends ParserRuleContext {
		public TerminalNode REPLACE() { return getToken(DeclarativeParser.REPLACE, 0); }
		public TerminalNode INCREMENT() { return getToken(DeclarativeParser.INCREMENT, 0); }
		public TerminalNode DECREMENT() { return getToken(DeclarativeParser.DECREMENT, 0); }
		public TerminalNode MAXIMUM() { return getToken(DeclarativeParser.MAXIMUM, 0); }
		public TerminalNode MINIMUM() { return getToken(DeclarativeParser.MINIMUM, 0); }
		public TerminalNode COLLECT() { return getToken(DeclarativeParser.COLLECT, 0); }
		public StateOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateOp; }
	}

	public final StateOpContext stateOp() throws RecognitionException {
		StateOpContext _localctx = new StateOpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_stateOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4329327034368L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StrategyContext extends ParserRuleContext {
		public TerminalNode SNAPSHOT() { return getToken(DeclarativeParser.SNAPSHOT, 0); }
		public TerminalNode CDC() { return getToken(DeclarativeParser.CDC, 0); }
		public TerminalNode PERIODIC() { return getToken(DeclarativeParser.PERIODIC, 0); }
		public StrategyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strategy; }
	}

	public final StrategyContext strategy() throws RecognitionException {
		StrategyContext _localctx = new StrategyContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_strategy);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 30786325577728L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeNameContext extends ParserRuleContext {
		public TerminalNode T_DOUBLE() { return getToken(DeclarativeParser.T_DOUBLE, 0); }
		public TerminalNode T_LONG() { return getToken(DeclarativeParser.T_LONG, 0); }
		public TerminalNode T_STRING() { return getToken(DeclarativeParser.T_STRING, 0); }
		public TerminalNode T_BOOLEAN() { return getToken(DeclarativeParser.T_BOOLEAN, 0); }
		public TerminalNode T_TIMESTAMP() { return getToken(DeclarativeParser.T_TIMESTAMP, 0); }
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_typeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			_la = _input.LA(1);
			if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & 31L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StrLitContext extends LiteralContext {
		public TerminalNode STRING() { return getToken(DeclarativeParser.STRING, 0); }
		public StrLitContext(LiteralContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueLitContext extends LiteralContext {
		public TerminalNode TRUE() { return getToken(DeclarativeParser.TRUE, 0); }
		public TrueLitContext(LiteralContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseLitContext extends LiteralContext {
		public TerminalNode FALSE() { return getToken(DeclarativeParser.FALSE, 0); }
		public FalseLitContext(LiteralContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumLitContext extends LiteralContext {
		public TerminalNode NUMBER() { return getToken(DeclarativeParser.NUMBER, 0); }
		public NumLitContext(LiteralContext ctx) { copyFrom(ctx); }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_literal);
		try {
			setState(491);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new NumLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(487);
				match(NUMBER);
				}
				break;
			case STRING:
				_localctx = new StrLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(488);
				match(STRING);
				}
				break;
			case TRUE:
				_localctx = new TrueLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(489);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseLitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(490);
				match(FALSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DeclarativeParser.ID, 0); }
		public TerminalNode STRING() { return getToken(DeclarativeParser.STRING, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493);
			_la = _input.LA(1);
			if ( !(_la==STRING || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 20:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 12);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001b\u01f0\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u0000E\b\u0000\u0001\u0001\u0001\u0001\u0005\u0001I\b\u0001\n\u0001"+
		"\f\u0001L\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001R\b\u0001\n\u0001\f\u0001U\t\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001Y\b\u0001\n\u0001\f\u0001\\\t\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001b\b\u0001\n\u0001\f\u0001e\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001i\b\u0001\n\u0001\f\u0001l\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001p\b\u0001\n\u0001\f\u0001s\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001w\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u0002\u0082\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003\u008d\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u0096\b\u0004\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u009a\b\u0005\n\u0005\f\u0005\u009d\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00a3\b\u0005\n\u0005"+
		"\f\u0005\u00a6\t\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00aa\b\u0005"+
		"\n\u0005\f\u0005\u00ad\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u00b3\b\u0005\n\u0005\f\u0005\u00b6\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005\u00ba\b\u0005\n\u0005\f\u0005\u00bd\t\u0005\u0001"+
		"\u0005\u0001\u0005\u0005\u0005\u00c1\b\u0005\n\u0005\f\u0005\u00c4\t\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005\u00c8\b\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006\u00d3\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u00de\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00e4"+
		"\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\n\u00f7\b\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0005\r\u0102\b\r\n\r\f\r\u0105\t\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u010d"+
		"\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0003\u000f\u0116\b\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u011f"+
		"\b\u0010\n\u0010\f\u0010\u0122\t\u0010\u0003\u0010\u0124\b\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u0129\b\u0010\n\u0010\f\u0010"+
		"\u012c\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u0132\b\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0143\b\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u0153\b\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u015f\b\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0005\u0014\u017c\b\u0014\n\u0014\f\u0014\u017f\t\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u01d8\b\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u01ec"+
		"\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0000\u0001(\u001f\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.02468:<\u0000\t\u0001\u0000Z\\\u0001\u0000-6\u0001\u0000"+
		"MR\u0001\u0000SV\u0001\u0000\u001f#\u0001\u0000$)\u0001\u0000*,\u0001"+
		"\u0000HL\u0002\u0000]]__\u0215\u0000D\u0001\u0000\u0000\u0000\u0002v\u0001"+
		"\u0000\u0000\u0000\u0004\u0081\u0001\u0000\u0000\u0000\u0006\u008c\u0001"+
		"\u0000\u0000\u0000\b\u0095\u0001\u0000\u0000\u0000\n\u00c7\u0001\u0000"+
		"\u0000\u0000\f\u00d2\u0001\u0000\u0000\u0000\u000e\u00dd\u0001\u0000\u0000"+
		"\u0000\u0010\u00e3\u0001\u0000\u0000\u0000\u0012\u00e5\u0001\u0000\u0000"+
		"\u0000\u0014\u00ef\u0001\u0000\u0000\u0000\u0016\u00f8\u0001\u0000\u0000"+
		"\u0000\u0018\u00fa\u0001\u0000\u0000\u0000\u001a\u00fd\u0001\u0000\u0000"+
		"\u0000\u001c\u010c\u0001\u0000\u0000\u0000\u001e\u010e\u0001\u0000\u0000"+
		"\u0000 \u0119\u0001\u0000\u0000\u0000\"\u012d\u0001\u0000\u0000\u0000"+
		"$\u0142\u0001\u0000\u0000\u0000&\u0152\u0001\u0000\u0000\u0000(\u015e"+
		"\u0001\u0000\u0000\u0000*\u01d7\u0001\u0000\u0000\u0000,\u01d9\u0001\u0000"+
		"\u0000\u0000.\u01db\u0001\u0000\u0000\u00000\u01dd\u0001\u0000\u0000\u0000"+
		"2\u01df\u0001\u0000\u0000\u00004\u01e1\u0001\u0000\u0000\u00006\u01e3"+
		"\u0001\u0000\u0000\u00008\u01e5\u0001\u0000\u0000\u0000:\u01eb\u0001\u0000"+
		"\u0000\u0000<\u01ed\u0001\u0000\u0000\u0000>?\u0003\u0002\u0001\u0000"+
		"?@\u0005\u0000\u0000\u0001@E\u0001\u0000\u0000\u0000AB\u0003\n\u0005\u0000"+
		"BC\u0005\u0000\u0000\u0001CE\u0001\u0000\u0000\u0000D>\u0001\u0000\u0000"+
		"\u0000DA\u0001\u0000\u0000\u0000E\u0001\u0001\u0000\u0000\u0000FJ\u0003"+
		"\u0004\u0002\u0000GI\u0003\b\u0004\u0000HG\u0001\u0000\u0000\u0000IL\u0001"+
		"\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000"+
		"KM\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000MN\u0003\u0006\u0003"+
		"\u0000Nw\u0001\u0000\u0000\u0000OS\u0003\u0004\u0002\u0000PR\u0003\b\u0004"+
		"\u0000QP\u0001\u0000\u0000\u0000RU\u0001\u0000\u0000\u0000SQ\u0001\u0000"+
		"\u0000\u0000ST\u0001\u0000\u0000\u0000TV\u0001\u0000\u0000\u0000US\u0001"+
		"\u0000\u0000\u0000VZ\u0003\u0012\t\u0000WY\u0003\u0010\b\u0000XW\u0001"+
		"\u0000\u0000\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000\u0000"+
		"Z[\u0001\u0000\u0000\u0000[]\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000"+
		"\u0000]^\u0003\u000e\u0007\u0000^w\u0001\u0000\u0000\u0000_c\u0003\u0004"+
		"\u0002\u0000`b\u0003\b\u0004\u0000a`\u0001\u0000\u0000\u0000be\u0001\u0000"+
		"\u0000\u0000ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000df\u0001"+
		"\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000fj\u0003\u0012\t\u0000gi\u0003"+
		"\u0010\b\u0000hg\u0001\u0000\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001"+
		"\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000km\u0001\u0000\u0000\u0000"+
		"lj\u0001\u0000\u0000\u0000mq\u0003\u0014\n\u0000np\u0003\b\u0004\u0000"+
		"on\u0001\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000rt\u0001\u0000\u0000\u0000sq\u0001\u0000"+
		"\u0000\u0000tu\u0003\u0006\u0003\u0000uw\u0001\u0000\u0000\u0000vF\u0001"+
		"\u0000\u0000\u0000vO\u0001\u0000\u0000\u0000v_\u0001\u0000\u0000\u0000"+
		"w\u0003\u0001\u0000\u0000\u0000xy\u0005\u0001\u0000\u0000yz\u0005\u0014"+
		"\u0000\u0000z\u0082\u0003<\u001e\u0000{|\u0005\u0001\u0000\u0000|}\u0005"+
		"\u0016\u0000\u0000}~\u0003<\u001e\u0000~\u007f\u0005\u0003\u0000\u0000"+
		"\u007f\u0080\u0005\u0010\u0000\u0000\u0080\u0082\u0001\u0000\u0000\u0000"+
		"\u0081x\u0001\u0000\u0000\u0000\u0081{\u0001\u0000\u0000\u0000\u0082\u0005"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005\u0002\u0000\u0000\u0084\u0085"+
		"\u0005\u0014\u0000\u0000\u0085\u008d\u0003<\u001e\u0000\u0086\u0087\u0005"+
		"\u0002\u0000\u0000\u0087\u0088\u0005\u0016\u0000\u0000\u0088\u0089\u0003"+
		"<\u001e\u0000\u0089\u008a\u0005\u0003\u0000\u0000\u008a\u008b\u0005\u0010"+
		"\u0000\u0000\u008b\u008d\u0001\u0000\u0000\u0000\u008c\u0083\u0001\u0000"+
		"\u0000\u0000\u008c\u0086\u0001\u0000\u0000\u0000\u008d\u0007\u0001\u0000"+
		"\u0000\u0000\u008e\u0096\u0003\u0018\f\u0000\u008f\u0096\u0003\u001a\r"+
		"\u0000\u0090\u0091\u0003\u001e\u000f\u0000\u0091\u0092\u0003 \u0010\u0000"+
		"\u0092\u0096\u0001\u0000\u0000\u0000\u0093\u0096\u0003 \u0010\u0000\u0094"+
		"\u0096\u0003$\u0012\u0000\u0095\u008e\u0001\u0000\u0000\u0000\u0095\u008f"+
		"\u0001\u0000\u0000\u0000\u0095\u0090\u0001\u0000\u0000\u0000\u0095\u0093"+
		"\u0001\u0000\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096\t\u0001"+
		"\u0000\u0000\u0000\u0097\u009b\u0003\f\u0006\u0000\u0098\u009a\u0003\u0010"+
		"\b\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000"+
		"\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000"+
		"\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000"+
		"\u0000\u009e\u009f\u0003\u000e\u0007\u0000\u009f\u00c8\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a4\u0003\f\u0006\u0000\u00a1\u00a3\u0003\u0010\b\u0000"+
		"\u00a2\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a7\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a7\u00ab\u0003\u0014\n\u0000\u00a8\u00aa\u0003\b\u0004\u0000\u00a9"+
		"\u00a8\u0001\u0000\u0000\u0000\u00aa\u00ad\u0001\u0000\u0000\u0000\u00ab"+
		"\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ae\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ae"+
		"\u00af\u0003\u0006\u0003\u0000\u00af\u00c8\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b4\u0003\f\u0006\u0000\u00b1\u00b3\u0003\u0010\b\u0000\u00b2\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000\u0000\u0000\u00b4\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b7\u00bb"+
		"\u0003\u0014\n\u0000\u00b8\u00ba\u0003\b\u0004\u0000\u00b9\u00b8\u0001"+
		"\u0000\u0000\u0000\u00ba\u00bd\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000\u00bc\u00be\u0001"+
		"\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00be\u00c2\u0003"+
		"\u0012\t\u0000\u00bf\u00c1\u0003\u0010\b\u0000\u00c0\u00bf\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c4\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c5\u00c6\u0003\u000e"+
		"\u0007\u0000\u00c6\u00c8\u0001\u0000\u0000\u0000\u00c7\u0097\u0001\u0000"+
		"\u0000\u0000\u00c7\u00a0\u0001\u0000\u0000\u0000\u00c7\u00b0\u0001\u0000"+
		"\u0000\u0000\u00c8\u000b\u0001\u0000\u0000\u0000\u00c9\u00ca\u0005\u0001"+
		"\u0000\u0000\u00ca\u00cb\u0005\u0015\u0000\u0000\u00cb\u00d3\u0003<\u001e"+
		"\u0000\u00cc\u00cd\u0005\u0001\u0000\u0000\u00cd\u00ce\u0005\u0016\u0000"+
		"\u0000\u00ce\u00cf\u0003<\u001e\u0000\u00cf\u00d0\u0005\u0003\u0000\u0000"+
		"\u00d0\u00d1\u0005\u000f\u0000\u0000\u00d1\u00d3\u0001\u0000\u0000\u0000"+
		"\u00d2\u00c9\u0001\u0000\u0000\u0000\u00d2\u00cc\u0001\u0000\u0000\u0000"+
		"\u00d3\r\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005\u0002\u0000\u0000\u00d5"+
		"\u00d6\u0005\u0015\u0000\u0000\u00d6\u00de\u0003<\u001e\u0000\u00d7\u00d8"+
		"\u0005\u0002\u0000\u0000\u00d8\u00d9\u0005\u0016\u0000\u0000\u00d9\u00da"+
		"\u0003<\u001e\u0000\u00da\u00db\u0005\u0003\u0000\u0000\u00db\u00dc\u0005"+
		"\u000f\u0000\u0000\u00dc\u00de\u0001\u0000\u0000\u0000\u00dd\u00d4\u0001"+
		"\u0000\u0000\u0000\u00dd\u00d7\u0001\u0000\u0000\u0000\u00de\u000f\u0001"+
		"\u0000\u0000\u0000\u00df\u00e4\u0003\u0018\f\u0000\u00e0\u00e4\u0003\u001a"+
		"\r\u0000\u00e1\u00e4\u0003 \u0010\u0000\u00e2\u00e4\u0003$\u0012\u0000"+
		"\u00e3\u00df\u0001\u0000\u0000\u0000\u00e3\u00e0\u0001\u0000\u0000\u0000"+
		"\u00e3\u00e1\u0001\u0000\u0000\u0000\u00e3\u00e2\u0001\u0000\u0000\u0000"+
		"\u00e4\u0011\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005\r\u0000\u0000\u00e6"+
		"\u00e7\u0005\u000e\u0000\u0000\u00e7\u00e8\u0005\u000f\u0000\u0000\u00e8"+
		"\u00e9\u0005\u0011\u0000\u0000\u00e9\u00ea\u0003<\u001e\u0000\u00ea\u00eb"+
		"\u0005\u0012\u0000\u0000\u00eb\u00ec\u0003<\u001e\u0000\u00ec\u00ed\u0005"+
		"\u0013\u0000\u0000\u00ed\u00ee\u00034\u001a\u0000\u00ee\u0013\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f0\u0005\r\u0000\u0000\u00f0\u00f1\u0005\u000e\u0000"+
		"\u0000\u00f1\u00f2\u0005\u0010\u0000\u0000\u00f2\u00f6\u0003\u0016\u000b"+
		"\u0000\u00f3\u00f4\u0005\u0004\u0000\u0000\u00f4\u00f5\u0005\f\u0000\u0000"+
		"\u00f5\u00f7\u0005]\u0000\u0000\u00f6\u00f3\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f7\u0001\u0000\u0000\u0000\u00f7\u0015\u0001\u0000\u0000\u0000\u00f8"+
		"\u00f9\u0007\u0000\u0000\u0000\u00f9\u0017\u0001\u0000\u0000\u0000\u00fa"+
		"\u00fb\u0005\u0005\u0000\u0000\u00fb\u00fc\u0003(\u0014\u0000\u00fc\u0019"+
		"\u0001\u0000\u0000\u0000\u00fd\u00fe\u0005\u0006\u0000\u0000\u00fe\u0103"+
		"\u0003\u001c\u000e\u0000\u00ff\u0100\u0005W\u0000\u0000\u0100\u0102\u0003"+
		"\u001c\u000e\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0102\u0105\u0001"+
		"\u0000\u0000\u0000\u0103\u0101\u0001\u0000\u0000\u0000\u0103\u0104\u0001"+
		"\u0000\u0000\u0000\u0104\u001b\u0001\u0000\u0000\u0000\u0105\u0103\u0001"+
		"\u0000\u0000\u0000\u0106\u0107\u0003(\u0014\u0000\u0107\u0108\u0005\u0003"+
		"\u0000\u0000\u0108\u0109\u0003<\u001e\u0000\u0109\u010d\u0001\u0000\u0000"+
		"\u0000\u010a\u010d\u0003<\u001e\u0000\u010b\u010d\u0005U\u0000\u0000\u010c"+
		"\u0106\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000\u010c"+
		"\u010b\u0001\u0000\u0000\u0000\u010d\u001d\u0001\u0000\u0000\u0000\u010e"+
		"\u010f\u0005\u000b\u0000\u0000\u010f\u0110\u0005X\u0000\u0000\u0110\u0111"+
		"\u0005]\u0000\u0000\u0111\u0112\u0005W\u0000\u0000\u0112\u0115\u0003<"+
		"\u001e\u0000\u0113\u0114\u0005W\u0000\u0000\u0114\u0116\u0005]\u0000\u0000"+
		"\u0115\u0113\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000"+
		"\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0118\u0005Y\u0000\u0000\u0118"+
		"\u001f\u0001\u0000\u0000\u0000\u0119\u011a\u0005\u0007\u0000\u0000\u011a"+
		"\u0123\u0005\b\u0000\u0000\u011b\u0120\u0003<\u001e\u0000\u011c\u011d"+
		"\u0005W\u0000\u0000\u011d\u011f\u0003<\u001e\u0000\u011e\u011c\u0001\u0000"+
		"\u0000\u0000\u011f\u0122\u0001\u0000\u0000\u0000\u0120\u011e\u0001\u0000"+
		"\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121\u0124\u0001\u0000"+
		"\u0000\u0000\u0122\u0120\u0001\u0000\u0000\u0000\u0123\u011b\u0001\u0000"+
		"\u0000\u0000\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000"+
		"\u0000\u0000\u0125\u012a\u0003\"\u0011\u0000\u0126\u0127\u0005W\u0000"+
		"\u0000\u0127\u0129\u0003\"\u0011\u0000\u0128\u0126\u0001\u0000\u0000\u0000"+
		"\u0129\u012c\u0001\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000"+
		"\u012a\u012b\u0001\u0000\u0000\u0000\u012b!\u0001\u0000\u0000\u0000\u012c"+
		"\u012a\u0001\u0000\u0000\u0000\u012d\u012e\u0003,\u0016\u0000\u012e\u0131"+
		"\u0005X\u0000\u0000\u012f\u0132\u0003(\u0014\u0000\u0130\u0132\u0005U"+
		"\u0000\u0000\u0131\u012f\u0001\u0000\u0000\u0000\u0131\u0130\u0001\u0000"+
		"\u0000\u0000\u0132\u0133\u0001\u0000\u0000\u0000\u0133\u0134\u0005Y\u0000"+
		"\u0000\u0134\u0135\u0005\u0003\u0000\u0000\u0135\u0136\u0003<\u001e\u0000"+
		"\u0136#\u0001\u0000\u0000\u0000\u0137\u0138\u00032\u0019\u0000\u0138\u0139"+
		"\u0005\t\u0000\u0000\u0139\u013a\u0003&\u0013\u0000\u013a\u013b\u0005"+
		"\u0004\u0000\u0000\u013b\u013c\u0003(\u0014\u0000\u013c\u0143\u0001\u0000"+
		"\u0000\u0000\u013d\u013e\u0005\n\u0000\u0000\u013e\u013f\u0003&\u0013"+
		"\u0000\u013f\u0140\u0005\u0004\u0000\u0000\u0140\u0141\u0003(\u0014\u0000"+
		"\u0141\u0143\u0001\u0000\u0000\u0000\u0142\u0137\u0001\u0000\u0000\u0000"+
		"\u0142\u013d\u0001\u0000\u0000\u0000\u0143%\u0001\u0000\u0000\u0000\u0144"+
		"\u0145\u0005\u0014\u0000\u0000\u0145\u0153\u0003<\u001e\u0000\u0146\u0147"+
		"\u0005\u0015\u0000\u0000\u0147\u0153\u0003<\u001e\u0000\u0148\u0149\u0005"+
		"\u0016\u0000\u0000\u0149\u014a\u0003<\u001e\u0000\u014a\u014b\u0005\u0003"+
		"\u0000\u0000\u014b\u014c\u0005\u0010\u0000\u0000\u014c\u0153\u0001\u0000"+
		"\u0000\u0000\u014d\u014e\u0005\u0016\u0000\u0000\u014e\u014f\u0003<\u001e"+
		"\u0000\u014f\u0150\u0005\u0003\u0000\u0000\u0150\u0151\u0005\u000f\u0000"+
		"\u0000\u0151\u0153\u0001\u0000\u0000\u0000\u0152\u0144\u0001\u0000\u0000"+
		"\u0000\u0152\u0146\u0001\u0000\u0000\u0000\u0152\u0148\u0001\u0000\u0000"+
		"\u0000\u0152\u014d\u0001\u0000\u0000\u0000\u0153\'\u0001\u0000\u0000\u0000"+
		"\u0154\u0155\u0006\u0014\uffff\uffff\u0000\u0155\u0156\u0005\u0019\u0000"+
		"\u0000\u0156\u015f\u0003(\u0014\n\u0157\u015f\u0003*\u0015\u0000\u0158"+
		"\u015f\u0003<\u001e\u0000\u0159\u015f\u0003:\u001d\u0000\u015a\u015b\u0005"+
		"X\u0000\u0000\u015b\u015c\u0003(\u0014\u0000\u015c\u015d\u0005Y\u0000"+
		"\u0000\u015d\u015f\u0001\u0000\u0000\u0000\u015e\u0154\u0001\u0000\u0000"+
		"\u0000\u015e\u0157\u0001\u0000\u0000\u0000\u015e\u0158\u0001\u0000\u0000"+
		"\u0000\u015e\u0159\u0001\u0000\u0000\u0000\u015e\u015a\u0001\u0000\u0000"+
		"\u0000\u015f\u017d\u0001\u0000\u0000\u0000\u0160\u0161\n\f\u0000\u0000"+
		"\u0161\u0162\u0005\u0017\u0000\u0000\u0162\u017c\u0003(\u0014\r\u0163"+
		"\u0164\n\u000b\u0000\u0000\u0164\u0165\u0005\u0018\u0000\u0000\u0165\u017c"+
		"\u0003(\u0014\f\u0166\u0167\n\t\u0000\u0000\u0167\u0168\u0003.\u0017\u0000"+
		"\u0168\u0169\u0003(\u0014\n\u0169\u017c\u0001\u0000\u0000\u0000\u016a"+
		"\u016b\n\b\u0000\u0000\u016b\u016c\u00030\u0018\u0000\u016c\u016d\u0003"+
		"(\u0014\t\u016d\u017c\u0001\u0000\u0000\u0000\u016e\u016f\n\u0007\u0000"+
		"\u0000\u016f\u0170\u0005\u001c\u0000\u0000\u0170\u0171\u0003(\u0014\u0000"+
		"\u0171\u0172\u0005\u0017\u0000\u0000\u0172\u0173\u0003(\u0014\b\u0173"+
		"\u017c\u0001\u0000\u0000\u0000\u0174\u0175\n\u0006\u0000\u0000\u0175\u0176"+
		"\u0005\u001a\u0000\u0000\u0176\u017c\u0005\u001b\u0000\u0000\u0177\u0178"+
		"\n\u0005\u0000\u0000\u0178\u0179\u0005\u001a\u0000\u0000\u0179\u017a\u0005"+
		"\u0019\u0000\u0000\u017a\u017c\u0005\u001b\u0000\u0000\u017b\u0160\u0001"+
		"\u0000\u0000\u0000\u017b\u0163\u0001\u0000\u0000\u0000\u017b\u0166\u0001"+
		"\u0000\u0000\u0000\u017b\u016a\u0001\u0000\u0000\u0000\u017b\u016e\u0001"+
		"\u0000\u0000\u0000\u017b\u0174\u0001\u0000\u0000\u0000\u017b\u0177\u0001"+
		"\u0000\u0000\u0000\u017c\u017f\u0001\u0000\u0000\u0000\u017d\u017b\u0001"+
		"\u0000\u0000\u0000\u017d\u017e\u0001\u0000\u0000\u0000\u017e)\u0001\u0000"+
		"\u0000\u0000\u017f\u017d\u0001\u0000\u0000\u0000\u0180\u0181\u00057\u0000"+
		"\u0000\u0181\u0182\u0005X\u0000\u0000\u0182\u0183\u0003(\u0014\u0000\u0183"+
		"\u0184\u0005Y\u0000\u0000\u0184\u01d8\u0001\u0000\u0000\u0000\u0185\u0186"+
		"\u00058\u0000\u0000\u0186\u0187\u0005X\u0000\u0000\u0187\u0188\u0003("+
		"\u0014\u0000\u0188\u0189\u0005Y\u0000\u0000\u0189\u01d8\u0001\u0000\u0000"+
		"\u0000\u018a\u018b\u00059\u0000\u0000\u018b\u018c\u0005X\u0000\u0000\u018c"+
		"\u018d\u0003(\u0014\u0000\u018d\u018e\u0005W\u0000\u0000\u018e\u018f\u0005"+
		"^\u0000\u0000\u018f\u0190\u0005Y\u0000\u0000\u0190\u01d8\u0001\u0000\u0000"+
		"\u0000\u0191\u0192\u0005:\u0000\u0000\u0192\u0193\u0005X\u0000\u0000\u0193"+
		"\u0194\u0003(\u0014\u0000\u0194\u0195\u0005Y\u0000\u0000\u0195\u01d8\u0001"+
		"\u0000\u0000\u0000\u0196\u0197\u0005;\u0000\u0000\u0197\u0198\u0005X\u0000"+
		"\u0000\u0198\u0199\u0003(\u0014\u0000\u0199\u019a\u0005Y\u0000\u0000\u019a"+
		"\u01d8\u0001\u0000\u0000\u0000\u019b\u019c\u0005<\u0000\u0000\u019c\u019d"+
		"\u0005X\u0000\u0000\u019d\u019e\u0003(\u0014\u0000\u019e\u019f\u0005Y"+
		"\u0000\u0000\u019f\u01d8\u0001\u0000\u0000\u0000\u01a0\u01a1\u0005=\u0000"+
		"\u0000\u01a1\u01a2\u0005X\u0000\u0000\u01a2\u01a3\u0003(\u0014\u0000\u01a3"+
		"\u01a4\u0005W\u0000\u0000\u01a4\u01a5\u00038\u001c\u0000\u01a5\u01a6\u0005"+
		"Y\u0000\u0000\u01a6\u01d8\u0001\u0000\u0000\u0000\u01a7\u01a8\u0005?\u0000"+
		"\u0000\u01a8\u01a9\u0005X\u0000\u0000\u01a9\u01aa\u0003(\u0014\u0000\u01aa"+
		"\u01ab\u0005Y\u0000\u0000\u01ab\u01d8\u0001\u0000\u0000\u0000\u01ac\u01ad"+
		"\u0005@\u0000\u0000\u01ad\u01ae\u0005X\u0000\u0000\u01ae\u01af\u0003("+
		"\u0014\u0000\u01af\u01b0\u0005W\u0000\u0000\u01b0\u01b1\u0005^\u0000\u0000"+
		"\u01b1\u01b2\u0005W\u0000\u0000\u01b2\u01b3\u0005^\u0000\u0000\u01b3\u01b4"+
		"\u0005Y\u0000\u0000\u01b4\u01d8\u0001\u0000\u0000\u0000\u01b5\u01b6\u0005"+
		"B\u0000\u0000\u01b6\u01b7\u0005X\u0000\u0000\u01b7\u01b8\u0003(\u0014"+
		"\u0000\u01b8\u01b9\u0005W\u0000\u0000\u01b9\u01ba\u0005]\u0000\u0000\u01ba"+
		"\u01bb\u0005Y\u0000\u0000\u01bb\u01d8\u0001\u0000\u0000\u0000\u01bc\u01bd"+
		"\u0005C\u0000\u0000\u01bd\u01be\u0005X\u0000\u0000\u01be\u01bf\u0003("+
		"\u0014\u0000\u01bf\u01c0\u0005W\u0000\u0000\u01c0\u01c1\u0005]\u0000\u0000"+
		"\u01c1\u01c2\u0005Y\u0000\u0000\u01c2\u01d8\u0001\u0000\u0000\u0000\u01c3"+
		"\u01c4\u0005D\u0000\u0000\u01c4\u01c5\u0005X\u0000\u0000\u01c5\u01c6\u0003"+
		"(\u0014\u0000\u01c6\u01c7\u0005Y\u0000\u0000\u01c7\u01d8\u0001\u0000\u0000"+
		"\u0000\u01c8\u01c9\u0005E\u0000\u0000\u01c9\u01ca\u0005X\u0000\u0000\u01ca"+
		"\u01cb\u0003(\u0014\u0000\u01cb\u01cc\u0005Y\u0000\u0000\u01cc\u01d8\u0001"+
		"\u0000\u0000\u0000\u01cd\u01ce\u0005F\u0000\u0000\u01ce\u01cf\u0005X\u0000"+
		"\u0000\u01cf\u01d0\u0003(\u0014\u0000\u01d0\u01d1\u0005Y\u0000\u0000\u01d1"+
		"\u01d8\u0001\u0000\u0000\u0000\u01d2\u01d3\u0005G\u0000\u0000\u01d3\u01d4"+
		"\u0005X\u0000\u0000\u01d4\u01d5\u0003(\u0014\u0000\u01d5\u01d6\u0005Y"+
		"\u0000\u0000\u01d6\u01d8\u0001\u0000\u0000\u0000\u01d7\u0180\u0001\u0000"+
		"\u0000\u0000\u01d7\u0185\u0001\u0000\u0000\u0000\u01d7\u018a\u0001\u0000"+
		"\u0000\u0000\u01d7\u0191\u0001\u0000\u0000\u0000\u01d7\u0196\u0001\u0000"+
		"\u0000\u0000\u01d7\u019b\u0001\u0000\u0000\u0000\u01d7\u01a0\u0001\u0000"+
		"\u0000\u0000\u01d7\u01a7\u0001\u0000\u0000\u0000\u01d7\u01ac\u0001\u0000"+
		"\u0000\u0000\u01d7\u01b5\u0001\u0000\u0000\u0000\u01d7\u01bc\u0001\u0000"+
		"\u0000\u0000\u01d7\u01c3\u0001\u0000\u0000\u0000\u01d7\u01c8\u0001\u0000"+
		"\u0000\u0000\u01d7\u01cd\u0001\u0000\u0000\u0000\u01d7\u01d2\u0001\u0000"+
		"\u0000\u0000\u01d8+\u0001\u0000\u0000\u0000\u01d9\u01da\u0007\u0001\u0000"+
		"\u0000\u01da-\u0001\u0000\u0000\u0000\u01db\u01dc\u0007\u0002\u0000\u0000"+
		"\u01dc/\u0001\u0000\u0000\u0000\u01dd\u01de\u0007\u0003\u0000\u0000\u01de"+
		"1\u0001\u0000\u0000\u0000\u01df\u01e0\u0007\u0004\u0000\u0000\u01e03\u0001"+
		"\u0000\u0000\u0000\u01e1\u01e2\u0007\u0005\u0000\u0000\u01e25\u0001\u0000"+
		"\u0000\u0000\u01e3\u01e4\u0007\u0006\u0000\u0000\u01e47\u0001\u0000\u0000"+
		"\u0000\u01e5\u01e6\u0007\u0007\u0000\u0000\u01e69\u0001\u0000\u0000\u0000"+
		"\u01e7\u01ec\u0005^\u0000\u0000\u01e8\u01ec\u0005]\u0000\u0000\u01e9\u01ec"+
		"\u0005\u001d\u0000\u0000\u01ea\u01ec\u0005\u001e\u0000\u0000\u01eb\u01e7"+
		"\u0001\u0000\u0000\u0000\u01eb\u01e8\u0001\u0000\u0000\u0000\u01eb\u01e9"+
		"\u0001\u0000\u0000\u0000\u01eb\u01ea\u0001\u0000\u0000\u0000\u01ec;\u0001"+
		"\u0000\u0000\u0000\u01ed\u01ee\u0007\b\u0000\u0000\u01ee=\u0001\u0000"+
		"\u0000\u0000$DJSZcjqv\u0081\u008c\u0095\u009b\u00a4\u00ab\u00b4\u00bb"+
		"\u00c2\u00c7\u00d2\u00dd\u00e3\u00f6\u0103\u010c\u0115\u0120\u0123\u012a"+
		"\u0131\u0142\u0152\u015e\u017b\u017d\u01d7\u01eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}