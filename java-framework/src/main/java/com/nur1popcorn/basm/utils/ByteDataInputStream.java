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

import com.nur1popcorn.basm.classfile.MalformedClassFileException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction.SWITCH_INS;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction.WIDE_INS;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction.indexType;

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

    public void skipInstructionParameters() throws IOException {
        final byte opcode = readByte();
        switch(indexType(opcode)) {
            case SWITCH_INS: {
                // skip padding bytes and skip default index.
                skipBytes(8 - (position() & 0x3));
                skipBytes(opcode == TABLESWITCH ?
                        (readInt() - readInt() + 1) << 2 :
                         readInt() << 3);
            }   break;
            case WIDE_INS:
                skipBytes(readByte() == IINC ?
                    4 : 2);
                break;
            default: {
                final int parameters = OPCODE_PARAMETERS[opcode & 0xff];
                if(parameters == UNKNOWN_PARAMETERS)
                    throw new MalformedClassFileException(
                        "The opcode=" + OPCODE_MNEMONICS[opcode & 0xff] +
                        " at index=" + position() + " is invalid."
                    );
                skipBytes(parameters);
            }   break;
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
