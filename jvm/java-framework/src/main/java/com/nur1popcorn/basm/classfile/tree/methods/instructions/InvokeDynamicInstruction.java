package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInvokeDynamic;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.INVOKEDYNAMIC;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.INVOKEDYNAMIC_INS;

public final class InvokeDynamicInstruction extends CPInstruction<ConstantInvokeDynamic> {
    public InvokeDynamicInstruction(ConstantInvokeDynamic info, ConstantPool cp) {
        super(INVOKEDYNAMIC, info, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        super.accept(visitor);
        visitor.visitInvokeDynamicInstruction(this);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeByte(0);
        os.writeByte(0);
    }

    @Override
    public int getConsumeStack() {
        int result = 0;
        for(Type parameter : getDesc().getParameters())
            result += parameter.getStackModifier();
        return result;
    }

    @Override
    public int getProduceStack() {
        return getDesc().getReturnType()
            .getStackModifier();
    }

    public Type getDesc() {
        final ConstantNameAndType nameAndType =
            info.indexNameAndType(cp);
        return Type.getType(
            nameAndType.indexDesc(cp)
                .bytes
        );
    }

    @Override
    public InstructionType getType() {
        return INVOKEDYNAMIC_INS;
    }
}
