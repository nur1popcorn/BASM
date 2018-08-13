package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;

public class ClassInstruction extends CPInstruction {
    /**
     * @param opcode
     */
    ClassInstruction(byte opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitCPInstruction(this);
        visitor.visitClassInstruction(this);
    }
}
