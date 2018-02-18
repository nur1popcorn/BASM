package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class LocalVariableInstructtion extends Instruction {
    public byte index;

    public LocalVariableInstructtion(byte opcode, byte index) {
        super(opcode);
        this.index = index;
    }
}
