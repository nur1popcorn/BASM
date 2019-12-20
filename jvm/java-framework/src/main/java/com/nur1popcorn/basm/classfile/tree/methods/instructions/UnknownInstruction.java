package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public final class UnknownInstruction extends Instruction {
    public UnknownInstruction(Opcode opcode) {
        super(opcode);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitUnkownInstruction(this);
    }
}
