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
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantName;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNode;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNode;
import com.nur1popcorn.basm.utils.ClassPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ClassFile} provides an abstraction layer between bytecode and user.
 *
 * @see IClassVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile implements IClassVisitor, IClassVersionProvider {

    private ClassPool classPool;

    private ConstantPoolGenerator constantPool;

    private int access;

    private int minorVersion,
                majorVersion;

    private String thisClass;
    private ClassFile superClass;

    private List<ClassFile> interfaces = new ArrayList<>();

    private List<FieldNode> fieldNodes = new ArrayList<>();
    private List<MethodNode> methodNodes = new ArrayList<>();

    public ClassFile(ClassPool classPool) {
        this.classPool = classPool;
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        // TODO: fix
        this.constantPool = new ConstantPoolGenerator(constantPool.getEntries());
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int interfaces[]) {
        this.access = access;
        this.thisClass = ((ConstantName)constantPool.getEntry(thisClass))
            .indexName(constantPool).bytes;
        if(superClass == 0) {
            if(!this.thisClass.equals("java/lang/Object"))
                throw new MalformedClassFileException("super class must be non-null");
            this.superClass = null;
        } else
            this.superClass = classPool.find(
                ((ConstantName)constantPool.getEntry(superClass))
                .indexName(constantPool).bytes
            );
        this.interfaces = new ArrayList<>(interfaces.length);
        for(int index : interfaces)
            this.interfaces.add(
                classPool.find(
                    ((ConstantName)constantPool.getEntry(index))
                    .indexName(constantPool).bytes
                )
            );
    }

    @Override
    public void visitFields(FieldMethodInfo[] fields) {
        for(FieldMethodInfo fieldInfo : fields)
            fieldNodes.add(new FieldNode(fieldInfo, constantPool));
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) throws IOException {
        for(FieldMethodInfo methodInfo : methods)
            methodNodes.add(new MethodNode(methodInfo, constantPool));
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) throws IOException {

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

    public void accept(IClassVisitor visitor) throws IOException {

    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }
}
