package com.nur1popcorn.basm.classfile.tree.methods;

public final class Label extends Instruction {
    public static final int LABEL_INSTRUCTION = 17;

    public Label() {
        super((byte)0);
    }

    @Override
    public byte getType() {
        return LABEL_INSTRUCTION;
    }
}
