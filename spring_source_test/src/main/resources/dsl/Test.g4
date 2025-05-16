grammar Test;   //名称需要和文件名一致

s : expr EOF;   //解决问题: no viable alternative at input '<EOF>'

expr
    : decl ';'
    | stat ';'
    ;

decl
  : ID ID
  | ID '(' ID ')'
  ;

stat
  : ID
  | INT
  | ID '(' stat ')'
  ;

BIGIN: 'begin';
ID: [a-z]+;

INT : [0-9]+                   //定义整数
    ;

WS  : [ \r\n\t]+ -> skip      //跳过空白类字符
    ;
