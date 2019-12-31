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
import com.nur1popcorn.basm.classfile.IClassVisitor;
import com.nur1popcorn.basm.classfile.attributes.AttributeDeprecated;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.attributes.IAttributeVisitor;

public abstract class FieldMethodNode extends AccessFlags implements IAttributeVisitor {
    private String name,
                   desc;
    protected AttributeInfo attributes[];
    protected ConstantPoolGenerator constantPool;

    public FieldMethodNode(int access, String name, String desc, AttributeInfo attributes[], ConstantPoolGenerator constantPool) {
        super(access);
        this.name = name;
        this.desc = desc;
        this.attributes = attributes;
        this.constantPool = constantPool;
    }

    public abstract void accept(IClassVisitor visitor);

    @Override
    public void visit(AttributeDeprecated attribute) {

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
