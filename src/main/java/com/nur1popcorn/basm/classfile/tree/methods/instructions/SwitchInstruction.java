package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.ICodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

public class SwitchInstruction extends Instruction {
    //TODO: merge with table switch ?

    public SwitchInstruction(byte opcode) {
        super(opcode);
    }

    @Override
    public void accept(ICodeVisitor visitor) {
        visitor.visitSwitchInstruction(this);
    }
}
