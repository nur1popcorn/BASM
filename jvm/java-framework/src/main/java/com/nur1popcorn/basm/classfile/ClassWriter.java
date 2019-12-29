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
import com.nur1popcorn.basm.classfile.tree.fields.FieldWriter;
import com.nur1popcorn.basm.classfile.tree.fields.IFieldNodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.IMethodNodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.MethodWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.MAGIC;

/**
 * The {@link ClassWriter} is a {@link IClassVisitor} which writes the visited class file to the
 * given {@link DataOutputStream}.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassWriter implements IClassVisitor {
    private int minorVersion, majorVersion;
    private ConstantPool constantPool;

    private int access, thisClass, superClass;
    private int[] interfaces;

    private final List<FieldWriter> fields = new ArrayList<>();
    private final List<MethodWriter> methods = new ArrayList<>();

    private AttributeInfo[] attributes;

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int[] interfaces) {
        this.access = access;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    @Override
    public IFieldNodeVisitor visitField(int access,
                                        int nameIndex,
                                        int descIndex,
                                        AttributeInfo attributes[]) {
        final FieldWriter fieldWriter = new FieldWriter(access,
            nameIndex, descIndex, attributes, constantPool);
        fields.add(fieldWriter);
        return fieldWriter;
    }

    @Override
    public IMethodNodeVisitor visitMethod(int access,
                                          int nameIndex,
                                          int descIndex,
                                          AttributeInfo attributes[]) {
        final MethodWriter methodWriter = new MethodWriter(access,
            nameIndex, descIndex, attributes, constantPool);
        methods.add(methodWriter);
        return methodWriter;
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }

    //TODO: create flags
    public void write(DataOutputStream out) throws IOException {
        // write out file header 0xcafebabe.
        out.writeInt(MAGIC);

        out.writeShort(minorVersion);
        out.writeShort(majorVersion);
        constantPool.write(out);

        out.writeShort(access);

        out.writeShort(thisClass);
        out.writeShort(superClass);

        out.writeShort(interfaces.length);
        for(int index : interfaces)
            out.writeShort(index);

        out.writeShort(fields.size());
        for(FieldWriter field : fields)
            field.write(out);

        out.writeShort(methods.size());
        for(MethodWriter method : methods)
            method.write(out);

        out.writeShort(attributes.length);
        for(AttributeInfo attribute : attributes)
            attribute.write(out, constantPool);
    }
}
