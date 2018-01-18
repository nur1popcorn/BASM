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
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link AttributeConstantValue} represents a value inside of the {@link ConstantPool}
 * and is only used by Field_Infos.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2">
 *     ConstantValue Attribute 4.7.2
 * </a>
 *
 * @see AttributeInfo
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AttributeConstantValue extends AttributeInfo {
    private int constantValueIndex /* u2 */;

    /**
     * @param nameIndex is a entry into the {@link ConstantPool} and represents the
     *                  {@link AttributeInfo}'s identifier
     * @param in the {@link DataInputStream} from which the constantValueIndex should be
     *           read.
     */
    public AttributeConstantValue(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        constantValueIndex = in.readUnsignedShort();
    }


    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(constantValueIndex);
    }

    @Override
    public String toString() {
        return "ConstantValue[" + constantValueIndex + "]";
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced constant value inside of the {@link ConstantPool}, valid
     *         entries are described here:
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2-300-C.1">
     *             Table 4.7.2-A
     *         </a>
     */
    public ConstantInfo indexConstantValue(ConstantPool constantPool) {
        return constantPool.getEntry(constantValueIndex);
    }
}
