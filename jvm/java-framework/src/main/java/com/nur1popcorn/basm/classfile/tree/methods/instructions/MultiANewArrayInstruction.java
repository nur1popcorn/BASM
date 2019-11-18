package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.MULTIANEWARRAY;

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
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(index);
        os.writeByte(dimensions);
    }

    /**
     * {@inheritDoc}
     */
    /*@Override
    public byte getStackModifier() {
        return dimensions;
    }*/
}
