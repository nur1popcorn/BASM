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
import com.nur1popcorn.basm.classfile.attributes.AttributeLineNumberTable;
import com.nur1popcorn.basm.classfile.attributes.LineNumberTableEntry;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link LineNumberTableFactory} is responsible for reading {@link AttributeLineNumberTable}s.
 *
 * @see AttributeLineNumberTable
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
final class LineNumberTableFactory implements AttributeInfoFactory<AttributeLineNumberTable> {
    @Override
    public AttributeLineNumberTable createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        final LineNumberTableEntry table[] = new LineNumberTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < table.length; i++)
            table[i] = new LineNumberTableEntry(
                in.readUnsignedShort(), in.readUnsignedShort());

        return new AttributeLineNumberTable(
            nameIndex, attributeLength, table);
    }
}
