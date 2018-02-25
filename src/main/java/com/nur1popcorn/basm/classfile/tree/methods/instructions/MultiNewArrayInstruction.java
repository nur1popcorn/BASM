package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class MultiNewArrayInstruction extends Instruction {
    public static final byte MULTIANEWARRAY_INSTRUCTION = 13;

    public MultiNewArrayInstruction(byte opcode) {
        super(opcode);
    }
}
