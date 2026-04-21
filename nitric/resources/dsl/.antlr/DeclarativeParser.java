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
		REPLACE=34, INCREMENT=35, DECREMENT=36, MAXIMUM=37, MINIMUM=38, COLLECT=39, 
		SNAPSHOT=40, CDC=41, PERIODIC=42, SUM=43, COUNT=44, AVG=45, MAX=46, MIN=47, 
		LAST=48, FIRST=49, STDDEV=50, VARIANCE=51, COLLECT_LIST=52, UPPER=53, 
		LOWER=54, ROUND=55, ABS=56, FLOOR=57, CEIL=58, CAST=59, COALESCE=60, LENGTH=61, 
		SUBSTRING=62, CONCAT=63, TO_TIMESTAMP=64, DATE_FORMAT=65, YEAR=66, MONTH=67, 
		DAY=68, HOUR=69, T_DOUBLE=70, T_LONG=71, T_STRING=72, T_BOOLEAN=73, T_TIMESTAMP=74, 
		EQ=75, NEQ=76, GT=77, LT=78, GTE=79, LTE=80, PLUS=81, MINUS=82, STAR=83, 
		SLASH=84, COMMA=85, LPAREN=86, RPAREN=87, ISTREAM=88, DSTREAM=89, RSTREAM=90, 
		STRING=91, NUMBER=92, ID=93, WS=94, LINE_COMMENT=95, BLOCK_COMMENT=96;
	public static final int
		RULE_pipeline = 0, RULE_dataPipeline = 1, RULE_dataSource = 2, RULE_dataSink = 3, 
		RULE_dataClause = 4, RULE_statePipeline = 5, RULE_stateSource = 6, RULE_stateSink = 7, 
		RULE_stateClause = 8, RULE_crossToState = 9, RULE_crossToData = 10, RULE_whereClause = 11, 
		RULE_selectClause = 12, RULE_selectExpr = 13, RULE_windowClause = 14, 
		RULE_groupByClause = 15, RULE_aggExpr = 16, RULE_joinClause = 17, RULE_expression = 18, 
		RULE_builtinFunc = 19, RULE_aggFunc = 20, RULE_compOp = 21, RULE_arithOp = 22, 
		RULE_joinType = 23, RULE_stateOp = 24, RULE_strategy = 25, RULE_typeName = 26, 
		RULE_literal = 27, RULE_name = 28;
	private static String[] makeRuleNames() {
		return new String[] {
			"pipeline", "dataPipeline", "dataSource", "dataSink", "dataClause", "statePipeline", 
			"stateSource", "stateSink", "stateClause", "crossToState", "crossToData", 
			"whereClause", "selectClause", "selectExpr", "windowClause", "groupByClause", 
			"aggExpr", "joinClause", "expression", "builtinFunc", "aggFunc", "compOp", 
			"arithOp", "joinType", "stateOp", "strategy", "typeName", "literal", 
			"name"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'FROM'", "'INTO'", "'AS'", "'ON'", "'WHERE'", "'SELECT'", "'GROUP'", 
			"'BY'", "'JOIN'", "'ENRICH'", "'WINDOW'", "'SCHEDULE'", "'MAP'", "'TO'", 
			"'STATE'", "'DATA'", "'KEY'", "'VALUE'", "'USING'", "'STREAM'", "'KV'", 
			"'TIMESERIES'", "'AND'", "'OR'", "'NOT'", "'IS'", "'NULL'", "'BETWEEN'", 
			"'TRUE'", "'FALSE'", "'INNER'", "'LEFT'", "'LEFT_SEMI'", "'REPLACE'", 
			"'INCREMENT'", "'DECREMENT'", "'MAXIMUM'", "'MINIMUM'", "'COLLECT'", 
			"'SNAPSHOT'", "'CDC'", "'PERIODIC'", "'SUM'", "'COUNT'", "'AVG'", "'MAX'", 
			"'MIN'", "'LAST'", "'FIRST'", "'STDDEV'", "'VARIANCE'", "'COLLECT_LIST'", 
			"'UPPER'", "'LOWER'", "'ROUND'", "'ABS'", "'FLOOR'", "'CEIL'", "'CAST'", 
			"'COALESCE'", "'LENGTH'", "'SUBSTRING'", "'CONCAT'", "'TO_TIMESTAMP'", 
			"'DATE_FORMAT'", "'YEAR'", "'MONTH'", "'DAY'", "'HOUR'", "'DOUBLE'", 
			"'LONG'", "'STRING_TYPE'", "'BOOLEAN'", "'TIMESTAMP'", "'='", "'!='", 
			"'>'", "'<'", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "','", "'('", 
			"')'", "'ISTREAM'", "'DSTREAM'", "'RSTREAM'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "FROM", "INTO", "AS", "ON", "WHERE", "SELECT", "GROUP", "BY", "JOIN", 
			"ENRICH", "WINDOW", "SCHEDULE", "MAP", "TO", "STATE", "DATA", "KEY", 
			"VALUE", "USING", "STREAM", "KV", "TIMESERIES", "AND", "OR", "NOT", "IS", 
			"NULL", "BETWEEN", "TRUE", "FALSE", "INNER", "LEFT", "LEFT_SEMI", "REPLACE", 
			"INCREMENT", "DECREMENT", "MAXIMUM", "MINIMUM", "COLLECT", "SNAPSHOT", 
			"CDC", "PERIODIC", "SUM", "COUNT", "AVG", "MAX", "MIN", "LAST", "FIRST", 
			"STDDEV", "VARIANCE", "COLLECT_LIST", "UPPER", "LOWER", "ROUND", "ABS", 
			"FLOOR", "CEIL", "CAST", "COALESCE", "LENGTH", "SUBSTRING", "CONCAT", 
			"TO_TIMESTAMP", "DATE_FORMAT", "YEAR", "MONTH", "DAY", "HOUR", "T_DOUBLE", 
			"T_LONG", "T_STRING", "T_BOOLEAN", "T_TIMESTAMP", "EQ", "NEQ", "GT", 
			"LT", "GTE", "LTE", "PLUS", "MINUS", "STAR", "SLASH", "COMMA", "LPAREN", 
			"RPAREN", "ISTREAM", "DSTREAM", "RSTREAM", "STRING", "NUMBER", "ID", 
			"WS", "LINE_COMMENT", "BLOCK_COMMENT"
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
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				dataPipeline();
				setState(59);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				statePipeline();
				setState(62);
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
			setState(114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				dataSource();
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(67);
					dataClause();
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73);
				dataSink();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				dataSource();
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(76);
					dataClause();
					}
					}
					setState(81);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(82);
				crossToState();
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(83);
					stateClause();
					}
					}
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(89);
				stateSink();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				dataSource();
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(92);
					dataClause();
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(98);
				crossToState();
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(99);
					stateClause();
					}
					}
					setState(104);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(105);
				crossToData();
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(106);
					dataClause();
					}
					}
					setState(111);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(112);
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
			setState(125);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new FromStreamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(116);
				match(FROM);
				setState(117);
				match(STREAM);
				setState(118);
				name();
				}
				break;
			case 2:
				_localctx = new FromTimeseriesDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				match(FROM);
				setState(120);
				match(TIMESERIES);
				setState(121);
				name();
				setState(122);
				match(AS);
				setState(123);
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
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new IntoStreamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				match(INTO);
				setState(128);
				match(STREAM);
				setState(129);
				name();
				}
				break;
			case 2:
				_localctx = new IntoTimeseriesDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(130);
				match(INTO);
				setState(131);
				match(TIMESERIES);
				setState(132);
				name();
				setState(133);
				match(AS);
				setState(134);
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
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHERE:
				_localctx = new WhereDataContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				whereClause();
				}
				break;
			case SELECT:
				_localctx = new SelectDataContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				selectClause();
				}
				break;
			case WINDOW:
				_localctx = new WindowGroupByContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(140);
				windowClause();
				setState(141);
				groupByClause();
				}
				break;
			case GROUP:
				_localctx = new GroupByDataContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(143);
				groupByClause();
				}
				break;
			case ENRICH:
			case INNER:
			case LEFT:
			case LEFT_SEMI:
				_localctx = new JoinDataContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(144);
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
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(147);
				stateSource();
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(148);
					stateClause();
					}
					}
					setState(153);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(154);
				stateSink();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				stateSource();
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(157);
					stateClause();
					}
					}
					setState(162);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(163);
				crossToData();
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(164);
					dataClause();
					}
					}
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(170);
				dataSink();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(172);
				stateSource();
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(173);
					stateClause();
					}
					}
					setState(178);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(179);
				crossToData();
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032388832L) != 0)) {
					{
					{
					setState(180);
					dataClause();
					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(186);
				crossToState();
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032386784L) != 0)) {
					{
					{
					setState(187);
					stateClause();
					}
					}
					setState(192);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(193);
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
			setState(206);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new FromKVContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(197);
				match(FROM);
				setState(198);
				match(KV);
				setState(199);
				name();
				}
				break;
			case 2:
				_localctx = new FromTimeseriesStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(200);
				match(FROM);
				setState(201);
				match(TIMESERIES);
				setState(202);
				name();
				setState(203);
				match(AS);
				setState(204);
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
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new IntoKVContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				match(INTO);
				setState(209);
				match(KV);
				setState(210);
				name();
				}
				break;
			case 2:
				_localctx = new IntoTimeseriesStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				match(INTO);
				setState(212);
				match(TIMESERIES);
				setState(213);
				name();
				setState(214);
				match(AS);
				setState(215);
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
			setState(223);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHERE:
				_localctx = new WhereStateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(219);
				whereClause();
				}
				break;
			case SELECT:
				_localctx = new SelectStateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(220);
				selectClause();
				}
				break;
			case GROUP:
				_localctx = new GroupByStateContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(221);
				groupByClause();
				}
				break;
			case ENRICH:
			case INNER:
			case LEFT:
			case LEFT_SEMI:
				_localctx = new JoinStateContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(222);
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
			setState(225);
			match(MAP);
			setState(226);
			match(TO);
			setState(227);
			match(STATE);
			setState(228);
			match(KEY);
			setState(229);
			((CrossToStateContext)_localctx).keyCol = name();
			setState(230);
			match(VALUE);
			setState(231);
			((CrossToStateContext)_localctx).valueCol = name();
			setState(232);
			match(USING);
			setState(233);
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
		public TerminalNode ISTREAM() { return getToken(DeclarativeParser.ISTREAM, 0); }
		public TerminalNode DSTREAM() { return getToken(DeclarativeParser.DSTREAM, 0); }
		public TerminalNode RSTREAM() { return getToken(DeclarativeParser.RSTREAM, 0); }
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
			setState(252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(235);
				match(MAP);
				setState(236);
				match(TO);
				setState(237);
				match(DATA);
				setState(238);
				match(ISTREAM);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				match(MAP);
				setState(240);
				match(TO);
				setState(241);
				match(DATA);
				setState(242);
				match(DSTREAM);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(243);
				match(MAP);
				setState(244);
				match(TO);
				setState(245);
				match(DATA);
				setState(246);
				match(RSTREAM);
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ON) {
					{
					setState(247);
					match(ON);
					setState(248);
					match(SCHEDULE);
					setState(249);
					((CrossToDataContext)_localctx).schedule = match(STRING);
					}
				}

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
		enterRule(_localctx, 22, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(WHERE);
			setState(255);
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
		enterRule(_localctx, 24, RULE_selectClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			match(SELECT);
			setState(258);
			selectExpr();
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(259);
				match(COMMA);
				setState(260);
				selectExpr();
				}
				}
				setState(265);
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
		enterRule(_localctx, 26, RULE_selectExpr);
		try {
			setState(272);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new AliasedSelectContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(266);
				expression(0);
				setState(267);
				match(AS);
				setState(268);
				name();
				}
				break;
			case 2:
				_localctx = new ColumnSelectContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				name();
				}
				break;
			case 3:
				_localctx = new StarSelectContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(271);
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
		enterRule(_localctx, 28, RULE_windowClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(WINDOW);
			setState(275);
			match(LPAREN);
			setState(276);
			((WindowClauseContext)_localctx).duration = match(STRING);
			setState(277);
			match(COMMA);
			setState(278);
			((WindowClauseContext)_localctx).timeCol = name();
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(279);
				match(COMMA);
				setState(280);
				((WindowClauseContext)_localctx).slide = match(STRING);
				}
			}

			setState(283);
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
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<AggExprContext> aggExpr() {
			return getRuleContexts(AggExprContext.class);
		}
		public AggExprContext aggExpr(int i) {
			return getRuleContext(AggExprContext.class,i);
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
		enterRule(_localctx, 30, RULE_groupByClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(GROUP);
			setState(286);
			match(BY);
			setState(287);
			name();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(288);
				match(COMMA);
				setState(289);
				name();
				}
				}
				setState(294);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(295);
			aggExpr();
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(296);
				match(COMMA);
				setState(297);
				aggExpr();
				}
				}
				setState(302);
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
		enterRule(_localctx, 32, RULE_aggExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			aggFunc();
			setState(304);
			match(LPAREN);
			setState(307);
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
			case COALESCE:
			case LENGTH:
			case SUBSTRING:
			case CONCAT:
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
				setState(305);
				expression(0);
				}
				break;
			case STAR:
				{
				setState(306);
				match(STAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(309);
			match(RPAREN);
			setState(310);
			match(AS);
			setState(311);
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
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
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
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode ON() { return getToken(DeclarativeParser.ON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExplicitJoinContext(JoinClauseContext ctx) { copyFrom(ctx); }
	}

	public final JoinClauseContext joinClause() throws RecognitionException {
		JoinClauseContext _localctx = new JoinClauseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_joinClause);
		try {
			setState(324);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
			case LEFT:
			case LEFT_SEMI:
				_localctx = new ExplicitJoinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(313);
				joinType();
				setState(314);
				match(JOIN);
				setState(315);
				name();
				setState(316);
				match(ON);
				setState(317);
				expression(0);
				}
				break;
			case ENRICH:
				_localctx = new EnrichJoinContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(319);
				match(ENRICH);
				setState(320);
				name();
				setState(321);
				match(ON);
				setState(322);
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
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(327);
				match(NOT);
				setState(328);
				expression(10);
				}
				break;
			case 2:
				{
				_localctx = new FuncExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(329);
				builtinFunc();
				}
				break;
			case 3:
				{
				_localctx = new ColRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(330);
				name();
				}
				break;
			case 4:
				{
				_localctx = new LitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(331);
				literal();
				}
				break;
			case 5:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(332);
				match(LPAREN);
				setState(333);
				expression(0);
				setState(334);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(367);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(365);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new AndExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(338);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(339);
						match(AND);
						setState(340);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new OrExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(341);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(342);
						match(OR);
						setState(343);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new CompExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(344);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(345);
						compOp();
						setState(346);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new ArithExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(348);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(349);
						arithOp();
						setState(350);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new BetweenExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(352);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(353);
						match(BETWEEN);
						setState(354);
						expression(0);
						setState(355);
						match(AND);
						setState(356);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new IsNullExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(358);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(359);
						match(IS);
						setState(360);
						match(NULL);
						}
						break;
					case 7:
						{
						_localctx = new IsNotNullExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(361);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(362);
						match(IS);
						setState(363);
						match(NOT);
						setState(364);
						match(NULL);
						}
						break;
					}
					} 
				}
				setState(369);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public TerminalNode COALESCE() { return getToken(DeclarativeParser.COALESCE, 0); }
		public TerminalNode LENGTH() { return getToken(DeclarativeParser.LENGTH, 0); }
		public TerminalNode SUBSTRING() { return getToken(DeclarativeParser.SUBSTRING, 0); }
		public TerminalNode CONCAT() { return getToken(DeclarativeParser.CONCAT, 0); }
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
		enterRule(_localctx, 38, RULE_builtinFunc);
		try {
			setState(471);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UPPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				match(UPPER);
				setState(371);
				match(LPAREN);
				setState(372);
				expression(0);
				setState(373);
				match(RPAREN);
				}
				break;
			case LOWER:
				enterOuterAlt(_localctx, 2);
				{
				setState(375);
				match(LOWER);
				setState(376);
				match(LPAREN);
				setState(377);
				expression(0);
				setState(378);
				match(RPAREN);
				}
				break;
			case ROUND:
				enterOuterAlt(_localctx, 3);
				{
				setState(380);
				match(ROUND);
				setState(381);
				match(LPAREN);
				setState(382);
				expression(0);
				setState(383);
				match(COMMA);
				setState(384);
				match(NUMBER);
				setState(385);
				match(RPAREN);
				}
				break;
			case ABS:
				enterOuterAlt(_localctx, 4);
				{
				setState(387);
				match(ABS);
				setState(388);
				match(LPAREN);
				setState(389);
				expression(0);
				setState(390);
				match(RPAREN);
				}
				break;
			case FLOOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(392);
				match(FLOOR);
				setState(393);
				match(LPAREN);
				setState(394);
				expression(0);
				setState(395);
				match(RPAREN);
				}
				break;
			case CEIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(397);
				match(CEIL);
				setState(398);
				match(LPAREN);
				setState(399);
				expression(0);
				setState(400);
				match(RPAREN);
				}
				break;
			case CAST:
				enterOuterAlt(_localctx, 7);
				{
				setState(402);
				match(CAST);
				setState(403);
				match(LPAREN);
				setState(404);
				expression(0);
				setState(405);
				match(COMMA);
				setState(406);
				typeName();
				setState(407);
				match(RPAREN);
				}
				break;
			case COALESCE:
				enterOuterAlt(_localctx, 8);
				{
				setState(409);
				match(COALESCE);
				setState(410);
				match(LPAREN);
				setState(411);
				expression(0);
				setState(412);
				match(COMMA);
				setState(413);
				expression(0);
				setState(414);
				match(RPAREN);
				}
				break;
			case LENGTH:
				enterOuterAlt(_localctx, 9);
				{
				setState(416);
				match(LENGTH);
				setState(417);
				match(LPAREN);
				setState(418);
				expression(0);
				setState(419);
				match(RPAREN);
				}
				break;
			case SUBSTRING:
				enterOuterAlt(_localctx, 10);
				{
				setState(421);
				match(SUBSTRING);
				setState(422);
				match(LPAREN);
				setState(423);
				expression(0);
				setState(424);
				match(COMMA);
				setState(425);
				match(NUMBER);
				setState(426);
				match(COMMA);
				setState(427);
				match(NUMBER);
				setState(428);
				match(RPAREN);
				}
				break;
			case CONCAT:
				enterOuterAlt(_localctx, 11);
				{
				setState(430);
				match(CONCAT);
				setState(431);
				match(LPAREN);
				setState(432);
				expression(0);
				setState(433);
				match(COMMA);
				setState(434);
				expression(0);
				setState(435);
				match(RPAREN);
				}
				break;
			case TO_TIMESTAMP:
				enterOuterAlt(_localctx, 12);
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
				enterOuterAlt(_localctx, 13);
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
				enterOuterAlt(_localctx, 14);
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
				enterOuterAlt(_localctx, 15);
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
				enterOuterAlt(_localctx, 16);
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
				enterOuterAlt(_localctx, 17);
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
		enterRule(_localctx, 40, RULE_aggFunc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(473);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8998403161718784L) != 0)) ) {
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
		enterRule(_localctx, 42, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			_la = _input.LA(1);
			if ( !(((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & 63L) != 0)) ) {
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
		enterRule(_localctx, 44, RULE_arithOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(477);
			_la = _input.LA(1);
			if ( !(((((_la - 81)) & ~0x3f) == 0 && ((1L << (_la - 81)) & 15L) != 0)) ) {
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
		public JoinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinType; }
	}

	public final JoinTypeContext joinType() throws RecognitionException {
		JoinTypeContext _localctx = new JoinTypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_joinType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032385536L) != 0)) ) {
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
		enterRule(_localctx, 48, RULE_stateOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1082331758592L) != 0)) ) {
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
		enterRule(_localctx, 50, RULE_strategy);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7696581394432L) != 0)) ) {
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
		enterRule(_localctx, 52, RULE_typeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			_la = _input.LA(1);
			if ( !(((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 31L) != 0)) ) {
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
		enterRule(_localctx, 54, RULE_literal);
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
		enterRule(_localctx, 56, RULE_name);
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
		case 18:
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
		"\u0004\u0001`\u01f0\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000A\b\u0000\u0001\u0001\u0001\u0001"+
		"\u0005\u0001E\b\u0001\n\u0001\f\u0001H\t\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001N\b\u0001\n\u0001\f\u0001Q\t\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001U\b\u0001\n\u0001\f\u0001X\t\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001^\b\u0001"+
		"\n\u0001\f\u0001a\t\u0001\u0001\u0001\u0001\u0001\u0005\u0001e\b\u0001"+
		"\n\u0001\f\u0001h\t\u0001\u0001\u0001\u0001\u0001\u0005\u0001l\b\u0001"+
		"\n\u0001\f\u0001o\t\u0001\u0001\u0001\u0001\u0001\u0003\u0001s\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002~\b\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003\u0089\b\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"\u0092\b\u0004\u0001\u0005\u0001\u0005\u0005\u0005\u0096\b\u0005\n\u0005"+
		"\f\u0005\u0099\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005\u009f\b\u0005\n\u0005\f\u0005\u00a2\t\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u00a6\b\u0005\n\u0005\f\u0005\u00a9\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00af\b\u0005\n\u0005"+
		"\f\u0005\u00b2\t\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00b6\b\u0005"+
		"\n\u0005\f\u0005\u00b9\t\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00bd"+
		"\b\u0005\n\u0005\f\u0005\u00c0\t\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u00c4\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00cf\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00da\b\u0007\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0003\b\u00e0\b\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u00fb\b\n\u0003\n\u00fd\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u0106"+
		"\b\f\n\f\f\f\u0109\t\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r"+
		"\u0003\r\u0111\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u011a\b\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005"+
		"\u000f\u0123\b\u000f\n\u000f\f\u000f\u0126\t\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0005\u000f\u012b\b\u000f\n\u000f\f\u000f\u012e\t\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0134\b\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u0145\b\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0151\b\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0005\u0012\u016e\b\u0012\n\u0012\f\u0012\u0171\t\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013"+
		"\u01d8\b\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0003\u001b\u01ec\b\u001b\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0000\u0001$\u001d\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468\u0000\b\u0001\u0000+4\u0001"+
		"\u0000KP\u0001\u0000QT\u0001\u0000\u001f!\u0001\u0000\"\'\u0001\u0000"+
		"(*\u0001\u0000FJ\u0002\u0000[[]]\u0217\u0000@\u0001\u0000\u0000\u0000"+
		"\u0002r\u0001\u0000\u0000\u0000\u0004}\u0001\u0000\u0000\u0000\u0006\u0088"+
		"\u0001\u0000\u0000\u0000\b\u0091\u0001\u0000\u0000\u0000\n\u00c3\u0001"+
		"\u0000\u0000\u0000\f\u00ce\u0001\u0000\u0000\u0000\u000e\u00d9\u0001\u0000"+
		"\u0000\u0000\u0010\u00df\u0001\u0000\u0000\u0000\u0012\u00e1\u0001\u0000"+
		"\u0000\u0000\u0014\u00fc\u0001\u0000\u0000\u0000\u0016\u00fe\u0001\u0000"+
		"\u0000\u0000\u0018\u0101\u0001\u0000\u0000\u0000\u001a\u0110\u0001\u0000"+
		"\u0000\u0000\u001c\u0112\u0001\u0000\u0000\u0000\u001e\u011d\u0001\u0000"+
		"\u0000\u0000 \u012f\u0001\u0000\u0000\u0000\"\u0144\u0001\u0000\u0000"+
		"\u0000$\u0150\u0001\u0000\u0000\u0000&\u01d7\u0001\u0000\u0000\u0000("+
		"\u01d9\u0001\u0000\u0000\u0000*\u01db\u0001\u0000\u0000\u0000,\u01dd\u0001"+
		"\u0000\u0000\u0000.\u01df\u0001\u0000\u0000\u00000\u01e1\u0001\u0000\u0000"+
		"\u00002\u01e3\u0001\u0000\u0000\u00004\u01e5\u0001\u0000\u0000\u00006"+
		"\u01eb\u0001\u0000\u0000\u00008\u01ed\u0001\u0000\u0000\u0000:;\u0003"+
		"\u0002\u0001\u0000;<\u0005\u0000\u0000\u0001<A\u0001\u0000\u0000\u0000"+
		"=>\u0003\n\u0005\u0000>?\u0005\u0000\u0000\u0001?A\u0001\u0000\u0000\u0000"+
		"@:\u0001\u0000\u0000\u0000@=\u0001\u0000\u0000\u0000A\u0001\u0001\u0000"+
		"\u0000\u0000BF\u0003\u0004\u0002\u0000CE\u0003\b\u0004\u0000DC\u0001\u0000"+
		"\u0000\u0000EH\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001"+
		"\u0000\u0000\u0000GI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000"+
		"IJ\u0003\u0006\u0003\u0000Js\u0001\u0000\u0000\u0000KO\u0003\u0004\u0002"+
		"\u0000LN\u0003\b\u0004\u0000ML\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000"+
		"\u0000OM\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PR\u0001\u0000"+
		"\u0000\u0000QO\u0001\u0000\u0000\u0000RV\u0003\u0012\t\u0000SU\u0003\u0010"+
		"\b\u0000TS\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000"+
		"\u0000\u0000VW\u0001\u0000\u0000\u0000WY\u0001\u0000\u0000\u0000XV\u0001"+
		"\u0000\u0000\u0000YZ\u0003\u000e\u0007\u0000Zs\u0001\u0000\u0000\u0000"+
		"[_\u0003\u0004\u0002\u0000\\^\u0003\b\u0004\u0000]\\\u0001\u0000\u0000"+
		"\u0000^a\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000"+
		"\u0000\u0000`b\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000bf\u0003"+
		"\u0012\t\u0000ce\u0003\u0010\b\u0000dc\u0001\u0000\u0000\u0000eh\u0001"+
		"\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000\u0000"+
		"gi\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000im\u0003\u0014\n\u0000"+
		"jl\u0003\b\u0004\u0000kj\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000np\u0001\u0000\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000pq\u0003\u0006\u0003\u0000qs\u0001\u0000"+
		"\u0000\u0000rB\u0001\u0000\u0000\u0000rK\u0001\u0000\u0000\u0000r[\u0001"+
		"\u0000\u0000\u0000s\u0003\u0001\u0000\u0000\u0000tu\u0005\u0001\u0000"+
		"\u0000uv\u0005\u0014\u0000\u0000v~\u00038\u001c\u0000wx\u0005\u0001\u0000"+
		"\u0000xy\u0005\u0016\u0000\u0000yz\u00038\u001c\u0000z{\u0005\u0003\u0000"+
		"\u0000{|\u0005\u0010\u0000\u0000|~\u0001\u0000\u0000\u0000}t\u0001\u0000"+
		"\u0000\u0000}w\u0001\u0000\u0000\u0000~\u0005\u0001\u0000\u0000\u0000"+
		"\u007f\u0080\u0005\u0002\u0000\u0000\u0080\u0081\u0005\u0014\u0000\u0000"+
		"\u0081\u0089\u00038\u001c\u0000\u0082\u0083\u0005\u0002\u0000\u0000\u0083"+
		"\u0084\u0005\u0016\u0000\u0000\u0084\u0085\u00038\u001c\u0000\u0085\u0086"+
		"\u0005\u0003\u0000\u0000\u0086\u0087\u0005\u0010\u0000\u0000\u0087\u0089"+
		"\u0001\u0000\u0000\u0000\u0088\u007f\u0001\u0000\u0000\u0000\u0088\u0082"+
		"\u0001\u0000\u0000\u0000\u0089\u0007\u0001\u0000\u0000\u0000\u008a\u0092"+
		"\u0003\u0016\u000b\u0000\u008b\u0092\u0003\u0018\f\u0000\u008c\u008d\u0003"+
		"\u001c\u000e\u0000\u008d\u008e\u0003\u001e\u000f\u0000\u008e\u0092\u0001"+
		"\u0000\u0000\u0000\u008f\u0092\u0003\u001e\u000f\u0000\u0090\u0092\u0003"+
		"\"\u0011\u0000\u0091\u008a\u0001\u0000\u0000\u0000\u0091\u008b\u0001\u0000"+
		"\u0000\u0000\u0091\u008c\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000"+
		"\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092\t\u0001\u0000\u0000"+
		"\u0000\u0093\u0097\u0003\f\u0006\u0000\u0094\u0096\u0003\u0010\b\u0000"+
		"\u0095\u0094\u0001\u0000\u0000\u0000\u0096\u0099\u0001\u0000\u0000\u0000"+
		"\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000"+
		"\u0098\u009a\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000"+
		"\u009a\u009b\u0003\u000e\u0007\u0000\u009b\u00c4\u0001\u0000\u0000\u0000"+
		"\u009c\u00a0\u0003\f\u0006\u0000\u009d\u009f\u0003\u0010\b\u0000\u009e"+
		"\u009d\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000\u0000\u0000\u00a0"+
		"\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1"+
		"\u00a3\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a7\u0003\u0014\n\u0000\u00a4\u00a6\u0003\b\u0004\u0000\u00a5\u00a4"+
		"\u0001\u0000\u0000\u0000\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a5"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00aa"+
		"\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ab"+
		"\u0003\u0006\u0003\u0000\u00ab\u00c4\u0001\u0000\u0000\u0000\u00ac\u00b0"+
		"\u0003\f\u0006\u0000\u00ad\u00af\u0003\u0010\b\u0000\u00ae\u00ad\u0001"+
		"\u0000\u0000\u0000\u00af\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001"+
		"\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b3\u0001"+
		"\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b7\u0003"+
		"\u0014\n\u0000\u00b4\u00b6\u0003\b\u0004\u0000\u00b5\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00ba\u0001\u0000"+
		"\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00be\u0003\u0012"+
		"\t\u0000\u00bb\u00bd\u0003\u0010\b\u0000\u00bc\u00bb\u0001\u0000\u0000"+
		"\u0000\u00bd\u00c0\u0001\u0000\u0000\u0000\u00be\u00bc\u0001\u0000\u0000"+
		"\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c1\u00c2\u0003\u000e\u0007"+
		"\u0000\u00c2\u00c4\u0001\u0000\u0000\u0000\u00c3\u0093\u0001\u0000\u0000"+
		"\u0000\u00c3\u009c\u0001\u0000\u0000\u0000\u00c3\u00ac\u0001\u0000\u0000"+
		"\u0000\u00c4\u000b\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u0001\u0000"+
		"\u0000\u00c6\u00c7\u0005\u0015\u0000\u0000\u00c7\u00cf\u00038\u001c\u0000"+
		"\u00c8\u00c9\u0005\u0001\u0000\u0000\u00c9\u00ca\u0005\u0016\u0000\u0000"+
		"\u00ca\u00cb\u00038\u001c\u0000\u00cb\u00cc\u0005\u0003\u0000\u0000\u00cc"+
		"\u00cd\u0005\u000f\u0000\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce"+
		"\u00c5\u0001\u0000\u0000\u0000\u00ce\u00c8\u0001\u0000\u0000\u0000\u00cf"+
		"\r\u0001\u0000\u0000\u0000\u00d0\u00d1\u0005\u0002\u0000\u0000\u00d1\u00d2"+
		"\u0005\u0015\u0000\u0000\u00d2\u00da\u00038\u001c\u0000\u00d3\u00d4\u0005"+
		"\u0002\u0000\u0000\u00d4\u00d5\u0005\u0016\u0000\u0000\u00d5\u00d6\u0003"+
		"8\u001c\u0000\u00d6\u00d7\u0005\u0003\u0000\u0000\u00d7\u00d8\u0005\u000f"+
		"\u0000\u0000\u00d8\u00da\u0001\u0000\u0000\u0000\u00d9\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d9\u00d3\u0001\u0000\u0000\u0000\u00da\u000f\u0001\u0000"+
		"\u0000\u0000\u00db\u00e0\u0003\u0016\u000b\u0000\u00dc\u00e0\u0003\u0018"+
		"\f\u0000\u00dd\u00e0\u0003\u001e\u000f\u0000\u00de\u00e0\u0003\"\u0011"+
		"\u0000\u00df\u00db\u0001\u0000\u0000\u0000\u00df\u00dc\u0001\u0000\u0000"+
		"\u0000\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00de\u0001\u0000\u0000"+
		"\u0000\u00e0\u0011\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\r\u0000\u0000"+
		"\u00e2\u00e3\u0005\u000e\u0000\u0000\u00e3\u00e4\u0005\u000f\u0000\u0000"+
		"\u00e4\u00e5\u0005\u0011\u0000\u0000\u00e5\u00e6\u00038\u001c\u0000\u00e6"+
		"\u00e7\u0005\u0012\u0000\u0000\u00e7\u00e8\u00038\u001c\u0000\u00e8\u00e9"+
		"\u0005\u0013\u0000\u0000\u00e9\u00ea\u00030\u0018\u0000\u00ea\u0013\u0001"+
		"\u0000\u0000\u0000\u00eb\u00ec\u0005\r\u0000\u0000\u00ec\u00ed\u0005\u000e"+
		"\u0000\u0000\u00ed\u00ee\u0005\u0010\u0000\u0000\u00ee\u00fd\u0005X\u0000"+
		"\u0000\u00ef\u00f0\u0005\r\u0000\u0000\u00f0\u00f1\u0005\u000e\u0000\u0000"+
		"\u00f1\u00f2\u0005\u0010\u0000\u0000\u00f2\u00fd\u0005Y\u0000\u0000\u00f3"+
		"\u00f4\u0005\r\u0000\u0000\u00f4\u00f5\u0005\u000e\u0000\u0000\u00f5\u00f6"+
		"\u0005\u0010\u0000\u0000\u00f6\u00fa\u0005Z\u0000\u0000\u00f7\u00f8\u0005"+
		"\u0004\u0000\u0000\u00f8\u00f9\u0005\f\u0000\u0000\u00f9\u00fb\u0005["+
		"\u0000\u0000\u00fa\u00f7\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000"+
		"\u0000\u0000\u00fb\u00fd\u0001\u0000\u0000\u0000\u00fc\u00eb\u0001\u0000"+
		"\u0000\u0000\u00fc\u00ef\u0001\u0000\u0000\u0000\u00fc\u00f3\u0001\u0000"+
		"\u0000\u0000\u00fd\u0015\u0001\u0000\u0000\u0000\u00fe\u00ff\u0005\u0005"+
		"\u0000\u0000\u00ff\u0100\u0003$\u0012\u0000\u0100\u0017\u0001\u0000\u0000"+
		"\u0000\u0101\u0102\u0005\u0006\u0000\u0000\u0102\u0107\u0003\u001a\r\u0000"+
		"\u0103\u0104\u0005U\u0000\u0000\u0104\u0106\u0003\u001a\r\u0000\u0105"+
		"\u0103\u0001\u0000\u0000\u0000\u0106\u0109\u0001\u0000\u0000\u0000\u0107"+
		"\u0105\u0001\u0000\u0000\u0000\u0107\u0108\u0001\u0000\u0000\u0000\u0108"+
		"\u0019\u0001\u0000\u0000\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a"+
		"\u010b\u0003$\u0012\u0000\u010b\u010c\u0005\u0003\u0000\u0000\u010c\u010d"+
		"\u00038\u001c\u0000\u010d\u0111\u0001\u0000\u0000\u0000\u010e\u0111\u0003"+
		"8\u001c\u0000\u010f\u0111\u0005S\u0000\u0000\u0110\u010a\u0001\u0000\u0000"+
		"\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0110\u010f\u0001\u0000\u0000"+
		"\u0000\u0111\u001b\u0001\u0000\u0000\u0000\u0112\u0113\u0005\u000b\u0000"+
		"\u0000\u0113\u0114\u0005V\u0000\u0000\u0114\u0115\u0005[\u0000\u0000\u0115"+
		"\u0116\u0005U\u0000\u0000\u0116\u0119\u00038\u001c\u0000\u0117\u0118\u0005"+
		"U\u0000\u0000\u0118\u011a\u0005[\u0000\u0000\u0119\u0117\u0001\u0000\u0000"+
		"\u0000\u0119\u011a\u0001\u0000\u0000\u0000\u011a\u011b\u0001\u0000\u0000"+
		"\u0000\u011b\u011c\u0005W\u0000\u0000\u011c\u001d\u0001\u0000\u0000\u0000"+
		"\u011d\u011e\u0005\u0007\u0000\u0000\u011e\u011f\u0005\b\u0000\u0000\u011f"+
		"\u0124\u00038\u001c\u0000\u0120\u0121\u0005U\u0000\u0000\u0121\u0123\u0003"+
		"8\u001c\u0000\u0122\u0120\u0001\u0000\u0000\u0000\u0123\u0126\u0001\u0000"+
		"\u0000\u0000\u0124\u0122\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000"+
		"\u0000\u0000\u0125\u0127\u0001\u0000\u0000\u0000\u0126\u0124\u0001\u0000"+
		"\u0000\u0000\u0127\u012c\u0003 \u0010\u0000\u0128\u0129\u0005U\u0000\u0000"+
		"\u0129\u012b\u0003 \u0010\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012b"+
		"\u012e\u0001\u0000\u0000\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c"+
		"\u012d\u0001\u0000\u0000\u0000\u012d\u001f\u0001\u0000\u0000\u0000\u012e"+
		"\u012c\u0001\u0000\u0000\u0000\u012f\u0130\u0003(\u0014\u0000\u0130\u0133"+
		"\u0005V\u0000\u0000\u0131\u0134\u0003$\u0012\u0000\u0132\u0134\u0005S"+
		"\u0000\u0000\u0133\u0131\u0001\u0000\u0000\u0000\u0133\u0132\u0001\u0000"+
		"\u0000\u0000\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0136\u0005W\u0000"+
		"\u0000\u0136\u0137\u0005\u0003\u0000\u0000\u0137\u0138\u00038\u001c\u0000"+
		"\u0138!\u0001\u0000\u0000\u0000\u0139\u013a\u0003.\u0017\u0000\u013a\u013b"+
		"\u0005\t\u0000\u0000\u013b\u013c\u00038\u001c\u0000\u013c\u013d\u0005"+
		"\u0004\u0000\u0000\u013d\u013e\u0003$\u0012\u0000\u013e\u0145\u0001\u0000"+
		"\u0000\u0000\u013f\u0140\u0005\n\u0000\u0000\u0140\u0141\u00038\u001c"+
		"\u0000\u0141\u0142\u0005\u0004\u0000\u0000\u0142\u0143\u0003$\u0012\u0000"+
		"\u0143\u0145\u0001\u0000\u0000\u0000\u0144\u0139\u0001\u0000\u0000\u0000"+
		"\u0144\u013f\u0001\u0000\u0000\u0000\u0145#\u0001\u0000\u0000\u0000\u0146"+
		"\u0147\u0006\u0012\uffff\uffff\u0000\u0147\u0148\u0005\u0019\u0000\u0000"+
		"\u0148\u0151\u0003$\u0012\n\u0149\u0151\u0003&\u0013\u0000\u014a\u0151"+
		"\u00038\u001c\u0000\u014b\u0151\u00036\u001b\u0000\u014c\u014d\u0005V"+
		"\u0000\u0000\u014d\u014e\u0003$\u0012\u0000\u014e\u014f\u0005W\u0000\u0000"+
		"\u014f\u0151\u0001\u0000\u0000\u0000\u0150\u0146\u0001\u0000\u0000\u0000"+
		"\u0150\u0149\u0001\u0000\u0000\u0000\u0150\u014a\u0001\u0000\u0000\u0000"+
		"\u0150\u014b\u0001\u0000\u0000\u0000\u0150\u014c\u0001\u0000\u0000\u0000"+
		"\u0151\u016f\u0001\u0000\u0000\u0000\u0152\u0153\n\f\u0000\u0000\u0153"+
		"\u0154\u0005\u0017\u0000\u0000\u0154\u016e\u0003$\u0012\r\u0155\u0156"+
		"\n\u000b\u0000\u0000\u0156\u0157\u0005\u0018\u0000\u0000\u0157\u016e\u0003"+
		"$\u0012\f\u0158\u0159\n\t\u0000\u0000\u0159\u015a\u0003*\u0015\u0000\u015a"+
		"\u015b\u0003$\u0012\n\u015b\u016e\u0001\u0000\u0000\u0000\u015c\u015d"+
		"\n\b\u0000\u0000\u015d\u015e\u0003,\u0016\u0000\u015e\u015f\u0003$\u0012"+
		"\t\u015f\u016e\u0001\u0000\u0000\u0000\u0160\u0161\n\u0007\u0000\u0000"+
		"\u0161\u0162\u0005\u001c\u0000\u0000\u0162\u0163\u0003$\u0012\u0000\u0163"+
		"\u0164\u0005\u0017\u0000\u0000\u0164\u0165\u0003$\u0012\b\u0165\u016e"+
		"\u0001\u0000\u0000\u0000\u0166\u0167\n\u0006\u0000\u0000\u0167\u0168\u0005"+
		"\u001a\u0000\u0000\u0168\u016e\u0005\u001b\u0000\u0000\u0169\u016a\n\u0005"+
		"\u0000\u0000\u016a\u016b\u0005\u001a\u0000\u0000\u016b\u016c\u0005\u0019"+
		"\u0000\u0000\u016c\u016e\u0005\u001b\u0000\u0000\u016d\u0152\u0001\u0000"+
		"\u0000\u0000\u016d\u0155\u0001\u0000\u0000\u0000\u016d\u0158\u0001\u0000"+
		"\u0000\u0000\u016d\u015c\u0001\u0000\u0000\u0000\u016d\u0160\u0001\u0000"+
		"\u0000\u0000\u016d\u0166\u0001\u0000\u0000\u0000\u016d\u0169\u0001\u0000"+
		"\u0000\u0000\u016e\u0171\u0001\u0000\u0000\u0000\u016f\u016d\u0001\u0000"+
		"\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000\u0170%\u0001\u0000\u0000"+
		"\u0000\u0171\u016f\u0001\u0000\u0000\u0000\u0172\u0173\u00055\u0000\u0000"+
		"\u0173\u0174\u0005V\u0000\u0000\u0174\u0175\u0003$\u0012\u0000\u0175\u0176"+
		"\u0005W\u0000\u0000\u0176\u01d8\u0001\u0000\u0000\u0000\u0177\u0178\u0005"+
		"6\u0000\u0000\u0178\u0179\u0005V\u0000\u0000\u0179\u017a\u0003$\u0012"+
		"\u0000\u017a\u017b\u0005W\u0000\u0000\u017b\u01d8\u0001\u0000\u0000\u0000"+
		"\u017c\u017d\u00057\u0000\u0000\u017d\u017e\u0005V\u0000\u0000\u017e\u017f"+
		"\u0003$\u0012\u0000\u017f\u0180\u0005U\u0000\u0000\u0180\u0181\u0005\\"+
		"\u0000\u0000\u0181\u0182\u0005W\u0000\u0000\u0182\u01d8\u0001\u0000\u0000"+
		"\u0000\u0183\u0184\u00058\u0000\u0000\u0184\u0185\u0005V\u0000\u0000\u0185"+
		"\u0186\u0003$\u0012\u0000\u0186\u0187\u0005W\u0000\u0000\u0187\u01d8\u0001"+
		"\u0000\u0000\u0000\u0188\u0189\u00059\u0000\u0000\u0189\u018a\u0005V\u0000"+
		"\u0000\u018a\u018b\u0003$\u0012\u0000\u018b\u018c\u0005W\u0000\u0000\u018c"+
		"\u01d8\u0001\u0000\u0000\u0000\u018d\u018e\u0005:\u0000\u0000\u018e\u018f"+
		"\u0005V\u0000\u0000\u018f\u0190\u0003$\u0012\u0000\u0190\u0191\u0005W"+
		"\u0000\u0000\u0191\u01d8\u0001\u0000\u0000\u0000\u0192\u0193\u0005;\u0000"+
		"\u0000\u0193\u0194\u0005V\u0000\u0000\u0194\u0195\u0003$\u0012\u0000\u0195"+
		"\u0196\u0005U\u0000\u0000\u0196\u0197\u00034\u001a\u0000\u0197\u0198\u0005"+
		"W\u0000\u0000\u0198\u01d8\u0001\u0000\u0000\u0000\u0199\u019a\u0005<\u0000"+
		"\u0000\u019a\u019b\u0005V\u0000\u0000\u019b\u019c\u0003$\u0012\u0000\u019c"+
		"\u019d\u0005U\u0000\u0000\u019d\u019e\u0003$\u0012\u0000\u019e\u019f\u0005"+
		"W\u0000\u0000\u019f\u01d8\u0001\u0000\u0000\u0000\u01a0\u01a1\u0005=\u0000"+
		"\u0000\u01a1\u01a2\u0005V\u0000\u0000\u01a2\u01a3\u0003$\u0012\u0000\u01a3"+
		"\u01a4\u0005W\u0000\u0000\u01a4\u01d8\u0001\u0000\u0000\u0000\u01a5\u01a6"+
		"\u0005>\u0000\u0000\u01a6\u01a7\u0005V\u0000\u0000\u01a7\u01a8\u0003$"+
		"\u0012\u0000\u01a8\u01a9\u0005U\u0000\u0000\u01a9\u01aa\u0005\\\u0000"+
		"\u0000\u01aa\u01ab\u0005U\u0000\u0000\u01ab\u01ac\u0005\\\u0000\u0000"+
		"\u01ac\u01ad\u0005W\u0000\u0000\u01ad\u01d8\u0001\u0000\u0000\u0000\u01ae"+
		"\u01af\u0005?\u0000\u0000\u01af\u01b0\u0005V\u0000\u0000\u01b0\u01b1\u0003"+
		"$\u0012\u0000\u01b1\u01b2\u0005U\u0000\u0000\u01b2\u01b3\u0003$\u0012"+
		"\u0000\u01b3\u01b4\u0005W\u0000\u0000\u01b4\u01d8\u0001\u0000\u0000\u0000"+
		"\u01b5\u01b6\u0005@\u0000\u0000\u01b6\u01b7\u0005V\u0000\u0000\u01b7\u01b8"+
		"\u0003$\u0012\u0000\u01b8\u01b9\u0005U\u0000\u0000\u01b9\u01ba\u0005["+
		"\u0000\u0000\u01ba\u01bb\u0005W\u0000\u0000\u01bb\u01d8\u0001\u0000\u0000"+
		"\u0000\u01bc\u01bd\u0005A\u0000\u0000\u01bd\u01be\u0005V\u0000\u0000\u01be"+
		"\u01bf\u0003$\u0012\u0000\u01bf\u01c0\u0005U\u0000\u0000\u01c0\u01c1\u0005"+
		"[\u0000\u0000\u01c1\u01c2\u0005W\u0000\u0000\u01c2\u01d8\u0001\u0000\u0000"+
		"\u0000\u01c3\u01c4\u0005B\u0000\u0000\u01c4\u01c5\u0005V\u0000\u0000\u01c5"+
		"\u01c6\u0003$\u0012\u0000\u01c6\u01c7\u0005W\u0000\u0000\u01c7\u01d8\u0001"+
		"\u0000\u0000\u0000\u01c8\u01c9\u0005C\u0000\u0000\u01c9\u01ca\u0005V\u0000"+
		"\u0000\u01ca\u01cb\u0003$\u0012\u0000\u01cb\u01cc\u0005W\u0000\u0000\u01cc"+
		"\u01d8\u0001\u0000\u0000\u0000\u01cd\u01ce\u0005D\u0000\u0000\u01ce\u01cf"+
		"\u0005V\u0000\u0000\u01cf\u01d0\u0003$\u0012\u0000\u01d0\u01d1\u0005W"+
		"\u0000\u0000\u01d1\u01d8\u0001\u0000\u0000\u0000\u01d2\u01d3\u0005E\u0000"+
		"\u0000\u01d3\u01d4\u0005V\u0000\u0000\u01d4\u01d5\u0003$\u0012\u0000\u01d5"+
		"\u01d6\u0005W\u0000\u0000\u01d6\u01d8\u0001\u0000\u0000\u0000\u01d7\u0172"+
		"\u0001\u0000\u0000\u0000\u01d7\u0177\u0001\u0000\u0000\u0000\u01d7\u017c"+
		"\u0001\u0000\u0000\u0000\u01d7\u0183\u0001\u0000\u0000\u0000\u01d7\u0188"+
		"\u0001\u0000\u0000\u0000\u01d7\u018d\u0001\u0000\u0000\u0000\u01d7\u0192"+
		"\u0001\u0000\u0000\u0000\u01d7\u0199\u0001\u0000\u0000\u0000\u01d7\u01a0"+
		"\u0001\u0000\u0000\u0000\u01d7\u01a5\u0001\u0000\u0000\u0000\u01d7\u01ae"+
		"\u0001\u0000\u0000\u0000\u01d7\u01b5\u0001\u0000\u0000\u0000\u01d7\u01bc"+
		"\u0001\u0000\u0000\u0000\u01d7\u01c3\u0001\u0000\u0000\u0000\u01d7\u01c8"+
		"\u0001\u0000\u0000\u0000\u01d7\u01cd\u0001\u0000\u0000\u0000\u01d7\u01d2"+
		"\u0001\u0000\u0000\u0000\u01d8\'\u0001\u0000\u0000\u0000\u01d9\u01da\u0007"+
		"\u0000\u0000\u0000\u01da)\u0001\u0000\u0000\u0000\u01db\u01dc\u0007\u0001"+
		"\u0000\u0000\u01dc+\u0001\u0000\u0000\u0000\u01dd\u01de\u0007\u0002\u0000"+
		"\u0000\u01de-\u0001\u0000\u0000\u0000\u01df\u01e0\u0007\u0003\u0000\u0000"+
		"\u01e0/\u0001\u0000\u0000\u0000\u01e1\u01e2\u0007\u0004\u0000\u0000\u01e2"+
		"1\u0001\u0000\u0000\u0000\u01e3\u01e4\u0007\u0005\u0000\u0000\u01e43\u0001"+
		"\u0000\u0000\u0000\u01e5\u01e6\u0007\u0006\u0000\u0000\u01e65\u0001\u0000"+
		"\u0000\u0000\u01e7\u01ec\u0005\\\u0000\u0000\u01e8\u01ec\u0005[\u0000"+
		"\u0000\u01e9\u01ec\u0005\u001d\u0000\u0000\u01ea\u01ec\u0005\u001e\u0000"+
		"\u0000\u01eb\u01e7\u0001\u0000\u0000\u0000\u01eb\u01e8\u0001\u0000\u0000"+
		"\u0000\u01eb\u01e9\u0001\u0000\u0000\u0000\u01eb\u01ea\u0001\u0000\u0000"+
		"\u0000\u01ec7\u0001\u0000\u0000\u0000\u01ed\u01ee\u0007\u0007\u0000\u0000"+
		"\u01ee9\u0001\u0000\u0000\u0000#@FOV_fmr}\u0088\u0091\u0097\u00a0\u00a7"+
		"\u00b0\u00b7\u00be\u00c3\u00ce\u00d9\u00df\u00fa\u00fc\u0107\u0110\u0119"+
		"\u0124\u012c\u0133\u0144\u0150\u016d\u016f\u01d7\u01eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}