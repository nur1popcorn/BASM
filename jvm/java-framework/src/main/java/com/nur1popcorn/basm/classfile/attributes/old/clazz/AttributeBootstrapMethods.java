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

package com.nur1popcorn.basm.classfile.attributes.clazz;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link AttributeBootstrapMethods} represents a TODO: desc
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.23">
 *     Bootstrap Methods 4.7.23
 * </a>
 *
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class AttributeBootstrapMethods extends AttributeInfo {

    private BootstrapMethod bootstrapMethods[] /* length: u2 */;

    public AttributeBootstrapMethods(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        bootstrapMethods = new BootstrapMethod[in.readUnsignedShort()];
        for(int i = 0; i < bootstrapMethods.length; i++)
            bootstrapMethods[i] = new BootstrapMethod(in);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(bootstrapMethods.length);
        for(BootstrapMethod bootstrapMethod : bootstrapMethods)
            bootstrapMethod.write(os);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("BootstrapMethods[");
        if(bootstrapMethods.length != 0) {
            stringBuilder.append(bootstrapMethods[0]);
            for(int i = 1; i < bootstrapMethods.length; i++)
                stringBuilder.append(", ")
                             .append(bootstrapMethods[i]);
        }
        return stringBuilder.append("]")
                .toString();
    }

    public static final class BootstrapMethod {
        private int bootstrapMethodRef /* u2 */;
        private int bootstrapArguments[] /* length: u2
                                            entries: u2 */;

        public BootstrapMethod(DataInputStream in) throws IOException {
            bootstrapMethodRef = in.readUnsignedShort();
            bootstrapArguments = new int[in.readUnsignedShort()];
            for(int i = 0; i < bootstrapArguments.length; i++)
                bootstrapArguments[i] = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(bootstrapMethodRef);
            os.writeShort(bootstrapArguments.length);
            for(int i : bootstrapArguments)
                os.writeShort(i);
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder()
                    .append("BootstrapMethod[")
                    .append(bootstrapMethodRef);
            if(bootstrapArguments.length != 0) {
                stringBuilder.append(", [");
                stringBuilder.append(bootstrapArguments[0]);
                for(int i = 1; i < bootstrapArguments.length; i++)
                    stringBuilder.append(", ")
                                 .append(bootstrapArguments[i]);
                stringBuilder.append("]");
            }
            return stringBuilder.append("]")
                    .toString();
        }
    }
}
