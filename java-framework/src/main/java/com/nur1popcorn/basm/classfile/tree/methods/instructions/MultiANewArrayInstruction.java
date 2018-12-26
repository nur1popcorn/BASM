package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.MULTIANEWARRAY;

public final class MultiANewArrayInstruction extends Instruction {
    private int index;
    private byte dimensions;

    MultiANewArrayInstruction(int index, byte dimensions) {
        super(MULTIANEWARRAY);
        this.index = index;
        this.dimensions = dimensions;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {

    }

    @Override
    public void write(DataOutputStream os, InstructionList instructions) throws IOException {
        super.write(os, instructions);
        os.writeShort(index);
        os.writeByte(dimensions);
    }
}
