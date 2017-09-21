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

import static com.nur1popcorn.basm.utils.Opcodes.CONSTANT_INVOKEDYNAMIC;

/**
 * The {@link ConstantInvokedynamic} TODO: describe too.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.10">
 *     CONSTANT_Invokedynamic 4.4.10
 * </a>
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantNameAndType
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class ConstantInvokedynamic extends ConstantInfo {
    private int bootstrapMethodAttrIndex /* u2 */,
                nameAndTypeIndex /* u2 */;

    /**
     * TODO: describe param bootstrapMethodAttrIndex
     * @param nameAndTypeIndex a index into the {@link ConstantPool} representing a CONSTANT_NameAndType.
     */
    public ConstantInvokedynamic(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(CONSTANT_INVOKEDYNAMIC);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(bootstrapMethodAttrIndex);
        os.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
                    bootstrapMethodAttrIndex +
                    "," +
                    nameAndTypeIndex +
                "]";
    }

    /*
     * TODO: index bootstrapMethodAttr
     */

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced method and descriptor inside of the {@link ConstantPool}.
     */
    public final ConstantNameAndType indexNameAndType(ConstantPool constantPool) {
        return (ConstantNameAndType) constantPool.getEntry(nameAndTypeIndex);
    }
}
