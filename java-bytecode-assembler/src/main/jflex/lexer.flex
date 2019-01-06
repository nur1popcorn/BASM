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

package com.nur1popcorn.basm;

import static com.nur1popcorn.basm.Parser.Lexer.*;

%%

%class FlexLexer
%line

%{
    private final StringBuilder sb = new StringBuilder();

    private static Yytoken yytoken(int token) {
        return new Yytoken(token);
    }

    private static <T> Yytoken<T> yytoken(int token, T lVal) {
        return new Yytoken<>(token, lVal);
    }

    private static String escape(String str) {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            switch(c) {
                case '\t': sb.append("\\t"); break;
                case '\b': sb.append("\\b"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\f': sb.append("\\f"); break;
                case '\"': sb.append("\\\""); break;
                default: sb.append(c); break;
            }
        }
        return sb.toString();
    }

    private static char parseUnicode(String character) {
        int i = 2;
        while(character.charAt(i) == 'u')
            i++;
        return (char) Integer.parseInt(character.substring(i), 16);
    }
%}

WHITE_SPACE = [\ \t\n\r\f]

END_OF_LINE = \n | \r
NOT_END_OF_LINE = [^\n] |
                  [^\r\n] |
                  [^\r]

SINGLE_LINE_COMMENT = "//" { NOT_END_OF_LINE }* { END_OF_LINE }?
%x MULTI_LINE_COMMENT

IDENTIFIER = [:jletter:][:jletterdigit:]*
%x STRING

%%

<YYINITIAL> {
    ".import" { return yytoken(IMPORT); }
    ".class" { return yytoken(CLASS); }
    ".field" { return yytoken(FIELD); }
    ".method" { return yytoken(METHOD); }

    "public" { return yytoken(PUBLIC); }
    "private" { return yytoken(PRIVATE); }
    "protected" { return yytoken(PROTECTED); }
    "static" { return yytoken(STATIC); }
    "final" { return yytoken(FINAL); }
    "synchronized" { return yytoken(SYNCHRONIZED); }
    "volatile" { return yytoken(VOLATILE); }
    "transient" { return yytoken(TRANSIENT); }
    "native" { return yytoken(NATIVE); }
    "interface" { return yytoken(INTERFACE); }
    "abstract" { return yytoken(ABSTRACT); }
    "strict" { return yytoken(STRICT); }
    "synthetic" { return yytoken(SYNTHETIC); }
    "annotation" { return yytoken(ANNOTATION); }
    "enum" { return yytoken(ENUM); }
    "super" { return yytoken(SUPER); }
    "bridge" { return yytoken(BRIDGE); }
    "varargs" { return yytoken(VARARGS); }
    "mandated" { return yytoken(MANDATED); }

    \{ { return yytoken(L_BRACE); }
    \} { return yytoken(R_BRACE); }

    {SINGLE_LINE_COMMENT} {  }
    "/*" { yybegin(MULTI_LINE_COMMENT); }

    { IDENTIFIER } { return yytoken(IDENTIFIER, yytext()); }
    \" {
           sb.setLength(0);
           yybegin(STRING);
       }

    { WHITE_SPACE } {  }
}

<MULTI_LINE_COMMENT> {
    "*/" { yybegin(YYINITIAL); }
    . {  }
}

<STRING> {
    \" {
           yybegin(YYINITIAL);
           return yytoken(STRING_LIT, sb.toString());
       }

    // https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.6
    "\\b"  { sb.append('\b'); }
    "\\t"  { sb.append('\t'); }
    "\\n"  { sb.append('\n'); }
    "\\f"  { sb.append('\f'); }
    "\\r"  { sb.append('\r'); }
    "\\\"" { sb.append('\"'); }
    "\\'"  { sb.append('\''); }
    "\\\\" { sb.append('\\'); }

    \\[0-3]?[0-7]?[0-7] { sb.append(Integer.parseInt(yytext().substring(1), 8)); }

    // https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.5
    \\ { throw new Error("Illegal string character '\\' at line: " + yyline + "."); }

    // https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-InputCharacter
    { END_OF_LINE } { throw new Error("Unexpected line feed at line: " + yyline + "."); }

    [\\][u]+[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { sb.append(parseUnicode(yytext())); }
    . { sb.append(yytext()); }
}

<<EOF>> { return yytoken(EOF); }

[^] { throw new Error("Illegal character '" + escape(yytext()) + "', at line: " + yyline + "."); }
