/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

%language "Java"

%define package { com.nur1popcorn.basm }
%define parser_class_name { Parser }
%define parse.error verbose

%code imports {
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.IOException;

    import static com.nur1popcorn.basm.classfile.AccessFlags.*;
}

%code {
    private static final class LexerAdapter implements Lexer {
        private final FlexLexer lexer;
        private Yytoken last;

        public LexerAdapter(InputStream in) {
            lexer = new FlexLexer(new InputStreamReader(in));
        }

        /**
         * Method to retrieve the semantic value of the last scanned token.
         *
         * @return the semantic value of the last scanned token.
         */
        @Override
        public Object getLVal() {
            return last.getLVal();
        }

        /**
         * Entry point for the scanner.  Returns the token identifier corresponding
         * to the next token and prepares to return the semantic value
         * of the token.
         *
         * @return the token identifier corresponding to the next token.
         */
        @Override
        public int yylex() throws IOException {
            return (last = lexer.yylex()).getToken();
        }

        /**
         * Entry point for error reporting.  Emits an error
         * in a user-defined way.
         *
         * @param msg The string for the error message.
         */
        @Override
        public void yyerror(String msg) {
            System.out.println(msg);
        }
    }

    public static void main(String args[]) throws IOException {
        Parser parser= new Parser(new LexerAdapter(System.in));
        parser.setDebugLevel(100);
        parser.parse();
    }
}

%token IMPORT ".import"
       CLASS ".class"
       FIELD ".field"
       METHOD ".method"

%token L_BRACE "{"
       R_BRACE "}"
       L_PAREN "("
       R_PAREN ")"
       COMMA ","
       COLON ":"

%token PUBLIC "public"
       PRIVATE "private"
       PROTECTED "protected"
       STATIC "static"
       FINAL "final"
       SYNCHRONIZED "synchronized"
       VOLATILE "volatile"
       TRANSIENT "transient"
       NATIVE "native"
       INTERFACE "interface"
       ABSTRACT "abstract"
       STRICT "strict"
       SYNTHETIC "synthetic"
       ANNOTATION "annotation"
       ENUM "enum"
       SUPER "super"
       BRIDGE "bridge"
       VARARGS "varargs"
       MANDATED "mandated"

%token IDENTIFIER
       STRING_LIT

%type<String> IDENTIFIER
              STRING_LIT
              identifier

%type<Integer> class_modifier class_modifiers
               field_modifier field_modifiers

%%

program:
    imports class_declaration;

l_brace:
    error | "{"


r_brace:
    error | "}"

identifier:
    error {  } |
    IDENTIFIER { $$ = $1; } |
    STRING_LIT { $$ = $1; }

import:
    error | ".import"

imports:
    imports import identifier {  } |
    %empty

class_modifier:
    "public" { $$ = ACC_PUBLIC; } |
    "final" { $$ = ACC_FINAL; } |
    "super" { $$ = ACC_SUPER; } |
    "interface" { $$ = ACC_INTERFACE; } |
    "abstract" { $$ = ACC_ABSTRACT; } |
    "synthetic" { $$ = ACC_SYNTHETIC; } |
    "annotation" { $$ = ACC_ANNOTATION; } |
    "enum" { $$ = ACC_ENUM; }

// https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-ClassModifier
class_modifiers:
    class_modifiers class_modifier { $$ = $1 | $2; } |
    %empty { $$ = 0; };

class_declaration:
    ".class" class_modifiers identifier l_brace directives r_brace {  }

field_modifier:
    "public" { $$ = ACC_PUBLIC; } |
    "private" { $$ = ACC_PRIVATE; } |
    "protected" { $$ = ACC_PROTECTED; } |
    "static" { $$ = ACC_STATIC; } |
    "final" { $$ = ACC_FINAL; } |
    "volatile" { $$ = ACC_VOLATILE; } |
    "transient" { $$ = ACC_TRANSIENT; } |
    "synthetic" { $$ = ACC_SYNTHETIC; } |
    "enum" { $$ = ACC_ENUM; }

field_modifiers:
    field_modifiers field_modifier { $$ = $1 | $2; } |
    %empty { $$ = 0; };

field:
    ".field" field_modifiers identifier {  }

directives:
    directives field |
    %empty

%%

final class Yytoken<T> {
    private int token;
    private T lVal;

    public Yytoken(int token) {
        this.token = token;
    }

    public Yytoken(int token, T lVal) {
        this.token = token;
        this.lVal = lVal;
    }

    public int getToken() {
        return token;
    }

    public T getLVal() {
        return lVal;
    }
}
