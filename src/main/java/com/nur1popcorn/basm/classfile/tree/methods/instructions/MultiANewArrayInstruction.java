package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import static com.nur1popcorn.basm.Constants.MULTIANEWARRAY;

public class MultiANewArrayInstruction extends Instruction {
    public String clazz;
    public byte dimensions;

    public MultiANewArrayInstruction(String clazz, byte dimensions) {
        super(MULTIANEWARRAY);
        this.clazz = clazz;
        this.dimensions = dimensions;
    }
}
