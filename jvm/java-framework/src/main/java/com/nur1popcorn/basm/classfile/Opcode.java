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

/**
 * The {@link Opcode} enum contains detailed information about every instruction which can be interpreted
 * by the JVM such as it's affect on the stack, the type, length and so forth.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html">
 *    Chapter 6. The Java Virtual Machine Instruction Set
 * </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public enum Opcode {
    /*                             <----+------+----------------+-------+--------+-------->   *
     *                                  |      |                |       |        |            *
     *                                  |  Op  |    Mnemonic    |  +Op  | -Stack | +Stack     *
     *                                  |      |                |       |        |            *
     *                             <----+------+----------------+-------+--------+-------->   */

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.nop"><i>NOP</i></a>
     *    instruction does nothing giving away the mnemonic's meaning which is an abbreviation
     *    for <q>no operation</q>.</p>
     * <b>Type</b>
     * <p>The <i>NOP</i> instruction is part of the no parameter family.</p>
     */
    NOP                                 (0x0,  "nop",             0x0,    0x0,    0x0),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.aconst_null"><i>ACONST_NULL</i></a>
     *    instruction does nothing giving away the mnemonic's meaning which is an abbreviation
     *    for <q>no operation</q>.</p>
     * Puts a null reference on the stack.
     * <b>Type</b>
     * <p>The <i>ICONST_&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>null</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    ACONST_NULL                         (0x1,  "aconst_null",     0x0,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iconst_i"><i>ICONST_&lt;i&gt;</i></a>
     *    instructions push their respective <i>&lt;i&gt;</i> &isin; { -1, 0, 1, 2, 3, 4, 5 } value
     *    onto the stack. The instructions are equivalent to <i>bipush</i> instruction, with the
     *    <i>bipush</i> instruction only differing in one regard that being the parameterlessness of
     *    the <i>ICONST_&lt;i&gt;</i> instructions.</p>
     * <b>Type</b>
     * <p>The <i>ICONST_&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;i&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    ICONST_M1                           (0x2,  "iconst_m1",       0x0,    0x0,    0x1),
    ICONST_0                            (0x3,  "iconst_0",        0x0,    0x0,    0x1),
    ICONST_1                            (0x4,  "iconst_1",        0x0,    0x0,    0x1),
    ICONST_2                            (0x5,  "iconst_2",        0x0,    0x0,    0x1),
    ICONST_3                            (0x6,  "iconst_3",        0x0,    0x0,    0x1),
    ICONST_4                            (0x7,  "iconst_4",        0x0,    0x0,    0x1),
    ICONST_5                            (0x8,  "iconst_5",        0x0,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.lconst_l"><i>LCONST_&lt;l&gt;</i></a>
     *    instructions push their respective <i>&lt;l&gt;</i> &isin; { 0, 1 } value
     *    onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>LCONST_&lt;l&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;l&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    LCONST_0                            (0x9,  "lconst_0",        0x0,    0x0,    0x2),
    LCONST_1                            (0xa,  "lconst_1",        0x0,    0x0,    0x2),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fconst_f"><i>FCONST_&lt;f&gt;</i></a>
     *    instructions push their respective <i>&lt;f&gt;</i> &isin; { 0.0, 1.0, 2.0 } value
     *    onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>FCONST_&lt;f&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;f&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    FCONST_0                            (0xb,  "fconst_0",        0x0,    0x0,    0x1),
    FCONST_1                            (0xc,  "fconst_1",        0x0,    0x0,    0x1),
    FCONST_2                            (0xd,  "fconst_2",        0x0,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.dconst_d"><i>DCONST_&lt;d&gt;</i></a>
     *    instructions push their respective <i>&lt;d&gt;</i> &isin; { 0.0, 1.0 } value
     *    onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>DCONST_&lt;d&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;d&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    DCONST_0                            (0xe,  "dconst_0",        0x0,    0x0,    0x2),
    DCONST_1                            (0xf,  "dconst_1",        0x0,    0x0,    0x2),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.bipush"><i>BIPUSH</i></a>
     *    instruction pushes it's respective parameter <i>&lt;p&gt;</i> &isin; { n: -(2^7) &le; n &le; (2^7) - 1 }
     *    onto the stack. The parameter <i>&lt;p&gt;</i> is embedded within the instruction using a single byte.</p>
     * <b>Type</b>
     * <p>The <i>BIPUSH</i> instruction is part of push instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;p&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    BIPUSH                              (0x10, "bipush",          0x1,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.sipush"><i>SIPUSH</i></a>
     *    instruction pushes it's respective parameter <i>&lt;p&gt;</i> &isin; { n: -(2^15) &le; n &le; (2^15) - 1 }
     *    onto the stack. The parameter <i>&lt;p&gt;</i> is embedded within the instruction using two bytes.</p>
     * <b>Type</b>
     * <p>The <i>SIPUSH</i> instruction is part of push instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>&lt;p&gt;</i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    SIPUSH                              (0x11, "sipush",          0x2,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc"><i>LDC</i></a>
     *    instruction pushes a constant of type int, float or reference onto the stack. It's respective parameter
     *    <i>&lt;i&gt;</i> acts as an index into the constant pool. The parameter <i>&lt;i&gt;</i> is embedded within
     *    the instruction using a single byte.</p>
     * <b>Type</b>
     * <p>The <i>LDC</i> instruction is part of ldc instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>cp<sub>&lt;i&gt;</sub></i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    LDC                                 (0x12, "ldc",             0x1,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w"><i>LDC_W</i></a>
     *    instruction pushes a constant of type int, float or reference onto the stack. It's respective parameter
     *    <i>&lt;i&gt;</i> acts as an index into the constant pool. The parameter <i>&lt;i&gt;</i> is embedded within
     *    the instruction using a two bytes. These two bytes <i>&lt;i<sub>0</sub>&gt;</i> and <i>&lt;i<sub>1</sub>&gt;</i>
     *    are assembled into an index as follows:
     *    <code><i>&lt;i&gt;</i> := (<i>&lt;i<sub>0</sub>&gt;</i> << 8) | <i>&lt;i<sub>1</sub>&gt;</i></code></p>
     * <b>Type</b>
     * <p>The <i>LDC_W</i> instruction is part of ldc instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>cp<sub>&lt;i&gt;</sub></i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    LDC_W                               (0x13, "ldc_w",           0x2,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w"><i>LDC2_W</i></a>
     *    instruction pushes a constant of type double or long onto the stack. It's respective parameter
     *    <i>&lt;i&gt;</i> acts as an index into the constant pool. The parameter <i>&lt;i&gt;</i> is embedded within
     *    the instruction using a two bytes. These two bytes <i>&lt;i<sub>0</sub>&gt;</i> and <i>&lt;i<sub>1</sub>&gt;</i>
     *    are assembled into an index as follows:
     *    <code><i>&lt;i&gt;</i> := (<i>&lt;i<sub>0</sub>&gt;</i> << 8) | <i>&lt;i<sub>1</sub>&gt;</i></code></p>
     * <b>Type</b>
     * <p>The <i>LDC2_W</i> instruction is part of ldc instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>cp<sub>&lt;i&gt;</sub></i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    LDC2_W                              (0x14, "ldc2_w",          0x2,    0x0,    0x2),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iconst_i"><i>&lt;t&gt;LOAD</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> local variable of type int, long, float, double or a reference
     *    onto the stack. The parameter <i>&lt;i&gt;</i> is embedded within the instruction using a single byte.</p>
     * <b>Type</b>
     * <p>The <i>&lt;t&gt;LOAD</i> instructions are part of the local variable instruction family.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>locals<sub>&lt;i&gt;</sub></i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    ILOAD                               (0x15, "iload",           0x1,    0x0,    0x1),
    LLOAD                               (0x16, "lload",           0x1,    0x0,    0x2),
    FLOAD                               (0x17, "fload",           0x1,    0x0,    0x1),
    DLOAD                               (0x18, "dload",           0x1,    0x0,    0x2),
    ALOAD                               (0x19, "aload",           0x1,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iconst_i"><i>ILOAD&lt;i&gt;</i></a>
     *    instructions push the <i>&lt;i&gt;</i> &isin; { 0, 1, 2, 3 } local variable of type int onto the stack.
     *    The parameter <i>&lt;i&gt;</i> is embedded within the instruction using a single byte.</p>
     * <b>Type</b>
     * <p>The <i>ILOAD&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
     *    embedded within the instruction itself.</p>
     * <b>Stack</b>
     * <table border="1">
     *     <tbody>
     *         <tr>
     *             <td>
     *                 <b>Before</b>
     *             </td>
     *             <td>
     *                 <b>After</b>
     *             </td>
     *         </tr>
     *         <tr>
     *             <td>
     *                 <p>...</p>
     *             </td>
     *             <td>
     *                 <i>locals<sub>&lt;i&gt;</sub></i>
     *             </td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    ILOAD_0                             (0x1a, "iload_0",         0x0,    0x0,    0x1),
    ILOAD_1                             (0x1b, "iload_1",         0x0,    0x0,    0x1),
    ILOAD_2                             (0x1c, "iload_2",         0x0,    0x0,    0x1),
    ILOAD_3                             (0x1d, "iload_3",         0x0,    0x0,    0x1),

    IINC                                (0x84, "iinc",            0x2,    0x0,    0x0),

    WIDE                                (0xc4, "wide",           -0x1,   -0x1,   -0x1),

    TABLESWITCH                         (0xaa, "tableswitch",    -0x1,    0x0,    0x0),
    LOOKUPSWITCH                        (0xab, "lookupswitch",   -0x1,    0x1,    0x0);

    private final byte opcode;
    private final String mnemonic;
    private final byte parameter, stackPop, stackPush;

    /**
     * @param opcode The opcode of the instruction.
     * @param mnemonic The mnemonic of the instruction.
     * @param stackPop The number of slots that will be popped from the stack
     * @param stackPush The number of slots that will be pushed onto the stack.
     */
    Opcode(int opcode, String mnemonic, int parameter, int stackPop, int stackPush) {
        this.opcode = (byte) opcode;
        this.mnemonic = mnemonic;
        this.parameter = (byte) parameter;
        this.stackPop = (byte) stackPop;
        this.stackPush = (byte) stackPush;
    }

    /**
     * @param opcode The opcode which should be returned.
     * @return The {@link Opcode} with the value which was provided or null if the value is invalid.
     */
    public static Opcode valueOf(byte opcode) {
        final Opcode opcodes[] = values();

        int low = 0;
        int high = opcodes.length - 1;
        while(low <= high) {
            final int mid = (low + high) >>> 1;
            final byte op = opcodes[mid]
                .getOpcode();

            if(opcode < op)
                high = mid - 1;
            else if(opcode > op)
                low = mid + 1;
            else
                return opcodes[mid];
        }

        return null;
    }

    /**
     * @return The opcode of the instruction.
     */
    public final byte getOpcode() {
        return opcode;
    }

    /**
     * @return The mnemonic of the instruction.
     */
    public final String getMnemonic() {
        return mnemonic;
    }

    /**
     * @return The size of the whole instruction (without the opcode) in bytes or -1 if the size can vary.
     */
    public final byte getParameter() {
        return parameter;
    }

    /**
     * @return The number of slots that will be popped from the stack if the instruction is executed or
     *         -1 if the number of slots can vary.
     */
    public final byte getStackPop() {
        return stackPop;
    }

    /**
     * @return The number of slots that will be pushed onto the stack if the instruction is executed or
     *         -1 if the number of slots can vary.
     */
    public final byte getStackPush() {
        return stackPush;
    }
}
