package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;

import static com.nur1popcorn.basm.Constants.INVOKEDYNAMIC;

public class InvokeDynamicInstruction extends CPInstruction {
    InvokeDynamicInstruction(int index, ConstantPool cp) {
        super(INVOKEDYNAMIC, index, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        // TODO: impl
    }
}
