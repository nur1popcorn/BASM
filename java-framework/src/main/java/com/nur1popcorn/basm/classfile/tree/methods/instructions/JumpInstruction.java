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

import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.GOTO_W;
import static com.nur1popcorn.basm.Constants.JSR_W;

public final class JumpInstruction extends Instruction {
    private int target;

    /**
     * @param opcode
     */
    JumpInstruction(byte opcode, int target) {
        super(opcode);
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitJumpInstruction(this);
    }

    private static int computeOffset(InstructionList instructions, int position, int targetIndex) {
        return instructions.computeIndex(targetIndex) - position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os, InstructionList instructions) throws IOException {
        final int position = os.size();
        os.writeByte(opcode);
        final int targetIndex = computeOffset(instructions, position, target);
        switch(opcode) {
            case GOTO_W:
            case JSR_W:
                os.writeInt(targetIndex);
                break;
            default:
                os.writeShort(targetIndex);
                break;
        }
    }
}
