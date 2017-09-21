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

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Opcodes.MAGIC;

public class ClassReader {
    private DataInputStream in;

    private int minorVersion,
                majorVersion;

    private ConstantPool constantPool;

    public int access;

    private int thisClass,
                superClass;

    private int interfaces[];
    private FieldMethodInfo fields[];
    private FieldMethodInfo methods[];

    public ClassReader(DataInputStream in) {
        this.in = in;
    }

    public final ClassFile read() throws IOException {
        // read and check magic value.
        final int magic = in.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);

        // read compiler version used.
        minorVersion = in.readUnsignedShort();
        majorVersion = in.readUnsignedShort();

        // read cp.
        constantPool = new ConstantPool(in);

        access = in.readUnsignedShort();
        thisClass = in.readUnsignedShort();
        superClass = in.readUnsignedShort();

        interfaces = new int[in.readUnsignedShort()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = in.readUnsignedShort();

        fields = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < fields.length; i++)
            fields[i] = new FieldMethodInfo(in, constantPool);

        methods = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < methods.length; i++)
            methods[i] = new FieldMethodInfo(in, constantPool);


        return null;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}
