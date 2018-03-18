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

package com.nur1popcorn.basm.transformers.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Tokenizer} turns the provided source code into a list of tokens.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class Tokenizer {
    private List<Token> result = new ArrayList<>();
    private Reader source;

    public Tokenizer(Reader source) {
        this.source = source;
    }

    public static List<Token> tokenize(Reader source) {
        return null;
    }

    public boolean isValidDesc(String token) {

        for(int i = 0; i < token.length(); i++) {

        }
                        /*switch()
                        B byte 	signed byte
                        C 	char 	Unicode character code point in the Basic Multilingual Plane, encoded with UTF-16
                        D 	double 	double-precision floating-point value
                        F 	float 	single-precision floating-point value
                        I 	int 	integer
                        J 	long 	long integer
                        L ClassName ; 	reference 	an instance of class ClassName
                                S 	short 	signed short
                        Z 	boolean 	true or false
                                [*/
    }

    public void tokenize() throws IOException {
        final StringBuilder sb = new StringBuilder();
        for(int i; (i = source.read()) != -1;) {
            char c = (char) i;
            switch(c) {
                case ':':
                    result.add(new Token(":", Token.TokenType.COLON));
                case ' ':
                case '\t':
                case '\r':
                    final String token = sb.toString();
                    switch(token) {
                        case "public":
                        case "final":
                        case "super":
                        case "interface":
                        case "abstract":
                        case "synthetic":
                        case "annotation":
                        case "enum":
                        case "private":
                        case "protected":
                        case "static":
                        case "volatile":
                        case "transient":
                        case "synchronized":
                        case "bridge":
                        case "varargs":
                        case "native":
                        case "strict":
                            result.add(new Token(token, Token.TokenType.ACC_FLAG));
                            continue;
                        case "class":
                            result.add(new Token(token, Token.TokenType.CLASS));
                            continue;
                        case "extends":
                            result.add(new Token(token, Token.TokenType.EXTENDS));
                            continue;
                    }

                    if(token.startsWith("("));
                    break;
                default:
                    if(Character.isLetter(c) ||
                       Character.isDigit(c) ||
                       c == '/' ||
                       c == '-' ||
                       c == '_' ||
                       c == '$' ||
                       c == '\'' ||
                       c == '"')
                        sb.append(c);
            }
        }
    }

}
