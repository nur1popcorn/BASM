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

import com.nur1popcorn.basm.classfile.constants.ConstantInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Opcodes.CONSTANT_DOUBLE;
import static com.nur1popcorn.basm.utils.Opcodes.CONSTANT_LONG;

/**
 * The {@link ConstantPool} stores all kind of constants embedded into the classfile.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     ConstantPool 4.4.
 * </a>
 *
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class ConstantPool {
    private ConstantInfo cpEntries[];

    /**
     * @param cpEntries the {@link ConstantInfo}s from which the {@link ConstantPool}
     *                  is supposed to be constructed.
     */
    public ConstantPool(ConstantInfo[] cpEntries) {
        this.cpEntries = cpEntries;
    }

    /**
     * @param in the {@link DataInputStream} from which the {@link ConstantPool} is
     *           supposed to be read.
     */
    public ConstantPool(DataInputStream in) throws IOException {
        final int cpSize = in.readUnsignedShort();
        cpEntries = new ConstantInfo[cpSize];
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++) {
            final ConstantInfo constantInfo = ConstantInfo.read(in);
            cpEntries[i] = constantInfo;
            //longs and doubles take up 2 spaces in the constant pool.
            final int tag = constantInfo.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
    }

    /**
     * @return the cp entry at given index.
     */
    public final ConstantInfo getEntry(int index) {
       return cpEntries[index];
    }

    /**
     * @return the cp's size.
     */
    public final int getSize() {
        return cpEntries.length;
    }

    /**
     * Writes the {@link ConstantPool} to the given {@link DataOutputStream}.
     *
     * @param os the {@link DataOutputStream} the {@link ConstantPool} should be written to.
     */
    public final void write(DataOutputStream os) throws IOException {
        os.writeShort(cpEntries.length);
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpEntries.length; i++) {
            final ConstantInfo constantInfo = cpEntries[i];
            constantInfo.write(os);
            //longs and doubles take up 2 spaces in the constant pool.
            final int tag = constantInfo.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
        os.flush();
    }
}
