package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.Type;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.NEWARRAY;

public final class NewArrayInstruction extends Instruction {
    private Type type;

    /**
     * @param type
     */
    NewArrayInstruction(Type type) {
        super(NEWARRAY);
        this.type = type;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        // TODO: impl.
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(type.getType());
    }
}
