grammar Declarative;

// =============================================================================
// DECLARATIVE PIPELINE GRAMMAR
//
// SQL-like syntax. Compiles to the same Pipeline proto as the functional SDK.
// Both grammars have identical expressive power.
//
// Example:
//   FROM STREAM temperature_readings
//   WHERE ROUND(temperature, 1) > 25.0
//     AND UPPER(unit) = 'C'
//   SELECT room_id, temperature * 1.8 + 32 AS temp_f
//   MAP TO STATE KEY room_id VALUE temperature USING REPLACE
//   WHERE temperature > 25.0
//   INTO KV hot_room_state
// =============================================================================

// ── Entry point ───────────────────────────────────────────────────────────────

pipeline
    : dataPipeline EOF
    | statePipeline EOF
    ;

// ── Data pipeline ─────────────────────────────────────────────────────────────
// Starts and ends in DATA mode.
// Optionally crosses to STATE and back.
// is it useful to cross to state and then cross back to data?? 

dataPipeline
    : dataSource dataClause* dataSink
    | dataSource dataClause* crossToState stateClause* stateSink
    | dataSource dataClause* crossToState stateClause* crossToData dataClause* dataSink
    ;

dataSource
    : FROM STREAM name                          # fromStream
    | FROM TIMESERIES name AS DATA              # fromTimeseriesData
    ;

dataSink
    : INTO STREAM name                          # intoStream
    | INTO TIMESERIES name AS DATA              # intoTimeseriesData
    ;

dataClause
    : whereClause                               # whereData
    | selectClause                              # selectData
    | windowClause groupByClause                # windowGroupBy
    | groupByClause                             # groupByData
    | joinClause                                # joinData
    ;

// ── State pipeline ────────────────────────────────────────────────────────────
// Starts and ends in STATE mode.
// Source must be KV or TIMESERIES AS STATE.
// Same question, is it useful to cross to data and then cross back to state??

statePipeline
    : stateSource stateClause* stateSink         // ends STATE → STATE sink
    | stateSource stateClause* crossToData dataClause* dataSink    // crosses to DATA
    | stateSource stateClause* crossToData dataClause* crossToState stateClause* stateSink  // round trip
    ;

stateSource
    : FROM KV name                              # fromKV
    | FROM TIMESERIES name AS STATE             # fromTimeseriesState
    ;

stateSink
    : INTO KV name                              # intoKV
    | INTO TIMESERIES name AS STATE             # intoTimeseriesState
    ;

stateClause
    : whereClause                               # whereState
    | selectClause                              # selectState
    | groupByClause                             # groupByState
    | joinClause                                # joinState
    ;

// ── MODE SWITCH ────────────────────────────────────────────────────────

//modeled after group by and aggregation.
crossToState
    : MAP TO STATE
      KEY keyCol=name
      VALUE valueCol=name
      USING stateOp
    ;

//modeled after CQL paper
crossToData
    : MAP TO DATA ISTREAM                              
    | MAP TO DATA DSTREAM                              
    | MAP TO DATA RSTREAM (ON SCHEDULE schedule=STRING)? 
    ;

// ── Clauses ───────────────────────────────────────────────────────────────────

whereClause
    : WHERE expression
    ;

selectClause
    : SELECT selectExpr (COMMA selectExpr)*
    ;

selectExpr
    : expression AS name                        # aliasedSelect
    | name                                      # columnSelect
    | STAR                                      # starSelect
    ;

windowClause
    : WINDOW LPAREN
        duration=STRING COMMA
        timeCol=name
        (COMMA slide=STRING)?
      RPAREN
    ;

groupByClause
    : GROUP BY name (COMMA name)*
      aggExpr (COMMA aggExpr)*
    ;

aggExpr
    : aggFunc LPAREN (expression | STAR) RPAREN AS name
    ;

joinClause
    : joinType JOIN name ON expression          # explicitJoin
    | ENRICH name ON expression                 # enrichJoin
    ;

// ── Expression language ───────────────────────────────────────────────────────
// Shared with the functional grammar — both compile to the same Expression proto.

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

// ── Built-in scalar functions ─────────────────────────────────────────────────

builtinFunc
    : UPPER LPAREN expression RPAREN
    | LOWER LPAREN expression RPAREN
    | ROUND LPAREN expression COMMA NUMBER RPAREN
    | ABS LPAREN expression RPAREN
    | FLOOR LPAREN expression RPAREN
    | CEIL LPAREN expression RPAREN
    | CAST LPAREN expression COMMA typeName RPAREN
    | COALESCE LPAREN expression COMMA expression RPAREN
    | LENGTH LPAREN expression RPAREN
    | SUBSTRING LPAREN expression COMMA NUMBER COMMA NUMBER RPAREN
    | CONCAT LPAREN expression COMMA expression RPAREN
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
joinType : INNER | LEFT | LEFT_SEMI ;
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
