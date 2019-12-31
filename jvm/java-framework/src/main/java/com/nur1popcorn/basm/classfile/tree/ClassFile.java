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
import com.nur1popcorn.basm.classfile.attributes.AttributeDeprecated;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.attributes.AttributeSourceFile;
import com.nur1popcorn.basm.classfile.attributes.IAttributeVisitor;
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
 * @see IClassVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile extends AccessFlags implements IClassVisitor, IClassVersionProvider, IAttributeVisitor {
    private ConstantPoolGenerator constantPool;

    private int minorVersion,
                majorVersion;

    private String thisClass;
    private String superClass;

    private List<String> interfaces = new ArrayList<>();

    private List<FieldNode> fieldNodes = new ArrayList<>();
    private List<MethodNode> methodNodes = new ArrayList<>();

    private AttributeInfo[] attributes;

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
    public void visitBody(int access, int thisClass, int superClass, int interfaces[]) {
        setAccessFlags(access);
        this.thisClass = ((ConstantName) constantPool.getEntry(thisClass))
            .indexName(constantPool)
            .bytes;
        this.superClass = superClass == 0 ?
            null :
            ((ConstantName) constantPool.getEntry(superClass, CONSTANT_CLASS))
                .indexName(constantPool)
                .bytes;

        this.interfaces = new ArrayList<>(interfaces.length);
        for(int index : interfaces)
            this.interfaces.add(
                ((ConstantName) constantPool.getEntry(index))
                    .indexName(constantPool)
                    .bytes
            );
    }

    @Override
    public void visitField(int access, int nameIndex, int descIndex, AttributeInfo attributes[]) {
        final FieldNode fieldNode = new FieldNode(
            access,
            ((ConstantUTF8)constantPool.getEntry(nameIndex, CONSTANT_UTF8))
                .bytes,
            ((ConstantUTF8)constantPool.getEntry(descIndex, CONSTANT_UTF8))
                .bytes,
            attributes, constantPool);
        for(AttributeInfo attribute : attributes)
            attribute.accept(fieldNode);
        fieldNodes.add(fieldNode);
    }

    @Override
    public void visitMethod(int access, int nameIndex, int descIndex, AttributeInfo attributes[]) {
        final MethodNode methodNode = new MethodNode(
            access,
            ((ConstantUTF8)constantPool.getEntry(nameIndex, CONSTANT_UTF8))
                .bytes,
            ((ConstantUTF8)constantPool.getEntry(descIndex, CONSTANT_UTF8))
                .bytes,
            attributes, constantPool);
        for(AttributeInfo attribute : attributes)
            attribute.accept(methodNode);
        methodNodes.add(methodNode);
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) {
        this.attributes = attributes;
        for(AttributeInfo attribute : attributes)
            attribute.accept(this);
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
    public void visit(AttributeSourceFile attribute) {

    }

    @Override
    public void visit(AttributeDeprecated attribute) {

    }

    public void accept(IClassVisitor visitor) {
        visitor.visitHead(minorVersion, majorVersion, constantPool);

        final int interfaces[] = new int[this.interfaces.size()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = constantPool.findClass(this.interfaces.get(i));
        visitor.visitBody(getAccessFlags(),
            constantPool.findClass(thisClass),
            constantPool.findClass(superClass),
            interfaces);

        for(FieldNode fieldNode : fieldNodes)
            fieldNode.accept(visitor);
        for(MethodNode methodNode : methodNodes)
            methodNode.accept(visitor);

        visitor.visitFooter(attributes);
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
