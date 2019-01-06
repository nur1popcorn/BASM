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

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.IINC;
import static com.nur1popcorn.basm.Constants.WIDE;

public final class WideInstruction extends Instruction {
    private byte opcode;
    private int index, constant;

    /**
     * @param index
     */
    WideInstruction(int index, int constant) {
        this(IINC, index);
        this.constant = constant;
    }

    /**
     * @param opcode
     */
    WideInstruction(byte opcode, int index) {
        super(WIDE);
        this.opcode = opcode;
        this.index = index;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitWideInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(opcode);
        os.writeShort(index);
        if(opcode == IINC)
            os.writeShort(constant);
    }

    public byte getOpcodeParameter() {
        return opcode;
    }
}
