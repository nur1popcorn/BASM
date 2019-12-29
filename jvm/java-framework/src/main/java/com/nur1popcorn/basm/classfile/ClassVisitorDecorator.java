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
import com.nur1popcorn.basm.classfile.tree.fields.IFieldNodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.IMethodNodeVisitor;

/**
 * @author Ben Kinney
 * @since 1.0.0-alpha
 */
public abstract class ClassVisitorDecorator implements IClassVisitor {

    private IClassVisitor parent;

    /**
     * @param parent Parent to the decorator.
     */
    public ClassVisitorDecorator(IClassVisitor parent) {
        this.parent = parent;
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        parent.visitHead(minorVersion, majorVersion, constantPool);
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int[] interfaces) {
        parent.visitBody(access, thisClass, superClass, interfaces);
    }

    @Override
    public IFieldNodeVisitor visitField(int access, int nameIndex, int descIndex, AttributeInfo[] attributes) {
        return parent.visitField(access, nameIndex, descIndex, attributes);
    }

    @Override
    public IMethodNodeVisitor visitMethod(int access, int nameIndex, int descIndex, AttributeInfo[] attributes) {
        return parent.visitMethod(access, nameIndex, descIndex, attributes);
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) {
        parent.visitFooter(attributes);
    }
}
