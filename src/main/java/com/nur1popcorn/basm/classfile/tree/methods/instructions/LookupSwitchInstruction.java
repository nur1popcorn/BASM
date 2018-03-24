package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class LookupSwitchInstruction extends Instruction {
    //TODO: merge with table switch ?

    public LookupSwitchInstruction(byte opcode) {
        super(opcode);
    }
}
