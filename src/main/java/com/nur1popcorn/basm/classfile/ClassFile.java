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

import com.nur1popcorn.basm.classfile.constants.ConstantName;

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Constants.MAGIC;

//TODO: desc.
/**
 * The {@link ClassFile}
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     ConstantPool 4.4.
 * </a>
 *
 * @see ConstantPool
 * @see FieldMethodInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class ClassFile {
    private int minorVersion,
                majorVersion;

    private ConstantPool constantPool;

    private int access;

    private int thisClass,
                superClass;

    private int interfaces[];
    private FieldMethodInfo fields[];
    private FieldMethodInfo methods[];

    public ClassFile(DataInputStream inputStream) throws IOException {
        final int magic = inputStream.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);

        minorVersion = inputStream.readUnsignedShort();
        majorVersion = inputStream.readUnsignedShort();

        constantPool = new ConstantPool(inputStream);

        access = inputStream.readUnsignedShort();
        thisClass = inputStream.readUnsignedShort();
        superClass = inputStream.readUnsignedShort();

        interfaces = new int[inputStream.readUnsignedShort()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = inputStream.readUnsignedShort();

        fields = new FieldMethodInfo[inputStream.readUnsignedShort()];
        for(int i = 0; i < fields.length; i++)
            fields[i] = new FieldMethodInfo(inputStream, constantPool);

        methods = new FieldMethodInfo[inputStream.readUnsignedShort()];
        for(int i = 0; i < methods.length; i++)
            methods[i] = new FieldMethodInfo(inputStream, constantPool);

        inputStream.close();
    }

    public ClassFile(int minorVersion,
                     int majorVersion,
                     ConstantPool constantPool,
                     int access,
                     int thisClass,
                     int superClass,
                     int interfaces[],
                     FieldMethodInfo fields[],
                     FieldMethodInfo methods[]) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;

        this.constantPool = constantPool;

        this.access = access;
        this.thisClass = thisClass;
        this.superClass = superClass;

        this.interfaces = interfaces;

        this.fields = fields;
        this.methods = methods;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccess() {
        return access;
    }

    public ConstantName getThisClass() {
        return (ConstantName) constantPool.getEntry(thisClass);
    }

    public ConstantName getSuperClass() {
        return (ConstantName) constantPool.getEntry(superClass);
    }

    public ConstantName[] getInterfaces() {
        final ConstantName interfaces[] = new ConstantName[this.interfaces.length];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = (ConstantName) constantPool.getEntry(i);
        return interfaces;
    }

    public FieldMethodInfo[] getFields() {
        return fields;
    }

    public FieldMethodInfo[] getMethods() {
        return methods;
    }
}
