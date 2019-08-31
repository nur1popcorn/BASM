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

package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.Opcode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.IINC;

public final class ByteDataInputStream extends DataInputStream {
    private ByteArrayInputStreamDelegate in;

    public ByteDataInputStream(byte buffer[]) {
        this(new ByteArrayInputStreamDelegate(buffer));
    }

    private ByteDataInputStream(ByteArrayInputStreamDelegate in) {
        super(in);
        this.in = in;
    }

    public int position() {
        return in.position();
    }

    public void skip(Opcode opcode) throws IOException {
        switch(opcode) {
            case TABLESWITCH: {
                // skip padding bytes and skip default index.
                skipBytes(-position() & 0x3);
                skipBytes(4);
                final int low = readInt();
                final int high = readInt();
                skipBytes((high - low + 1) << 2);
            }   break;
            case LOOKUPSWITCH:
                // skip padding bytes and skip default index.
                skipBytes(-position() & 0x3);
                skipBytes(4);
                skipBytes(readInt() << 3);
                break;
            case WIDE: {
                skipBytes(
                    readByte() == IINC.getOpcode() ?
                        4 : 2);
            }   break;
            default:
                skipBytes(opcode.getParameter());
                break;
        }
    }

    private static final class ByteArrayInputStreamDelegate extends ByteArrayInputStream {
        public ByteArrayInputStreamDelegate(byte[] buffer) {
            super(buffer);
        }

        public int position() {
            return pos;
        }
    }
}
