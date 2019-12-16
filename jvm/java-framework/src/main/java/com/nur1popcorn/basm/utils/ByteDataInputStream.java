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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Label;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.IINC;
import static com.nur1popcorn.basm.classfile.Opcode.TABLESWITCH;

public final class ByteDataInputStream extends DataInputStream {
    private ByteArrayInputStreamDelegate in;
    private final Label labels[];

    public ByteDataInputStream(byte buffer[]) {
        this(new ByteArrayInputStreamDelegate(buffer));
    }

    private ByteDataInputStream(ByteArrayInputStreamDelegate in) {
        super(in);
        this.in = in;
        labels = new Label[in.length()];
    }

    public void skip(Opcode opcode) throws IOException {
        switch(opcode.getType()) {
            case SWITCH_INS:
                // skip padding bytes and skip default index.
                skipBytes((-position() & 0x3) + 4);
                final int length = readInt();
                skipBytes(opcode == TABLESWITCH ?
                    (readInt() - length + 1) << 2 :
                                 length << 3);
                break;
            case WIDE_INS:
                skipBytes(
                    readByte() == IINC.getOpcode() ?
                        4 : 2);
                break;
            default:
                skipBytes(opcode.getParameter());
                break;
        }
    }

    public int position() {
        return in.position();
    }

    public Label readLabel(int offset) {
        if(labels[offset] == null)
            labels[offset] = new Label();
        return labels[offset];
    }

    public Label[] getLabels() {
        return labels;
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
