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
    private final int instructions[];

    public ByteDataInputStream(byte buffer[]) throws IOException {
        this(new ByteArrayInputStreamDelegate(buffer));
    }

    private ByteDataInputStream(ByteArrayInputStreamDelegate in) throws IOException {
        super(in);
        this.in = in;
        instructions = new int[in.length()];
        for(int i = 0, offset = 0; available() != 0; i++) {
            skip(Opcode.valueOf(readByte()));
            instructions[offset] = i;
            while(++offset < position())
                instructions[offset] = -1; /* illegal location */
        }
        reset();
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

    public int getIndex(int offset) {
        if(offset < 0 || offset >= in.length() || instructions[offset] < 0)
            throw new IllegalArgumentException("Illegal target of jump or branch: " + offset);
        return instructions[offset];
    }

    public int numberOfInstructions() {
        return instructions.length;
    }

    private static final class ByteArrayInputStreamDelegate extends ByteArrayInputStream {
        private ByteArrayInputStreamDelegate(byte[] buffer) {
            super(buffer);
        }

        private int position() {
            return pos;
        }

        private int length() {
            return buf.length;
        }
    }
}
