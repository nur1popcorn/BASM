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

import static com.nur1popcorn.basm.Constants.*;

/**
 * The {@link ConstantName} represents all types of {@link ConstantInfo}s that reference a {@link ConstantUTF8}.
 * [
 *     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.1">
 *         CONSTANT_Class 4.4.1
 *     </a>
 *     ,
 *     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.3">
 *         CONSTANT_String 4.4.3
 *     </a>
 *     ,
 *     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.9">
 *         CONSTANT_MethodType 4.4.9
 *     </a>
 * ]
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantUTF8
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantName extends ConstantInfo implements ConstantPoolPointer {
    /*
     *
     */
    private int nameIndex /* u2 */;

    /**
     * @param tag a valid tag for a {@link ConstantName} including
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.1">
     *                CONSTANT_Class 4.4.1
     *            </a>
     *            ,
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.3">
     *                CONSTANT_String 4.4.3
     *            </a>
     *            ,
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.9">
     *                CONSTANT_MethodType 4.4.9
     *            </a>
     * @param nameIndex a valid index into the {@link ConstantPool} referencing
     *                  a {@link ConstantUTF8}.
     */
    public ConstantName(byte tag, int nameIndex) {
        super(tag);
        this.nameIndex = nameIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(nameIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(ConstantVisitor visitor) {
        visitor.visitCPPointer(this);
        switch(getTag()) {
            case CONSTANT_CLASS: visitor.visitClass(this); break;
            case CONSTANT_STRING: visitor.visitString(this); break;
            case CONSTANT_METHOD_TYPE: visitor.visitMethodType(this); break;
            case CONSTANT_MODULE: visitor.visitModule(this); break;
            case CONSTANT_PACKAGE: visitor.visitPackage(this); break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(ConstantPool constantPool) {
        indexName(constantPool)
            .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(ConstantPool constantPool) {
        indexName(constantPool)
            .removePointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        this.nameIndex = newIndex;
    }

    /**
     * @return
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * Set the index in the constant pool to the utf8 structure that represents the name.
     * @param nameIndex The index.
     */
    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced CONSTANT_Utf8 inside of the {@link ConstantPool}.
     */
    public ConstantUTF8 indexName(ConstantPool constantPool) {
        return constantPool.getEntry(nameIndex, CONSTANT_UTF8);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantName) {
            final ConstantName constantName = (ConstantName) other;
            return constantName.getTag() == getTag() &&
                   constantName.nameIndex == nameIndex;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nameIndex * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
                nameIndex +
                "]";
    }
}
