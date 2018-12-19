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

import static com.nur1popcorn.basm.Constants.CONSTANT_DYNAMIC;
import static com.nur1popcorn.basm.Constants.CONSTANT_INVOKEDYNAMIC;
import static com.nur1popcorn.basm.Constants.CONSTANT_NAME_AND_TYPE;

/**
 * The {@link ConstantInvokeDynamic} class is derived from the abstract {@link ConstantInfo} class
 * and is used by the 'invokedynamic' instruction. The instruction selects a method from the
 * 'BootstrapMethods Attribute's' table using this constant's 'bootstrapMethodAttrIndex'.
 * The implementation closely follows this part of the JVM ClassFile document:
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.10">
 *     CONSTANT_InvokeDynamic 4.4.10
 * </a>
 *
 * @see ConstantInfo
 * @see ConstantPool
 * @see ConstantNameAndType
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantInvokeDynamic extends ConstantInfo implements IConstantPoolPointer {
    /* This 'bootstrapMethodAttrIndex' points to a method within the 'BootstrapMethods Attribute's' table and
     * is used to obtain said method.
     */
    private int bootstrapMethodAttrIndex /* u2 */,

    /* The value of this 'nameAndTypeIndex' seems to be almost fully ignored by the JVM, therefore
     * I suggest to fully rely on the 'bootstrapMethodAttrIndex' as a way of obtaining the name and type
     * of the method invoked.
     *
     * If someone could enlighten me on this i would be delighted :)
     */
                nameAndTypeIndex /* u2 */;

    /**
     * @param bootstrapMethodAttrIndex
     * @param nameAndTypeIndex
     */
    public ConstantInvokeDynamic(byte tag, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(tag);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(bootstrapMethodAttrIndex);
        os.writeShort(nameAndTypeIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IConstantVisitor visitor) {
        visitor.visitCPPointer(this);
        if(getTag() == CONSTANT_INVOKEDYNAMIC)
            visitor.visitInvokeDynamic(this);
        else if(getTag() == CONSTANT_DYNAMIC)
            visitor.visitDynamicConstant(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(ConstantPool constantPool) {
        indexNameAndType(constantPool)
            .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(ConstantPool constantPool) {
        indexNameAndType(constantPool)
            .removePointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        nameAndTypeIndex = newIndex;
    }

    /**
     * @return
     */
    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    /**
     * @return
     */
    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    /**
     *
     *
     * @param constantPool
     *
     * @return
     */
    public ConstantNameAndType indexNameAndType(ConstantPool constantPool) {
        return constantPool.getEntry(
            nameAndTypeIndex,
            CONSTANT_NAME_AND_TYPE
        );
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantInvokeDynamic) {
            final ConstantInvokeDynamic invokedynamic = (ConstantInvokeDynamic) other;
            return invokedynamic.bootstrapMethodAttrIndex == bootstrapMethodAttrIndex &&
                   invokedynamic.nameAndTypeIndex == nameAndTypeIndex;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return bootstrapMethodAttrIndex * nameAndTypeIndex * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
            bootstrapMethodAttrIndex +
            "," +
            nameAndTypeIndex +
        "]";
    }
}
