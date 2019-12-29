package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.MULTIANEWARRAY;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.MULTIANEWARRAY_INS;

public final class MultiANewArrayInstruction extends Instruction {
    private int index;
    private byte dimensions;

    public MultiANewArrayInstruction(int index, byte dimensions) {
        super(MULTIANEWARRAY);
        this.index = index;
        this.dimensions = dimensions;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitMultiANewArrayInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(index);
        os.writeByte(dimensions);
    }

    @Override
    public int getConsumeStack() {
        return dimensions;
    }

    @Override
    public InstructionType getType() {
        return MULTIANEWARRAY_INS;
    }
}
