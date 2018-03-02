package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import static com.nur1popcorn.basm.Constants.MUTLIANEWARRAY;

public class MultiANewArrayInstruction extends Instruction {
    public static final byte MULTIANEWARRAY_INSTRUCTION = 12;

    public String clazz;
    public int dimensions;

    public MultiANewArrayInstruction(String clazz, int dimensions) {
        super(MUTLIANEWARRAY);
        this.clazz = clazz;
        this.dimensions = dimensions;
    }
}
