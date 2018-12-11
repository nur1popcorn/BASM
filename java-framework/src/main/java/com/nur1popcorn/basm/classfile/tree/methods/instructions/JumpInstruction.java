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

import com.nur1popcorn.basm.classfile.tree.methods.InstructionHandle;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.GOTO_W;
import static com.nur1popcorn.basm.Constants.JSR_W;

public final class JumpInstruction extends Instruction implements IInstructionPointer {
    private int index;

    /**
     * @param opcode
     */
    JumpInstruction(byte opcode, int index) {
        super(opcode);
        this.index = index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitInstructionPointer(this);
        visitor.visitJumpInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        //TODO: fix this
        switch(opcode) {
            case GOTO_W:
            case JSR_W:
                os.writeInt(index);
                break;
            default:
                os.writeShort(index);
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
    public void update(int newIndex) {
        this.index = newIndex;
    }

    /**
     * @return
     */
    public InstructionHandle indexTarget(InstructionList instructions) {
        return instructions.get(index);
    }
}
