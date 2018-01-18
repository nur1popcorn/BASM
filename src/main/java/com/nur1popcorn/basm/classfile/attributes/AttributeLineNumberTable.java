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

package com.nur1popcorn.basm.classfile.attributes;

import com.nur1popcorn.basm.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link AttributeLineNumberTable} is an optional attribute and is used to show
 * which line number corresponds with which instructions.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.12">
 *     LineNumberTable 4.7.12
 * </a>
 *
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AttributeLineNumberTable extends AttributeInfo {
    private LineNumberTableEntry lineNumberTable[] /* length: u2 */;

    public AttributeLineNumberTable(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        lineNumberTable = new LineNumberTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < lineNumberTable.length; i++)
            lineNumberTable[i] = new LineNumberTableEntry(in);
    }

    public AttributeLineNumberTable(int nameIndex, LineNumberTableEntry lineNumberTable[]) {
        super(nameIndex, 2 + /* length */ lineNumberTable.length * 4 /* 2 x u2 */);
        this.lineNumberTable = lineNumberTable;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(lineNumberTable.length);
        for(LineNumberTableEntry entry : lineNumberTable)
            entry.write(os);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("LineNumberTable[");
        if(lineNumberTable.length != 0) {
            stringBuilder.append(lineNumberTable[0]);
            for(int i = 1; i < lineNumberTable.length; i++)
                stringBuilder.append(", ")
                             .append(lineNumberTable[i]);
        }
        return stringBuilder.append("]")
                .toString();
    }

    /**
     * @return the {@link AttributeLineNumberTable}'s entries.
     */
    public LineNumberTableEntry[] getLineNumberTable() {
        return lineNumberTable;
    }

    public static final class LineNumberTableEntry {
        public int startPc /* u2 */,
                   lineNumber /* u2 */;

        /**
         * @param startPc is an valid index into the bytecode array.
         * @param lineNumber the linenumber that corresponds with given index.
         */
        public LineNumberTableEntry(int startPc, int lineNumber) {
            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }

        /**
         * @param in the {@link DataInputStream} from which the {@link LineNumberTableEntry}
         *           should be read.
         */
        public LineNumberTableEntry(DataInputStream in) throws IOException {
            this(in.readUnsignedShort(),
                 in.readUnsignedShort());
        }

        @Override
        public String toString() {
            return "LineNumberTableEntry[" +
                        startPc +
                        ", " +
                        lineNumber +
                    "]";
        }

        /**
         * @param os the {@link DataOutputStream} to which the {@link LineNumberTableEntry}'s
         *           bytecode index and corresponding linenumber should be written to.
         */
        public void write(DataOutputStream os) throws IOException {
            os.writeShort(startPc);
            os.writeShort(lineNumber);
        }
    }
}
