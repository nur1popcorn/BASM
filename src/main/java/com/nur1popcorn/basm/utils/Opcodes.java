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

package com.nur1popcorn.basm.utils;

public final class Opcodes {

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
    public static final byte CONSTANT_INVOKEDYNAMIC =        18;

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
        null,
        "CONSTANT_Invokedynamic",
    };

    /**
     *
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
     * All valid access flags.
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1">
     *             Table 4.1-A Class access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5-200-A.1">
     *             Table 4.5-A. Field access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1">
     *             Table 4.6-A Method access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.6-300-D.1-D.1">
     *             Table 4.7.6-A Nested class access flags
     *         </a>
     *     </li>
     * </ul>
     */
    public static final int ACC_PUBLIC =                     0x1;
    public static final int ACC_PRIVATE =                    0x2;
    public static final int ACC_PROTECTED =                  0x4;

    public static final int ACC_STATIC =                     0x8;
    public static final int ACC_FINAL =                      0x10;

    public static final int ACC_SYNCHRONIZED =               0x20;
    public static final int ACC_VOLATILE =                   0x40;
    public static final int ACC_TRANSIENT =                  0x80;
    public static final int ACC_NATIVE =                     0x100;

    public static final int ACC_INTERFACE =                  0x200;
    public static final int ACC_ABSTRACT =                   0x400;

    public static final int ACC_STRICT =                     0x800;
    public static final int ACC_SYNTHETIC =                  0x1000;

    public static final int ACC_ANNOTATION =                 0x2000;
    public static final int ACC_ENUM =                       0x4000;


    // Marks a bridge method generated by the compiler.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
    public static final int ACC_BRIDGE =                     0x40;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-13.html#jls-13.1
    public static final int ACC_MANDATED =                   0x8000;

    // Changes how super methods are treated by the 'invokespecial' instruction.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
    public static final int ACC_SUPER =                      0x20;

    /**
     * An array containing a string representation of the available access flags used by classes.
     */
    public static final String ACC_CLASS_NAMES[] = {
        "public",
        null,
        null,
        null,
        "final",
        "super",
        null,
        null,
        null,
        "interface",
        "abstract",
        null,
        "synthetic",
        "annotation",
        "enum"
    };

    /**
     * An array containing a string representation of the available access flags used by fields.
     */
    public static final String ACC_FIELD_NAMES[] = {
        "public",
        "private",
        "protected",
        "static",
        "final",
        null,
        "volatile",
        "transient",
        null,
        null,
        null,
        null,
        "synthetic",
        null,
        "enum"
    };

    /**
     * An array containing a string representation of the available access flags used by methods.
     */
    public static final String ACC_METHOD_NAMES[] = {
        "public",
        "private",
        "protected",
        "static",
        "final",
        "synchronized",
        "bridge",
        "varargs",
        "native",
        null,
        "abstract",
        "strict",
        "synthetic"
    };

    /**
     * An array containing a string representation of the available access flags used by nested classes.
     */
    public static final String ACC_NESTED_CLASS_NAMES[] = {
        "public",
        "private",
        "protected",
        "static",
        "final",
        null,
        null,
        null,
        null,
        "interface",
        "abstract",
        null,
        "synthetic",
        "annotation",
        "enum"
    };

    // prevent construction :/
    private Opcodes()
    {}
}
