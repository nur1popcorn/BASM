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

/**
 * The {@link Opcodes} class is a class containing lots of useful constants.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
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


    // Changes how super methods are treated by the 'invokespecial' instruction.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
    public static final int ACC_SUPER =                      0x20;

    // Marks a bridge method generated by the compiler.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
    public static final int ACC_BRIDGE =                     0x40;

    // Marks a method with variable number of arguments.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
    public static final int ACC_VARARGS =                    0x80;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-13.html#jls-13.1
    public static final int ACC_MANDATED =                   0x8000;

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

    /* stands for no operation and does nothing. */
    public static final byte NOP =                                 0x0;

    /* puts null on the stack */
    public static final byte ACONST_NULL =                         0x1;

    /* puts int value between -1 and 5 on the stack. */
    public static final byte ICONST_M1 =                           0x2;
    public static final byte ICONST_0 =                            0x3;
    public static final byte ICONST_1 =                            0x4;
    public static final byte ICONST_2 =                            0x5;
    public static final byte ICONST_3 =                            0x6;
    public static final byte ICONST_4 =                            0x7;
    public static final byte ICONST_5 =                            0x8;

    /* puts long value between 0L and 1L on the stack. */
    public static final byte LCONST_0 =                            0x9;
    public static final byte LCONST_1 =                            0xa;

    /* puts float value between 0f and 2f on the stack. */
    public static final byte FCONST_0 =                            0xb;
    public static final byte FCONST_1 =                            0xc;
    public static final byte FCONST_2 =                            0xd;

    /* puts double value between 0.0 and 1.0 on the stack. */
    public static final byte DCONST_0 =                            0xe;
    public static final byte DCONST_1 =                            0xf;

    /* puts a byte on the stack. */
    public static final byte BIPUSH =                              0x10;
    
    /* puts a short on the stack. */
    public static final byte SIPUSH =                              0x11;

    /* stands for load constant and pushes a constant onto the stack.
       it requires 1 parameter of type u1. */
    public static final byte LDC =                                 0x12;

    /* requires 1 parameter of type u2. */
    public static final byte LDC_W =                               0x13;

    /* pushes longs or doubles onto the stack.
       and requires 1 parameter of type u2. */
    public static final byte LDC2_W =                              0x14;

    /* puts value of local variable at given index of type int onto stack.
       it requires 1 parameter of type u1. */
    public static final byte ILOAD =                               0x15;
    /* of type long. */
    public static final byte LLOAD =                               0x16;
    /* of type float. */
    public static final byte FLOAD =                               0x17;
    /* of type double */
    public static final byte DLOAD =                               0x18;
    /* puts object reference onto the stack. */
    public static final byte ALOAD =                               0x19;

    /* requires no parameters and loads the N th local variable of type int onto the stack. */
    public static final byte ILOAD_0 =                             0x1a;
    public static final byte ILOAD_1 =                             0x1b;
    public static final byte ILOAD_2 =                             0x1c;
    public static final byte ILOAD_3 =                             0x1d;

    /* of type long. */
    public static final byte LLOAD_0 =                             0x1e;
    public static final byte LLOAD_1 =                             0x1f;
    public static final byte LLOAD_2 =                             0x20;
    public static final byte LLOAD_3 =                             0x21;

    /* of type float. */
    public static final byte FLOAD_0 =                             0x22;
    public static final byte FLOAD_1 =                             0x23;
    public static final byte FLOAD_2 =                             0x24;
    public static final byte FLOAD_3 =                             0x25;

    /* of type double. */
    public static final byte DLOAD_0 =                             0x26;
    public static final byte DLOAD_1 =                             0x27;
    public static final byte DLOAD_2 =                             0x28;
    public static final byte DLOAD_3 =                             0x29;

    /* puts object reference onto the stack. */
    public static final byte ALOAD_0 =                             0x2a;
    public static final byte ALOAD_1 =                             0x2b;
    public static final byte ALOAD_2 =                             0x2c;
    public static final byte ALOAD_3 =                             0x2d;

    /* loads value of type int from given array at given index. (arrayref and index should lay on the stack before execution)
       index and arrayref will be popped of the stack after execution and the result will be pushed onto the stack.*/
    public static final byte IALOAD =                              0x2e;
    /* of type long. */
    public static final byte LALOAD =                              0x2f;
    /* of type float. */
    public static final byte FALOAD =                              0x30;
    /* of type double. */
    public static final byte DALOAD =                              0x31;
    /* puts object reference onto stack. */
    public static final byte AALOAD =                              0x32;
    /* of type boolean. */
    public static final byte BALOAD =                              0x33;
    /* of type char. */
    public static final byte CALOAD =                              0x34;
    /* of type short. */
    public static final byte SALOAD =                              0x35;

    /* stores value of type int on stack in localvariable at given offset. */
    public static final byte ISTORE =                              0x36;
    /* of type long. */
    public static final byte LSTORE =                              0x37;
    /* of type float. */
    public static final byte FSTORE =                              0x38;
    /* of type double. */
    public static final byte DSTORE =                              0x39;
    /* stores object reference. */
    public static final byte ASTORE =                              0x3a;

    /* stores value of type int in N th localvariable. */
    public static final byte ISTORE_0 =                            0x3b;
    public static final byte ISTORE_1 =                            0x3c;
    public static final byte ISTORE_2 =                            0x3d;
    public static final byte ISTORE_3 =                            0x3e;

    /* of type long. */
    public static final byte LSTORE_0 =                            0x3f;
    public static final byte LSTORE_1 =                            0x40;
    public static final byte LSTORE_2 =                            0x41;
    public static final byte LSTORE_3 =                            0x42;

    /* of type float. */
    public static final byte FSTORE_0 =                            0x43;
    public static final byte FSTORE_1 =                            0x44;
    public static final byte FSTORE_2 =                            0x45;
    public static final byte FSTORE_3 =                            0x46;

    /* of type long. */
    public static final byte DSTORE_0 =                            0x47;
    public static final byte DSTORE_1 =                            0x48;
    public static final byte DSTORE_2 =                            0x49;
    public static final byte DSTORE_3 =                            0x4a;

    /* stores object reference. */
    public static final byte ASTORE_0 =                            0x4b;
    public static final byte ASTORE_1 =                            0x4c;
    public static final byte ASTORE_2 =                            0x4d;
    public static final byte ASTORE_3 =                            0x4e;

    public static final String OPCODE_MNEMONICS[] = new String[] {
        "nop",

        "aconst_null",

        "iconst_m1",
        "iconst_0",
        "iconst_1",
        "iconst_2",
        "iconst_3",
        "iconst_4",
        "iconst_5",

        "lconst_0",
        "lconst_1",

        "fconst_0",
        "fconst_1",
        "fconst_2",

        "dconst_0",
        "dconst_1",

        "bipush",
        "sipush",

        "ldc",
        "ldc_w",
        "ldc2_w",

        "iload",
        "lload",
        "fload",
        "dload",
        "aload",

        "iload_0",
        "iload_1",
        "iload_2",
        "iload_3",

        "lload_0",
        "lload_1",
        "lload_2",
        "lload_3",

        "fload_0",
        "fload_1",
        "fload_2",
        "fload_3",

        "dload_0",
        "dload_1",
        "dload_2",
        "dload_3",

        "aload_0",
        "aload_1",
        "aload_2",
        "aload_3",

        "iaload",
        "laload",
        "faload",
        "daload",
        "baload",
        "caload",
        "saload",

        "istore",
        "lstore",
        "fstore",
        "dstore",
        "astore",

        "istore_0",
        "istore_1",
        "istore_2",
        "istore_3",

        "lstore_0",
        "lstore_1",
        "lstore_2",
        "lstore_3",

        "fstore_0",
        "fstore_1",
        "fstore_2",
        "fstore_3",

        "dstore_0",
        "dstore_1",
        "dstore_2",
        "dstore_3",

        "astore_0",
        "astore_1",
        "astore_2",
        "astore_3",
    };

    // prevent construction :/
    private Opcodes()
    {}
}
