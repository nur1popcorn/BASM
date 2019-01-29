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
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;
import com.nur1popcorn.basm.classfile.constants.IConstantPoolPointer;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

public abstract class FieldMethodNode extends AccessFlags implements IConstantPoolPointer {
    protected final ConstantPool constantPool;

    /*
     *
     */
    protected int nameIndex,
                  descIndex;
    /**
     * @param access
     * @param nameIndex
     * @param descIndex
     * @param constantPool
     */
    protected FieldMethodNode(int access, int nameIndex, int descIndex, ConstantPool constantPool) {
        super(access);
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
        this.constantPool = constantPool;
    }

    /**
     * @param info
     * @param constantPool
     */
    protected FieldMethodNode(FieldMethodInfo info, ConstantPool constantPool) {
        this(info.getAccessFlags(),
             info.getNameIndex(),
             info.getDescIndex(),
             constantPool);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(ConstantPool constantPool) {
        indexName(constantPool)
            .addPointer(this);
        indexDesc(constantPool)
            .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(ConstantPool constantPool) {
        indexName(constantPool)
            .removePointer(this);
        indexDesc(constantPool)
            .removePointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        if(oldIndex == nameIndex)
            nameIndex = newIndex;
        else //if(oldIndex == descIndex)
            descIndex = newIndex;
    }

    public abstract void accept(IFieldMethodNodeVisitor visitor);

    /**
     * @param constantPool
     *
     * @return
     */
    private ConstantUTF8 indexName(ConstantPool constantPool) {
        return constantPool.getEntry(nameIndex, CONSTANT_UTF8);
    }


    /**
     * @param constantPool
     *
     * @return
     */
    private ConstantUTF8 indexDesc(ConstantPool constantPool) {
        return constantPool.getEntry(nameIndex, CONSTANT_UTF8);
    }

    /**
     * @return
     */
    public String getName() {
        return indexName(constantPool)
            .bytes;
    }

    /**
     * @return
     */
    public Type getDesc() {
        return Type.getType(
            indexDesc(constantPool)
                .bytes
        );
    }
}
