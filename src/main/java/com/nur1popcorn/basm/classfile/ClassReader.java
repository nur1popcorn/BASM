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
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Constants.*;

/**
 * The {@link ClassReader} is used for reading the full or parts of the class provided.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1">
 *     ClassFile 4.1.
 * </a>
 *
 * @see ConstantPool
 * @see FieldMethodInfo
 * @see IClassReaderVisitor
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassReader {

    /**
     * <b>Read head</b>
     *
     * <p>This flag enables reading the head part of the JavaClass.</p>
     * <p>These are considered part of the head:</p>
     * <ul>
     *     <li>minor version</li>
     *     <li>major version</li>
     *     <li>constantpool</li>
     * </ul>
     */
    public static final int READ_HEAD = 0x1;

    /**
     * <b>Read body</b>
     */
    public static final int READ_BODY = 0x2;
    public static final int READ_FIELDS = 0x4;
    public static final int READ_METHODS = 0x8;

    /**
     * A table which can be indexed to obtain the expected size of any {@link ConstantPool} entry.
     */
    private static final int CONSTANT_INFO_SKIP_TABLE[] = {
        0,
        0, // CONSTANT_Utf8
        0,
        4, // CONSTANT_Integer
        4, // CONSTANT_Float
        8, // CONSTANT_Long
        8, // CONSTANT_Double
        2, // CONSTANT_Class
        2, // CONSTANT_String
        4, // CONSTANT_Fieldref
        4, // CONSTANT_Methodref
        4, // CONSTANT_InterfaceMethodref
        4, // CONSTANT_NameAndType
        0,
        0,
        3, // CONSTANT_MethodHandle
        2, // CONSTANT_MethodType
        0,
        4, // CONSTANT_Invokedynamic
    };
    
    private DataInputStream in;

    private int minorVersion,
                majorVersion;

    private ConstantPool constantPool;

    private int access;

    private int thisClass,
                superClass;

    private int interfaces[];
    private FieldMethodInfo fields[];
    private FieldMethodInfo methods[];

    public ClassReader(DataInputStream in) throws IOException {
        final int magic = in.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);
    }

    private void readHead() throws IOException {
        minorVersion = in.readUnsignedShort();
        majorVersion = in.readUnsignedShort();

        constantPool = new ConstantPool(in);
    }

    private void readBody() throws IOException {
        access = in.readUnsignedShort();
        thisClass = in.readUnsignedShort();
        superClass = in.readUnsignedShort();

        interfaces = new int[in.readUnsignedShort()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = in.readUnsignedShort();
    }

    private void readFields() throws IOException {
        fields = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < fields.length; i++)
            fields[i] = new FieldMethodInfo(in, constantPool);
    }

    private void readMethods() throws IOException {
        methods = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < methods.length; i++)
            methods[i] = new FieldMethodInfo(in, constantPool);
    }

    public void accept(IClassReaderVisitor visitor, int read) throws IOException {
        assert(read & READ_HEAD) != 0 &&
               ((read & READ_BODY) == 0 ||
                (read & READ_FIELDS) == 0 ||
                (read & READ_METHODS) == 0);
        if((read & READ_HEAD) != 0) {
            readHead();
            visitor.visit(constantPool);
        } else {
            // skip minor/major version
            in.skipBytes(4);
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4
            final int cpSize = in.readUnsignedShort();
            for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++) {
                final byte tag = in.readByte();
                switch(tag) {
                    case CONSTANT_UTF8:
                        in.skipBytes(in.readUnsignedShort());
                        break;
                    case CONSTANT_LONG:
                    case CONSTANT_DOUBLE:
                        i++ /* padding */;
                    default:
                        in.skipBytes(CONSTANT_INFO_SKIP_TABLE[tag]);
                }
            }
        }

        if((read & (READ_BODY | READ_FIELDS | READ_METHODS)) == 0) {
            in.close();
            return;
        }

        if((read & READ_BODY) != 0) {
            readBody();
            final ConstantUtf8 interfaces[] = new ConstantUtf8[this.interfaces.length];
            for(int i = 0; i < interfaces.length; i++)
                interfaces[i] = ((ConstantName)constantPool.getEntry(this.interfaces[i]))
                                        .indexName(constantPool);
            visitor.visit(access,
                          ((ConstantName)constantPool.getEntry(thisClass))
                                  .indexName(constantPool),
                          ((ConstantName)constantPool.getEntry(superClass))
                                  .indexName(constantPool),
                          interfaces);
        } else {
            // skip accessFlags, thisClass and superClass.
            in.skipBytes(6);
            // skip interfaces.
            in.skipBytes(in.readUnsignedShort());
        }

        if((read & (READ_FIELDS | READ_METHODS)) == 0) {
            in.close();
            return;
        }

        if((read & (READ_FIELDS)) != 0) {

        } else {

        }

        if((read & READ_METHODS) != 0) {

        }
        in.close();
    }
}
