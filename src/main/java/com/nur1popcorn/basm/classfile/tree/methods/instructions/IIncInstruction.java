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

import static com.nur1popcorn.basm.Constants.IINC;

public final class IIncInstruction extends Instruction {
    public byte index;
    public byte constant;

    public IIncInstruction(byte index, byte constant) {
        super(IINC);
        this.index = index;
        this.constant = constant;
    }

    @Override
    public void accept(ICodeVisitor visitor) {
        visitor.visitIIncInstruction(this);
    }
}
