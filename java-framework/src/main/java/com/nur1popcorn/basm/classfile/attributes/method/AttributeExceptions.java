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

package com.nur1popcorn.basm.classfile.attributes.method;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link AttributeExceptions}
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.5">
 *     Exceptions Attribute 4.7.5
 * </a>
 *
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AttributeExceptions extends AttributeInfo {
    private int exceptionIndexTable[] /* length: u2
                                         entries: u2 */;

    //TODO: add write toString etc
    public AttributeExceptions(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        exceptionIndexTable = new int[in.readUnsignedShort()];
        for(int i = 0; i < exceptionIndexTable.length; i++)
            exceptionIndexTable[i] = in.readUnsignedShort();
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(exceptionIndexTable.length);
        for(int i : exceptionIndexTable)
            os.writeShort(i);
    }

    public int[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
