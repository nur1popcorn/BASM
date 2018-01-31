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
import com.nur1popcorn.basm.classfile.IClassReaderVisitor;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.ClassReader.*;

/**
 * The {@link ClassFile} TODO: desc
 *
 * @see IClassReaderVisitor
 *
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile implements IClassReaderVisitor {

    public int access;

    public ClassFile(DataInputStream in) throws IOException {
        new ClassReader(in).accept(this, READ_HEAD |
                                         READ_BODY |
                                         READ_METHODS |
                                         READ_FIELDS);
    }

    @Override
    public void visit(int access, ConstantUtf8 thisClass, ConstantUtf8 superClass, ConstantUtf8[] interfaces) {
        this.access = access;
        //TODO: ..
    }
}
