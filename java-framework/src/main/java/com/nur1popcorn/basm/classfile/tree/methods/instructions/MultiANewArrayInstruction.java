package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import static com.nur1popcorn.basm.Constants.MULTIANEWARRAY;

public class MultiANewArrayInstruction extends Instruction {
    MultiANewArrayInstruction(int index, byte dimensions) {
        super(MULTIANEWARRAY);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {

    }
}
