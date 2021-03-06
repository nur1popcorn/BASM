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
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.INVOKEINTERFACE;

public final class MethodInstruction extends FieldMethodInstruction {
    private int count;

    /**
     * @param cp
     * @param opcode
     * @param index
     */
    MethodInstruction(byte opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
    }

    MethodInstruction(int index, int count, ConstantPool cp) {
        super(INVOKEINTERFACE, index, cp);
        this.count = count;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitCPInstruction(this);
        visitor.visitFieldMethodInstruction(this);
        visitor.visitMethodInstruction(this);
    }

    @Override
    public void write(DataOutputStream os, InstructionList instructions) throws IOException {
        super.write(os, instructions);
        if(opcode == INVOKEINTERFACE) {
            os.writeByte(count & 0xff);
            os.writeByte(0);
        }
    }
}
