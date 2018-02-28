package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class LookupSwitchInstruction extends Instruction {
    public static final byte LOOKUPSWITCH_INSTRUCTION = 15;

    public LookupSwitchInstruction(byte opcode) {
        super(opcode);
    }
}
