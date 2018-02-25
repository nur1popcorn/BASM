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
 * The {@link Constants} a class containing a lot of useful constants.
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
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

    /* stores value of type int in at given index in given array
       (arrayref, index, value)*/
    public static final byte IASTORE =                             0x4f;
    /* of type long. */
    public static final byte LASTORE =                             0x50;
    /* of type float. */
    public static final byte FASTORE =                             0x51;
    /* of type double. */
    public static final byte DASTORE =                             0x52;
    /* stores object reference. */
    public static final byte AASTORE =                             0x53;
    /* of type bool. */
    public static final byte BASTORE =                             0x54;
    /* of type char. */
    public static final byte CASTORE =                             0x55;
    /* of type short. */
    public static final byte SASTORE =                             0x56;

    /* discards top value on stack. */
    public static final byte POP =                                 0x57;
    /* discards top 2 values or 1st if the value is of type long or double. */
    public static final byte POP2 =                                0x58;

    /* puts a copy of the top value on the stack on the stack. */
    public static final byte DUP =                                 0x59;
    /* a copy of the 2nd value on the stack. */
    public static final byte DUP_X1 =                              0x5a;
    /* a copy of the 3rd value on the stack. */
    public static final byte DUP_X2 =                              0x5b;

    /* puts a copy of the 1st and 2nd or 1st if the value of type long or double on the stack on the stack. */
    public static final byte DUP2 =                                0x5c;
    /* a copy of the 2nd and 3rd value on the stack. */
    public static final byte DUP2_x1 =                             0x5d;
    /* a copy of the 3rd and 4th value on the stack. */
    public static final byte DUP2_x2 =                             0x5e;

    /* swaps top 2 values on the stack. */
    public static final byte SWAP =                                0x5f;

    /* adds top two ints on the stack and puts result on the stack. */
    public static final byte IADD =                                0x60;
    /* top two longs. */
    public static final byte LADD =                                0x61;
    /* top two floats. */
    public static final byte FADD =                                0x62;
    /* top two doubles. */
    public static final byte DADD =                                0x63;

    /* subtracts top two ints on the stack and puts result on the stack. */
    public static final byte ISUB =                                0x64;
    /* top two longs. */
    public static final byte LSUB =                                0x65;
    /* top two floats. */
    public static final byte FSUB =                                0x66;
    /* top two doubles. */
    public static final byte DSUB =                                0x67;

    /* multiplies top two ints on the stack and puts result on the stack. */
    public static final byte IMUL =                                0x68;
    /* top two longs. */
    public static final byte LMUL =                                0x69;
    /* top two floats. */
    public static final byte FMUL =                                0x6a;
    /* top two doubles. */
    public static final byte DMUL =                                0x6b;

    /* divides top two ints on the stack and puts result on the stack. */
    public static final byte IDIV =                                0x6c;
    /* top two longs. */
    public static final byte LDIV =                                0x6d;
    /* top two floats. */
    public static final byte FDIV =                                0x6e;
    /* top two doubles. */
    public static final byte DDIV =                                0x6f;

    /* computes the remainder from division of the top two ints on the stack and puts result on the stack. */
    public static final byte IREM =                                0x70;
    /* top two longs. */
    public static final byte LREM =                                0x71;
    /* top two floats. */
    public static final byte FREM =                                0x72;
    /* top two doubles. */
    public static final byte DREM =                                0x73;

    /* negates the top value on the stack and puts the result on the stack. */
    public static final byte INEG =                                0x74;
    /* top long. */
    public static final byte LNEG =                                0x75;
    /* top float. */
    public static final byte FNEG =                                0x76;
    /* top double. */
    public static final byte DNEG =                                0x77;

    /* shifts top int left by 2nd int on the stack and puts result on the stack. */
    public static final byte ISHL =                                0x78;
    /* top long by 2nd long. */
    public static final byte LSHL =                                0x79;

    /* arithmetically shifts top int right by 2nd int on the stack and puts result on the stack. */
    public static final byte ISHR =                                0x7a;
    /* top long by 2nd long. */
    public static final byte LSHR =                                0x7b;

    /* logically shifts top int right by 2nd int on the stack and puts result on the stack. */
    public static final byte IUSHR =                               0x7c;
    /* top long by 2nd long. */
    public static final byte LUSHR =                               0x7d;

    /* performs bitwise and on 1st and 2nd int on the stack. */
    public static final byte IAND =                                0x7e;
    /* 1st and 2nd long. */
    public static final byte LAND =                                0x7f;

    /* performs bitwise or on 1st and 2nd int on the stack. */
    public static final byte IOR =                          (byte) 0x80;
    /* 1st and 2nd long. */
    public static final byte LOR =                          (byte) 0x81;

    /* performs bitwise xor on 1st and 2nd int on the stack. */
    public static final byte IXOR =                         (byte) 0x82;
    /* 1st and 2nd long. */
    public static final byte LXOR =                         (byte) 0x83;

    /* performs bitwise xor on 1st and 2nd int on the stack. */
    public static final byte IINC =                         (byte) 0x84;

    /* converts the int on top of the stack to a long. */
    public static final byte I2L =                          (byte) 0x85;
    /* to a float */
    public static final byte I2F =                          (byte) 0x86;
    /* to a double */
    public static final byte I2D =                          (byte) 0x87;

    /* converts the long on top of the stack to an int. */
    public static final byte L2I =                          (byte) 0x88;
    /* to a float */
    public static final byte L2F =                          (byte) 0x89;
    /* to a double */
    public static final byte L2D =                          (byte) 0x8a;

    /* converts the float on top of the stack to an int. */
    public static final byte F2I =                          (byte) 0x8b;
    /* to a long */
    public static final byte F2L =                          (byte) 0x8c;
    /* to a double */
    public static final byte F2D =                          (byte) 0x8d;

    /* converts the double on top of the stack to an int. */
    public static final byte D2I =                          (byte) 0x8e;
    /* to a long */
    public static final byte D2L =                          (byte) 0x8f;
    /* to a float */
    public static final byte D2F =                          (byte) 0x90;

    /* converts the int on top of the stack to a byte. */
    public static final byte I2B =                          (byte) 0x91;
    /* to a char */
    public static final byte I2C =                          (byte) 0x92;
    /* to a short */
    public static final byte I2S =                          (byte) 0x93;

    /* pushes 0 on the stack if both longs on are equal, 1 if the 2nd on is greater than the top one and -1 otherwise.  */
    public static final byte LCMP =                         (byte) 0x94;

    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    public static final byte FCMPL =                        (byte) 0x95;
    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    public static final byte FCMPG =                        (byte) 0x96;

    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    public static final byte DCMPL =                        (byte) 0x97;
    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    public static final byte DCMPG =                        (byte) 0x98;

    /* if the value on the stack is equal to 0 goto the instruction at instruction offset provided. */
    public static final byte IFEQ =                         (byte) 0x99;
    /* is not equal to 0 */
    public static final byte IFNE =                         (byte) 0x9a;
    /* is less than 0 */
    public static final byte IFLT =                         (byte) 0x9b;
    /* is greater than or equal to 0 */
    public static final byte IFGE =                         (byte) 0x9c;
    /* is greater than 0 */
    public static final byte IFGT =                         (byte) 0x9d;
    /* is less than or equal to 0 */
    public static final byte IFLE =                         (byte) 0x9e;

    /* if the two ints on the stack are equal continue execution at the instruction offset provided. */
    public static final byte IF_ICMPEQ =                    (byte) 0x9f;
    /* are not equal */
    public static final byte IF_ICMPNE =                    (byte) 0xa0;
    /* if the 2nd int on the stack is less than the 1st one */
    public static final byte IF_ICMPLT =                    (byte) 0xa1;
    /* if the 1st value is greater than or equal to the 2nd one */
    public static final byte IF_ICMPGE =                    (byte) 0xa2;
    /* if the 1st value is greater than the 2nd one */
    public static final byte IF_ICMPGT =                    (byte) 0xa3;
    /* if the 1st value is less than or equal to the 2nd value */
    public static final byte IF_ICMPLE =                    (byte) 0xa4;

    /* if the two objects on the stack are equal continue execution at the instruction offset provided. */
    public static final byte IF_ACMPEQ =                    (byte) 0xa5;
    /* are not equal */
    public static final byte IF_ACMPNQ =                    (byte) 0xa6;

    /* continue execution at the instruction offset provided. */
    public static final byte GOTO =                         (byte) 0xa7;

    /* jumps to subroutine and pushes return address onto the stack. */
    public static final byte JSR =                          (byte) 0xa8;
    /* continue execution at the address on top of the stack. */
    public static final byte RET =                          (byte) 0xa9;

    /* access a jump table at the index provided and then continues execution from the indexed location. */
    public static final byte TABLESWITCH =                  (byte) 0xaa;
    /* looks up jump address using the key on top of the stack then continues execution from the indexed location. */
    public static final byte LOOKUPSWITCH =                 (byte) 0xab;

    /* returns an int from the method. */
    public static final byte IRETURN =                      (byte) 0xac;
    /* a long */
    public static final byte LRETURN =                      (byte) 0xad;
    /* a float */
    public static final byte FRETURN =                      (byte) 0xae;
    /* a double */
    public static final byte DRETURN =                      (byte) 0xaf;
    /* an object */
    public static final byte ARETURN =                      (byte) 0xb0;
    /* returns from the method. */
    public static final byte RETURN =                       (byte) 0xb1;

    /* puts the value of static field on top of the stack using the index into the constantpool provided. */
    public static final byte GETSTATIC =                    (byte) 0xb2;
    /* sets a static field's value using the index into the constantpool provided and the value on top of the stack. */
    public static final byte PUTSTATIC =                    (byte) 0xb3;

    /* puts the value of field on top of the stack using the index into the constantpool provided and the object instance on top of the stack. */
    public static final byte GETFIELD =                     (byte) 0xb4;
    /* sets a field's value using the index into the constantpool provided, the instance of the object on top of the stack and the value 2nd on the stack. */
    public static final byte PUTFIELD =                     (byte) 0xb5;

    /* calls a virtual method using the parameters on the stack and the object instance on top of the stack. */
    public static final byte INVOKEVIRTUAL =                (byte) 0xb6;
    /* calls either a private method or a method located in the superclass using the parameters on the stack and the object instance on top of the stack. */
    public static final byte INVOKESPECIAL =                (byte) 0xb7;
    /* calls a static method using the parameters on the stack. */
    public static final byte INVOKESTATIC =                 (byte) 0xb8;
    /* calls an interface-method using the parameters on the stack and the object instance on top of the stack. */
    public static final byte INVOKEINTERFACE =              (byte) 0xb9;
    //TODO: desc.
    public static final byte INVOKEDYNAMIC =                (byte) 0xba;

    public static final byte NEW =                          (byte) 0xbb;

    public static final byte NEWARRAY =                     (byte) 0xbc;

    // TODO: move, desc
    public static final byte IFNULL =                       (byte) 0xc6;
    public static final byte IFNONNULL =                    (byte) 0xc7;
    public static final byte GOTO_W =                       (byte) 0xc8;
    public static final byte JSR_W =                        (byte) 0xc9;

    //TODO: desc.
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
        "aaload",
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

        "iastore",
        "lastore",
        "fastore",
        "dastore",
        "aastore",
        "bastore",
        "castore",
        "sastore",

        "pop",
        "pop2",

        "dup",
        "dup_x1",
        "dup_x2",

        "dup2",
        "dup2_x1",
        "dup2_x2",

        "swap",

        "iadd",
        "ladd",
        "fadd",
        "dadd",

        "isub",
        "lsub",
        "fsub",
        "dsub",

        "imul",
        "lmul",
        "fmul",
        "dmul",

        "idiv",
        "ldiv",
        "fdiv",
        "ddiv",

        "irem",
        "lrem",
        "frem",
        "drem",

        "ineg",
        "lneg",
        "fneg",
        "dneg",

        "ishl",
        "lshl",
        "ishr",
        "lshr",

        "iushr",
        "lushr",

        "iand",
        "land",

        "ior",
        "lor",

        "ixor",
        "lxor",

        "iinc",

        "i2l",
        "i2f",
        "i2d",

        "l2i",
        "l2f",
        "l2d",

        "f2i",
        "f2l",
        "f2d",

        "d2i",
        "d2l",
        "d2f",

        "i2b",
        "i2c",
        "i2s",

        "lcmp",

        "fcmpl",
        "fcmpg",

        "dcmpl",
        "dcmpg",

        "ifeq",
        "ifne",
        "iflt",
        "ifge",
        "ifgt",
        "ifle",

        "if_icmpeq",
        "if_icmpne",
        "if_icmplt",
        "if_icmpge",
        "if_icmpgt",
        "if_icmple",
        "if_acmpeq",
        "if_acmpne",
            
        "goto",

        "jsr",

        "ret",

        "tableswitch",
        "lookupswitch",

        "ireturn",
        "lreturn",
        "freturn",
        "dreturn",
        "areturn",

        "return",

        "getstatic",
        "putstatic",

        "getfield",
        "putfield",

        "invokevirtual",
        "invokespecial",
        "invokestatic",
        "invokeinterface",
        "invokedynamic",

        "new",
        "newarray",
        "anewarray",

        "arraylength",

        "athrow",

        "checkcast",

        "instanceof",

        "monitorenter",
        "monitorexit",

        "wide",

        "multianewarray",

        "ifnull",
        "ifnonnull",

        "goto_w",

        "jsr_w",

        "breakpoint",

        null, // unimplemented instructions
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        
        "impdep1",
        "impdep2",
    };

    /**
     * An array containing the number of parameters required for each instruction.
     */
    public static final byte OPCODE_PARAMETERS[] = {
        0, // nop

        0, // aconst_null

        0, // iconst_m1
        0, // iconst_0
        0, // iconst_1
        0, // iconst_2
        0, // iconst_3
        0, // iconst_4
        0, // iconst_5

        0, // lconst_0
        0, // lconst_1

        0, // fconst_0
        0, // fconst_1
        0, // fconst_2

        0, // dconst_0
        0, // dconst_1

        1, // bipush
        2, // sipush

        1, // ldc
        2, // ldc_w
        2, // ldc2_w

        1, // iload
        1, // lload
        1, // fload
        1, // dload
        1, // aload

        0, // iload_0
        0, // iload_1
        0, // iload_2
        0, // iload_3

        0, // lload_0
        0, // lload_1
        0, // lload_2
        0, // lload_3

        0, // fload_0
        0, // fload_1
        0, // fload_2
        0, // fload_3

        0, // dload_0
        0, // dload_1
        0, // dload_2
        0, // dload_3

        0, // aload_0
        0, // aload_1
        0, // aload_2
        0, // aload_3

        0, // iaload
        0, // laload
        0, // faload
        0, // daload
        0, // aaload
        0, // baload
        0, // caload
        0, // saload

        1, // istore
        1, // lstore
        1, // fstore
        1, // dstore
        1, // astore

        0, // istore_0
        0, // istore_1
        0, // istore_2
        0, // istore_3

        0, // lstore_0
        0, // lstore_1
        0, // lstore_2
        0, // lstore_3

        0, // fstore_0
        0, // fstore_1
        0, // fstore_2
        0, // fstore_3

        0, // dstore_0
        0, // dstore_1
        0, // dstore_2
        0, // dstore_3

        0, // astore_0
        0, // astore_1
        0, // astore_2
        0, // astore_3

        0, // iastore
        0, // lastore
        0, // fastore
        0, // dastore
        0, // aastore
        0, // bastore
        0, // castore
        0, // sastore

        0, // pop
        0, // pop2

        0, // dup
        0, // dup_x1
        0, // dup_x2

        0, // dup2
        0, // dup2_x1
        0, // dup2_x2

        0, // swap

        0, // iadd
        0, // ladd
        0, // fadd
        0, // dadd

        0, // isub
        0, // lsub
        0, // fsub
        0, // dsub

        0, // imul
        0, // lmul
        0, // fmul
        0, // dmul

        0, // idiv
        0, // ldiv
        0, // fdiv
        0, // ddiv

        0, // irem
        0, // lrem
        0, // frem
        0, // drem

        0, // ineg
        0, // lneg
        0, // fneg
        0, // dneg

        0, // ishl
        0, // lshl

        0, // ishr
        0, // lshr

        0, // iushr
        0, // lushr

        0, // iand
        0, // land

        0, // ior
        0, // lor

        0, // ixor
        0, // lxor

        2, // iinc

        0, // i2l
        0, // i2f
        0, // i2d

        0, // l2i
        0, // l2f
        0, // l2d

        0, // f2i
        0, // f2l
        0, // f2d

        0, // d2i
        0, // d2l
        0, // d2f

        0, // i2b
        0, // i2c
        0, // i2s

        0, // lcmp

        0, // fcmpl
        0, // fcmpg

        0, // dcmpl
        0, // dcmpg

        2, // ifeq
        2, // ifne
        2, // iflt
        2, // ifge
        2, // ifgt
        2, // ifle

        2, // if_icmpeq
        2, // if_icmpne
        2, // if_icmplt
        2, // if_icmpge
        2, // if_icmpgt
        2, // if_icmple
        2, // if_acmpeq
        2, // if_acmpne

        2, // goto

        2, // jsr

        1, // ret

        0, // tableswitch //TODO: no clue.
        0, // lookupswitch //TODO: no clue.

        0, // ireturn
        0, // lreturn
        0, // freturn
        0, // dreturn
        0, // areturn

        0, // return

        2, // getstatic
        2, // putstatic

        2, // getfield
        2, // putfield

        2, // invokevirtual
        2, // invokespecial
        2, // invokestatic
        4, // invokeinterface
        4, // invokedynamic

        2, // new
        1, // newarray
        2, // anewarray

        0, // arraylength

        0, // athrow

        2, // checkcast

        2, // instanceof

        0, // monitorenter
        0, // monitorexit

        0, // wide //TODO: no idea i will figure it out later.

        3, // multianewarray

        2, // ifnull
        2, // ifnonnull

        4, // goto_w

        4, // jsr_w

        0, // breakpoint

        0, // unimplemented instructions
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,

        0, // impdep1
        0, // impdep2
    };

    // prevent construction :/
    private Constants()
    {}
}
