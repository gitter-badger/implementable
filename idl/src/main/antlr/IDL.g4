grammar IDL;

@header {
package org.implementable.idl.parser;
}

specification
    : definition+
    ;

definition
    : interface_decl SEMICOLON?
    ;

// Type

type_spec
    : id=(REMOTE_ID|LOCAL_ID) template?
    ;

template_component
    : type_spec ( interface_inheritance_list )?
    ;

template_components
    : template_component ( COMA template_component )*
    ;

template
    : LEFT_ANGULAR_BRACKET template_components RIGHT_ANGULAR_BRACKET
    ;

// Interface

interface_decl
    : KEYWORD_INTERFACE type_spec ( interface_inheritance_list )? LEFT_BRACE interface_body RIGHT_BRACE
    ;

interface_inheritance
    : type_spec ( COMA type_spec )*
    ;
interface_inheritance_list
    :  KEYWORD_IMPLEMENTS interface_inheritance
    ;

interface_body
    : interface_element*;

interface_element
    : function_decl SEMICOLON
    | struct_decl SEMICOLON?
    ;

// Struct
struct_member
    : type_spec id=LOCAL_ID SEMICOLON
    ;

struct_members
    : LEFT_BRACE struct_member* RIGHT_BRACE
    ;

struct_decl
    : KEYWORD_STRUCT type_spec struct_members
    ;
// Function

function_decl
    : type_spec id=LOCAL_ID arguments_decl
    ;

arguments_decl
    : LEFT_BRACKET RIGHT_BRACKET
    | LEFT_BRACKET argument_decl ( COMA argument_decl )* RIGHT_BRACKET
    ;

argument_decl
    : type_spec id=LOCAL_ID
    ;

// Namespace
namespace_decl
    : KEYWORD_NAMESPACE id=(REMOTE_ID|LOCAL_ID) LEFT_BRACE definition* RIGHT_BRACE;


KEYWORD_NAMESPACE:  'namespace';
KEYWORD_INTERFACE:  'interface';
KEYWORD_IMPLEMENTS: 'implements';
KEYWORD_STRUCT:     'struct';

PERIOD:          '.';
COMA:            ',';
SEMICOLON:       ';';

LEFT_BRACE:      '{';
RIGHT_BRACE:     '}';

LEFT_BRACKET:    '(';
RIGHT_BRACKET:   ')';

LEFT_ANGULAR_BRACKET:  '<';
RIGHT_ANGULAR_BRACKET: '>';

WS
    :   (' ' | '\r' | '\t' | '\u000C' | '\n') -> channel(HIDDEN);


COMMENT
    :   '/*' .*? '*/' -> channel(HIDDEN)
    ;

LINE_COMMENT
    :   '//' ~('\n' | '\r')* '\r'? '\n' -> channel(HIDDEN)
    ;

fragment
LETTER
    :   '\u0024'
    |   '\u0041'..'\u005a'
    |   '\u005f'
    |   '\u0061'..'\u007a'
    |   '\u00c0'..'\u00d6'
    |   '\u00d8'..'\u00f6'
    |   '\u00f8'..'\u00ff'
    |   '\u0100'..'\u1fff'
    |   '\u3040'..'\u318f'
    |   '\u3300'..'\u337f'
    |   '\u3400'..'\u3d2d'
    |   '\u4e00'..'\u9fff'
    |   '\uf900'..'\ufaff'
    ;


fragment
ID_DIGIT
    :   '\u0030'..'\u0039'
    |   '\u0660'..'\u0669'
    |   '\u06f0'..'\u06f9'
    |   '\u0966'..'\u096f'
    |   '\u09e6'..'\u09ef'
    |   '\u0a66'..'\u0a6f'
    |   '\u0ae6'..'\u0aef'
    |   '\u0b66'..'\u0b6f'
    |   '\u0be7'..'\u0bef'
    |   '\u0c66'..'\u0c6f'
    |   '\u0ce6'..'\u0cef'
    |   '\u0d66'..'\u0d6f'
    |   '\u0e50'..'\u0e59'
    |   '\u0ed0'..'\u0ed9'
    |   '\u1040'..'\u1049'
    ;

// Identifier
REMOTE_ID: LOCAL_ID ( PERIOD LOCAL_ID )+;
LOCAL_ID: LETTER (LETTER|ID_DIGIT)*;
