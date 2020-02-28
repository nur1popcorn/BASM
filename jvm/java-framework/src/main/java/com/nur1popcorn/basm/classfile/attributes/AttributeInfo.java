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

package com.nur1popcorn.basm.classfile.attributes;

import com.nur1popcorn.basm.classfile.ConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link AttributeInfo} are used in the ClassFile, field_info, method_info and Code_attribute
 * structures of the class file format. They can provide all sorts of general data about the class
 * file.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7">
 *     Attributes 4.7
 * </a>
 *
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class AttributeInfo {
    private int nameIndex /* u2 */,
                attributeLength /* u4 */;

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public AttributeInfo(int nameIndex, int attributeLength) {
        this.nameIndex = nameIndex;
        this.attributeLength = attributeLength;
    }

    /**
     * Writes the {@link AttributeInfo} to the given {@link DataOutputStream}.
     *
     * @param os The {@link DataOutputStream} which the {@link AttributeInfo} should be written to.
     * @param cp The {@link ConstantPool} may be used by certain attributes.
     */
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        os.writeShort(nameIndex);
        os.writeInt(attributeLength);
    }

    /**
     * Accepts a {@link AttributeVisitor}, calls the for the {@link AttributeInfo} appropriate 'visitXXX()'
     * methods to notify the visitor of what type of {@link AttributeInfo} is being entered.
     *
     * @param v The {@link AttributeVisitor} whom's callbacks will be invoked.
     */
    public abstract void accept(AttributeVisitor v);

    /**
     * @return The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     */
    public final int getNameIndex() {
        return nameIndex;
    }

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     */
    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * @return The {@link AttributeInfo}'s full length including the six bytes the used for the
     *         attribute_name_index attribute_length.
     */
    public final int getRealAttributeLength() {
        return attributeLength + 6;
    }

    /**
     * @return The {@link AttributeInfo}'s length in bytes.
     */
    public final int getAttributeLength() {
        return attributeLength;
    }

    /**
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public final void setAttributeLength(int attributeLength) {
        this.attributeLength = attributeLength;
    }
}
