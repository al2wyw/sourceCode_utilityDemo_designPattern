grammar Cal;

prog : stat+;

stat: expr           # calExpression
    | ID '=' expr    # assign
    ;

expr: expr op=('*'|'/') expr    # MulDiv
| expr op=('+'|'-') expr        # AddSub
| expr op=('>'|'<') expr        # GreaterLess
| INT                           # int
| ID                            # id
| '(' expr ')'                  # parens
;

MUL : '*' ; // assigns token name to '*' used above in grammar
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
GREATER : '>' ;
LESS : '<' ;
ID : [a-zA-Z]+ ;
INT : [0-9]+ ;
fragment NEWLINE:'\r'? '\n';
WS : NEWLINE+ -> skip;