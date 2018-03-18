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

import static com.nur1popcorn.basm.Constants.*;

/**
 * The {@link ClassReader} is used for reading the full or parts of the JavaClass provided.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1">
 *     ClassFile 4.1.
 * </a>
 *
 * @see ConstantPool
 * @see FieldMethodInfo
 * @see IClassVisitor
 *
 * @see #accept(IClassVisitor, int)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassReader {
    /**
     * <p>Enables reading the head part of the JavaClass which contains:</p>
     * <ul>
     *     <li>
     *         <p>MinorVersion, MajorVersion</p>
     *         <p>Minor and major version describe the version of the Jdk used to compile the class. </p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-B">
     *             MinorVersion, MajorVersion 4.1-200-B
     *         </a>
     *     </li>
     *     <li>
     *         <p>ConstantPool</p>
     *         <p>The ConstantPool is made up of various constants including: </p>
     *         <ul>
     *             <li>CONSTANT_Utf8</li>
     *             <li>CONSTANT_Integer</li>
     *             <li>CONSTANT_Float</li>
     *             <li>CONSTANT_Long</li>
     *             <li>CONSTANT_Double</li>
     *             <li>CONSTANT_Class</li>
     *             <li>CONSTANT_String</li>
     *             <li>CONSTANT_Fieldref</li>
     *             <li>CONSTANT_Methodref</li>
     *             <li>CONSTANT_InterfaceMethodref</li>
     *             <li>CONSTANT_NameAndType</li>
     *             <li>CONSTANT_MethodHandle</li>
     *             <li>CONSTANT_MethodType</li>
     *             <li>CONSTANT_Invokedynamic</li>
     *         </ul>
     *         <p>These constants are used to describe any non changing value and are frequently
     *            referenced by the rest of the JavaClass.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-C">
     *             ConstantPool 4.1-200-C
     *         </a>
     *     </li>
     * </ul>
     *
     * @see #readHead()
     * @see #accept(IClassVisitor, int)
     */
    public static final int READ_HEAD = 0x1;

    /**
     * <p>Enables reading the body part of the JavaClass which contains:</p>
     * <ul>
     *     <li>
     *         <p>AccessFlags</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E">
     *             AccessFlags 4.1-200-E
     *         </a>
     *     </li>
     *     <li>
     *         <p>ThisClass</p>
     *         <p>A pointer into the the {@link ConstantPool}, pointing to an instance of a
     *            CONSTANT_Class representing the class read. </p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-F">
     *             ThisClass 4.1-200-I
     *         </a>
     *     </li>
     *     <li>
     *         <p>SuperClass</p>
     *         <p>A pointer into the the {@link ConstantPool}, pointing to an instance of a
     *            CONSTANT_Class representing the class-read's super class.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-G">
     *             SuperClass 4.1-200-G
     *         </a>
     *     </li>
     *     <li>
     *         <p>Interfaces</p>
     *         <p>A table of pointers pointing into the {@link ConstantPool}, to instances of
     *            CONSTANT_Classes representing the classes-read's implemented interfaces.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-I">
     *             Interfaces 4.1-200-I
     *         </a>
     *     </li>
     * </ul>
     *
     * @see #readBody()
     * @see #accept(IClassVisitor, int)
     */
    public static final int READ_BODY = 0x2;

    /**
     * <p>Enables reading the fields of the JavaClass.</p>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-J">
     *     Fields 4.1-200-J
     * </a>
     *
     * @see #readFields()
     * @see #accept(IClassVisitor, int)
     */
    public static final int READ_FIELDS = 0x4;

    /**
     * <p>Enables reading the methods of the JavaClass.</p>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-L">
     *     Methods 4.1-200-L
     * </a>
     *
     * @see #readMethods()
     * @see #accept(IClassVisitor, int)
     */
    public static final int READ_METHODS = 0x8;

    /**
     * <p>Enables reading the footer part of the JavaClass.</p>
     * <p>The footer is made up of various attributes describing the ClassFile including:</p>
     * <ul>
     *     <li>SourceFile</li>
     *     <li>InnerClasses</li>
     *     <li>EnclosingMethod</li>
     *     <li>SourceDebugExtension</li>
     *     <li>RuntimeVisibleTypeAnnotations</li>
     *     <li>RuntimeInvisibleTypeAnnotations</li>
     * </ul>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-N">
     *     Attributes 4.1-200-N
     * </a>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7-320">
     *     Attributes Table 4.7-320
     * </a>
     */
    public static final int READ_FOOTER = 0x10;

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
        this.in = in;
        final int magic = in.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);
    }

    /**
     * <p>Reads the head part of the JavaClass which contains:</p>
     * <ul>
     *     <li>
     *         <p>MinorVersion, MajorVersion</p>
     *         <p>Minor and major version describe the version of the Jdk used to compile the class. </p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-B">
     *             MinorVersion, MajorVersion 4.1-200-B
     *         </a>
     *     </li>
     *     <li>
     *         <p>ConstantPool</p>
     *         <p>The ConstantPool is made up of various constants including: </p>
     *         <ul>
     *             <li>CONSTANT_Utf8</li>
     *             <li>CONSTANT_Integer</li>
     *             <li>CONSTANT_Float</li>
     *             <li>CONSTANT_Long</li>
     *             <li>CONSTANT_Double</li>
     *             <li>CONSTANT_Class</li>
     *             <li>CONSTANT_String</li>
     *             <li>CONSTANT_Fieldref</li>
     *             <li>CONSTANT_Methodref</li>
     *             <li>CONSTANT_InterfaceMethodref</li>
     *             <li>CONSTANT_NameAndType</li>
     *             <li>CONSTANT_MethodHandle</li>
     *             <li>CONSTANT_MethodType</li>
     *             <li>CONSTANT_Invokedynamic</li>
     *         </ul>
     *         <p>These constants are used to describe any non changing value and are frequently
     *            referenced by the rest of the JavaClass.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-C">
     *             ConstantPool 4.1-200-C
     *         </a>
     *     </li>
     * </ul>
     *
     * @throws IOException if an error occurs during the process of reading from the {@link DataInputStream}.
     *
     * @see #accept(IClassVisitor, int)
     */
    private void readHead() throws IOException {
        minorVersion = in.readUnsignedShort();
        majorVersion = in.readUnsignedShort();

        constantPool = new ConstantPool(in);
    }

    /**
     * <p>Reads the body part of the JavaClass which contains:</p>
     * <ul>
     *     <li>
     *         AccessFlags
     *     </li>
     *     <li>
     *         <p>ThisClass</p>
     *         <p>A pointer into the the {@link ConstantPool}, pointing to an instance of a
     *            CONSTANT_Class representing the class read. </p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-F">
     *             ThisClass 4.1-200-I
     *         </a>
     *     </li>
     *     <li>
     *         <p>SuperClass</p>
     *         <p>A pointer into the the {@link ConstantPool}, pointing to an instance of a
     *            CONSTANT_Class representing the class-read's super class.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-G">
     *             SuperClass 4.1-200-G
     *         </a>
     *     </li>
     *     <li>
     *         <p>Interfaces</p>
     *         <p>A table of pointers pointing into the {@link ConstantPool}, to instances of
     *            CONSTANT_Classes representing the classes-read's implemented interfaces.</p>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-I">
     *             Interfaces 4.1-200-I
     *         </a>
     *     </li>
     * </ul>
     *
     * @throws IOException if an error occurs during the process of reading from the {@link DataInputStream}.
     *
     * @see #accept(IClassVisitor, int)
     */
    private void readBody() throws IOException {
        access = in.readUnsignedShort();
        thisClass = in.readUnsignedShort();
        superClass = in.readUnsignedShort();

        interfaces = new int[in.readUnsignedShort()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = in.readUnsignedShort();
    }

    /**
     * <p>Reads the fields of the JavaClass.</p>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-J">
     *     Fields 4.1-200-J
     * </a>
     *
     * @throws IOException if an error occurs during the process of reading from the {@link DataInputStream}.
     *
     * @see #accept(IClassVisitor, int)
     */
    private void readFields() throws IOException {
        fields = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < fields.length; i++)
            fields[i] = new FieldMethodInfo(in, constantPool);
    }

    /**
     * <p>Reads the methods of the JavaClass.</p>
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-L">
     *     Methods 4.1-200-L
     * </a>
     *
     * @throws IOException if an error occurs during the process of reading from the {@link DataInputStream}.
     *
     * @see #accept(IClassVisitor, int)
     */
    private void readMethods() throws IOException {
        methods = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < methods.length; i++)
            methods[i] = new FieldMethodInfo(in, constantPool);
    }

    /**
     * Visitor? I barely know her!
     *
     * - b.k.
     *
     * @param visitor the visitor for whom the class partially or fully read.
     * @param read the flags used to determine what parts of the class should be read.
     *
     * @throws IOException if an error occurs during the process of reading from the {@link DataInputStream}.
     *
     * @see IClassVisitor
     * @see #READ_HEAD
     * @see #READ_BODY
     * @see #READ_FIELDS
     * @see #READ_METHODS
     */
    public void accept(IClassVisitor visitor, int read) throws IOException {
        assert((read & READ_HEAD) != 0 ||
                ((read & READ_FIELDS) == 0 &&
                 (read & READ_METHODS) == 0));
        if((read & READ_HEAD) != 0) {
            readHead();
            visitor.visitHead(minorVersion, majorVersion, constantPool);
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
            visitor.visitBody(
                access,
                thisClass,
                superClass,
                interfaces
            );
        } else {
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E
            // skip accessFlags, thisClass and superClass.
            in.skipBytes(6);
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-H
            // skip interfaces.
            in.skipBytes(in.readUnsignedShort());
        }

        if((read & (READ_FIELDS | READ_METHODS)) == 0) {
            in.close();
            return;
        }

        if((read & (READ_FIELDS)) != 0) {
            readFields();
            visitor.visitFields(fields);
        } else {
            final int fieldCount = in.readUnsignedShort();
            for(int i = 0; i < fieldCount; i++) {
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5
                // skip access flags, name index and desc index.
                in.skipBytes(6);
                final int attributeCount = in.readUnsignedShort();
                for(int j = 0; j < attributeCount; j++) {
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6
                    // skip attribute name index.
                    in.skipBytes(2);
                    // read and skip attribute length.
                    in.skipBytes(in.readInt());
                }
            }
        }

        if((read & READ_METHODS) != 0) {
            readMethods();
            visitor.visitMethods(methods);
        }

        //TODO: read class footer

        in.close();
    }
}
