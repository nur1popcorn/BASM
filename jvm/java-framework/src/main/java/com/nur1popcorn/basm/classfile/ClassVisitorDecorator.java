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

import com.nur1popcorn.basm.classfile.attributes.AttributeDeprecated;
import com.nur1popcorn.basm.classfile.attributes.AttributeSourceFile;
import com.nur1popcorn.basm.classfile.attributes.AttributeUnknown;
import com.nur1popcorn.basm.classfile.attributes.IAttributeVisitor;

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
    public void visitBody(int access, int thisClass, int superClass) {
        parent.visitBody(access, thisClass, superClass);
    }

    @Override
    public void visitInterface(int index) {
        parent.visitInterface(index);
    }

    @Override
    public IAttributeVisitor visitField(FieldMethodInfo field) {
        return parent.visitField(field);
    }

    @Override
    public IAttributeVisitor visitMethod(FieldMethodInfo method) {
        return parent.visitMethod(method);
    }

    @Override
    public void visit(AttributeSourceFile attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeDeprecated attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeUnknown attribute) {
        parent.visit(attribute);
    }
}
