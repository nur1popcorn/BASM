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

import static com.nur1popcorn.basm.Constants.CONSTANT_INTEGER;

/**
 * The {@link ConstantInteger} is made up of 4 bytes of data either belonging to an integer or float.
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
    private int bytes /* u4 */;

    /**
     * @param tag a identifier either of type CONSTANT_Integer or CONSTANT_Float.
     * @param bytes 4 bytes of data either belonging to a int or float.
     */
    public ConstantInteger(byte tag, int bytes) {
        super(tag);
        this.bytes = bytes;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeInt(bytes);
    }

    @Override
    public String toString() {
        return super.toString() + "[" + (
                    getTag() == CONSTANT_INTEGER ?
                        bytes :
                        Float.intBitsToFloat(bytes)
                ) + "]";
    }

    @Override
    public int hashCode() {
        return bytes * 33 ^ getTag();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ConstantInteger &&
               ((ConstantInteger) other).bytes == bytes;
    }

    /**
     * @param bytes the value which the the integer will be set to.
     */
    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    /**
     * @param bytes the value which the the float will be set to.
     */
    public void setBytes(float bytes) {
        this.bytes = Float.floatToIntBits(bytes);
    }

    /**
     * @return the value of bytes as an integer.
     */
    public int asInteger() {
        return bytes;
    }

    /**
     * @return the value of bytes as a float.
     */
    public float asFloat() {
        return Float.intBitsToFloat(bytes);
    }
}
