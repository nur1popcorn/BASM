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

package com.nur1popcorn.basm.classfile.attributes.annotation;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AttributeRuntimeVisibleParameterAnnotations extends AttributeInfo {
    private final ParameterAnnotation[] parameterAnnotations;

    public AttributeRuntimeVisibleParameterAnnotations(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        parameterAnnotations = new ParameterAnnotation[in.readUnsignedByte()];
        for(int i = 0; i < parameterAnnotations.length; i++)
            parameterAnnotations[i] = new ParameterAnnotation(in);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeByte(parameterAnnotations.length);
        for(ParameterAnnotation annotation : parameterAnnotations)
            annotation.write(os);
    }
}
