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

import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link FieldMethodInfo} TODO: describe
 * [
 *     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5">
 *         Fields 4.5
 *     </a>
 *     ,
 *     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6">
 *         Methods 4.6
 *     </a>
 * ]
 *
 * @see ConstantPool
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class FieldMethodInfo {
    private int access;

    private int nameIndex,
                descIndex;

    private AttributeInfo attributes[];

    /**
     * @param access the fields access modifiers.
     * @param nameIndex a index into the {@link ConstantPool} representing the field' TODO: add type of index
     *                  or method's name.
     * @param descIndex a index into the {@link ConstantPool} representing the field's
     *                  or method's descriptor.
     * @param attributes TODO: describe
     */
    public FieldMethodInfo(int access, int nameIndex, int descIndex, AttributeInfo attributes[]) {
        this.access = access;
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
        this.attributes = attributes;
    }

    /**
     * @param in the {@link DataInputStream} from which the {@link FieldMethodInfo}
     *           is supposed to be read.
     * @param constantPool the {@link ConstantPool} with which the {@link AttributeInfo}s
     *                     should be read.
     *
     * @see AttributeInfo#read(DataInputStream, ConstantPool)
     */
    public FieldMethodInfo(DataInputStream in, ConstantPool constantPool) throws IOException {
        this(
            in.readUnsignedShort(), // access.
            in.readUnsignedShort(), // nameIndex.
            in.readUnsignedShort(), // descIndex.
            AttributeInfo.read(in, constantPool) // attributes.
        );
    }

    //TODO: desc
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        os.writeShort(access);
        os.writeShort(nameIndex);
        os.writeShort(descIndex);
        
        os.writeShort(attributes.length);
        for(AttributeInfo attributeInfo : attributes)
            attributeInfo.write(os, constantPool);
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced field's or method's name inside of the {@link ConstantPool}.
     */
    public ConstantUtf8 indexName(ConstantPool constantPool) {
        return (ConstantUtf8) constantPool.getEntry(nameIndex);
    }

    /**
     * @param constantPool the {@link ConstantPool} which should be indexed.
     *
     * @return the referenced field's or method's descriptor inside of the
     *         {@link ConstantPool}.
     */
    public ConstantUtf8 indexDesc(ConstantPool constantPool) {
        return (ConstantUtf8) constantPool.getEntry(descIndex);
    }

    /**
     * @return the field's or method's attributes.
     */
    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    //TODO: desc.
    public int getAccess() {
        return access;
    }
}
