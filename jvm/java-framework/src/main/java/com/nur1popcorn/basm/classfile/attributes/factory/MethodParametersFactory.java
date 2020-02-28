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
import com.nur1popcorn.basm.classfile.attributes.AttributeMethodParameters;
import com.nur1popcorn.basm.classfile.attributes.MethodParameter;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link MethodParametersFactory} is responsible for reading {@link AttributeMethodParameters}.
 *
 * @see AttributeMethodParameters
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class MethodParametersFactory implements AttributeInfoFactory<AttributeMethodParameters> {
    @Override
    public AttributeMethodParameters createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        final MethodParameter parameters[] = new MethodParameter[in.readUnsignedByte()];
        for(int i = 0; i < parameters.length; i++)
            parameters[i] = new MethodParameter(in.readShort(), in.readShort());
        return new AttributeMethodParameters(nameIndex, attributeLength, parameters);
    }
}
