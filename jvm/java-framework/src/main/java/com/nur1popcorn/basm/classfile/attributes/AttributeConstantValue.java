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

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 */
public final class AttributeConstantValue extends AttributeInfo {
    private final int constantValueIndex /* u2 */;

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     * @param constantValueIndex The index of the constant value in the constant pool.
     */
    public AttributeConstantValue(int nameIndex, int attributeLength, int constantValueIndex) {
        super(nameIndex, attributeLength);
        this.constantValueIndex = constantValueIndex;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(constantValueIndex);
    }

    @Override
    public void accept(IAttributeVisitor v) {
        v.visit(this);
    }

    /**
     * @return The index of the constant value in the constant pool.
     */
    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced constant value inside of the {@link ConstantPool}, all valid entries
     *         are listed here:
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2-300-C.1">
     *             Table 4.7.2-A
     *         </a>
     */
    public ConstantInfo indexConstantValue(ConstantPool constantPool) {
        return constantPool.getEntry(constantValueIndex);
    }
}
