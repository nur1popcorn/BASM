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

package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.ICodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

/**
 * The {@link NoParameterInstruction} is an {@link Instruction} with no parameters meaning that it just consists of it's opcode.
 * <p>The list provided lists all no parameter opcodes:</p>
 * <ul>
 *    <li>nop</li>
 *
 *    <li>aconst_null</li>
 *
 *    <li>iconst_m1</li>
 *    <li>iconst_0</li>
 *    <li>iconst_1</li>
 *    <li>iconst_2</li>
 *    <li>iconst_3</li>
 *    <li>iconst_4</li>
 *    <li>iconst_5</li>
 *
 *    <li>lconst_0</li>
 *    <li>lconst_1</li>
 *
 *    <li>fconst_0</li>
 *    <li>fconst_1</li>
 *    <li>fconst_2</li>
 *
 *    <li>dconst_0</li>
 *    <li>dconst_1</li>
 *
 *    <li>iload_0</li>
 *    <li>iload_1</li>
 *    <li>iload_2</li>
 *    <li>iload_3</li>
 *
 *    <li>lload_0</li>
 *    <li>lload_1</li>
 *    <li>lload_2</li>
 *    <li>lload_3</li>
 *
 *    <li>fload_0</li>
 *    <li>fload_1</li>
 *    <li>fload_2</li>
 *    <li>fload_3</li>
 *
 *    <li>dload_0</li>
 *    <li>dload_1</li>
 *    <li>dload_2</li>
 *    <li>dload_3</li>
 *
 *    <li>aload_0</li>
 *    <li>aload_1</li>
 *    <li>aload_2</li>
 *    <li>aload_3</li>
 *
 *    <li>iaload</li>
 *    <li>laload</li>
 *    <li>faload</li>
 *    <li>daload</li>
 *    <li>aaload</li>
 *    <li>baload</li>
 *    <li>caload</li>
 *    <li>saload</li>
 *
 *    <li>istore_0</li>
 *    <li>istore_1</li>
 *    <li>istore_2</li>
 *    <li>istore_3</li>
 *
 *    <li>lstore_0</li>
 *    <li>lstore_1</li>
 *    <li>lstore_2</li>
 *    <li>lstore_3</li>
 *
 *    <li>fstore_0</li>
 *    <li>fstore_1</li>
 *    <li>fstore_2</li>
 *    <li>fstore_3</li>
 *
 *    <li>dstore_0</li>
 *    <li>dstore_1</li>
 *    <li>dstore_2</li>
 *    <li>dstore_3</li>
 *
 *    <li>astore_0</li>
 *    <li>astore_1</li>
 *    <li>astore_2</li>
 *    <li>astore_3</li>
 *
 *    <li>iastore</li>
 *    <li>lastore</li>
 *    <li>fastore</li>
 *    <li>dastore</li>
 *    <li>aastore</li>
 *    <li>bastore</li>
 *    <li>castore</li>
 *    <li>sastore</li>
 *
 *    <li>pop</li>
 *    <li>pop2</li>
 *
 *    <li>dup</li>
 *    <li>dup_x1</li>
 *    <li>dup_x2</li>
 *    <li>dup2</li>
 *    <li>dup2_x1</li>
 *    <li>dup2_x2</li>
 *
 *    <li>swap</li>
 *
 *    <li>iadd</li>
 *    <li>ladd</li>
 *    <li>fadd</li>
 *    <li>dadd</li>
 *
 *    <li>isub</li>
 *    <li>lsub</li>
 *    <li>fsub</li>
 *    <li>dsub</li>
 *
 *    <li>imul</li>
 *    <li>lmul</li>
 *    <li>fmul</li>
 *    <li>dmul</li>
 *
 *    <li>idiv</li>
 *    <li>ldiv</li>
 *    <li>fdiv</li>
 *    <li>ddiv</li>
 *
 *    <li>irem</li>
 *    <li>lrem</li>
 *    <li>frem</li>
 *    <li>drem</li>
 *
 *    <li>ineg</li>
 *    <li>lneg</li>
 *    <li>fneg</li>
 *    <li>dneg</li>
 *
 *    <li>ishl</li>
 *    <li>lshl</li>
 *    <li>ishr</li>
 *    <li>lshr</li>
 *
 *    <li>iushr</li>
 *    <li>lushr</li>
 *
 *    <li>iand</li>
 *    <li>land</li>
 *
 *    <li>ior</li>
 *    <li>lor</li>
 *
 *    <li>ixor</li>
 *    <li>lxor</li>
 *
 *    <li>iinc</li>
 *
 *    <li>i2l</li>
 *    <li>i2f</li>
 *    <li>i2d</li>
 *
 *    <li>l2i</li>
 *    <li>l2f</li>
 *    <li>l2d</li>
 *
 *    <li>f2i</li>
 *    <li>f2l</li>
 *    <li>f2d</li>
 *
 *    <li>d2i</li>
 *    <li>d2l</li>
 *    <li>d2f</li>
 *
 *    <li>i2b</li>
 *    <li>i2c</li>
 *    <li>i2s</li>
 *
 *    <li>lcmp</li>
 *    <li>fcmpl</li>
 *    <li>fcmpg</li>
 *    <li>dcmpl</li>
 *    <li>dcmpg</li>
 *
 *    <li>ireturn</li>
 *    <li>lreturn</li>
 *    <li>freturn</li>
 *    <li>dreturn</li>
 *    <li>areturn</li>
 *
 *    <li>return</li>
 *
 *    <li>arraylength</li>
 *
 *    <li>athrow</li>
 *
 *    <li>breakpoint</li>
 *
 *    <li>impdep1</li>
 *    <li>impdep2</li>
 * </ul>
 *
 * @see Instruction
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class NoParameterInstruction extends Instruction {
    // TODO: desc constructor.
    public NoParameterInstruction(byte opcode) {
        super(opcode);
    }

    @Override
    public void accept(ICodeVisitor visitor) {
        visitor.visitNoParameterInstruction(this);
    }
}
