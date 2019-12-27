package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import static com.nur1popcorn.basm.classfile.Opcode.INVALID;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.NOT_AN_INS;

public final class UnknownInstruction extends Instruction {
    public UnknownInstruction() {
        super(INVALID);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitUnkownInstruction(this);
    }

    @Override
    public InstructionType getType() {
        return NOT_AN_INS;
    }
}
