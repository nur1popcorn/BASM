package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.CLASS_INS;

public final class ClassInstruction extends CPInstruction {
    /**
     * @param opcode
     */
    public ClassInstruction(Opcode opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
        if(opcode.getType() != CLASS_INS)
            throw new IllegalArgumentException();
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitCPInstruction(this);
        visitor.visitClassInstruction(this);
    }
}
