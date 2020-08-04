package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.constants.ConstantPoolPointer;

public class ConstantLostException extends Exception {
    private ConstantPoolPointer pointers[];

    public ConstantLostException(ConstantPoolPointer pointers[]) {
        this.pointers = pointers;
    }

    public ConstantPoolPointer[] getPointers() {
        return pointers;
    }
}
