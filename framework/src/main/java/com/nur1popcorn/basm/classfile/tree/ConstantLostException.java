package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.constants.IConstantPoolPointer;

public class ConstantLostException extends Exception {
    private IConstantPoolPointer pointers[];

    public ConstantLostException(IConstantPoolPointer pointers[]) {
        this.pointers = pointers;
    }

    public IConstantPoolPointer[] getPointers() {
        return pointers;
    }
}