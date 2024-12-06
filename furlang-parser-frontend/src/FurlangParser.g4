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

// +-------------------------------------------------------------------------------------------------------------------------------------+
// |                                                   Type, type and value parameters                                                   |
// +-------------------------------------------------------------------------------------------------------------------------------------+

// Type System
unsignedIntType: TYPE_U8 | TYPE_U16 | TYPE_U32 | TYPE_U64;
signedIntType: TYPE_I8 | TYPE_I16 | TYPE_I32 | TYPE_I64;
intType: signedIntType | unsignedIntType;

closureModifier: KW_CONST;
closureType: closureModifier*? L_PAREN (type COMMA?)*? R_PAREN ARROW type;

type:
    closureType
    | L_PAREN (type COMMA?)+ R_PAREN
    | intType
    | TYPE_BOOLEAN
    | TYPE_STRING
    | IDENTIFIER
    ;

// Type parameters
typeParameterDeclarationModifier: KW_VARARG;
typeParameterDonstraints: COLON expression;
typeParameterDefault: ARROW expression;
typeParameterDeclaration: typeParameterDeclarationModifier? IDENTIFIER typeParameterDonstraints? typeParameterDefault?;
typeParametersDeclaration: L_CHEVRON (typeParameterDeclaration COMMA?)+ R_CHEVRON;

// Value parameters
functionValueParameterDeclarationModifier: KW_VARARG;
functionValueParameterDeclaration: functionValueParameterDeclarationModifier? IDENTIFIER COLON type;
functionValueParameterDeclarations: (functionValueParameterDeclaration COMMA?)+;

constructorValueParameterDeclarationModifier: KW_VARARG;
constructorValueParameterDeclaration: constructorValueParameterDeclarationModifier? (KW_LET KW_MUTABLE?)? IDENTIFIER COLON type;
constructorValueParameterDeclarations: (constructorValueParameterDeclaration COMMA?)+;

// +-------------------------------------------------------------------------------------------------------------------------------------+
// |                                                             Declaration                                                             |
// +-------------------------------------------------------------------------------------------------------------------------------------+

declaration:
    importDeclaration
    | functionDeclaration
    | class_declaration;

importDeclaration: KW_IMPORT identifier end?;

annotationParameter: (IDENTIFIER ASSIGN)? expression COMMA?;
annotationParameters: L_PAREN annotationParameter+ R_PAREN;
annotationDeclaration: AT IDENTIFIER annotationParameters?;

// Class
class_modifier: KW_PUBLIC | KW_PROTECTED | KW_PRIVATE | KW_INTERNAL | KW_OPEN | KW_ABSTRACT;
class_declaration:
    (annotationDeclaration+)?
    class_modifier*?
    (KW_CLASS | KW_INTERFACE | KW_ANNOTATION)
    IDENTIFIER
    typeParametersDeclaration?
    constructorFunctionSignature?
    L_BRACE NL?
    (declaration | NL)+
    R_BRACE
    ;

// Function
multilineFunctionBody: L_BRACE NL? (declaration | statement | expression | NL)*? R_BRACE;
singlelineFunctionBody: ARROW expression;
functionBody: multilineFunctionBody | singlelineFunctionBody;

constructorFunctionModifier: KW_PUBLIC | KW_PROTECTED | KW_PRIVATE | KW_INTERNAL;
constructorFunctionSignature:
    (annotationDeclaration+)?
    (constructorFunctionModifier+)?
    KW_CONSTRUCTOR
    L_PAREN
    constructorValueParameterDeclarations?
    R_PAREN
    ;
constructorFunctionDeclaration: constructorFunctionSignature multilineFunctionBody;

functionCallingConvention: KW_CALLCONV L_PAREN simpleString R_PAREN;
namedFunctionModifier: functionCallingConvention | KW_PUBLIC | KW_PROTECTED | KW_PRIVATE | KW_INTERNAL | KW_EXTERN | KW_CONST;
namedFunctionSignature:
    (annotationDeclaration+)?
    (namedFunctionModifier+)?
    KW_FUNCTION
    typeParameterDeclaration?
    IDENTIFIER
    L_PAREN
    functionValueParameterDeclarations
    R_PAREN
    (COLON type)?;
namedFunctionDeclaration:
    namedFunctionSignature
    functionBody
    ;

functionDeclaration:
    namedFunctionDeclaration
    | constructorFunctionDeclaration
    ;

// +-------------------------------------------------------------------------------------------------------------------------------------+
// |                                                              Statement                                                              |
// +-------------------------------------------------------------------------------------------------------------------------------------+

statement:
    letStatement
    | returnStatement
    | breakStatement
    | continueStatement
    ;

breakStatement: KW_BREAK end?;
continueStatement: KW_CONTINUE end?;
returnStatement: KW_RETURN expression? end?;

letStatement:
    KW_LET
    KW_MUTABLE?
    (IDENTIFIER | L_PAREN (IDENTIFIER COMMA?)+ R_PAREN)
    (COLON type)?
    ASSIGN
    expression
    end?
    ;

// +-------------------------------------------------------------------------------------------------------------------------------------+
// |                                                        Expression and string                                                        |
// +-------------------------------------------------------------------------------------------------------------------------------------+

// Strings
string: simpleString | multilineString;

simpleString:
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

multilineString:
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

// Expression
primary: INTEGER | FLOAT | string | KW_TRUE | KW_FALSE;
tupleExpression: L_PAREN (expression COMMA?)+ R_PAREN end?;

callExpression:
    identifier
    (
        (
            L_PAREN
            (expression COMMA?)*?
            R_PAREN
            closureExpression?
        )
        | closureExpression
    )
    end?
    ;

closureVariable: IDENTIFIER (COLON type)?;

closureExpression:
    L_BRACE NL?
    (
        L_PAREN
        (closureVariable COMMA?)+
        R_PAREN
        ARROW NL?
    )?
    (declaration | statement | expression | NL)+
    R_BRACE
    ;

expression:
    primary
    | closureExpression
    | tupleExpression
    | callExpression
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
    ;

// +-------------------------------------------------------------------------------------------------------------------------------------+
// |                                                                Other                                                                |
// +-------------------------------------------------------------------------------------------------------------------------------------+

identifier: IDENTIFIER (DOT IDENTIFIER)*?;
end: SEMICOLON | NL;
