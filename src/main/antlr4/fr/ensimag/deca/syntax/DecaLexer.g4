lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}
<<<<<<< HEAD
// Deca lexer rules.

// Mots réservés
ASM : 'asm' { skip() ; } ;
CLASS : 'class' { skip() ; } ;
EXTENDS : 'extends' { skip() ; } ;
ELSE : 'else' { skip() ; } ;
FALSE : 'false' { skip() ; } ;
IF : 'if' { skip() ; } ;
INSTANCEOF : 'instanceof' { skip() ; } ;
NEW  : 'new' { skip() ; } ;
NULL : 'null' { skip() ; } ;
READINT : 'readInt' { skip() ; } ;
READFLOAT : 'readFloat' { skip() ; } ;
PRINT : 'print' { skip() ; } ;
PRINTLN : 'println' { skip() ; } ;
PRINTLNX : 'printlnx' { skip() ; } ;
PRINTX : 'printx' { skip() ; } ;
PROTECTED : 'protected' { skip() ; } ;
RETURN : 'return' { skip() ; } ;
THIS : 'this' { skip() ; } ;
TRUE : 'true' { skip() ; } ;
WHILE : 'while' { skip() ; } ;

// Identificateurs
fragment LETTER : 'a' .. 'z' 'A' .. 'Z';
fragment DIGIT : '0' .. '9' ;
IDENT : (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')* ;

// Symboles spéciaux
LT : '<' { skip() ; } ;
GT : '>' { skip() ; } ;
EQUALS : '=' { skip() ; } ;
PLUS : '+' { skip() ; } ;
MINUS : '-' { skip() ; } ;
TIMES : '*' { skip() ; } ;
SLASH : '/' { skip() ; } ;
PERCENT : '%' { skip() ; } ;
DOT : '.' { skip() ; } ;
COMMA : ',' { skip() ; } ;
OPARENT : '(' { skip() ; } ;
CPARENT : ')' { skip() ; } ;
OBRACE : '{' { skip() ; } ;
CBRACE : '}' { skip() ; } ;
EXCLAM : '!' { skip() ; } ;
SEMI : ';' { skip() ; } ;
EQEQ : '==' { skip() ; } ;
NEQ : '!=' { skip() ; } ;
GEQ : '>=' { skip() ; } ;
LEQ : '<=' { skip() ; } ;
AND : '&&' { skip() ; } ;
OR : '||' { skip() ; } ;

// Littéraux entiers
fragment POSITIVE_DIGIT : '1' .. '9' ;
INT : '0' | POSITIVE_DIGIT DIGIT* ;

// Littéraux flottants
NUM : DIGIT+ ;
SIGN : '+' | '-' | ;
EXP : ('E' | 'e') SIGN NUM ;
DEC : NUM '.' NUM ;
FLOATDEC : (DEC | DEC EXP) ('F' | 'f' | ) ;
fragment HEX_MAJUSC : 'A' .. 'F' ;
fragment HEX_MINUSC : 'a' .. 'f' ;
DIGITHEX : DIGIT | HEX_MAJUSC | HEX_MINUSC ;
NUMHEX : DIGITHEX+ ;
FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | ) ;
FLOAT : FLOATDEC | FLOATHEX ;

// Chaines de caractères
fragment STRING_CAR : (~('"') ~('\\') ~('\n'))+ ;
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"' ;
MULTI_LINE_STRING : '"' (STRING_CAR | '\n' | '\\"' | '\\\\')* '"' ;

// Commentaires
MULTI_LINE_COMMENT : '/*' MULTI_LINE_STRING '*/' { skip() ; } ;
MONO_LINE_COMMENT : '//' (~('\n'))* ('\n' | EOF) { skip() ; } ;

// Séparateurs
SEPAR : ( ' ' | '\t' | '\n' | '\r' ) { skip(); } ;

// Inclusion de fichier
FILENAME : (LETTER | DIGIT | '.' | '-' | '_')+ ;
INCLUDE : '#include' (' ')* '"' FILENAME '"' ;
