package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.Type;

import static com.nur1popcorn.basm.Constants.NEWARRAY;

public class NewArrayInstruction extends Instruction {
    private Type type;

    /**
     * @param type
     */
    NewArrayInstruction(Type type) {
        super(NEWARRAY);
        this.type = type;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        // TODO: impl.
    }
}
