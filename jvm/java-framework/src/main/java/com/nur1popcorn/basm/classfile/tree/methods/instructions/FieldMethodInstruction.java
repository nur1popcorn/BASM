package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;
import com.nur1popcorn.basm.classfile.tree.Type;

public abstract class FieldMethodInstruction extends CPInstruction<ConstantMethodRef> {
    /**
     * @param opcode
     * @param info
     * @param cp
     */
    FieldMethodInstruction(Opcode opcode, ConstantMethodRef info, ConstantPool cp) {
        super(opcode, info, cp);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        super.accept(visitor);
        visitor.visitFieldMethodInstruction(this);
    }

    /**
     * @return
     */
    public Type getDesc() {
        final ConstantNameAndType nameAndType =
            info.indexNameAndType(cp);
        return Type.getType(
            nameAndType.indexDesc(cp)
                       .bytes
        );
    }

    /**
     * @return
     */
    public String getName() {
        final ConstantNameAndType nameAndType =
            info.indexNameAndType(cp);
        return nameAndType.indexName(cp)
                          .bytes;
    }
}
