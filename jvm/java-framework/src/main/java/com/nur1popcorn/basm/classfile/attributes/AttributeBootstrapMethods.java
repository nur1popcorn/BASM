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
import java.util.Arrays;

/**
 * @author Ben Kinney
 * @since 1.0.0-alpha
 */
public final class AttributeBootstrapMethods extends AttributeInfo {
    private BootstrapMethodsEntry[] bootstrapMethods;

    public AttributeBootstrapMethods(int nameIndex, int attributeLength, BootstrapMethodsEntry[] bootstrapMethods) {
        super(nameIndex, attributeLength);
        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethodsEntry[] getBootstrapMethods() {
        return bootstrapMethods;
    }

    @Override
    public void accept(AttributeVisitor v) {
        v.visit(this);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(bootstrapMethods.length);
        for(BootstrapMethodsEntry entry : bootstrapMethods)
            entry.write(os);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeBootstrapMethods that = (AttributeBootstrapMethods) o;
        return Arrays.equals(bootstrapMethods, that.bootstrapMethods);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bootstrapMethods);
    }
}
