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

import static com.nur1popcorn.basm.classfile.Opcode.IINC;
import static com.nur1popcorn.basm.classfile.Opcode.WIDE;

public final class WideInstruction extends Instruction {
    private Opcode opcodeParameter;
    private int index, constant;

    /**
     * @param index
     */
    public WideInstruction(int index, int constant) {
        this(IINC, index);
        this.constant = constant;
    }

    /**
     * @param opcodeParameter
     */
    public WideInstruction(Opcode opcodeParameter, int index) {
        super(WIDE);
        this.opcodeParameter = opcodeParameter;
        this.index = index;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitWideInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(opcodeParameter.getOpcode());
        os.writeShort(index);
        if(opcodeParameter == IINC)
            os.writeShort(constant);
    }

    @Override
    public int getLength() {
        return opcodeParameter == IINC ? 6 : 4;
    }

    public Opcode getOpcodeParameter() {
        return opcodeParameter;
    }

    public void setOpcodeParameter(Opcode opcodeParameter) {
        this.opcodeParameter = opcodeParameter;
    }
}
