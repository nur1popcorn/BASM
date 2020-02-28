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

import com.nur1popcorn.basm.classfile.AccessFlags;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

/**
 * The {@link MethodParameter} class holds the nameIndex and access flags of a method parameter.
 *
 * @see AttributeMethodParameters
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class MethodParameter extends AccessFlags {
    private int nameIndex;

    public MethodParameter(int nameIndex, int accessFlags) {
        super(accessFlags);
        this.nameIndex = nameIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public ConstantUTF8 indexName(ConstantPool constantPool) {
        return constantPool.getEntry(nameIndex, CONSTANT_UTF8);
    }
}
