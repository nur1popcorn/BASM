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

/**
 * The {@link AssemblyTokenizer} TODO: desc.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AssemblyTokenizer {

    private State state = State.HEAD;
    private Reader source;

    public AssemblyTokenizer(Reader source) {
        this.source = source;
    }

    public TokenType nextToken() throws IOException {
        for(int i; (i = source.read()) != -1;) {
            final char c = (char) i;
            switch(state) {
                case HEAD:
                    switch(c) {
                        case ' ':
                            break;
                        case ':':
                            state = State.BODY;
                            break;
                    }
                    break;
                case BODY:
                    break;
                case METHOD:
                    break;
            }
        }
        return TokenType.EOF;
    }

    private enum State {
        HEAD,
        BODY,
        METHOD
    }
}
