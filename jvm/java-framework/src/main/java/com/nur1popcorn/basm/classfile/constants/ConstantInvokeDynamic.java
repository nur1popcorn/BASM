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
import com.nur1popcorn.basm.classfile.attributes.AttributeBootstrapMethods;
import com.nur1popcorn.basm.classfile.attributes.BootstrapMethodsEntry;

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
public final class ConstantInvokeDynamic extends ConstantInfo implements ConstantPoolPointer {
    /* The value of the 'bootstrapMethodAttrIndex' references an index into the bootstrap methods
     * attribute array.
     *
     * See the AttributeBootstrapMethods and BootstrapMethodsEntry classes for more information.
     */
    private int bootstrapMethodAttrIndex /* u2 */,

    /* The name and the type for the entry in the constant pool at the index of the 'nameAndTypeIndex'
     * is passed to the bootstrap method (pointed to by the bootstrapMethodAttrIndex) when the JVM
     * first encounters the invoke dynamic instruction. Other than that, the name and type are
     * ignored by the JVM.
     *
     * See the AttributeBootstrapMethods and BootstrapMethodsEntry classes for more information.
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
    public void accept(ConstantVisitor visitor) {
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
     * Set the bootstrap method attribute entry index used for the name and type of the {@link ConstantInvokeDynamic}.
     * @param bootstrapMethodAttrIndex The index.
     */
    public void setBootstrapMethodAttrIndex(int bootstrapMethodAttrIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
    }

    /**
     * Set the constant pool index used for the name and type of the {@link ConstantInvokeDynamic}.
     * @param nameAndTypeIndex The index.
     */
    public void setNameAndTypeIndex(int nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
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

    public BootstrapMethodsEntry indexBootstrapMethod(AttributeBootstrapMethods attribute) {
        return attribute.getEntry(bootstrapMethodAttrIndex);
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
