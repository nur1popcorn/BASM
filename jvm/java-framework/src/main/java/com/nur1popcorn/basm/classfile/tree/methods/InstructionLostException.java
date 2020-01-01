package com.nur1popcorn.basm.classfile.tree.methods;

public final class InstructionLostException extends RuntimeException {
    private InstructionPointer pointers[];

    public InstructionLostException(InstructionPointer pointers[]) {
        this.pointers = pointers;
    }

    public InstructionPointer[] getPointers() {
        return pointers;
    }
}
