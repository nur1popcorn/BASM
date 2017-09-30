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
 * The {@link AttributeLocalVariableTypeTable} TODO: desc
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.14">
 *     LocalVariableTypeTable 4.7.14
 * </a>
 *
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AttributeLocalVariableTypeTable extends AttributeInfo {
    private LocalVariableTypeTableEntry localVariableTypeTable[] /* length: u2 */;

    public AttributeLocalVariableTypeTable(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        localVariableTypeTable = new LocalVariableTypeTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < localVariableTypeTable.length; i++)
            localVariableTypeTable[i] = new LocalVariableTypeTableEntry(in);
        System.out.println(toString());
        System.out.println();
    }

    public AttributeLocalVariableTypeTable(int nameIndex, LocalVariableTypeTableEntry localVariableTypeTable[]) {
        super(nameIndex, 2 /* length */ + localVariableTypeTable.length * 10 /* 5 x u2 */);
        this.localVariableTypeTable = localVariableTypeTable;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("LocalVariableTypeTable[");
        if(localVariableTypeTable.length != 0) {
            stringBuilder.append(localVariableTypeTable[0]);
            for(int i = 1; i < localVariableTypeTable.length; i++)
                stringBuilder.append(",")
                        .append(localVariableTypeTable[i]);
        }
        return stringBuilder.append("]")
                .toString();
    }

    /**
     * @return the {@link AttributeLocalVariableTypeTable}'s entries.
     */
    public LocalVariableTypeTableEntry[] getLocalVariableTypeTable() {
        return localVariableTypeTable;
    }

    //TODO: add index methods etc.
    public static final class LocalVariableTypeTableEntry {
        public int startPc /* u2 */,
                   length /* u2 */,
                   nameIndex /* u2 */,
                   signatureIndex /* u2 */,
                   index /* u2 */;

        public LocalVariableTypeTableEntry(int startPc, int length, int nameIndex, int signatureIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.signatureIndex = signatureIndex;
            this.index = index;
        }

        public LocalVariableTypeTableEntry(DataInputStream in) throws IOException {
            this(in.readUnsignedShort(),
                 in.readUnsignedShort(),
                 in.readUnsignedShort(),
                 in.readUnsignedShort(),
                 in.readUnsignedShort());
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(startPc);
            os.writeShort(length);
            os.writeShort(nameIndex);
            os.writeShort(signatureIndex);
            os.writeShort(index);
        }

        @Override
        public String toString() {
            return "LocalVariableTableEntry[" +
                        startPc +
                        "," +
                        length +
                        "," +
                        nameIndex +
                        "," +
                        signatureIndex +
                        "," +
                        index +
                   "]";
        }
    }
}
