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

import com.nur1popcorn.basm.classfile.attributes.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.MAGIC;

/**
 * The {@link ClassWriter} is a {@link ClassVisitor} which writes the visited class file to the
 * given {@link DataOutputStream}.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassWriter implements ClassVisitor {
    private int minorVersion, majorVersion;
    private ConstantPool constantPool;

    private int access, thisClass, superClass;

    private List<Integer> interfaces = new ArrayList<>();

    private final List<FieldMethodInfo> fields = new ArrayList<>();
    private final List<FieldMethodInfo> methods = new ArrayList<>();

    private final List<AttributeInfo> attributes = new ArrayList<>();

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass) {
        this.access = access;
        this.thisClass = thisClass;
        this.superClass = superClass;
    }

    @Override
    public void visitInterface(int index) {
        interfaces.add(index);
    }

    @Override
    public AttributeVisitor visitField(FieldMethodInfo field) {
        fields.add(field);
        return field;
    }

    @Override
    public AttributeVisitor visitMethod(FieldMethodInfo method) {
        methods.add(method);
        return method;
    }

    @Override
    public void visit(AttributeSourceFile attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeDeprecated attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeUnknown attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeConstantValue attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeCode attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeLineNumberTable attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeBootstrapMethods attribute) {
        attributes.add(attribute);
    }

    public void write(OutputStream out) throws IOException {
        write(new DataOutputStream(out));
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

        out.writeShort(interfaces.size());
        for(int index : interfaces)
            out.writeShort(index);

        out.writeShort(fields.size());
        for(FieldMethodInfo field : fields)
            field.write(out, constantPool);

        out.writeShort(methods.size());
        for(FieldMethodInfo method : methods)
            method.write(out, constantPool);

        out.writeShort(attributes.size());
        for(AttributeInfo attribute : attributes)
            attribute.write(out, constantPool);
    }
}
