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
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;
import com.nur1popcorn.basm.classfile.constants.ConstantPoolPointer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

public final class FieldMethodInfo extends AccessFlags implements ConstantPoolPointer, AttributeVisitor {
    /*
     *
     */
    private int nameIndex,
                descIndex;
    /*
     *
     */
    private final List<AttributeInfo> attributes = new ArrayList<>();

    /**
     * @param access
     * @param nameIndex
     * @param descIndex
     */
    public FieldMethodInfo(int access, int nameIndex, int descIndex) {
        super(access);
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
    }

    /**
     * @param os
     *
     * @throws IOException
     */
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        os.writeShort(getAccessFlags());
        os.writeShort(nameIndex);
        os.writeShort(descIndex);

        os.writeShort(attributes.size());
        for(AttributeInfo attributeInfo : attributes)
            attributeInfo.write(os, constantPool);
    }

    @Override
    public void visit(AttributeSourceFile attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeBootstrapMethods attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeStackMapTable attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeConstantValue attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeCode attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeLineNumberTable attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeDeprecated attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeUnknown attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeSynthetic attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeMethodParameters attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeLocalVariableTable attribute) {
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeLocalVariableTypeTable attribute) {
        attributes.add(attribute);
    }

    /**
     * @return
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * @return
     */
    public int getDescIndex() {
        return descIndex;
    }

    /**
     * Sets the constant pool index of the name for the {@link FieldMethodInfo}.
     * @param index The index.
     */
    public void setNameIndex(int index) {
        nameIndex = index;
    }

    /**
     * Sets the constant pool index of the desc for the {@link FieldMethodInfo}.
     * @param index The index.
     */
    public void setDescIndex(int index) {
        descIndex = index;
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
}
