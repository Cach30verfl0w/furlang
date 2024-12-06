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

parser grammar FurlangParser;

/**
 * @author Cedric Hammes
 * @since  01/12/2024
 */

options {
    tokenVocab = FurlangLexer;
}


file: (declaration | NL)+ EOF;
end: SEMICOLON | NL;

// Type parameters
closure_modifier: KW_CONST;

unsigned_int_type:
    TYPE_U8
    | TYPE_U16
    | TYPE_U32
    | TYPE_U64
    ;

signed_int_type:
    TYPE_I8
    | TYPE_I16
    | TYPE_I32
    | TYPE_I64
    ;

int_type:
    signed_int_type
    | unsigned_int_type
    ;

closure_type:
    closure_modifier*?
    L_PAREN
    (type COMMA?)*?
    R_PAREN
    ARROW
    type;

type:
    closure_type
    | type type_parameters_usage
    | L_PAREN (type COMMA?)+ R_PAREN
    | int_type
    | TYPE_BOOLEAN
    | TYPE_STRING
    | IDENTIFIER
    ;

type_parameter_declaration_modifier: KW_VARARG;
type_parameter_declaration: type_parameter_declaration_modifier? IDENTIFIER (COLON expression)?;
type_parameters_declaration: L_CHEVRON (type_parameter_declaration COMMA?)+ R_CHEVRON;
type_parameters_usage: L_CHEVRON (type COMMA?)+? R_CHEVRON;

// Value parameters
value_parameter_declaration_modifier: KW_VARARG;
value_parameter_declaration: value_parameter_declaration_modifier? IDENTIFIER COLON type;
value_parameter_declarations: (value_parameter_declaration COMMA?)+;

// Annotation
annotation_usage:
    AT
    IDENTIFIER
    annotation_parameters?
    ;

annotation_parameters:
    L_PAREN
    annotation_parameter+
    R_PAREN
    ;

annotation_parameter:
    (
        IDENTIFIER
        EQUALS
    )?
    expression
    COMMA?
    ;

annotations_usage: (annotation_usage NL)+;

// Declaration
declaration:
    function_declaration
    | import_declaration
    ;

import_declaration:
    KW_IMPORT
    identifier
    end?
    ;

// Expression and Statements
statement:
    (
        return_statement
        | let_statement
        | for_statement
        | while_statement
    )
    end
    ;

let_statement:
    KW_LET
    KW_MUTABLE?
    (IDENTIFIER | L_PAREN (IDENTIFIER COMMA?)+ R_PAREN)
    (COLON type)?
    ASSIGN
    (expression | statement)
    end?
    ;

while_statement: // TODO: do while support
    KW_WHILE
    L_PAREN
    expression
    R_PAREN
    L_BRACE
    (statement | expression | NL)*?
    R_BRACE
    end?
    ;

for_statement:
    KW_FOR
    L_PAREN
    expression
    R_PAREN
    L_BRACE
    (statement | expression | NL)*?
    R_BRACE
    end?
    ;

return_statement:
    KW_RETURN
    expression
    | KW_BREAK
    | KW_CONTINUE
    ;

primary:
    INTEGER
    | FLOAT
    | string
    | KW_TRUE
    | KW_FALSE;

expression:
    primary
    | closure_expression
    | tuple_expression
    | call_expression
    | L_PAREN expression R_PAREN
    | expression DOTDOT expression
    | expression (LOGICAL_AND | LOGICAL_OR) expression
    | expression (KW_IN) expression
    | expression (KW_AS | KW_IS) type
    | expression (INCREMENT | DECREMENT)
    | <assoc=right> (ASTERISK | INCREMENT | DECREMENT | NOT) expression
    | expression (ASTERISK | DIV | MOD) expression
    | expression (PLUS | MINUS) expression
    | expression (EQUALS | NOT_EQUALS) expression
    | <assoc_right> expression (ASSIGN) expression
    | identifier
    | IDENTIFIER
    ;

tuple_expression:
    L_PAREN
    (expression COMMA?)+
    R_PAREN
    end?
    ;

call_expression:
    identifier
    (
        (
            L_PAREN
            (expression COMMA?)*?
            R_PAREN
            closure_expression?
        )
        | closure_expression
    )
    end?
    ;

closure_variable:
    IDENTIFIER
    (
        COLON
        type
    )?
    ;

closure_expression:
    L_BRACE NL?
    (
        L_PAREN
        (closure_variable COMMA?)+
        R_PAREN
        ARROW NL?
    )?
    (statement | declaration | expression | NL)*?
    R_BRACE
    ;

// String
string: simple_string | ml_string;

simple_string:
    (DOUBLE_QUOTE
    (STRING_MODE_TEXT
    | STRING_MODE_ESCAPED_STRING_END
    | STRING_MODE_ESCAPED_CHAR
    | (STRING_MODE_LERP_BEGIN
    expression*?
    R_BRACE))+
    DOUBLE_QUOTE)
    | EMPTY_STRING
    ;

ml_string:
    (ML_STRING_BEGIN
    (ML_STRING_MODE_TEXT
    | ML_STRING_MODE_ESCAPED_ML_STRING_END
    | ML_STRING_MODE_ESCAPED_CHAR
    | (ML_STRING_MODE_LERP_BEGIN
    expression*?
    R_BRACE))+
    ML_STRING_END)
    | EMPTY_ML_STRING
    ;

// Function
function_calling_convention: KW_CALLCONV L_PAREN simple_string R_PAREN;

function_modifier:
    KW_PUBLIC
    | KW_PROTECTED
    | KW_PRIVATE
    | KW_INTERNAL
    | KW_EXTERN
    | KW_CONST
    | function_calling_convention
    ;

function_declaration:
    annotations_usage?
    function_signature_declaration
    (multiline_function_body | singleline_function_body)?
    ;

function_signature_declaration:
    function_modifier*?
    KW_FUNCTION
    type_parameters_declaration?
    IDENTIFIER
    L_PAREN
    value_parameter_declarations?
    R_PAREN
    (COLON type)?
    ;

multiline_function_body:
    L_BRACE NL?
    (statement | declaration | expression | NL)*?
    R_BRACE
    ;

singleline_function_body:
    ARROW
    expression
    ;

// Other
identifier: IDENTIFIER ((DOT | COLONCOLON) IDENTIFIER)*?;
