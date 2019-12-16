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
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.JUMP_INS;

public final class JumpInstruction extends Instruction implements IInstructionPointer {
    private Label target;

    /**
     * @param opcode
     */
    public JumpInstruction(Opcode opcode, Label target) {
        super(opcode);
        if(opcode.getType() != JUMP_INS)
            throw new IllegalArgumentException();
        (this.target = target)
            .addPointer(this);
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
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        final int length = target.getOffset() - getOffset();
        switch(getOpcode()) {
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
    public void dispose() {
        target.removePointer(this);
    }

    public Label getTarget() {
        return target;
    }

    public void setTarget(Label target) {
        this.target = target;
    }
}
