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
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

/**
 * The {@link AttributeSourceFile} is an optional attribute which stores a reference to
 * the original java class file name.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.10">
 *     4.7.10. The SourceFile Attribute
 * </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class AttributeSourceFile extends AttributeInfo {
    private int sourceFileIndex /* u2 */;

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     * @param sourceFileIndex The index to the CONSTANT_UTF8 which holds the original java class name.
     */
    public AttributeSourceFile(int nameIndex, int attributeLength, int sourceFileIndex) {
        super(nameIndex, attributeLength);
        this.sourceFileIndex = sourceFileIndex;
    }

    @Override
    public void accept(IAttributeVisitor v) {
        v.visit(this);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(sourceFileIndex);
    }

    /**
     * @param sourceFileIndex The new source file index.
     */
    public void setSourceFileIndex(int sourceFileIndex) {
        this.sourceFileIndex = sourceFileIndex;
    }

    /**
     * @return The index of the CONSTANT_UTF8 which this attribute is referencing.
     */
    public int getSourceFileIndex() {
        return sourceFileIndex;
    }

    /**
     * @param constantPool The constant pool in which the source file should be looked up in.
     * @return The CONSTANT_UTF8 which this attribute is referencing.
     */
    public ConstantUTF8 indexSourceFile(ConstantPool constantPool) {
        return constantPool.getEntry(sourceFileIndex, CONSTANT_UTF8);
    }

    @Override
    public boolean equals(Object other) {
        return this == other ||
            (other instanceof AttributeSourceFile &&
             sourceFileIndex == ((AttributeSourceFile) other).sourceFileIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceFileIndex);
    }
}
