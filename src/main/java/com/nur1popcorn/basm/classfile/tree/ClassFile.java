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
import com.nur1popcorn.basm.classfile.IClassReaderVisitor;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.nur1popcorn.basm.classfile.ClassReader.*;
import static com.nur1popcorn.basm.utils.Constants.*;

/**
 * The {@link ClassFile} TODO: desc
 *
 * @see IClassReaderVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile implements IClassReaderVisitor {

    public int access;

    private ConstantPool constantPool;

    // prevent construction :/
    private ClassFile()
    {}

    public ClassFile(InputStream in) throws IOException {
        new ClassReader(new DataInputStream(in)).accept(this, READ_HEAD |
                                                              READ_BODY |
                                                              READ_METHODS |
                                                              READ_FIELDS);
    }

    public static ClassFileBuilder builder() {
        return new ClassFileBuilder();
    }

    @Override
    public void visitHead(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, ConstantUtf8 thisClass, ConstantUtf8 superClass, ConstantUtf8[] interfaces) {
        this.access = access;
    }

    @Override
    public void visitFields(FieldMethodInfo[] fields) {
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) {
    }

    public void accept(IClassFileVisitor visitor) {

    }

    /**
     * @see #builder()
     */
    public static class ClassFileBuilder {
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
