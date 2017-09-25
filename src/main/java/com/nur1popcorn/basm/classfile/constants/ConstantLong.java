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

import static com.nur1popcorn.basm.utils.Opcodes.CONSTANT_LONG;

/**
 * The {@link ConstantLong} is made up of 8 bytes of data either belonging to a long or double.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.5">
 *     CONSTANT_Long 4.4.5
 * </a>
 *
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantLong extends ConstantInfo {
    private long bytes /* 2x u4 */;

    /**
     * @param tag a identifier either of type CONSTANT_Long or CONSTANT_Double.
     * @param bytes 8 bytes of data either belonging to a long or double.
     */
    public ConstantLong(byte tag, long bytes) {
        super(tag);
        this.bytes = bytes;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeLong(bytes);
    }

    @Override
    public String toString() {
        return super.toString() + "[" + (
                    getTag() == CONSTANT_LONG ?
                        Double.longBitsToDouble(bytes) :
                        bytes
               ) + "]";
    }

    /**
     * @param bytes the value which the the long will be set to.
     */
    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    /**
     * @param bytes the value which the the double will be set to.
     */
    public void setBytes(double bytes) {
        this.bytes = Double.doubleToLongBits(bytes);
    }

    /**
     * @return the value of bytes as a long.
     */
    public long asLong() {
        return bytes;
    }

    /**
     * @return the value of bytes as a double.
     */
    public double asDouble() {
        return Double.longBitsToDouble(bytes);
    }
}
