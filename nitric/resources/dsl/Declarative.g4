grammar Declarative;

//30 aprile 4.00 PM

//IMPORTANTE -> l'engine (spark, streaming, mat view,...) viene scelto 
//a livello di esecuzione? o a livello di deployment? in un solo "progetto"
//l'engine è solo uno oppure se ci sono più pipeline si possono usare una combinazione
//di più engine?

//IMPORTANTE -> una pipeline con un cross boundary (state -> data o viceversa)
//in realtà sono due pipeline distinte 

//from stream ...
//data clauses/steps
//map to state operator --> here I just created a state sink! this state needs to be maintained and the engine could be different?
//state clauses/steps
//to table --> Here I created another sink with the processed state
//

//if I consider this as a single pipeline, it cannot be executed
//with a single execution engine (ex: spark streaming cannot
// is not the best fit for handling the state processing part)

//            FROM STREAM temperature-readings
//            WHERE building == 21
//            MAP TO STATE
//              KEY room_id
//              VALUE temperature
//              USING REPLACE
//            WHERE temperature > 30.0
//            MAP TO DATA ISTREAM
//            INTO STREAM newly-hot-rooms-21
//with this query, i start from a stream, i generate a state and then i create a stream
// the placement of the WHERE clause determines how the state is maintained
//if i place it before the MAP TO STATE then the state contains only the hot rooms (low temperature data is discarded before arriving to the state update)
//if i place it after the MAP TO STATE then the state contains all rooms with their current temperature, the filter than create a new state containing the hot rooms

// with ISTREAM only new record should trigger a publish on the stream, we need something as "USTREAM"


// ── Entry point ───────────────────────────────────────────────────────────────
// the two pipeline types correspond to the 2 ways a pipeline can start
//from a Data source or from a state source
pipeline
    : dataPipeline EOF
    | statePipeline EOF
    ;

// ── Data pipeline ─────────────────────────────────────────────────────────────
// Starts from a data source
// is it useful to cross to state and then cross back to data?? 
//1 -> starts from Data, performs 0 or more operations on data and write
//     to a data sink
//2 -> starts from a data source, may perform some operations on data, then switch to 
//     state, perform some operations on state and write to state sink
//3 -> switch from data to state and then switch back to data
dataPipeline
    : dataSource dataClause* dataSink
    | dataSource dataClause* crossToState stateClause* stateSink
    | dataSource dataClause* crossToState stateClause* crossToData dataClause* dataSink
    ;

//Stream implicitly means that it holds data, meanwhile timeseries can
//hold both, so it must be explicitly defined as data source/sink
dataSource
    : FROM STREAM name                          # fromStream
    | FROM TIMESERIES name AS DATA              # fromTimeseriesData
    ;

dataSink
    : INTO STREAM name                          # intoStream
    | INTO TIMESERIES name AS DATA              # intoTimeseriesData
    ;

//available operations on data (can be extended with more clauses)
// ?? -> define the entire set of available operations
dataClause
    : whereClause                               # whereData
    | selectClause                              # selectData
    | windowClause groupByClause                # windowGroupBy
    | groupByClause                             # groupByData
    | joinClause                                # joinData
    ;

// ── State pipeline ────────────────────────────────────────────────────────────
// Starts in state mode
// Same question, is it useful to cross to data and then cross back to state??
// I should add the Update strategy to the statesouce so the engine knows how to update the state (default = replace)
statePipeline
    : stateSource stateClause* stateSink         
    | stateSource stateClause* crossToData dataClause* dataSink    
    | stateSource stateClause* crossToData dataClause* crossToState stateClause* stateSink 
    ;

//kv storage is implicitly state, meanwhile ts can be both state or data
//therefore it must be explicitly defined as state source/sink
stateSource
    : FROM KV name                              # fromKV
    | FROM TIMESERIES name AS STATE             # fromTimeseriesState
    ;

stateSink
    : INTO KV name                              # intoKV
    | INTO TIMESERIES name AS STATE             # intoTimeseriesState
    ;

//windowing is not supported in state mode
stateClause
    : whereClause                               # whereState
    | selectClause                              # selectState
    | groupByClause                             # groupByState
    | joinClause                                # joinState
    ;

// ── MODE SWITCH INSTRUCTIONS ────────────────────────────────────────────────────────

//modeled after group by and aggregation.
// used to go from data processing to state processing
//keyCol identify the columns which holds the key of the state
//valuecol identify the column which holds the value of the state
//stateop tells how to update the state based on incoming data
//(replace, increment, ...)
crossToState
    : MAP TO STATE
      KEY keyCol=name
      VALUE valueCol=name
      USING stateOp
    ;

//modeled after CQL paper
//used to go from state processing to data processing
//schedule catches the optional scheduling parameter for periodic pipelines
//maybe the schedule should be used only in RSTREAM, since istream and dstream
//are more for capturing data changes while Rsteram is more like a snapshot
crossToData
    : MAP TO DATA streamOperator (ON SCHEDULE schedule=STRING)?
    ;

//in CQL paper, ISTREAM captures insertions, DSTREAM capture deletions
//and RSTREAM captures the entire relation catch
//the paper use this to create a stream starting from a relation
//since we operate on state, should we add another operator to capture
//changes in the state or include it in ISTREAM?
//maybe USTREAM for Upserts?
streamOperator
    : ISTREAM
    | DSTREAM
    | RSTREAM
    ;

// ── Clauses ───────────────────────────────────────────────────────────────────

//where clause that filters the data/state based on the evaluation of the expression.
// It can be used in both data and state processing
whereClause
    : WHERE expression
    ;

//select clause that projects the data/state based on the evaluation of the expression.
selectClause
    : SELECT selectExpr (COMMA selectExpr)*
    ;

selectExpr
    : expression AS name                        # aliasedSelect
    | name                                      # columnSelect
    | STAR                                      # starSelect
    ;

// windowing for streams
// it groups event arrived in a certain time interval, defined by the duration
windowClause
    : WINDOW LPAREN
        duration=STRING COMMA
        timeCol=name
        (COMMA slide=STRING)?
      RPAREN
    ;

//group by and aggregate
groupByClause
    : GROUP BY (name (COMMA name)*)?
      aggExpr (COMMA aggExpr)*
    ;

aggExpr
    : aggFunc LPAREN (expression | STAR) RPAREN AS name
    ;

//join operation between two data sources
// stream join stream -> keep data mode, do we support this? if yes we need to introduce watermarking
// stream join table -> data from stream enriched with state information, data mode prevails
// table join stream -> it's always data driven, so do we need to handle this case?
// table table join -> remains in state mode

joinClause
    : joinType JOIN sourceRef ON expression     # explicitJoin
    | ENRICH sourceRef ON expression            # enrichJoin
    ;

sourceRef
    : STREAM name                               # joinStream
    | KV name                                   # joinKV
    | TIMESERIES name AS DATA                   # joinTimeseriesData
    | TIMESERIES name AS STATE                  # joinTimeseriesState
    ;

// ── Expression language ───────────────────────────────────────────────────────
// Shared with the functional grammar — both compile to the same expression proto --> if a grammar is needed for the functional language.

expression
    : expression AND expression                 # andExpr
    | expression OR expression                  # orExpr
    | NOT expression                            # notExpr
    | expression compOp expression              # compExpr
    | expression arithOp expression             # arithExpr
    | expression BETWEEN expression AND expression  # betweenExpr
    | expression IS NULL                        # isNullExpr
    | expression IS NOT NULL                    # isNotNullExpr
    | builtinFunc                               # funcExpr
    | name                                      # colRef
    | literal                                   # litExpr
    | LPAREN expression RPAREN                  # parenExpr
    ;

// ── built-in functions ─────────────────────────────────────────────────

builtinFunc
    : UPPER LPAREN expression RPAREN
    | LOWER LPAREN expression RPAREN
    | ROUND LPAREN expression COMMA NUMBER RPAREN
    | ABS LPAREN expression RPAREN
    | FLOOR LPAREN expression RPAREN
    | CEIL LPAREN expression RPAREN
    | CAST LPAREN expression COMMA typeName RPAREN
    | LENGTH LPAREN expression RPAREN
    | SUBSTRING LPAREN expression COMMA NUMBER COMMA NUMBER RPAREN
    | TO_TIMESTAMP LPAREN expression COMMA STRING RPAREN
    | DATE_FORMAT LPAREN expression COMMA STRING RPAREN
    | YEAR LPAREN expression RPAREN
    | MONTH LPAREN expression RPAREN
    | DAY LPAREN expression RPAREN
    | HOUR LPAREN expression RPAREN
    ;

// ── Aggregation functions ─────────────────────────────────────────────────────

aggFunc
    : SUM | COUNT | AVG | MAX | MIN | LAST | FIRST
    | STDDEV | VARIANCE | COLLECT_LIST
    ;

// ── Terminals ─────────────────────────────────────────────────────────────────

compOp   : EQ | NEQ | GT | LT | GTE | LTE ;
arithOp  : PLUS | MINUS | STAR | SLASH ;
joinType : INNER | LEFT | LEFT_SEMI | FULL | FULL_OUTER;
stateOp  : REPLACE | INCREMENT | DECREMENT | MAXIMUM | MINIMUM | COLLECT ;
strategy : SNAPSHOT | CDC | PERIODIC ;
typeName : T_DOUBLE | T_LONG | T_STRING | T_BOOLEAN | T_TIMESTAMP ;
literal  : NUMBER # numLit | STRING # strLit | TRUE # trueLit | FALSE # falseLit ;
name     : ID | STRING ;

// ── Keywords (uppercase — SQL convention) ─────────────────────────────────────

FROM         : 'FROM' ;
INTO         : 'INTO' ;
AS           : 'AS' ;
ON           : 'ON' ;
WHERE        : 'WHERE' ;
SELECT       : 'SELECT' ;
GROUP        : 'GROUP' ;
BY           : 'BY' ;
JOIN         : 'JOIN' ;
ENRICH       : 'ENRICH' ;
WINDOW       : 'WINDOW' ;
SCHEDULE     : 'SCHEDULE' ;
MAP          : 'MAP' ;
TO           : 'TO' ;
STATE        : 'STATE' ;
DATA         : 'DATA' ;
KEY          : 'KEY' ;
VALUE        : 'VALUE' ;
USING        : 'USING' ;
STREAM       : 'STREAM' ;
KV           : 'KV' ;
TIMESERIES   : 'TIMESERIES' ;
AND          : 'AND' ;
OR           : 'OR' ;
NOT          : 'NOT' ;
IS           : 'IS' ;
NULL         : 'NULL' ;
BETWEEN      : 'BETWEEN' ;
TRUE         : 'TRUE' ;
FALSE        : 'FALSE' ;
INNER        : 'INNER' ;
LEFT         : 'LEFT' ;
LEFT_SEMI    : 'LEFT_SEMI' ;
FULL         : 'FULL' ;
FULL_OUTER   : 'FULL_OUTER' ;
REPLACE      : 'REPLACE' ;
INCREMENT    : 'INCREMENT' ;
DECREMENT    : 'DECREMENT' ;
MAXIMUM      : 'MAXIMUM' ;
MINIMUM      : 'MINIMUM' ;
COLLECT      : 'COLLECT' ;
SNAPSHOT     : 'SNAPSHOT' ;
CDC          : 'CDC' ;
PERIODIC     : 'PERIODIC' ;
SUM          : 'SUM' ;
COUNT        : 'COUNT' ;
AVG          : 'AVG' ;
MAX          : 'MAX' ;
MIN          : 'MIN' ;
LAST         : 'LAST' ;
FIRST        : 'FIRST' ;
STDDEV       : 'STDDEV' ;
VARIANCE     : 'VARIANCE' ;
COLLECT_LIST : 'COLLECT_LIST' ;
UPPER        : 'UPPER' ;
LOWER        : 'LOWER' ;
ROUND        : 'ROUND' ;
ABS          : 'ABS' ;
FLOOR        : 'FLOOR' ;
CEIL         : 'CEIL' ;
CAST         : 'CAST' ;
COALESCE     : 'COALESCE' ;
LENGTH       : 'LENGTH' ;
SUBSTRING    : 'SUBSTRING' ;
CONCAT       : 'CONCAT' ;
TO_TIMESTAMP : 'TO_TIMESTAMP' ;
DATE_FORMAT  : 'DATE_FORMAT' ;
YEAR         : 'YEAR' ;
MONTH        : 'MONTH' ;
DAY          : 'DAY' ;
HOUR         : 'HOUR' ;
T_DOUBLE     : 'DOUBLE' ;
T_LONG       : 'LONG' ;
T_STRING     : 'STRING_TYPE' ;
T_BOOLEAN    : 'BOOLEAN' ;
T_TIMESTAMP  : 'TIMESTAMP' ;

// ── Operators and symbols ──────────────────────────────────────────────────────

EQ    : '=' ;
NEQ   : '!=' ;
GT    : '>' ;
LT    : '<' ;
GTE   : '>=' ;
LTE   : '<=' ;
PLUS  : '+' ;
MINUS : '-' ;
STAR  : '*' ;
SLASH : '/' ;
COMMA : ',' ;
LPAREN : '(' ;
RPAREN : ')' ;
ISTREAM : 'ISTREAM' ;
DSTREAM : 'DSTREAM' ;
RSTREAM : 'RSTREAM' ;

// ── Literals ──────────────────────────────────────────────────────────────────

STRING  : '\'' (~['\r\n])* '\'' | '"' (~["\r\n])* '"' ;
NUMBER  : '-'? [0-9]+ ('.' [0-9]+)? ;
ID      : [a-zA-Z_] [a-zA-Z0-9_\-]* ;

// ── Ignored ───────────────────────────────────────────────────────────────────

WS           : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '--' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;
