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

import com.nur1popcorn.basm.classfile.ClassReader;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.IClassVisitor;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNode;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.classfile.ClassReader.*;

/**
 * The {@link ClassFile} provides an abstraction layer between bytecode and user.
 *
 * @see IClassVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile implements IClassVisitor {

    private ConstantPool constantPool;

    public int access;
    public String thisClass,
                  superClass,
                  interfaces[];

    private List<FieldNode> fieldNodes = new ArrayList<>();
    private List<MethodNode> methodNodes = new ArrayList<>();

    // prevent construction :/
    private ClassFile()
    {}

    public ClassFile(DataInputStream din) throws IOException {
        new ClassReader(din)
                .accept(this, READ_HEAD |
                              READ_BODY |
                              READ_METHODS |
                              READ_FIELDS);
    }

    public static ClassFileBuilder builder() {
        return new ClassFileBuilder();
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, ConstantUtf8 thisClass, ConstantUtf8 superClass, ConstantUtf8[] interfaces) {
        this.access = access;
        this.thisClass = thisClass.bytes;
        this.superClass = superClass.bytes;
        this.interfaces = new String[interfaces.length];
        for(int i = 0; i < interfaces.length; i++)
            this.interfaces[i] = interfaces[i].bytes;
    }

    @Override
    public void visitFields(FieldMethodInfo[] fields) {
        for(FieldMethodInfo fieldInfo : fields)
            fieldNodes.add(new FieldNode(fieldInfo, constantPool));
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) {
        //for(FieldMethodInfo methodInfo : methods)
        //    methodNodes.add(new MethodNode(methodInfo, constantPool));
    }

    public void accept(IClassVisitor visitor) {
        visitor.visitBody(access, null, null, null);
    }

    /**
     * @see #builder()
     */
    public static final class ClassFileBuilder {
        private ClassFile classFile;

        // prevent construction :/
        private ClassFileBuilder() {
            classFile = new ClassFile();
        }

        public ClassFileBuilder setAccess(int access) {
            classFile.access = access;
            return this;
        }
    }
}
