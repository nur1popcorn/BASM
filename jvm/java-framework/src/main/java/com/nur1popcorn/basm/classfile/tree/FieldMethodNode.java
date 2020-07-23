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

import com.nur1popcorn.basm.classfile.AccessFlags;
import com.nur1popcorn.basm.classfile.ClassVisitor;
import com.nur1popcorn.basm.classfile.attributes.*;

import java.util.ArrayList;
import java.util.List;

public abstract class FieldMethodNode extends AccessFlags implements EmptyAttributeVisitor {
    private String name,
                   desc;
    protected final List<AttributeInfo> attributes = new ArrayList<>();
    protected ConstantPoolGenerator constantPool;

    public FieldMethodNode(int access, String name, String desc, ConstantPoolGenerator constantPool) {
        super(access);
        this.name = name;
        this.desc = desc;
        this.constantPool = constantPool;
    }

    public abstract void accept(ClassVisitor visitor);

    @Override
    public void visit(AttributeDeprecated attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeUnknown attribute) {
        attributes.add(attribute);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getDesc() {
        return desc;
    }
}