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

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_FLOAT;
import static com.nur1popcorn.basm.Constants.CONSTANT_INTEGER;

/**
 * The {@link ConstantInteger} class is derived from the abstract {@link ConstantInfo} class and
 * mainly consists of 4 bytes of data belonging to either a 'CONSTANT_Integer' or 'CONSTANT_Float'.
 * The implementation closely follows this part of the JVM ClassFile document:
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.4">
 *     CONSTANT_Integer 4.4.4
 * </a>
 *
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantInteger extends ConstantInfo {
    /* These 4 bytes of data either belonging to a 'CONSTANT_Integer' or 'CONSTANT_Float', represent the
     * constant value of an int or float primitive data type.
     */
    private int bytes /* u4 */;

    /**
     * @param bytes 4 bytes of data belonging to a 'CONSTANT_Integer', representing the constant value
     *              of an int primitive data type.
     */
    public ConstantInteger(int bytes) {
        super(CONSTANT_INTEGER);
        this.bytes = bytes;
    }

    /**
     * @param bytes 4 bytes of data belonging to a 'CONSTANT_Integer', representing the constant value
     *              of an int primitive data type.
     */
    public ConstantInteger(float bytes) {
        super(CONSTANT_FLOAT);
        this.bytes = Float.floatToIntBits(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeInt(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(ConstantVisitor visitor) {
        switch(getTag()) {
            case CONSTANT_INTEGER: visitor.visitInt(this); break;
            case CONSTANT_FLOAT: visitor.visitFloat(this); break;
        }
    }

    /**
     * @param bytes 4 bytes of data belonging to a 'CONSTANT_Integer', representing the constant value
     *              of an int primitive data type.
     */
    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    /**
     * @param bytes 4 bytes of data belonging to a 'CONSTANT_Float', representing the constant value
     *              of a float primitive data type.
     */
    public void setBytes(float bytes) {
        this.bytes = Float.floatToIntBits(bytes);
    }

    /**
     * @return 4 bytes of data belonging to a 'CONSTANT_Integer', representing the constant value
     *         of an int primitive data type.
     */
    public int asInteger() {
        return bytes;
    }

    /**
     * @return 4 bytes of data belonging to a 'CONSTANT_Float', representing the constant value
     *         of a float primitive data type.
     */
    public float asFloat() {
        return Float.intBitsToFloat(bytes);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantInteger) {
            final ConstantInteger constantInteger = (ConstantInteger) other;
            return constantInteger.getTag() == getTag() &&
                    constantInteger.bytes == bytes;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return bytes * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" + (
            getTag() == CONSTANT_INTEGER ?
                bytes :
                Float.intBitsToFloat(bytes)
        ) + "]";
    }
}
