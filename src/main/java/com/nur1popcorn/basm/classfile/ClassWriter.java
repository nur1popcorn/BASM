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

package com.nur1popcorn.basm.classfile;

import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.MAGIC;

public final class ClassWriter implements IClassVisitor {
    private final DataOutputStream out;

    private ConstantPool constantPool;

    public ClassWriter(DataOutputStream out) {
        this.out = out;
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) throws IOException {
        // write out file header 0xcafebabe.
        out.writeInt(MAGIC);

        out.writeShort(minorVersion);
        out.writeShort(majorVersion);

        (this.constantPool = constantPool).write(out);
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int[] interfaces) throws IOException {
        out.writeShort(access);

        out.writeShort(thisClass);
        out.writeShort(superClass);

        out.writeShort(interfaces.length);
        for(int i : interfaces)
            out.writeShort(i);
    }

    @Override
    public void visitFields(FieldMethodInfo[] fields) throws IOException {
        out.writeShort(fields.length);
        for(FieldMethodInfo fieldInfo : fields)
            fieldInfo.write(out, constantPool);
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) throws IOException {
        out.writeShort(methods.length);
        for(FieldMethodInfo methodInfo : methods)
            methodInfo.write(out, constantPool);
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) throws IOException {
        out.writeShort(attributes.length);
        for(int i = 0; i < attributes.length; i++)
            attributes[i].write(out, constantPool);
    }
}
