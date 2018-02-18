package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import static com.nur1popcorn.basm.Constants.*;

public class LDCInstruction extends Instruction {
    public byte constantPoolIndex;

    public LDCInstruction(byte constantPoolIndex) {
        super(LDC);
        this.constantPoolIndex = constantPoolIndex;
    }

    public ConstantInfo indexConstantPool(ConstantPool constantPool) {
        return constantPool.getEntry(constantPoolIndex);
    }
}
