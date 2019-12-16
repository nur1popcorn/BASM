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
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.BIPUSH;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.PUSH_INS;

public final class PushInstruction extends Instruction {
    /*
     *
     */
    private short value;

    /**
     * @param opcode
     */
    public PushInstruction(Opcode opcode, short value) {
        super(opcode);
        if(opcode.getType() != PUSH_INS)
            throw new IllegalArgumentException();
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitPushInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        if(getOpcode() == BIPUSH)
            os.writeByte(value);
        else //if(getOpcode() == SIPUSH)
            os.writeShort(value);
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
}
