package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantName;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.CLASS_INS;

public final class ClassInstruction extends CPInstruction<ConstantName> {
    /**
     * @param opcode
     */
    public ClassInstruction(Opcode opcode, ConstantName info, ConstantPool cp) {
        super(opcode, info, cp);
        if(opcode.getType() != CLASS_INS)
            throw new IllegalArgumentException();
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPInstruction(this);
        visitor.visitClassInstruction(this);
    }
}
