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
 * The {@link LocalVariableInstruction} is an {@link Instruction} with 1 parameter indicating the
 *                                       localvariable which should be read or manipulated.
 * <p>The list provided lists all opcodes with a localvariable index as a parameter:</p>
 * <ul>
 *    <li>iload</li>
 *    <li>lload</li>
 *    <li>fload</li>
 *    <li>dload</li>
 *    <li>aload</li>
 *
 *    <li>istore</li>
 *    <li>lstore</li>
 *    <li>fstore</li>
 *    <li>dstore</li>
 *    <li>astore</li>
 *
 *    <li>ret</li>
 * </ul>
 *
 * @see Instruction
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class LocalVariableInstruction extends Instruction {
    public byte index;

    // TODO: desc constructor.
    public LocalVariableInstruction(byte opcode, byte index) {
        super(opcode);
        this.index = index;
    }

    @Override
    public void accept(ICodeVisitor visitor) {
        visitor.visitLocalVariableInstruction(this);
    }
}
