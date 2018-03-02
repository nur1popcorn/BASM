package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import static com.nur1popcorn.basm.Constants.SIPUSH;

public final class SIPushInstruction extends Instruction {
    public static final byte SIPUSH_INSTRUCTION = 7;

    public short data;

    public SIPushInstruction(short data) {
        super(SIPUSH);
        this.data = data;
    }
}
