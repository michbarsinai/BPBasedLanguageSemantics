grammar Calc;

expr	:	subEx ( (PLS|MIN) subEx )*;
subEx	:	atom ((DIV|MUL) atom )*;
atom	:	NUMBER;
PLS	:	'+';
MIN	:	'-';
MUL	:	'*';
DIV	:	'/';
NUMBER : ('0'..'9')+  ;

WHITESPACE  :   (' ' | '\t' | '\r'| '\n') {$channel=HIDDEN;} ;