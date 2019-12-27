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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.FIELD_INS;

public final class FieldInstruction extends FieldMethodInstruction {
    /**
     * @param opcode
     * @param info
     * @param cp
     */
    public FieldInstruction(Opcode opcode, ConstantMethodRef info, ConstantPool cp) {
        super(opcode, info, cp);
        if(opcode.getType() != FIELD_INS)
            throw new IllegalArgumentException();
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPInstruction(this);
        visitor.visitFieldMethodInstruction(this);
        visitor.visitFieldInstruction(this);
    }

    @Override
    public int getProduceStack() {
        switch(getOpcode()) {
            case GETFIELD:
            case GETSTATIC:
                return getDesc().getStackModifier();
            default:
                return super.getProduceStack();
        }
    }

    @Override
    public int getConsumeStack() {
        int result = 0;
        switch(getOpcode()) {
            case PUTFIELD:
                result++;
                // fallthrough.
            case PUTSTATIC:
                result += getDesc()
                    .getStackModifier();
                return result;
            default:
                return super.getConsumeStack();
        }
    }
}
