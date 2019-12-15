package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInvokeDynamic;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.INVOKEDYNAMIC;

public final class InvokeDynamicInstruction extends CPInstruction {
    public InvokeDynamicInstruction(ConstantInvokeDynamic info, ConstantPool cp) {
        super(INVOKEDYNAMIC, info, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        // TODO: impl
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(0);
        os.writeByte(0);
    }
}
