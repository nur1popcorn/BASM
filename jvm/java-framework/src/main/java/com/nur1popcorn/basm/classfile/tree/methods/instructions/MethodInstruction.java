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
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.INVOKEINTERFACE;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.METHOD_INS;

public final class MethodInstruction extends FieldMethodInstruction {
    private int count;

    /**
     * @param opcode
     * @param info
     * @param cp
     */
    public MethodInstruction(Opcode opcode, ConstantMethodRef info, ConstantPool cp) {
        super(opcode, info, cp);
    }

    public MethodInstruction(ConstantMethodRef info, int count, ConstantPool cp) {
        super(INVOKEINTERFACE, info, cp);
        this.count = count;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        super.accept(visitor);
        visitor.visitMethodInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        if(getOpcode() == INVOKEINTERFACE) {
            os.writeByte(count);
            os.writeByte(0);
        }
    }

    @Override
    public int getConsumeStack() {
        int result = 0;
        switch(getOpcode()) {
            case INVOKEVIRTUAL:
            case INVOKESPECIAL:
            case INVOKEINTERFACE:
                result++;
                // fallthrough.
            case INVOKESTATIC:
                for(Type parameter : getDesc().getParameters())
                    result += parameter.getStackModifier();
        }
        return result;
    }

    @Override
    public int getProduceStack() {
        return getDesc().getReturnType()
            .getStackModifier();
    }

    @Override
    public InstructionType getType() {
        return METHOD_INS;
    }
}
