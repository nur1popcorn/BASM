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
 * The {@link AttributeLocalVariableTable} describes a method's localvariables.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.13">
 *     LocalVariableTable 4.7.13
 * </a>
 *
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class AttributeLocalVariableTable extends AttributeInfo {
    private LocalVariableTableEntry localVariableTable[] /* length: u2 */;

    public AttributeLocalVariableTable(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        int len = in.readUnsignedShort();
        localVariableTable = new LocalVariableTableEntry[len];
        for(int i = 0; i < localVariableTable.length; i++)
            localVariableTable[i] = new LocalVariableTableEntry(in);
    }

    public AttributeLocalVariableTable(int nameIndex, LocalVariableTableEntry localVariableTable[]) {
        super(nameIndex, 2 /* length */ + localVariableTable.length * 10 /* 5 x u2 */);
        this.localVariableTable = localVariableTable;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(localVariableTable.length);
        for(LocalVariableTableEntry entry : localVariableTable)
            entry.write(os);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("LocalVariableTable[");
        for(LocalVariableTableEntry entry : localVariableTable)
            stringBuilder.append(entry.toString())
                         .append(",");
        return stringBuilder.append("]")
                .toString();
    }

    /**
     * @return the {@link AttributeLocalVariableTable}'s entries.
     */
    public LocalVariableTableEntry[] getLocalVariableTable() {
        return localVariableTable;
    }

    //TODO: add lookup funcs and desc etc.
    public static final class LocalVariableTableEntry {
        private int startPc /* u2 */,
                    length /* u2 */,
                    nameIndex /* u2 */,
                    descIndex /* u2 */,
                    index /* u2 */;

        public LocalVariableTableEntry(int startPc, int length, int nameIndex, int descIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descIndex = descIndex;
            this.index = index;
        }

        public LocalVariableTableEntry(DataInputStream in) throws IOException {
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
            os.writeShort(descIndex);
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
                       descIndex +
                       "," +
                       index +
                   "]";
        }
    }
}
