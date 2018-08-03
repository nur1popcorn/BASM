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

import static com.nur1popcorn.basm.Constants.CONSTANT_DOUBLE;
import static com.nur1popcorn.basm.Constants.CONSTANT_LONG;

/**
 * The {@link ConstantLong} class is derived from the abstract {@link ConstantInfo} class and
 * mainly consists of 8 bytes of data either belonging to a 'CONSTANT_Long' or 'CONSTANT_Double'.
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
    /* These 8 bytes of data either belonging to a 'CONSTANT_Long' or 'CONSTANT_Double', represent the
     * constant value of an long or double primitive data type.
     */
    private long bytes /* 2x u4 */;

    /**
     * @param bytes 8 bytes of data either belonging to a long or double.
     */
    public ConstantLong(long bytes) {
        super(CONSTANT_LONG);
        this.bytes = bytes;
    }

    /**
     * @param bytes 8 bytes of data either belonging to a long or double.
     */
    public ConstantLong(double bytes) {
        super(CONSTANT_DOUBLE);
        this.bytes = Double.doubleToLongBits(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeLong(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IConstantVisitor visitor) {
        switch(getTag()) {
            case CONSTANT_LONG: visitor.visitLong(this); break;
            case CONSTANT_DOUBLE: visitor.visitDouble(this); break;
        }
    }

    /**
     * @return
     */
    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    /**
     * @return
     */
    public void setBytes(double bytes) {
        this.bytes = Double.doubleToLongBits(bytes);
    }

    /**
     * @return
     */
    public long asLong() {
        return bytes;
    }

    /**
     * @return
     */
    public double asDouble() {
        return Double.longBitsToDouble(bytes);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantLong) {
            final ConstantLong constantLong = (ConstantLong) other;
            return constantLong.getTag() == getTag() &&
                   constantLong.bytes == bytes;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)(bytes * 33 ^ getTag());
    }

    @Override
    public String toString() {
        return super.toString() + "[" + (
                getTag() == CONSTANT_LONG ?
                        bytes :
                        Double.longBitsToDouble(bytes)
        ) + "]";
    }
}
