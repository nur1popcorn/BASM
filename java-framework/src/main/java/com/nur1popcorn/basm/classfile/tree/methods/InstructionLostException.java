package com.nur1popcorn.basm.classfile.tree.methods;

public final class InstructionLostException extends RuntimeException {
    private IInstructionPointer pointers[];

    public InstructionLostException(IInstructionPointer pointers[]) {
        this.pointers = pointers;
    }

    public IInstructionPointer[] getPointers() {
        return pointers;
    }
}
