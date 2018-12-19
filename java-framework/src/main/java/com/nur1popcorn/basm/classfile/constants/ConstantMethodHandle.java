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
 * The {@link ConstantMethodHandle} describes a CONSTANT_Fieldref's, CONSTANT_Methodref's or
 * CONSTANT_InterfaceMethodref's bytecode behavior.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.8">
 *     CONSTANT_MethodHandle 4.4.2
 * </a>
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantMethodRef
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantMethodHandle extends ConstantInfo implements IConstantPoolPointer {
    /*
     *
     */
    private final byte refKind /* u1 */;

    /*
     *
     */
    private int refIndex /* u2 */;

    /**
     * @param refKind the type of type of reference referenced by 'refIndex', valid types are listed here:
     *                <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-5.html#jvms-5.4.3.5-220">
     *                    Table 5.4.3.5-A
     *                </a>
     * @param refIndex a index into the {@link ConstantPool} referencing a CONSTANT_Methodref.
     */
    public ConstantMethodHandle(byte refKind, int refIndex) {
        super(CONSTANT_METHOD_HANDLE);
        this.refKind = refKind;
        this.refIndex = refIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(refIndex);
        os.writeShort(refIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IConstantVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitMethodHandle(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(ConstantPool constantPool) {
        indexRef(constantPool)
            .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(ConstantPool constantPool) {
        indexRef(constantPool)
            .removePointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        refIndex = newIndex;
    }

    /**
     * @return the type of reference and its behavior in bytecode.
     */
    public byte getRefKind() {
        return refKind;
    }

    /**
     * @return
     */
    public int getRefIndex() {
        return refIndex;
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced CONSTANT_Fieldref, CONSTANT_Methodref or CONSTANT_InterfaceMethodref
     *         inside of the {@link ConstantPool}.
     */
    public ConstantMethodRef indexRef(ConstantPool constantPool) {
        final byte actual = constantPool.getEntry(refIndex).getTag();
        // allow these tag types also:
        byte expected = CONSTANT_METHOD_REF;
        switch(actual) {
            case CONSTANT_FIELD_REF:
            case CONSTANT_INTERFACE_METHOD_REF:
                expected = actual;
        }
        return constantPool.getEntry(refIndex, expected);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantMethodHandle) {
            final ConstantMethodHandle constantMethodHandle = (ConstantMethodHandle) other;
            return constantMethodHandle.refKind == refKind &&
                   constantMethodHandle.refIndex == refIndex;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return refKind * refIndex * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
            REF_NAMES[refKind] +
            "," +
            refIndex +
        "]";
    }
}
