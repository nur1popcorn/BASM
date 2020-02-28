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
import java.io.IOException;

/**
 * The {@link AttributeMethodParameters} is an optional attribute in any method.
 * It is used to denote the names of method parameters.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.24">
 *     4.7.24. The MethodParameters Attribute
 * </a>
 *
 * @see MethodParameter
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class AttributeMethodParameters extends AttributeInfo {
    private MethodParameter parameters[];

    public AttributeMethodParameters(int nameIndex, int attributeLength, MethodParameter parameters[]) {
        super(nameIndex, attributeLength);
        this.parameters = parameters;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeByte(parameters.length);
        for(MethodParameter parameter : parameters) {
            os.writeShort(parameter.getNameIndex());
            os.writeShort(parameter.getAccessFlags());
        }
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public MethodParameter getParameter(int index) {
        return parameters[index];
    }

    public void setParameters(MethodParameter parameters[]) {
        this.parameters = parameters;
    }

    @Override
    public void accept(AttributeVisitor v) {
        v.visit(this);
    }
}
