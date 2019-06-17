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

package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.ExceptionTableEntry;

import java.io.DataInputStream;
import java.io.IOException;

final class CodeFactory implements IAttributeInfoFactory<AttributeCode> {
    @Override
    public AttributeCode createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        final int maxStack = in.readUnsignedShort();
        final int maxLocals = in.readUnsignedShort();

        final byte code[] = new byte[in.readInt()];
        in.readFully(code);

        final ExceptionTableEntry table[] = new ExceptionTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < table.length; i++)
            table[i] = new ExceptionTableEntry(
                in.readUnsignedShort(), in.readUnsignedShort(),
                in.readUnsignedShort(), in.readUnsignedShort());

        return new AttributeCode(
            nameIndex, attributeLength, maxStack, maxLocals,
            code, table, AttributeFactory.read(in, cp));
    }
}
