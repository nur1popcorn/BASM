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

package com.nur1popcorn.basm.classfile.attributes.clazz;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AttributeInnerClasses extends AttributeInfo {
    private final InnerClass[] innerClasses;

    public AttributeInnerClasses(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        innerClasses = new InnerClass[in.readUnsignedShort()];
        for(int i = 0; i < innerClasses.length; i++)
            innerClasses[i] = new InnerClass(in);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(innerClasses.length);
        for(InnerClass innerClass : innerClasses)
            innerClass.write(os);
    }

    public static final class InnerClass {
        private final int innerClassInfoIndex,
                          outerClassInfoIndex,
                          innerNameIndex,
                          innerClassAccessFlags;

        public InnerClass(DataInputStream in) throws IOException {
            innerClassInfoIndex = in.readUnsignedShort();
            outerClassInfoIndex = in.readUnsignedShort();
            innerNameIndex = in.readUnsignedShort();
            innerClassAccessFlags = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(innerClassInfoIndex);
            os.writeShort(outerClassInfoIndex);
            os.writeShort(innerNameIndex);
            os.writeShort(innerClassAccessFlags);
        }
    }
}
