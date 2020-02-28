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

package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.*;
import com.nur1popcorn.basm.classfile.attributes.*;
import com.nur1popcorn.basm.classfile.constants.ConstantName;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNode;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.CONSTANT_CLASS;
import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;
import static com.nur1popcorn.basm.classfile.ClassReader.READ_ALL;

/**
 * The {@link ClassFile} provides an abstraction layer between bytecode and user.
 *
 * @see ClassVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile extends AccessFlags implements ClassVisitor, ClassVersionProvider {
    private ConstantPoolGenerator constantPool;

    private int minorVersion,
                majorVersion;

    private String thisClass;
    private String superClass;

    private final List<String> interfaces = new ArrayList<>();

    private final List<FieldNode> fieldNodes = new ArrayList<>();
    private final List<MethodNode> methodNodes = new ArrayList<>();

    private final List<AttributeInfo> attributes = new ArrayList<>();

    public ClassFile(InputStream in) throws IOException {
        new ClassReader(in)
            .accept(
                this,
                READ_ALL
            );
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = new ConstantPoolGenerator(constantPool.getEntries());
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass) {
        setAccessFlags(access);
        this.thisClass = ((ConstantName) constantPool.getEntry(thisClass))
            .indexName(constantPool)
            .bytes;
        this.superClass = superClass == 0 ?
            null :
            ((ConstantName) constantPool.getEntry(superClass, CONSTANT_CLASS))
                .indexName(constantPool)
                .bytes;
    }

    @Override
    public void visitInterface(int index) {
        interfaces.add(
            ((ConstantName) constantPool.getEntry(index))
                .indexName(constantPool)
                .bytes
        );
    }

    @Override
    public AttributeVisitor visitField(FieldMethodInfo info) {
        final FieldNode fieldNode = new FieldNode(
            info.getAccessFlags(),
            ((ConstantUTF8)constantPool.getEntry(info.getNameIndex(), CONSTANT_UTF8))
                .bytes,
            ((ConstantUTF8)constantPool.getEntry(info.getDescIndex(), CONSTANT_UTF8))
                .bytes,
            constantPool);
        fieldNodes.add(fieldNode);
        return fieldNode;
    }

    @Override
    public AttributeVisitor visitMethod(FieldMethodInfo method) {
        final MethodNode methodNode = new MethodNode(
            method.getAccessFlags(),
            ((ConstantUTF8)constantPool.getEntry(method.getNameIndex(), CONSTANT_UTF8))
                .bytes,
            ((ConstantUTF8)constantPool.getEntry(method.getDescIndex(), CONSTANT_UTF8))
                .bytes,
            constantPool);
        methodNodes.add(methodNode);
        return methodNode;
    }

    @Override
    public void ensureMajorVersion(int majorVersion) {
        if(this.majorVersion < majorVersion)
            this.majorVersion = majorVersion;
    }

    @Override
    public void ensureMinorVersion(int minorVersion) {
        if(this.minorVersion < minorVersion)
            this.minorVersion = minorVersion;
    }

    @Override
    public void visit(AttributeSynthetic attribute) {
        attributes.add(attribute);
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

    public void accept(ClassVisitor visitor) {
        visitor.visitHead(minorVersion, majorVersion, constantPool);

        visitor.visitBody(getAccessFlags(),
            constantPool.findClass(thisClass),
            superClass == null ?
                0 : constantPool.findClass(superClass));

        for(String anInterface : interfaces)
            visitor.visitInterface(constantPool.findClass(anInterface));

        for(FieldNode fieldNode : fieldNodes)
            fieldNode.accept(visitor);
        for(MethodNode methodNode : methodNodes)
            methodNode.accept(visitor);

        for(AttributeInfo attribute : attributes)
            attribute.accept(visitor);
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }
}
