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

import com.nur1popcorn.basm.classfile.attributes.*;

/**
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public abstract class AttributeVisitorDecorator implements AttributeVisitor {
    private AttributeVisitor parent;

    public AttributeVisitorDecorator(AttributeVisitor parent) {
        this.parent = parent;
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
    public void visit(AttributeConstantValue attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeCode attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeLineNumberTable attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeBootstrapMethods attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeStackMapTable attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeUnknown attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeSynthetic attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeMethodParameters attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeLocalVariableTable attribute) {
        parent.visit(attribute);
    }

    @Override
    public void visit(AttributeLocalVariableTypeTable attribute) {
        parent.visit(attribute);
    }
}
