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

package com.nur1popcorn.basm;

//TODO: write a better description for this class.

/**
 * This class is the code implementation of the conglomeration of various constants related to the JVM.
 */

/**
 * The {@link Constants} a class containing a lot of useful constants.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */

/**
 * The {@link ConstantInfo} class is the abstract super class which all constant pool entries base their
 * implementation on. The class mostly consists of two things: A tag, which is used to identify the
 * type of 'CONSTANT_Info' and a set of pointers, which denotes any kind of {@link IConstantPoolPointer}
 * pointing at this specific constant. The implementation closely follows this part of the JVM ClassFile document:
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     CONSTANT_Info 4.4
 * </a>
 *
 * @see IConstantPoolPointer
 * @see IConstantVisitor
 *
 * @see #accept(IConstantVisitor)
 *
 * @see #read(DataInputStream)
 * @see #write(DataOutputStream)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */

/**
 * Constants defined by the JVM.
 */

/**
 * The {@link Constants} class
 */
public final class Constants {
    /**
     * 4 bytes used to identify java class files.
     */
    public static final int MAGIC =                          0xcafe_babe;

    /**
     * All valid constant pool entry tags.
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-140">
     *     Table-4.4-A cp tags
     * </a>
     */
    public static final byte CONSTANT_UTF8 =                 1;
    public static final byte CONSTANT_INTEGER =              3;
    public static final byte CONSTANT_FLOAT =                4;
    public static final byte CONSTANT_LONG =                 5;
    public static final byte CONSTANT_DOUBLE =               6;
    public static final byte CONSTANT_CLASS =                7;
    public static final byte CONSTANT_STRING =               8;
    public static final byte CONSTANT_FIELD_REF =            9;
    public static final byte CONSTANT_METHOD_REF =           10;
    public static final byte CONSTANT_INTERFACE_METHOD_REF = 11;
    public static final byte CONSTANT_NAME_AND_TYPE =        12;
    public static final byte CONSTANT_METHOD_HANDLE =        15;
    public static final byte CONSTANT_METHOD_TYPE =          16;
    public static final byte CONSTANT_DYNAMIC =              17;
    public static final byte CONSTANT_INVOKEDYNAMIC =        18;
    public static final byte CONSTANT_MODULE =               19;
    public static final byte CONSTANT_PACKAGE =              20;

    /**
     * An array containing a string representation of the available constant pool entries.
     */
    public static final String CONSTANT_POOL_ENTRY_NAMES[] = new String[] {
        null,
        "CONSTANT_Utf8",
        null,
        "CONSTANT_Integer",
        "CONSTANT_Float",
        "CONSTANT_Long",
        "CONSTANT_Double",
        "CONSTANT_Class",
        "CONSTANT_String",
        "CONSTANT_Fieldref",
        "CONSTANT_Methodref",
        "CONSTANT_InterfaceMethodref",
        "CONSTANT_NameAndType",
        null,
        null,
        "CONSTANT_MethodHandle",
        "CONSTANT_MethodType",
        "CONSTANT_Dynamic",
        "CONSTANT_Invokedynamic",
        "CONSTANT_Module",
        "CONSTANT_Package"
    };

    /**
     * TODO: desc
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-5.html#jvms-5.4.3.5">
     *     5.4.3.5 Method Type and Method Handle Resolution
     * </a>
     */
    public static final byte REF_GETFIELD =                  1;
    public static final byte REF_GETSTATIC =                 2;
    public static final byte REF_PUTFIELD =                  3;
    public static final byte REF_PUTSTATIC =                 4;
    public static final byte REF_INVOKEVIRTUAL =             5;
    public static final byte REF_INVOKESTATIC =              6;
    public static final byte REF_INVOKESPECIAL =             7;
    public static final byte REF_NEWINVOKESPECIAL =          8;
    public static final byte REF_INVOKEINTERFACE =           9;

    /**
     * An array containing a string representation of the available ref types.
     */
    public static final String REF_NAMES[] = new String[] {
        null,
        "REF_getField",
        "REF_getStatic",
        "REF_putField",
        "REF_putStatic",
        "REF_invokeVirtual",
        "REF_invokeStatic",
        "REF_invokeSpecial",
        "REF_newInvokeSpecial",
        "REF_invokeInterface"
    };

    /**
     * TODO: desc
     * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.4">
     *     StackMapTable 4.7.4
     * </a>
     */
    public static final int ITEM_TOP =                             0;
    public static final int ITEM_INTEGER =                         1;
    public static final int ITEM_FLOAT =                           2;
    public static final int ITEM_DOUBLE =                          3;
    public static final int ITEM_LONG =                            4;
    public static final int ITEM_NULL =                            5;
    public static final int ITEM_UNINITIALISED_THIS =              6;
    public static final int ITEM_OBJECT =                          7;
    public static final int ITEM_UNINITIALISED =                   8;

    /**
     * TODO: desc
     */
    public static String ITEM_INFO_NAMES[] = {
        "Top_variable_info",
        "Integer_variable_info",
        "Float_variable_info",
        "Double_variable_info",
        "Long_variable_info",
        "Null_variable_info",
        "UninitializedThis_variable_info",
        "Object_variable_info",
        "Uninitialized_variable_info"
    };

    // prevent construction :/
    private Constants()
    {}
}
