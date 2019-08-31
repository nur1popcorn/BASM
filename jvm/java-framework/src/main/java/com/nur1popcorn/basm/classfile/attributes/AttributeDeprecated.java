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

public final class AttributeDeprecated extends AttributeInfo {
    private final byte info[];

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     * @param info The bytes which make up the deprecated attribute.
     */
    public AttributeDeprecated(int nameIndex, int attributeLength, byte info[]) {
        super(nameIndex, attributeLength);
        this.info = info;
    }

    @Override
    public void accept(IAttributeVisitor v) {
        v.visit(this);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.write(info);
    }

    /**
     * @return The bytes which make up the {@link AttributeDeprecated}.
     */
    public byte[] getInfo() {
        return info;
    }
}
