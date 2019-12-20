package com.nur1popcorn.basm.classfile.tree.methods.instructions.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.UnknownInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

public final class UnknownFactory implements IInstructionFactory<UnknownInstruction> {
    @Override
    public UnknownInstruction createInstruction(ByteDataInputStream in, Opcode opcode, ConstantPool cp) {
        return new UnknownInstruction(opcode);
    }
}
