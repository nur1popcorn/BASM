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
%define parser_class_name { Assembler }

%code imports {
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.IOException;

    import com.nur1popcorn.basm.classfile.AccessFlags;
}

%code {
    private static final class LexerAdapter implements Lexer {
        private final AssemblerLexer lexer;
        private Yytoken last;

        public LexerAdapter(InputStream in) {
            lexer = new AssemblerLexer(new InputStreamReader(in));
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
        final Assembler asm = new Assembler(new LexerAdapter(System.in));
        //asm.setDebugLevel(100);
        asm.parse();
    }
}

%token IMPORT ".import"
       CLASS ".class"
       FIELD ".field"
       METHOD ".method"

%token EXTENDS "extends"
       IMPLEMENTS "implements"

%token L_BRACE "{"
       R_BRACE "}"
       L_PAREN "("
       R_PAREN ")"
       COMMA ","
       COLON ":"

%token ACC_PUBLIC "public"
       ACC_PRIVATE "private"
       ACC_PROTECTED "protected"
       ACC_STATIC "static"
       ACC_FINAL "final"
       ACC_SYNCHRONIZED "synchronized"
       ACC_VOLATILE "volatile"
       ACC_TRANSIENT "transient"
       ACC_NATIVE "native"
       ACC_INTERFACE "interface"
       ACC_ABSTRACT "abstract"
       ACC_STRICT "strict"
       ACC_SYNTHETIC "synthetic"
       ACC_ANNOTATION "annotation"
       ACC_ENUM "enum"
       ACC_SUPER "super"
       ACC_BRIDGE "bridge"
       ACC_VARARGS "varargs"
       ACC_MANDATED "mandated"

%token IDENTIFIER
       STRING_LIT

%type<String> IDENTIFIER
              STRING_LIT

%type<Integer> class_modifier

%%

program:
    imports class "{" "}";

imports:
    imports ".import" IDENTIFIER { System.out.println("Identifier: " + $3); } |
    imports ".import" STRING_LIT { System.out.println("String: " + $3); } |
    %empty {  }

class_modifier:
    "public" { $$ = AccessFlags.ACC_PUBLIC; } |
    "private" { $$ = AccessFlags.ACC_PRIVATE; } |
    "protected" { $$ = AccessFlags.ACC_PROTECTED; } |
    %empty { $$ = 0; }

class:
    ".class" class_modifier IDENTIFIER { System.out.println("Identifier, " + $2 + ", " + $3); } |
    ".class" class_modifier STRING_LIT { System.out.println("Identifier, " + $2 + ", " + $3); }

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
