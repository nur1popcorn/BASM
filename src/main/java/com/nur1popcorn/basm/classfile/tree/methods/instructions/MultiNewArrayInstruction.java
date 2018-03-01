package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class MultiNewArrayInstruction extends Instruction {
    public static final byte MULTIANEWARRAY_INSTRUCTION = 12;

    //TODO: maybe merge multi new array with new array instruction and add dimension as param.

    public MultiNewArrayInstruction(byte opcode) {
        super(opcode);
    }
}
