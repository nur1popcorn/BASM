package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.utils.ByteDataOutputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.INVOKEDYNAMIC;

public final class InvokeDynamicInstruction extends CPInstruction {
    InvokeDynamicInstruction(int index, ConstantPool cp) {
        super(INVOKEDYNAMIC, index, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        // TODO: impl
    }

    @Override
    public void write(ByteDataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(0);
        os.writeByte(0);
    }
}
