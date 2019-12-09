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

public final class ByteDataInputStream extends DataInputStream {
    private ByteArrayInputStreamDelegate in;
    private int count;
    private final Label labels[];

    public ByteDataInputStream(byte buffer[]) throws IOException {
        this(new ByteArrayInputStreamDelegate(buffer));
    }

    private ByteDataInputStream(ByteArrayInputStreamDelegate in) throws IOException {
        super(in);
        this.in = in;
        labels = new Label[in.length()];
        for(;available() != 0; count++) {
            final int offset = position();
            final Opcode opcode = Opcode.valueOf(readByte());
            switch(opcode.getType()) {
                case JUMP_INS:
                    switch(opcode) {
                        case GOTO_W:
                        case JSR_W:
                            readLabel(offset + readInt());
                            break;
                        default:
                            readLabel(offset + readShort());
                            break;
                    }
                    break;
                case SWITCH_INS:
                    skipBytes(-position() & 0x3);
                    readLabel(offset + readInt());
                    switch(opcode) {
                        case TABLESWITCH:
                            final int low = readInt();
                            final int high = readInt();
                            for(int i = 0; i < high - low + 1; i++)
                                readLabel(offset + readInt());
                            break;
                        case LOOKUPSWITCH:
                            final int length = readInt();
                            for(int i = 0; i < length; i++) {
                                skipBytes(4);
                                readLabel(offset + readInt());
                            }
                            break;
                    }
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
        reset();
    }

    public Label readLabel(int offset) {
        if(labels[offset] == null)
            labels[offset] = new Label();
        return labels[offset];
    }

    public int position() {
        return in.position();
    }

    public int numberOfInstructions() {
        return count;
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
