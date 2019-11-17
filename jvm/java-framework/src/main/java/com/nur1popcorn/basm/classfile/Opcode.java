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
    INVALID                             (-0x1,  "invalid",       -0x1,   -0x1,   -0x1),

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
     * <p>The <i>ICONST_&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter
     *    is embedded within the instruction itself.</p>
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
     * <p>The <i>ICONST_&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter
     *    is embedded within the instruction itself.</p>
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
     *    instructions push their respective <i>&lt;l&gt;</i> &isin; { 0, 1 } value onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>LCONST_&lt;l&gt;</i> instruction is part of the no parameter family, as the parameter
     *    is embedded within the instruction itself.</p>
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
     *    instructions push their respective <i>&lt;f&gt;</i> &isin; { 0.0, 1.0, 2.0 } value onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>FCONST_&lt;f&gt;</i> instruction is part of the no parameter family, as the parameter
     *    is embedded within the instruction itself.</p>
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
     *    instructions push their respective <i>&lt;d&gt;</i> &isin; { 0.0, 1.0 } value onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>DCONST_&lt;d&gt;</i> instruction is part of the no parameter family, as the parameter
     *    is embedded within the instruction itself.</p>
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
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iload"><i>ILOAD</i></a>,
     *        <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.lload"><i>LLOAD</i></a>,
     *        <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fload"><i>FLOAD</i></a>,
     *        <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.dload"><i>DLOAD</i></a> and
     *        <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.aload"><i>ALOAD</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> local variable of type int, long, float, double or reference
     *    onto the stack. The parameter <i>&lt;i&gt;</i> is embedded within the instruction using a single byte.</p>
     * <b>Type</b>
     * <p>The <i>ILOAD</i>, <i>LLOAD</i>, <i>FLOAD</i>, <i>DLOAD</i> and <i>ALOAD</i> instructions are part
     *    of the local variable instruction family.</p>
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
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> &isin; { 0, 1, 2, 3 } local variable of type int
     *    onto the stack.</p>
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

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.lload_n"><i>LLOAD&lt;i&gt;</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> &isin; { 0, 1, 2, 3 } local variable of type
     *    long onto the stack. </p>
     * <b>Type</b>
     * <p>The <i>LLOAD&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
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
    LLOAD_0                             (0x1e, "lload_0",         0x0,    0x0,    0x2),
    LLOAD_1                             (0x1f, "lload_1",         0x0,    0x0,    0x2),
    LLOAD_2                             (0x20, "lload_2",         0x0,    0x0,    0x2),
    LLOAD_3                             (0x21, "lload_3",         0x0,    0x0,    0x2),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fload_n"><i>FLOAD&lt;i&gt;</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> &isin; { 0, 1, 2, 3 } local variable of type
     *    float onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>FLOAD&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
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
    FLOAD_0                             (0x22, "fload_0",         0x0,    0x0,    0x1),
    FLOAD_1                             (0x23, "fload_1",         0x0,    0x0,    0x1),
    FLOAD_2                             (0x24, "fload_2",         0x0,    0x0,    0x1),
    FLOAD_3                             (0x25, "fload_3",         0x0,    0x0,    0x1),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fload_n"><i>DLOAD&lt;i&gt;</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> &isin; { 0, 1, 2, 3 } local variable of type
     *    double onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>DLOAD&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
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
    DLOAD_0                             (0x26, "dload_0",         0x0,    0x0,    0x2),
    DLOAD_1                             (0x27, "dload_1",         0x0,    0x0,    0x2),
    DLOAD_2                             (0x28, "dload_2",         0x0,    0x0,    0x2),
    DLOAD_3                             (0x29, "dload_3",         0x0,    0x0,    0x2),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.fload_n"><i>ALOAD&lt;i&gt;</i></a>
     *    instructions push the <i>&lt;i&gt;</i><sup>th</sup> &isin; { 0, 1, 2, 3 } local variable of type
     *    reference onto the stack.</p>
     * <b>Type</b>
     * <p>The <i>ALOAD&lt;i&gt;</i> instruction is part of the no parameter family, as the parameter is
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
    ALOAD_0                             (0x2a, "aload_0",         0x0,    0x0,    0x1),
    ALOAD_1                             (0x2b, "aload_1",         0x0,    0x0,    0x1),
    ALOAD_2                             (0x2c, "aload_2",         0x0,    0x0,    0x1),
    ALOAD_3                             (0x2d, "aload_3",         0x0,    0x0,    0x1),

    IALOAD                              (0x2e, "iaload",          0x0,    0x2,    0x1),
    LALOAD                              (0x2f, "laload",          0x0,    0x2,    0x2),
    FALOAD                              (0x30, "faload",          0x0,    0x2,    0x1),
    DALOAD                              (0x31, "daload",          0x0,    0x2,    0x2),
    AALOAD                              (0x32, "aaload",          0x0,    0x2,    0x1),
    BALOAD                              (0x33, "baload",          0x0,    0x2,    0x1),
    CALOAD                              (0x34, "caload",          0x0,    0x2,    0x1),
    SALOAD                              (0x35, "saload",          0x0,    0x2,    0x1),

    ISTORE                              (0x36, "istore",          0x1,    0x1,    0x0),
    LSTORE                              (0x37, "lstore",          0x1,    0x2,    0x0),
    FSTORE                              (0x38, "fstore",          0x1,    0x1,    0x0),
    DSTORE                              (0x39, "dstore",          0x1,    0x2,    0x0),
    ASTORE                              (0x3a, "astore",          0x1,    0x1,    0x0),

    ISTORE_0                            (0x3b, "istore_0",        0x0,    0x1,    0x0),
    ISTORE_1                            (0x3c, "istore_1",        0x0,    0x1,    0x0),
    ISTORE_2                            (0x3d, "istore_2",        0x0,    0x1,    0x0),
    ISTORE_3                            (0x3e, "istore_3",        0x0,    0x1,    0x0),

    LSTORE_0                            (0x3f, "lstore_0",        0x0,    0x2,    0x0),
    LSTORE_1                            (0x40, "lstore_1",        0x0,    0x2,    0x0),
    LSTORE_2                            (0x41, "lstore_2",        0x0,    0x2,    0x0),
    LSTORE_3                            (0x42, "lstore_3",        0x0,    0x2,    0x0),

    FSTORE_0                            (0x43, "fstore_0",        0x0,    0x1,    0x0),
    FSTORE_1                            (0x44, "fstore_1",        0x0,    0x1,    0x0),
    FSTORE_2                            (0x45, "fstore_2",        0x0,    0x1,    0x0),
    FSTORE_3                            (0x46, "fstore_3",        0x0,    0x1,    0x0),

    DSTORE_0                            (0x47, "dstore_0",        0x0,    0x2,    0x0),
    DSTORE_1                            (0x48, "dstore_1",        0x0,    0x2,    0x0),
    DSTORE_2                            (0x49, "dstore_2",        0x0,    0x2,    0x0),
    DSTORE_3                            (0x4a, "dstore_3",        0x0,    0x2,    0x0),

    ASTORE_0                            (0x4b, "astore_0",        0x0,    0x1,    0x0),
    ASTORE_1                            (0x4c, "astore_1",        0x0,    0x1,    0x0),
    ASTORE_2                            (0x4d, "astore_2",        0x0,    0x1,    0x0),
    ASTORE_3                            (0x4e, "astore_3",        0x0,    0x1,    0x0),

    IASTORE                             (0x4f, "iastore",         0x0,    0x3,    0x0),
    LASTORE                             (0x50, "lastore",         0x0,    0x4,    0x0),
    FASTORE                             (0x51, "fastore",         0x0,    0x3,    0x0),
    DASTORE                             (0x52, "dastore",         0x0,    0x4,    0x0),
    AASTORE                             (0x53, "aastore",         0x0,    0x3,    0x0),
    BASTORE                             (0x54, "bastore",         0x0,    0x3,    0x0),
    CASTORE                             (0x55, "castore",         0x0,    0x3,    0x0),
    SASTORE                             (0x56, "sastore",         0x0,    0x3,    0x0),

    POP                                 (0x57, "pop",             0x0,    0x1,    0x0),
    POP2                                (0x58, "pop2",            0x0,    0x2,    0x0),

    DUP                                 (0x59, "dup",             0x0,    0x1,    0x2),
    DUP_X1                              (0x5a, "dup_x1",          0x0,    0x2,    0x3),
    DUP_X2                              (0x5b, "dup_x2",          0x0,    0x3,    0x4),

    DUP2                                (0x5c, "dup2",            0x0,    0x2,    0x4),
    DUP2_X1                             (0x5d, "dup2_x1",         0x0,    0x3,    0x5),
    DUP2_X2                             (0x5e, "dup2_x2",         0x0,    0x4,    0x6),

    SWAP                                (0x5f, "swap",            0x0,    0x2,    0x2),

    IADD                                (0x60, "iadd",            0x0,    0x2,    0x1),
    LADD                                (0x61, "ladd",            0x0,    0x4,    0x2),
    FADD                                (0x62, "fadd",            0x0,    0x2,    0x1),
    DADD                                (0x63, "dadd",            0x0,    0x4,    0x2),

    ISUB                                (0x64, "isub",            0x0,    0x2,    0x1),
    LSUB                                (0x65, "lsub",            0x0,    0x4,    0x2),
    FSUB                                (0x66, "fsub",            0x0,    0x2,    0x1),
    DSUB                                (0x67, "dsub",            0x0,    0x4,    0x2),

    IMUL                                (0x68, "imul",            0x0,    0x2,    0x1),
    LMUL                                (0x69, "lmul",            0x0,    0x4,    0x2),
    FMUL                                (0x6a, "fmul",            0x0,    0x2,    0x1),
    DMUL                                (0x6b, "dmul",            0x0,    0x4,    0x2),

    IDIV                                (0x6c, "idiv",            0x0,    0x2,    0x1),
    LDIV                                (0x6d, "ldiv",            0x0,    0x4,    0x2),
    FDIV                                (0x6e, "fdiv",            0x0,    0x2,    0x1),
    DDIV                                (0x6f, "ddiv",            0x0,    0x4,    0x2),

    IREM                                (0x70, "irem",            0x0,    0x2,    0x1),
    LREM                                (0x71, "lrem",            0x0,    0x4,    0x2),
    FREM                                (0x72, "frem",            0x0,    0x2,    0x1),
    DREM                                (0x73, "drem",            0x0,    0x4,    0x2),

    INEG                                (0x74, "ineg",            0x0,    0x1,    0x1),
    LNEG                                (0x75, "lneg",            0x0,    0x2,    0x2),
    FNEG                                (0x76, "fneg",            0x0,    0x1,    0x1),
    DNEG                                (0x77, "dneg",            0x0,    0x2,    0x2),

    ISHL                                (0x78, "ishl",            0x0,    0x2,    0x1),
    LSHL                                (0x79, "lshl",            0x0,    0x3,    0x2),
    ISHR                                (0x7a, "ishr",            0x0,    0x2,    0x1),
    LSHR                                (0x7b, "lshr",            0x0,    0x3,    0x2),

    IUSHR                               (0x7c, "iushr",           0x0,    0x2,    0x1),
    LUSHR                               (0x7d, "lushr",           0x0,    0x3,    0x2),

    IAND                                (0x7e, "iand",            0x0,    0x2,    0x1),
    LAND                                (0x7f, "land",            0x0,    0x4,    0x2),

    IOR                                 (0x80, "ior",             0x0,    0x2,    0x1),
    LOR                                 (0x81, "lor",             0x0,    0x4,    0x2),

    IXOR                                (0x82, "ixor",            0x0,    0x2,    0x1),
    LXOR                                (0x83, "lxor",            0x0,    0x4,    0x2),

    IINC                                (0x84, "iinc",            0x2,    0x0,    0x0),

    I2L                                 (0x85, "i2l",             0x0,    0x1,    0x2),
    I2F                                 (0x86, "i2f",             0x0,    0x1,    0x1),
    I2D                                 (0x87, "i2d",             0x0,    0x1,    0x2),

    L2I                                 (0x88, "l2i",             0x0,    0x2,    0x1),
    L2F                                 (0x89, "l2f",             0x0,    0x2,    0x1),
    L2D                                 (0x8a, "l2d",             0x0,    0x2,    0x2),

    F2I                                 (0x8b, "f2i",             0x0,    0x1,    0x1),
    F2L                                 (0x8c, "f2l",             0x0,    0x1,    0x2),
    F2D                                 (0x8d, "f2d",             0x0,    0x1,    0x2),

    D2I                                 (0x8e, "d2i",             0x0,    0x2,    0x1),
    D2L                                 (0x8f, "d2l",             0x0,    0x2,    0x2),
    D2F                                 (0x90, "d2f",             0x0,    0x2,    0x1),

    I2B                                 (0x91, "i2b",             0x0,    0x1,    0x1),
    I2C                                 (0x92, "i2c",             0x0,    0x1,    0x1),
    I2S                                 (0x93, "i2s",             0x0,    0x1,    0x1),

    LCMP                                (0x94, "lcmp",            0x0,    0x4,    0x1),

    FCMPL                               (0x95, "fcmpl",           0x0,    0x2,    0x1),
    FCMPG                               (0x96, "fcmpg",           0x0,    0x2,    0x1),

    DCMPL                               (0x97, "dcmpl",           0x0,    0x4,    0x1),
    DCMPG                               (0x98, "dcmpg",           0x0,    0x4,    0x1),

    IFEQ                                (0x99, "ifeq",            0x2,    0x1,    0x0),
    IFNE                                (0x9a, "ifne",            0x2,    0x1,    0x0),
    IFLT                                (0x9b, "iflt",            0x2,    0x1,    0x0),
    IFGE                                (0x9c, "ifge",            0x2,    0x1,    0x0),
    IFGT                                (0x9d, "ifgt",            0x2,    0x1,    0x0),
    IFLE                                (0x9e, "ifle",            0x2,    0x1,    0x0),

    IF_ICMPEQ                           (0x9f, "if_icmpeq",       0x2,    0x2,    0x0),
    IF_ICMPNE                           (0xa0, "if_icmpne",       0x2,    0x2,    0x0),
    IF_ICMPLT                           (0xa1, "if_icmplt",       0x2,    0x2,    0x0),
    IF_ICMPGE                           (0xa2, "if_icmpge",       0x2,    0x2,    0x0),
    IF_ICMPGT                           (0xa3, "if_icmpgt",       0x2,    0x2,    0x0),
    IF_ICMPLE                           (0xa4, "if_icmple",       0x2,    0x2,    0x0),
    IF_ACMPEQ                           (0xa5, "if_acmpeq",       0x2,    0x2,    0x0),
    IF_ACMPNQ                           (0xa6, "if_acmpne",       0x2,    0x2,    0x0),

    GOTO                                (0xa7, "goto",            0x2,    0x0,    0x0),
    JSR                                 (0xa8, "jsr",             0x2,    0x0,    0x1),
    RET                                 (0xa9, "ret",             0x1,    0x0,    0x0),

    TABLESWITCH                         (0xaa, "tableswitch",    -0x1,    0x1,    0x0),
    LOOKUPSWITCH                        (0xab, "lookupswitch",   -0x1,    0x1,    0x0),

    IRETURN                             (0xac, "ireturn",         0x0,    0x1,    0x0),
    LRETURN                             (0xad, "lreturn",         0x0,    0x2,    0x0),
    FRETURN                             (0xae, "freturn",         0x0,    0x1,    0x0),
    DRETURN                             (0xaf, "dreturn",         0x0,    0x2,    0x0),
    ARETURN                             (0xb0, "areturn",         0x0,    0x1,    0x0),
    RETURN                              (0xb1, "return",          0x0,    0x0,    0x0),

    GETSTATIC                           (0xb2, "getstatic",       0x2,    0x0,   -0x1),
    PUTSTATIC                           (0xb3, "putstatic",       0x2,   -0x1,    0x0),

    GETFIELD                            (0xb4, "getfield",        0x2,    0x1,   -0x1),
    PUTFIELD                            (0xb5, "putfield",        0x2,   -0x1,    0x0),

    INVOKEVIRTUAL                       (0xb6, "invokevirtual",   0x2,   -0x1,   -0x1),
    INVOKESPECIAL                       (0xb7, "invokespecial",   0x2,   -0x1,   -0x1),
    INVOKESTATIC                        (0xb8, "invokestatic",    0x2,   -0x1,   -0x1),
    INVOKEINTERFACE                     (0xb9, "invokeinterface", 0x4,   -0x1,   -0x1),
    INVOKEDYNAMIC                       (0xba, "invokedynamic",   0x4,   -0x1,   -0x1),

    NEW                                 (0xbb, "new",             0x2,    0x0,    0x1),
    NEWARRAY                            (0xbc, "newarray",        0x1,    0x1,    0x1),
    ANEWARRAY                           (0xbd, "anewarray",       0x2,    0x1,    0x1),

    ARRAYLENGTH                         (0xbe, "arraylength",     0x0,    0x1,    0x1),
    ATHROW                              (0xbf, "athrow",          0x0,    0x1,    0x1),
    CHECKCAST                           (0xc0, "checkcast",       0x2,    0x1,    0x1),
    INSTANCEOF                          (0xc1, "instanceof",      0x2,    0x1,    0x1),
    MONITORENTER                        (0xc2, "monitorenter",    0x0,    0x1,    0x0),
    MONITOREXIT                         (0xc3, "monitorexit",     0x0,    0x1,    0x0),

    WIDE                                (0xc4, "wide",           -0x1,    0x0,    0x0),
    MULTIANEWARRAY                      (0xc5, "multianewarray",  0x3,   -0x1,    0x1),

    IFNULL                              (0xc6, "ifnull",          0x2,    0x1,    0x0),
    IFNONNULL                           (0xc7, "ifnonnull",       0x2,    0x1,    0x0),
    GOTO_W                              (0xc8, "goto_w",          0x4,    0x0,    0x0),
    JSR_W                               (0xc9, "jsr_w",           0x4,    0x0,    0x1),

    BREAKPOINT                          (0xca, "breakpoint",      0x0,    0x0,    0x0),
    IMPDEP1                             (0xfe, "impdep1",         0x0,   -0x1,   -0x1),
    IMPDEP2                             (0xff, "impdep2",         0x0,   -0x1,   -0x1);

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

        int low = 1;
        int high = opcodes.length - 1;
        while(low <= high) {
            final int mid = (low + high) >>> 1;
            final int op = opcodes[mid]
                .getOpcode() & 0xff;

            if(opcode < op)
                high = mid - 1;
            else if(opcode > op)
                low = mid + 1;
            else
                return opcodes[mid];
        }
        return INVALID;
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
