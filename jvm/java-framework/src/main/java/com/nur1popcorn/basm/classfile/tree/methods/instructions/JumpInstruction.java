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

import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.IInstructionPointer;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;
import com.nur1popcorn.basm.utils.ByteDataOutputStream;

import java.io.IOException;

public final class JumpInstruction extends Instruction  implements IInstructionPointer {
    private int target;

    /**
     * @param opcode
     */
    JumpInstruction(Opcode opcode, int target) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(ByteDataOutputStream os) throws IOException {
        final int offset = os.size();
        os.writeByte(opcode.getOpcode());
        final int length = os.getOffset(target) - offset;
        switch(opcode) {
            case GOTO_W:
            case JSR_W:
                os.writeInt(length);
                break;
            default:
                os.writeShort(length);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(InstructionList instructions) {
        indexTarget(instructions)
            .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(InstructionList instructions) {
        indexTarget(instructions)
            .removePointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        if(target == oldIndex)
            target = newIndex;
    }

    /**
     * @return
     */
    public Instruction indexTarget(InstructionList instructions) {
        return instructions.get(target);
    }
}
