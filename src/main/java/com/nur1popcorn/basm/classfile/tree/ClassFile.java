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
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.*;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNode;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNodeParseException;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNode;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNodeParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.*;
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
    public void visitFields(FieldMethodInfo[] fields) throws FieldNodeParseException {
        for(FieldMethodInfo fieldInfo : fields)
            fieldNodes.add(new FieldNode(fieldInfo, constantPool));
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) throws MethodNodeParseException {
        for(FieldMethodInfo methodInfo : methods)
            methodNodes.add(new MethodNode(methodInfo, constantPool));
    }

    public void accept(IClassVisitor visitor) throws IOException {
        final List<ConstantInfo> constantInfos = new ArrayList<>();

        constantInfos.add(null);

        constantInfos.add(new ConstantUtf8(this.thisClass));
        constantInfos.add(new ConstantName(CONSTANT_CLASS, 1));
        constantInfos.add(new ConstantUtf8(this.superClass));
        constantInfos.add(new ConstantName(CONSTANT_CLASS, 3));

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
                new AttributeInfo[0] //TODO: remove
            );
            constantInfos.add(new ConstantUtf8(fieldNode.name));
            constantInfos.add(new ConstantUtf8(fieldNode.desc));
        }

        //TODO: remove
        final FieldMethodInfo methods[] = new FieldMethodInfo[1]; // TODO: impl
        int size = constantInfos.size();
        AttributeInfo attributeInfos[] = new AttributeInfo[] {
            new AttributeCode(size++, 1, 1,
            new byte[] {
                ALOAD_0,
                (byte) 0xb7, 0, (byte) size++, // invokespecial
                (byte) 0xb1 // return
            },
            new AttributeCode.ExceptionTableEntry[0],
            new AttributeInfo[0])
        };


        constantInfos.add(new ConstantUtf8("Code"));
        constantInfos.add(new ConstantMethodRef(CONSTANT_METHOD_REF, size++, size++));

        constantInfos.add(new ConstantName(CONSTANT_CLASS, 3));
        constantInfos.add(new ConstantNameAndType(size++, size++));

        constantInfos.add(new ConstantUtf8("<init>"));
        constantInfos.add(new ConstantUtf8("()V"));

        methods[0] = new FieldMethodInfo(ACC_PUBLIC, size++, size++, attributeInfos);
        constantInfos.add(new ConstantUtf8("<init>"));
        constantInfos.add(new ConstantUtf8("()V"));

        visitor.visitHead(
            minorVersion,
            majorVersion,
            new ConstantPool(constantInfos.toArray(new ConstantInfo[constantInfos.size()]))
        );

        visitor.visitBody(
            access,
            2,
            4,
            interfaces
        );

        visitor.visitFields(
            fields
        );

        visitor.visitMethods(
            methods
        );
    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
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
