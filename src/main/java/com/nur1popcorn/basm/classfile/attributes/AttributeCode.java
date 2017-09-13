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
import java.io.IOException;

public class AttributeCode extends AttributeInfo {
    private int maxStack /* u2 */,
                maxLocals /* u2 */;

    private byte code[] /* length: u4
                           entries: u1 */;

    private ExceptionTableEntry exceptionTable[] /* length: u2 */;
    private AttributeInfo attributes[] /* length: u2 */;

    public AttributeCode(int nameIndex, DataInputStream in, ConstantPool constantPool) throws IOException {
        super(nameIndex, in);
        maxStack = in.readUnsignedShort();
        maxLocals = in.readUnsignedShort();

        code = new byte[in.readInt()];
        for(int i = 0; i < code.length; i++)
            code[i] = in.readByte();

        exceptionTable = new ExceptionTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < exceptionTable.length; i++)
            exceptionTable[i] = new ExceptionTableEntry(in);

        attributes = AttributeInfo.read(in, constantPool);
    }

    public static class ExceptionTableEntry {
        private int startPc /* u2 */,
                    endPc /* u2 */,
                    handlerPc /* u2 */,
                    catchType /* u2 */;

        public ExceptionTableEntry(DataInputStream in) throws IOException {
            startPc = in.readUnsignedShort();
            endPc = in.readUnsignedShort();
            handlerPc = in.readUnsignedShort();
            catchType = in.readUnsignedShort();
        }
    }
}
