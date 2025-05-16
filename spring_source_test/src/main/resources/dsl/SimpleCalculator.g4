grammar SimpleCalculator;   //名称需要和文件名一致

@parser::members { public static boolean test;}

cal : expr;

expr
    : '(' expr ')'      #first
    | expr '*' expr  {System.out.println("expr: " + $expr.text);}   #mul
    | expr '+' expr     #add   //标签会生成对应访问方法方便我们实现调用逻辑编写
    | expr '-' expr     #sub
    | INT               #int
    | ID                #ID
    ;

INT : [0-9]+                   //定义整数
    ;

ID : [a-zA-Z]+                   //定义变量
    ;

WS  : [ \r\n\t]+ -> skip      //跳过空白类字符
    ;
