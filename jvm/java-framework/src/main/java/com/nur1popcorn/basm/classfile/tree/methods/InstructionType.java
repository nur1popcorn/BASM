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
package com.nur1popcorn.basm.classfile.tree.methods;

public enum InstructionType {
    /**
     * This constant denotes instructions, which are not implemented by the JVM. These instructions
     * are associated with opcodes ranging from xCB to xFD and are reserved for future use.
     *
     * TODO: mention quick instructions ?
     */
    NOT_AN_INS,

    /**
     * This constant denotes instructions, which solely consist of their opcode, they therefore tend
     * to also have a very predictable effect on the stack.
     */
    NO_PARAM_INS,

    /**
     * <p>This constant denotes instructions, which sign-extend their parameters and push onto the stack.
     *    The constant is employed by the following 2 instructions: </p>
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.bipush">
     *             bipush
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.sipush">
     *             sipush
     *         </a>
     *     </li>
     * </ul>
     */
    PUSH_INS,

    /**
     * <p>This constant denotes instructions, which push entries from the constant pool onto the stack.
     *    The constant is employed by the following 3 instructions: </p>
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc">
     *             ldc
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w">
     *             ldc_w
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w">
     *             ldc2_w
     *         </a>
     *     </li>
     * </ul>
     * <p>The instructions are very predictable in terms of their effect on the stack ldc & ldc_w will
     *    increment the stack size by 1 while ldc2_w will only increment it by 2.</p>
     */
    LDC_INS,

    /**
     * This constant denotes instruction, which require a local variable index to be present as a parameter.
     */
    LOCAL_VARIABLE_INS,

    /**
     *
     */
    IINC_INS,

    /**
     *
     */
    JUMP_INS,

    /**
     *
     */
    SWITCH_INS,

    /**
     *
     */
    FIELD_INS,

    /**
     *
     */
    METHOD_INS,

    /**
     *
     */
    INVOKEDYNAMIC_INS,

    /**
     *
     */
    CLASS_INS,

    /**
     *
     */
    NEWARRAY_INS,

    /**
     *
     */
    WIDE_INS,

    /**
     *
     */
    MULTIANEWARRAY_INS,

    /**
     *
     */
    RESERVED_INS,
}
