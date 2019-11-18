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

import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.*;

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
    /*        <----+------+----------------+-------+--------+--------+----------+---->   *
     *             |      |                |       |        |        |          |        *
     *             |  Op  |    Mnemonic    |  +Op  | -Stack | +Stack |   Type   |        *
     *             |      |                |       |        |        |          |        *
     *        <----+------+----------------+-------+--------+--------+----------+---->   */
    INVALID                  (-0x1,  "invalid",                 -0x1,   -0x1,    -0x1,    NOT_AN_INS),

    /**
     * <b>Description</b>
     * <p>The <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.nop"><i>NOP</i></a>
     *    instruction does nothing giving away the mnemonic's meaning which is an abbreviation
     *    for <q>no operation</q>.</p>
     * <b>Type</b>
     * <p>The <i>NOP</i> instruction is part of the no parameter family.</p>
     */
    NOP                      ( 0x0,  "nop",                      0x0,    0x0,     0x0,    NO_PARAM_INS),

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
    ACONST_NULL              ( 0x1,  "aconst_null",              0x0,    0x0,     0x1,    NO_PARAM_INS),

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
    ICONST_M1                ( 0x2,  "iconst_m1",                0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_0                 ( 0x3,  "iconst_0",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_1                 ( 0x4,  "iconst_1",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_2                 ( 0x5,  "iconst_2",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_3                 ( 0x6,  "iconst_3",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_4                 ( 0x7,  "iconst_4",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    ICONST_5                 ( 0x8,  "iconst_5",                 0x0,    0x0,     0x1,    NO_PARAM_INS),

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
    LCONST_0                 ( 0x9,  "lconst_0",                 0x0,    0x0,     0x2,    NO_PARAM_INS),
    LCONST_1                 ( 0xa,  "lconst_1",                 0x0,    0x0,     0x2,    NO_PARAM_INS),

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
    FCONST_0                 ( 0xb,  "fconst_0",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    FCONST_1                 ( 0xc,  "fconst_1",                 0x0,    0x0,     0x1,    NO_PARAM_INS),
    FCONST_2                 ( 0xd,  "fconst_2",                 0x0,    0x0,     0x1,    NO_PARAM_INS),

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
    DCONST_0                 ( 0xe,  "dconst_0",                 0x0,    0x0,     0x2,    NO_PARAM_INS),
    DCONST_1                 ( 0xf,  "dconst_1",                 0x0,    0x0,     0x2,    NO_PARAM_INS),

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
    BIPUSH                   ( 0x10, "bipush",                   0x1,    0x0,     0x1,    PUSH_INS),

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
    SIPUSH                   ( 0x11, "sipush",                   0x2,    0x0,     0x1,    PUSH_INS),

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
    LDC                      ( 0x12, "ldc",                      0x1,    0x0,     0x1,    LDC_INS),

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
    LDC_W                    ( 0x13, "ldc_w",                    0x2,    0x0,     0x1,    LDC_INS),

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
    LDC2_W                   ( 0x14, "ldc2_w",                   0x2,    0x0,     0x2,    LDC_INS),

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
    ILOAD                    ( 0x15, "iload",                    0x1,    0x0,     0x1,    LOCAL_VARIABLE_INS),
    LLOAD                    ( 0x16, "lload",                    0x1,    0x0,     0x2,    LOCAL_VARIABLE_INS),
    FLOAD                    ( 0x17, "fload",                    0x1,    0x0,     0x1,    LOCAL_VARIABLE_INS),
    DLOAD                    ( 0x18, "dload",                    0x1,    0x0,     0x2,    LOCAL_VARIABLE_INS),
    ALOAD                    ( 0x19, "aload",                    0x1,    0x0,     0x1,    LOCAL_VARIABLE_INS),

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
    ILOAD_0                  ( 0x1a, "iload_0",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ILOAD_1                  ( 0x1b, "iload_1",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ILOAD_2                  ( 0x1c, "iload_2",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ILOAD_3                  ( 0x1d, "iload_3",                  0x0,    0x0,     0x1,    NO_PARAM_INS),

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
    LLOAD_0                  ( 0x1e, "lload_0",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    LLOAD_1                  ( 0x1f, "lload_1",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    LLOAD_2                  ( 0x20, "lload_2",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    LLOAD_3                  ( 0x21, "lload_3",                  0x0,    0x0,     0x2,    NO_PARAM_INS),

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
    FLOAD_0                  ( 0x22, "fload_0",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    FLOAD_1                  ( 0x23, "fload_1",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    FLOAD_2                  ( 0x24, "fload_2",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    FLOAD_3                  ( 0x25, "fload_3",                  0x0,    0x0,     0x1,    NO_PARAM_INS),

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
    DLOAD_0                  ( 0x26, "dload_0",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    DLOAD_1                  ( 0x27, "dload_1",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    DLOAD_2                  ( 0x28, "dload_2",                  0x0,    0x0,     0x2,    NO_PARAM_INS),
    DLOAD_3                  ( 0x29, "dload_3",                  0x0,    0x0,     0x2,    NO_PARAM_INS),

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
    ALOAD_0                  ( 0x2a, "aload_0",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ALOAD_1                  ( 0x2b, "aload_1",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ALOAD_2                  ( 0x2c, "aload_2",                  0x0,    0x0,     0x1,    NO_PARAM_INS),
    ALOAD_3                  ( 0x2d, "aload_3",                  0x0,    0x0,     0x1,    NO_PARAM_INS),

    /* loads value of type int from given array at given index. (arrayref and index should lay on the stack before execution)
       index and arrayref will be popped of the stack after execution and the result will be pushed onto the stack.*/
    IALOAD                   ( 0x2e, "iaload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* of type long. */
    LALOAD                   ( 0x2f, "laload",                   0x0,    0x2,     0x2,    NO_PARAM_INS),
    /* of type float. */
    FALOAD                   ( 0x30, "faload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* of type double. */
    DALOAD                   ( 0x31, "daload",                   0x0,    0x2,     0x2,    NO_PARAM_INS),
    /* puts object reference onto stack. */
    AALOAD                   ( 0x32, "aaload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* of type boolean. */
    BALOAD                   ( 0x33, "baload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* of type char. */
    CALOAD                   ( 0x34, "caload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* of type short. */
    SALOAD                   ( 0x35, "saload",                   0x0,    0x2,     0x1,    NO_PARAM_INS),

    /* stores value of type int on stack in localvariable at given offset. */
    ISTORE                   ( 0x36, "istore",                   0x1,    0x1,     0x0,    LOCAL_VARIABLE_INS),
    /* of type long. */
    LSTORE                   ( 0x37, "lstore",                   0x1,    0x2,     0x0,    LOCAL_VARIABLE_INS),
    /* of type float. */
    FSTORE                   ( 0x38, "fstore",                   0x1,    0x1,     0x0,    LOCAL_VARIABLE_INS),
    /* of type double. */
    DSTORE                   ( 0x39, "dstore",                   0x1,    0x2,     0x0,    LOCAL_VARIABLE_INS),
    /* stores object reference. */
    ASTORE                   ( 0x3a, "astore",                   0x1,    0x1,     0x0,    LOCAL_VARIABLE_INS),

    /* stores value of type int in N th localvariable. */
    ISTORE_0                 ( 0x3b, "istore_0",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ISTORE_1                 ( 0x3c, "istore_1",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ISTORE_2                 ( 0x3d, "istore_2",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ISTORE_3                 ( 0x3e, "istore_3",                 0x0,    0x1,     0x0,    NO_PARAM_INS),

    /* of type long. */
    LSTORE_0                 ( 0x3f, "lstore_0",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    LSTORE_1                 ( 0x40, "lstore_1",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    LSTORE_2                 ( 0x41, "lstore_2",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    LSTORE_3                 ( 0x42, "lstore_3",                 0x0,    0x2,     0x0,    NO_PARAM_INS),

    /* of type float. */
    FSTORE_0                 ( 0x43, "fstore_0",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    FSTORE_1                 ( 0x44, "fstore_1",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    FSTORE_2                 ( 0x45, "fstore_2",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    FSTORE_3                 ( 0x46, "fstore_3",                 0x0,    0x1,     0x0,    NO_PARAM_INS),

    /* of type long. */
    DSTORE_0                 ( 0x47, "dstore_0",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    DSTORE_1                 ( 0x48, "dstore_1",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    DSTORE_2                 ( 0x49, "dstore_2",                 0x0,    0x2,     0x0,    NO_PARAM_INS),
    DSTORE_3                 ( 0x4a, "dstore_3",                 0x0,    0x2,     0x0,    NO_PARAM_INS),

    /* stores object reference. */
    ASTORE_0                 ( 0x4b, "astore_0",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ASTORE_1                 ( 0x4c, "astore_1",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ASTORE_2                 ( 0x4d, "astore_2",                 0x0,    0x1,     0x0,    NO_PARAM_INS),
    ASTORE_3                 ( 0x4e, "astore_3",                 0x0,    0x1,     0x0,    NO_PARAM_INS),

    /* stores value of type int in at given index in given array
       (arrayref, index, value)*/
    IASTORE                  ( 0x4f, "iastore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),
    /* of type long. */
    LASTORE                  ( 0x50, "lastore",                  0x0,    0x4,     0x0,    NO_PARAM_INS),
    /* of type float. */
    FASTORE                  ( 0x51, "fastore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),
    /* stores object reference. */
    AASTORE                  ( 0x53, "aastore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),
    /* of type bool. */
    BASTORE                  ( 0x54, "bastore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),
    /* of type char. */
    CASTORE                  ( 0x55, "castore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),
    /* of type short. */
    SASTORE                  ( 0x56, "sastore",                  0x0,    0x3,     0x0,    NO_PARAM_INS),

    /* discards top value on stack. */
    POP                      ( 0x57, "pop",                      0x0,    0x1,     0x0,    NO_PARAM_INS),
    /* discards top 2 values or 1st if the value is of type long or double. */
    POP2                     ( 0x58, "pop2",                     0x0,    0x2,     0x0,    NO_PARAM_INS),

    /* puts a copy of the top value on the stack on the stack. */
    DUP                      ( 0x59, "dup",                      0x0,    0x1,     0x2,    NO_PARAM_INS),
    /* a copy of the 2nd value on the stack. */
    DUP_X1                   ( 0x5a, "dup_x1",                   0x0,    0x2,     0x3,    NO_PARAM_INS),
    /* a copy of the 3rd value on the stack. */
    DUP_X2                   ( 0x5b, "dup_x2",                   0x0,    0x3,     0x4,    NO_PARAM_INS),

    /* puts a copy of the 1st and 2nd or 1st if the value of type long or double on the stack on the stack. */
    DUP2                     ( 0x5c, "dup2",                     0x0,    0x2,     0x4,    NO_PARAM_INS),
    /* a copy of the 2nd and 3rd value on the stack. */
    DUP2_X1                  ( 0x5d, "dup2_x1",                  0x0,    0x3,     0x5,    NO_PARAM_INS),
    /* a copy of the 3rd and 4th value on the stack. */
    DUP2_X2                  ( 0x5e, "dup2_x2",                  0x0,    0x4,     0x6,    NO_PARAM_INS),

    /* swaps top 2 values on the stack. */
    SWAP                     ( 0x5f, "swap",                     0x0,    0x2,     0x2,    NO_PARAM_INS),

    /* adds top two ints on the stack and puts result on the stack. */
    IADD                     ( 0x60, "iadd",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two longs. */
    LADD                     ( 0x61, "ladd",                     0x0,    0x4,     0x2,    NO_PARAM_INS),
    /* top two floats. */
    FADD                     ( 0x62, "fadd",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two doubles. */
    DADD                     ( 0x63, "dadd",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* subtracts top two ints on the stack and puts result on the stack. */
    ISUB                     ( 0x64, "isub",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two longs. */
    LSUB                     ( 0x65, "lsub",                     0x0,    0x4,     0x2,    NO_PARAM_INS),
    /* top two floats. */
    FSUB                     ( 0x66, "fsub",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two doubles. */
    DSUB                     ( 0x67, "dsub",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* multiplies top two ints on the stack and puts result on the stack. */
    IMUL                     ( 0x68, "imul",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two longs. */
    LMUL                     ( 0x69, "lmul",                     0x0,    0x4,     0x2,    NO_PARAM_INS),
    /* top two floats. */
    FMUL                     ( 0x6a, "fmul",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two doubles. */
    DMUL                     ( 0x6b, "dmul",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* divides top two ints on the stack and puts result on the stack. */
    IDIV                     ( 0x6c, "idiv",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two longs. */
    LDIV                     ( 0x6d, "ldiv",                     0x0,    0x4,     0x2,    NO_PARAM_INS),
    /* top two floats. */
    FDIV                     ( 0x6e, "fdiv",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two doubles. */
    DDIV                     ( 0x6f, "ddiv",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* computes the remainder from division of the top two ints on the stack and puts result on the stack. */
    IREM                     ( 0x70, "irem",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two longs. */
    LREM                     ( 0x71, "lrem",                     0x0,    0x4,     0x2,    NO_PARAM_INS),
    /* top two floats. */
    FREM                     ( 0x72, "frem",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top two doubles. */
    DREM                     ( 0x73, "drem",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* negates the top value on the stack and puts the result on the stack. */
    INEG                     ( 0x74, "ineg",                     0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* top long. */
    LNEG                     ( 0x75, "lneg",                     0x0,    0x2,     0x2,    NO_PARAM_INS),
    /* top float. */
    FNEG                     ( 0x76, "fneg",                     0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* top double. */
    DNEG                     ( 0x77, "dneg",                     0x0,    0x2,     0x2,    NO_PARAM_INS),

    /* shifts top int left by 2nd int on the stack and puts result on the stack. */
    ISHL                     ( 0x78, "ishl",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top long by 2nd long. */
    LSHL                     ( 0x79, "lshl",                     0x0,    0x3,     0x2,    NO_PARAM_INS),
    /* arithmetically shifts top int right by 2nd int on the stack and puts result on the stack. */
    ISHR                     ( 0x7a, "ishr",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top long by 2nd long. */
    LSHR                     ( 0x7b, "lshr",                     0x0,    0x3,     0x2,    NO_PARAM_INS),

    /* logically shifts top int right by 2nd int on the stack and puts result on the stack. */
    IUSHR                    ( 0x7c, "iushr",                    0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* top long by 2nd long. */
    LUSHR                    ( 0x7d, "lushr",                    0x0,    0x3,     0x2,    NO_PARAM_INS),

    /* performs bitwise and on 1st and 2nd int on the stack. */
    IAND                     ( 0x7e, "iand",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* 1st and 2nd long. */
    LAND                     ( 0x7f, "land",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* performs bitwise or on 1st and 2nd int on the stack. */
    IOR                      ( 0x80, "ior",                      0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* 1st and 2nd long. */
    LOR                      ( 0x81, "lor",                      0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* performs bitwise xor on 1st and 2nd int on the stack. */
    IXOR                     ( 0x82, "ixor",                     0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* 1st and 2nd long. */
    LXOR                     ( 0x83, "lxor",                     0x0,    0x4,     0x2,    NO_PARAM_INS),

    /* performs bitwise xor on 1st and 2nd int on the stack. */
    IINC                     ( 0x84, "iinc",                     0x2,    0x0,     0x0,    IINC_INS),

    /* converts the int on top of the stack to a long. */
    I2L                      ( 0x85, "i2l",                      0x0,    0x1,     0x2,    NO_PARAM_INS),
    /* to a float */
    I2F                      ( 0x86, "i2f",                      0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* to a double */
    I2D                      ( 0x87, "i2d",                      0x0,    0x1,     0x2,    NO_PARAM_INS),

    /* converts the long on top of the stack to an int. */
    L2I                      ( 0x88, "l2i",                      0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* to a float */
    L2F                      ( 0x89, "l2f",                      0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* to a double */
    L2D                      ( 0x8a, "l2d",                      0x0,    0x2,     0x2,    NO_PARAM_INS),

    /* converts the float on top of the stack to an int. */
    F2I                      ( 0x8b, "f2i",                      0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* to a long */
    F2L                      ( 0x8c, "f2l",                      0x0,    0x1,     0x2,    NO_PARAM_INS),
    /* to a double */
    F2D                      ( 0x8d, "f2d",                      0x0,    0x1,     0x2,    NO_PARAM_INS),

    /* converts the double on top of the stack to an int. */
    D2I                      ( 0x8e, "d2i",                      0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* to a long */
    D2L                      ( 0x8f, "d2l",                      0x0,    0x2,     0x2,    NO_PARAM_INS),
    /* to a float */
    D2F                      ( 0x90, "d2f",                      0x0,    0x2,     0x1,    NO_PARAM_INS),

    /* converts the int on top of the stack to a byte. */
    I2B                      ( 0x91, "i2b",                      0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* to a char */
    I2C                      ( 0x92, "i2c",                      0x0,    0x1,     0x1,    NO_PARAM_INS),
    /* to a short */
    I2S                      ( 0x93, "i2s",                      0x0,    0x1,     0x1,    NO_PARAM_INS),

    /* pushes 0 on the stack if both longs on are equal, 1 if the 2nd on is greater than the top one and -1 otherwise.  */
    LCMP                     ( 0x94, "lcmp",                     0x0,    0x4,     0x1,    NO_PARAM_INS),

    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    FCMPL                    ( 0x95, "fcmpl",                    0x0,    0x2,     0x1,    NO_PARAM_INS),
    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    FCMPG                    ( 0x96, "fcmpg",                    0x0,    0x2,     0x1,    NO_PARAM_INS),

    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    DCMPL                    ( 0x97, "dcmpl",                    0x0,    0x4,     0x1,    NO_PARAM_INS),
    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    DCMPG                    ( 0x98, "dcmpg",                    0x0,    0x4,     0x1,    NO_PARAM_INS),

    /* if the value on the stack is equal to 0 goto the instruction at instruction offset provided. */
    IFEQ                     ( 0x99, "ifeq",                     0x2,    0x1,     0x0,    JUMP_INS),
    /* is not equal to 0 */
    IFNE                     ( 0x9a, "ifne",                     0x2,    0x1,     0x0,    JUMP_INS),
    /* is less than 0 */
    IFLT                     ( 0x9b, "iflt",                     0x2,    0x1,     0x0,    JUMP_INS),
    /* is greater than or equal to 0 */
    IFGE                     ( 0x9c, "ifge",                     0x2,    0x1,     0x0,    JUMP_INS),
    /* is greater than 0 */
    IFGT                     ( 0x9d, "ifgt",                     0x2,    0x1,     0x0,    JUMP_INS),
    /* is less than or equal to 0 */
    IFLE                     ( 0x9e, "ifle",                     0x2,    0x1,     0x0,    JUMP_INS),

    /* if the two ints on the stack are equal continue execution at the instruction offset provided. */
    IF_ICMPEQ                ( 0x9f, "if_icmpeq",                0x2,    0x2,     0x0,    JUMP_INS),
    /* are not equal */
    IF_ICMPNE                ( 0xa0, "if_icmpne",                0x2,    0x2,     0x0,    JUMP_INS),
    /* if the 2nd int on the stack is less than the 1st one */
    IF_ICMPLT                ( 0xa1, "if_icmplt",                0x2,    0x2,     0x0,    JUMP_INS),
    /* if the 1st value is greater than or equal to the 2nd one */
    IF_ICMPGE                ( 0xa2, "if_icmpge",                0x2,    0x2,     0x0,    JUMP_INS),
    /* if the 1st value is greater than the 2nd one */
    IF_ICMPGT                ( 0xa3, "if_icmpgt",                0x2,    0x2,     0x0,    JUMP_INS),
    /* if the 1st value is less than or equal to the 2nd value */
    IF_ICMPLE                ( 0xa4, "if_icmple",                0x2,    0x2,     0x0,    JUMP_INS),
    /* if the two objects on the stack are equal continue execution at the instruction offset provided. */
    IF_ACMPEQ                ( 0xa5, "if_acmpeq",                0x2,    0x2,     0x0,    JUMP_INS),
    /* are not equal */
    IF_ACMPNQ                ( 0xa6, "if_acmpne",                0x2,    0x2,     0x0,    JUMP_INS),

    /* continue execution at the instruction offset provided. */
    GOTO                     ( 0xa7, "goto",                     0x2,    0x0,     0x0,    JUMP_INS),
    /* jumps to subroutine and pushes return address onto the stack. */
    JSR                      ( 0xa8, "jsr",                      0x2,    0x0,     0x1,    JUMP_INS),
    /* continue execution at the address on top of the stack. */
    RET                      ( 0xa9, "ret",                      0x1,    0x0,     0x0,    LOCAL_VARIABLE_INS),

    /* access a jump table at the index provided and then continues execution from the indexed location. */
    TABLESWITCH              ( 0xaa, "tableswitch",             -0x1,    0x1,     0x0,    SWITCH_INS),
    /* looks up jump address using the key on top of the stack then continues execution from the indexed location. */
    LOOKUPSWITCH             ( 0xab, "lookupswitch",            -0x1,    0x1,     0x0,    SWITCH_INS),

    /* returns an int from the method. */
    IRETURN                  ( 0xac, "ireturn",                  0x0,    0x1,     0x0,    NO_PARAM_INS),
    /* a long */
    LRETURN                  ( 0xad, "lreturn",                  0x0,    0x2,     0x0,    NO_PARAM_INS),
    /* a float */
    FRETURN                  ( 0xae, "freturn",                  0x0,    0x1,     0x0,    NO_PARAM_INS),
    /* a double */
    DRETURN                  ( 0xaf, "dreturn",                  0x0,    0x2,     0x0,    NO_PARAM_INS),
    /* an object */
    ARETURN                  ( 0xb0, "areturn",                  0x0,    0x1,     0x0,    NO_PARAM_INS),
    /* returns from the method. */
    RETURN                   ( 0xb1, "return",                   0x0,    0x0,     0x0,    NO_PARAM_INS),

    /* puts the value of static field on top of the stack using the index into the constantpool provided. */
    GETSTATIC                ( 0xb2, "getstatic",                0x2,    0x0,    -0x1,    FIELD_INS),

    /* sets a static field's value using the index into the constantpool provided and the value on top of the stack. */
    PUTSTATIC                ( 0xb3, "putstatic",                0x2,   -0x1,     0x0,    FIELD_INS),

    /* puts the value of field on top of the stack using the index into the constantpool provided and the object instance on top of the stack. */
    GETFIELD                 ( 0xb4, "getfield",                 0x2,    0x1,    -0x1,    FIELD_INS),

    /* sets a field's value using the index into the constantpool provided, the instance of the object on top of the stack and the value 2nd on the stack. */
    PUTFIELD                 ( 0xb5, "putfield",                 0x2,   -0x1,     0x0,    FIELD_INS),

    /* calls a virtual method using the parameters on the stack and the object instance on top of the stack. */
    INVOKEVIRTUAL            ( 0xb6, "invokevirtual",            0x2,   -0x1,    -0x1,    METHOD_INS),

    /* calls either a private method or a method located in the superclass using the parameters on the stack and the object instance on top of the stack. */
    INVOKESPECIAL            ( 0xb7, "invokespecial",            0x2,   -0x1,    -0x1,    METHOD_INS),

    /* calls a static method using the parameters on the stack. */
    INVOKESTATIC             ( 0xb8, "invokestatic",             0x2,   -0x1,    -0x1,    METHOD_INS),

    /* calls an interface-method using the parameters on the stack and the object instance on top of the stack. */
    INVOKEINTERFACE          ( 0xb9, "invokeinterface",          0x4,   -0x1,    -0x1,    METHOD_INS),
    INVOKEDYNAMIC            ( 0xba, "invokedynamic",            0x4,   -0x1,    -0x1,    INVOKEDYNAMIC_INS),

    NEW                      ( 0xbb, "new",                      0x2,    0x0,     0x1,    CLASS_INS),
    NEWARRAY                 ( 0xbc, "newarray",                 0x1,    0x1,     0x1,    NEWARRAY_INS),
    ANEWARRAY                ( 0xbd, "anewarray",                0x2,    0x1,     0x1,    CLASS_INS),

    ARRAYLENGTH              ( 0xbe, "arraylength",              0x0,    0x1,     0x1,    NO_PARAM_INS),
    ATHROW                   ( 0xbf, "athrow",                   0x0,    0x1,     0x1,    NO_PARAM_INS),
    CHECKCAST                ( 0xc0, "checkcast",                0x2,    0x1,     0x1,    CLASS_INS),
    INSTANCEOF               ( 0xc1, "instanceof",               0x2,    0x1,     0x1,    CLASS_INS),
    MONITORENTER             ( 0xc2, "monitorenter",             0x0,    0x1,     0x0,    NO_PARAM_INS),
    MONITOREXIT              ( 0xc3, "monitorexit",              0x0,    0x1,     0x0,    NO_PARAM_INS),

    WIDE                     ( 0xc4, "wide",                    -0x1,    0x0,     0x0,    WIDE_INS),
    MULTIANEWARRAY           ( 0xc5, "multianewarray",           0x3,   -0x1,     0x1,    MULTIANEWARRAY_INS),

    IFNULL                   ( 0xc6, "ifnull",                   0x2,    0x1,     0x0,    JUMP_INS),
    IFNONNULL                ( 0xc7, "ifnonnull",                0x2,    0x1,     0x0,    JUMP_INS),
    GOTO_W                   ( 0xc8, "goto_w",                   0x4,    0x0,     0x0,    JUMP_INS),
    JSR_W                    ( 0xc9, "jsr_w",                    0x4,    0x0,     0x1,    JUMP_INS),

    BREAKPOINT               ( 0xca, "breakpoint",               0x0,    0x0,     0x0,    NO_PARAM_INS),

    LDC_QUICK                ( 0xcb, "ldc_quick",                0x1,   -0x1,    -0x1,    RESERVED_INS),
    LDC_W_QUICK              ( 0xcc, "ldc_w_quick",              0x2,   -0x1,    -0x1,    RESERVED_INS),
    LDC2_W_QUICK             ( 0xcd, "ldc2_w_quick",             0x2,   -0x1,    -0x1,    RESERVED_INS),
    GETFIELD_QUICK           ( 0xce, "getfield_quick",           0x2,   -0x1,    -0x1,    RESERVED_INS),
    PUTFIELD_QUICK           ( 0xcf, "putfield_quick",           0x2,   -0x1,    -0x1,    RESERVED_INS),
    GETFIELD2_QUICK          ( 0xd0, "getfield2_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    PUTFIELD2_QUICK          ( 0xd1, "putfield2_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    GETSTATIC_QUICK          ( 0xd2, "getstatic_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    PUTSTATIC_QUICK          ( 0xd3, "putstatic_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    GETSTATIC2_QUICK         ( 0xd4, "getstatic2_quick",         0x2,   -0x1,    -0x1,    RESERVED_INS),
    PUTSTATIC2_QUICK         ( 0xd5, "putstatic2_quick",         0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKEVIRTUAL_QUICK      ( 0xd6, "invokevirtual_quick",      0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKENONVIRTUAL_QUICK   ( 0xd7, "invokenonvirtual_quick",   0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKESUPER_QUICK        ( 0xd8, "invokesuper_quick",        0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKESTATIC_QUICK       ( 0xd9, "invokestatic_quick",       0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKEINTERFACE_QUICK    ( 0xda, "invokeinterface_quick",    0x4,   -0x1,    -0x1,    RESERVED_INS),
    INVOKEVIRTUALOBJECT_QUICK( 0xdb, "invokevirtualobject_quick",0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKEIGNORED_QUICK      ( 0xdc, "invokeignored_quick",      0x2,   -0x1,    -0x1,    RESERVED_INS),
    NEW_QUICK                ( 0xdd, "new_quick",                0x2,   -0x1,    -0x1,    RESERVED_INS),
    ANEWARRAY_QUICK          ( 0xde, "anewarray_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    MULTIANEWARRAY_QUICK     ( 0xdf, "multianewarray_quick",     0x3,   -0x1,    -0x1,    RESERVED_INS),
    CHECKCAST_QUICK          ( 0xe0, "checkcast_quick",          0x2,   -0x1,    -0x1,    RESERVED_INS),
    INSTANCEOF_QUICK         ( 0xe1, "instanceof_quick",         0x2,   -0x1,    -0x1,    RESERVED_INS),
    INVOKEVIRTUAL_QUICK_W    ( 0xe2, "invokevirtual_quick_w",    0x2,   -0x1,    -0x1,    RESERVED_INS),
    GETFIELD_QUICK_W         ( 0xe3, "getfield_quick_w",         0x2,   -0x1,    -0x1,    RESERVED_INS),
    PUTFIELD_QUICK_W         ( 0xe4, "putfield_quick_w",         0x2,   -0x1,    -0x1,    RESERVED_INS),
    NONNULL_QUICK            ( 0xe5, "nonnull_quick",            0x0,   -0x1,    -0x1,    RESERVED_INS),
    EXITINTERPRETER          ( 0xe6, "exitinterpreter",          0x0,   -0x1,    -0x1,    RESERVED_INS),

    IMPDEP1                  ( 0xfe, "impdep1",                  0x0,   -0x1,    -0x1,    NO_PARAM_INS),
    IMPDEP2                  ( 0xff, "impdep2",                  0x0,   -0x1,    -0x1,    NO_PARAM_INS);

    private final byte opcode;
    private final String mnemonic;
    private final byte parameter, stackPop, stackPush;
    private final InstructionType type;

    /**
     * @param opcode The opcode of the instruction.
     * @param mnemonic The mnemonic of the instruction.
     * @param stackPop The number of slots that will be popped from the stack
     * @param stackPush The number of slots that will be pushed onto the stack.
     */
    Opcode(int opcode, String mnemonic, int parameter, int stackPop, int stackPush, InstructionType type) {
        this.opcode = (byte) opcode;
        this.mnemonic = mnemonic;
        this.parameter = (byte) parameter;
        this.stackPop = (byte) stackPop;
        this.stackPush = (byte) stackPush;
        this.type = type;
    }

    /**
     * @param opcode The opcode which should be returned.
     * @return The {@link Opcode} with the value which was provided or null if the value is invalid.
     */
    public static Opcode valueOf(byte opcode) {
        final Opcode opcodes[] = values();
        final int o = opcode & 0xff;

        int low = 1;
        int high = opcodes.length - 1;
        while(low <= high) {
            final int mid = (low + high) >>> 1;
            final int op = opcodes[mid]
                .getOpcode() & 0xff;

            if(o < op)
                high = mid - 1;
            else if(o > op)
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

    public InstructionType getType() {
        return type;
    }
}
