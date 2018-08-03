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

package com.nur1popcorn.basm.classfile.constants;

import com.nur1popcorn.basm.classfile.ConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_NAME_AND_TYPE;
import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

/**
 * The {@link ConstantNameAndType} is used to store references to either a field's or a method's
 * name and descriptor.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.6">
 *     CONSTANT_NameAndType 4.4.6
 * </a>
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantUTF8
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantNameAndType extends ConstantInfo implements IConstantPoolPointer {
    /*
     *
     */
    private int nameIndex /* u2 */,

    /*
     *
     */
                descIndex /* u2 */;

    /**
     * @param nameIndex a index into the {@link ConstantPool} representing a method's
     *                  or field's name.
     * @param descIndex a index into the {@link ConstantPool} representing the method's
     *                  or field's descriptor.
     */
    public ConstantNameAndType(int nameIndex, int descIndex) {
        super(CONSTANT_NAME_AND_TYPE);
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(nameIndex);
        os.writeShort(descIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IConstantVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitNameAndType(this);
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
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced field's or method's name inside of the {@link ConstantPool}.
     */
    public ConstantUTF8 indexName(ConstantPool constantPool) {
        return constantPool.getEntry(nameIndex, CONSTANT_UTF8);
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced field's or method's descriptor inside of the {@link ConstantPool}.
     */
    public ConstantUTF8 indexDesc(ConstantPool constantPool) {
        return constantPool.getEntry(descIndex, CONSTANT_UTF8);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof  ConstantNameAndType) {
            final ConstantNameAndType nameAndType = (ConstantNameAndType) other;
            return nameAndType.nameIndex == nameIndex &&
                   nameAndType.descIndex == descIndex;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nameIndex * descIndex * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
            nameIndex +
            "," +
            descIndex +
        "]";
    }
}
