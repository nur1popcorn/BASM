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
        Parser parser= new Parser(new LexerAdapter(System.in))
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

%type<Integer> public_flag
               private_flag
               protected_flag
               static_flag
               final_flag
               synchronized_flag
               volatile_flag
               transient_flag
               native_flag
               interface_flag
               abstract_flag
               strict_flag
               synthetic_flag
               annotation_flag
               enum_flag
               super_flag
               bridge_flag
               varargs_flag
               mandated_flag
               class_modifiers

%%

program:
    imports class_declaration "{" directives "}";

imports:
    imports ".import" IDENTIFIER { System.out.println("Identifier: " + $3); } |
    imports ".import" STRING_LIT { System.out.println("String: " + $3); } |
    %empty

public_flag:
    "public" { $$ = ACC_PUBLIC; } |
    %empty { $$ = 0; }

private_flag:
    "private" { $$ = ACC_PRIVATE; } |
    %empty { $$ = 0; }

protected_flag:
    "protected" { $$ = ACC_PROTECTED; } |
    %empty { $$ = 0; }

static_flag:
    "static" { $$ = ACC_STATIC; } |
    %empty { $$ = 0; }

final_flag:
    "final" { $$ = ACC_FINAL; } |
    %empty { $$ = 0; }

synchronized_flag:
    "synchronized" { $$ = ACC_SYNCHRONIZED; } |
    %empty { $$ = 0; }

volatile_flag:
    "volatile" { $$ = ACC_SYNCHRONIZED; } |
    %empty { $$ = 0; }

transient_flag:
    "transient" { $$ = ACC_TRANSIENT; } |
    %empty { $$ = 0; }

native_flag:
    "native" { $$ = ACC_NATIVE; } |
    %empty { $$ = 0; }

interface_flag:
    "interface" { $$ = ACC_INTERFACE; } |
    %empty { $$ = 0; }

abstract_flag:
    "abstract" { $$ = ACC_ABSTRACT; } |
    %empty { $$ = 0; }

strict_flag:
    "strict" { $$ = ACC_STRICT; } |
    %empty { $$ = 0; }

synthetic_flag:
    "synthetic" { $$ = ACC_SYNTHETIC; } |
    %empty { $$ = 0; }

annotation_flag:
    "annotation" { $$ = ACC_ANNOTATION; } |
    %empty { $$ = 0; }

enum_flag:
    "enum" { $$ = ACC_ENUM; } |
    %empty { $$ = 0; }

super_flag:
    "super" { $$ = ACC_SUPER; } |
    %empty { $$ = 0; }

bridge_flag:
    "bridge" { $$ = ACC_BRIDGE; } |
    %empty { $$ = 0; }

varargs_flag:
    "varargs" { $$ = ACC_VARARGS; } |
    %empty { $$ = 0; }

mandated_flag:
    "mandated" { $$ = ACC_MANDATED; } |
    %empty { $$ = 0; }

// https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-ClassModifier
class_modifiers:
    public_flag final_flag super_flag interface_flag abstract_flag synthetic_flag annotation_flag enum_flag
        {
            $$ = $1 | $2 | $3 | $4 |
                 $5 | $6 | $7 | $8;
        }

class_declaration:
    ".class" class_modifiers IDENTIFIER { System.out.println("Identifier, " + $2 + ", " + $3); } |
    ".class" class_modifiers STRING_LIT { System.out.println("Identifier, " + $2 + ", " + $3); }

field:
    ".field"

directives:
    //directive field |
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
