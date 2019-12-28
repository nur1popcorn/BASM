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
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.IClassVisitor;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

public abstract class FieldMethodNode extends AccessFlags implements IFieldMethodNodeVisitor {
    protected ConstantPoolGenerator constantPool;

    private String name,
                   desc;

    @Override
    public void visit(int access, int nameIndex, int descIndex, ConstantPool constantPool) {
        setAccessFlags(access);
        name = ((ConstantUTF8)constantPool.getEntry(nameIndex, CONSTANT_UTF8))
            .bytes;
        desc = ((ConstantUTF8)constantPool.getEntry(descIndex, CONSTANT_UTF8))
            .bytes;
        this.constantPool = new ConstantPoolGenerator(constantPool.getEntries());
    }

    public void accept(IClassVisitor visitor) {
        visitor.visitMethod(
            getAccessFlags(),
            constantPool.findUTF8(name),
            constantPool.findUTF8(desc),
            constantPool
        );
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
