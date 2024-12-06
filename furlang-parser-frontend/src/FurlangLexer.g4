/*
 * Copyright 2024 Cach30verfl0w
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2024 Cach30verfl0w & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

lexer grammar FurlangLexer;

/**
 * @author Cedric Hammes
 * @since  01/12/2024
 */

// Whitespace
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN);
BLOCK_COMMENT: '/*' (BLOCK_COMMENT | .)*? '*/' -> channel(HIDDEN);
WS: [\u0020\u0009\u000C] -> channel(HIDDEN);
NL: ('\n' | ('\r' '\n'?));

// Strings
ML_STRING_END: '"#';
EMPTY_STRING: '""';
DOUBLE_QUOTE: '"' -> pushMode(STRING_MODE);
EMPTY_ML_STRING: '#""#';
ML_STRING_BEGIN: '#"' -> pushMode(ML_STRING_MODE);

// Parenthese, brackets, braces & chevrons
L_BRACE: '{';
R_BRACE: '}';
L_BRACKET: '[';
R_BRACKET: ']';
L_PAREN: '(';
R_PAREN: ')';
L_CHEVRON: '<';
R_CHEVRON: '>';

// Keywords
KW_PUBLIC: 'pub';
KW_PROTECTED: 'prot';
KW_PRIVATE: 'priv';
KW_INTERNAL: 'internal';
KW_EXTERN: 'extern';
KW_CONST: 'const';
KW_VARARG: 'vararg';
KW_IMPORT: 'import';
KW_FUNCTION: 'fun';
KW_OPERATOR: 'operator';
KW_ABSTRACT: 'abstract';
KW_OPEN: 'open';

KW_CLASS: 'class';
KW_INTERFACE: 'interface';
KW_ANNOTATION: 'annotation';

KW_IN: 'in';
KW_AS: 'as';
KW_IS: 'is';

KW_LET: 'let';
KW_MUTABLE: 'mut';
KW_WHEN: 'when';
KW_WHILE: 'while';
KW_DO: 'do';
KW_FOR: 'for';
KW_IF: 'if';
KW_ELSE: 'else';
KW_RETURN: 'return';
KW_NULL: 'null';
KW_BREAK: 'break';
KW_CONTINUE: 'continue';

KW_TRUE: 'true';
KW_FALSE: 'false';

// Types
TYPE_I8: 'i8';
TYPE_I16: 'i16';
TYPE_I32: 'i32';
TYPE_I64: 'i64';
TYPE_U8: 'u8';
TYPE_U16: 'u16';
TYPE_U32: 'u32';
TYPE_U64: 'u64';
TYPE_BOOLEAN: 'bool';
TYPE_STRING: 'str';

// Operator
ASSIGN: '=';
EQUALS: '==';
NOT_EQUALS: '!=';

PLUS: '+';
PLUS_ASSIGN: '+=';
MINUS: '-';
MINUS_ASSIGN: '-=';
ASTERISK: '*';
TIMES_ASSIGN: '*=';
DIV: '/';
DIV_ASSIGN: '/=';
MOD: '%';
MOD_ASSIGN: '%=';
INCREMENT: '++';
DECREMENT: '--';
GREATER_EQUALS_THAN: '>=';
LESS_EQUALS_THAN: '<=';

NOT: '~';
BITWISE_OR: '|';
BITWISE_OR_ASSIGN: '|=';
BITWISE_AND: '&';
BITWISE_AND_ASSIGN: '&=';
BITWISE_XOR: '^';
BITWISR_XOR_ASSIGN: '^=';
RIGHT_SHIFT: '>>';
RIGHT_SHIFT_ASSIGN: '>>=';
LEFT_SHIFT: '<<';
LEFT_SHIFT_ASSIGN: '<<=';

LOGICAL_AND: '&&';
LOGICAL_OR: '||';

// Symbols
ARROW: '->';
DOT: '.';
DOTDOT: '..';
COLON: ':';
COLONCOLON: '::';
COMMA: ',';
SEMICOLON: ';';
AT: '@';

// Other
IDENTIFIER: [a-zA-Z_]+[a-zA-Z0-9_]*;
fragment F_ESCAPED_CHAR: '\\' [nrbt0];

// Numbers
INTEGER: '0b' BINARY_DIGIT | '0o' OCTAL_DIGIT | '0x' HEXADECIMAL_DIGIT | DECIMAL_DIGIT;
FLOAT: DECIMAL_DIGIT '.' DECIMAL_DIGIT;
fragment DECIMAL_DIGIT: [0-9]+;
fragment BINARY_DIGIT: [0-1]+;
fragment HEXADECIMAL_DIGIT: [0-9a-fA-F]+;
fragment OCTAL_DIGIT: [0-7]+;

// Strings
mode STRING_MODE;

STRING_MODE_ESCAPED_STRING_END: '\\' DOUBLE_QUOTE;
STRING_MODE_STRING_END: DOUBLE_QUOTE -> popMode, type(DOUBLE_QUOTE);
STRING_MODE_ESCAPED_CHAR: F_ESCAPED_CHAR;
STRING_MODE_LERP_BEGIN: '${' -> pushMode(DEFAULT_MODE);
STRING_MODE_TEXT: ~('\\' | '"' | '$')+ | '$';

mode ML_STRING_MODE;

ML_STRING_MODE_ESCAPED_ML_STRING_END: '\\' ML_STRING_END;
ML_STRING_MODE_ML_STRING_END: ML_STRING_END -> popMode, type(ML_STRING_END);
ML_STRING_MODE_ESCAPED_CHAR: F_ESCAPED_CHAR;
ML_STRING_MODE_LERP_BEGIN: '${' -> pushMode(DEFAULT_MODE);
ML_STRING_MODE_TEXT: ~('\\' | '"' | '$')+ | '$';
