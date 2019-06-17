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

import com.nur1popcorn.basm.classfile.attributes.factory.AttributeFactory;
import com.nur1popcorn.basm.classfile.attributes.method.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;
import com.nur1popcorn.basm.classfile.tree.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

public final class FieldMethodInfo extends AccessFlags {
    private final ConstantPool constantPool;

    /*
     *
     */
    private int nameIndex,
                descIndex;
    /*
     *
     */
    private AttributeInfo attributes[];

    /**
     * @param access
     * @param nameIndex
     * @param descIndex
     * @param attributes
     * @param constantPool
     */
    public FieldMethodInfo(int access, int nameIndex, int descIndex, AttributeInfo attributes[], ConstantPool constantPool) {
        super(access);
        this.nameIndex = nameIndex;
        this.descIndex = descIndex;
        this.attributes = attributes;
        this.constantPool = constantPool;
    }

    /**
     * @param in
     * @param constantPool
     *
     * @throws IOException
     */
    public FieldMethodInfo(DataInputStream in, ConstantPool constantPool) throws IOException {
        this(in.readUnsignedShort(),
             in.readUnsignedShort(),
             in.readUnsignedShort(),
             AttributeFactory.read(in, constantPool),
             constantPool);
    }

    /**
     * @param os
     *
     * @throws IOException
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(access);
        os.writeShort(nameIndex);
        os.writeShort(descIndex);
        
        os.writeShort(attributes.length);
        for(AttributeInfo attributeInfo : attributes)
            attributeInfo.write(os, constantPool);
    }

    /**
     * @return
     */
    public AttributeCode getCode() {
        for(AttributeInfo info : attributes)
            if(info instanceof AttributeCode)
                return (AttributeCode) info;
        return null;
    }

    /**
     * @return
     */
    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    /**
     * @return
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * @return
     */
    public int getDescIndex() {
        return descIndex;
    }

    /**
     * @return
     */
    public String getName() {
        final ConstantUTF8 name =
            constantPool.getEntry(nameIndex, CONSTANT_UTF8);
        return name.bytes;
    }

    /**
     * @return
     */
    public Type getDesc() {
        final ConstantUTF8 desc =
            constantPool.getEntry(descIndex, CONSTANT_UTF8);
        return Type.getType(desc.bytes);
    }
}
