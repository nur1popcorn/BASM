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

import java.io.DataOutputStream;

/**
 * The {@link AttributeInfo} is an abstract class containing information about the
 * attribute and ways to read them.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7">
 *     Attributes 4.7
 * </a>
 *
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class AttributeInfo {
    private final int nameIndex /* u2 */,
                      attributeLength /* u4 */;

    /**
     * @param nameIndex
     * @param attributeLength
     */
    public AttributeInfo(int nameIndex, int attributeLength) {
        this.nameIndex = nameIndex;
        this.attributeLength = attributeLength;
    }

    public void write(DataOutputStream os, ConstantPool cp) {

    }

    /**
     * @return
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * @return
     */
    public int getAttributeLength() {
        return attributeLength;
    }
}
