package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.NEWARRAY;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.NEWARRAY_INS;

public final class NewArrayInstruction extends Instruction {
    private Type type;

    /**
     * @param type
     */
    public NewArrayInstruction(Type type) {
        super(NEWARRAY);
        this.type = type;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitNewArrayInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(type.getType());
    }

    @Override
    public InstructionType getType() {
        return NEWARRAY_INS;
    }
}
