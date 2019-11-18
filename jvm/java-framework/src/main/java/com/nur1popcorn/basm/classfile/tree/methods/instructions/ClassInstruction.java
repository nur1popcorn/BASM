package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;

public final class ClassInstruction extends CPInstruction {
    /**
     * @param opcode
     */
    ClassInstruction(Opcode opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitCPInstruction(this);
        visitor.visitClassInstruction(this);
    }
}
