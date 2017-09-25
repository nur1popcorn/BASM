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

/**
 * The {@link ConstantMethodRef} stores references to fields, methods and interface methods and
 * their owner class or interface.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.2">
 *     CONSTANT_Methodref 4.4.2
 * </a>
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantName
 * @see ConstantNameAndType
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantMethodRef extends ConstantInfo {
    private int classIndex /* u2 */,
                nameAndTypeIndex /* u2 */;

    /**
     * @param tag a identifier either of type CONSTANT_Fieldref, CONSTANT_Methodref or
     *            CONSTANT_InterfaceMethodref.
     * @param classIndex a index into the {@link ConstantPool} referencing a CONSTANT_Class.
     * @param nameAndTypeIndex a index into the {@link ConstantPool} referencing a
     *                         CONSTANT_NameAndType.
     */
    public ConstantMethodRef(byte tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(classIndex);
        os.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
                    classIndex +
                    "," +
                    nameAndTypeIndex +
                "]";
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced CONSTANT_Class inside of the {@link ConstantPool} referencing the CONSTANT_Fieldref',
     *         CONSTANT_Methodref' or CONSTANT_InterfaceMethodref's class or interface.
     */
    public ConstantName indexClass(ConstantPool constantPool) {
        return (ConstantName) constantPool.getEntry(classIndex);
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced CONSTANT_NameAndType inside of the {@link ConstantPool}, containing the
     *         CONSTANT_Fieldref', CONSTANT_Methodref' or CONSTANT_InterfaceMethodref's name and descriptor.
     */
    public ConstantNameAndType indexNameAndType(ConstantPool constantPool) {
        return (ConstantNameAndType) constantPool.getEntry(nameAndTypeIndex);
    }
}
