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
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantName;
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

    private int minorVersion,
                majorVersion;

    public String thisClass,
                  superClass;

    private List<String> interfaces = new ArrayList<>();

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
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int interfaces[]) {
        this.access = access;
        this.thisClass = ((ConstantName)constantPool.getEntry(thisClass))
                .indexName(constantPool).bytes;
        this.superClass = ((ConstantName)constantPool.getEntry(superClass))
                .indexName(constantPool).bytes;
        this.interfaces = new ArrayList<>(interfaces.length);
        for (int anInterface : interfaces)
            this.interfaces.add(
                ((ConstantName)constantPool.getEntry(anInterface))
                    .indexName(constantPool).bytes
            );
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
        final List<ConstantInfo> constantInfos = new ArrayList<>();

        constantInfos.add(null);

        constantInfos.add(new ConstantUtf8(this.thisClass)); //TODO: wrong type.
        constantInfos.add(new ConstantUtf8(this.superClass));

        final int interfaces[] = new int[this.interfaces.size()];
        for(int i = 0; i < interfaces.length; i++) {
            interfaces[i] = constantInfos.size();
            constantInfos.add(new ConstantUtf8(this.interfaces.get(i)));
        }

        final FieldMethodInfo fields[] = new FieldMethodInfo[fieldNodes.size()];
        for(int i = 0; i < fields.length; i++) {
            final FieldNode fieldNode = fieldNodes.get(i);
            int size = constantInfos.size();
            fields[i] = new FieldMethodInfo(
                fieldNode.access,
                size++,
                size,
                null
            );
        }

        visitor.visitHead(
            minorVersion,
            majorVersion,
            new ConstantPool((ConstantInfo[]) constantInfos.toArray())
        );

        visitor.visitBody(
            access,
            1, // skip 1st entry.
            2,
            interfaces
        );

        visitor.visitFields(
            null
        );

        visitor.visitMethods(
            null
        );
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
