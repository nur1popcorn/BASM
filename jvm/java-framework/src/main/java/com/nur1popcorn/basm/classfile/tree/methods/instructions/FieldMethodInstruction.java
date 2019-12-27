package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;
import com.nur1popcorn.basm.classfile.tree.Type;

public abstract class FieldMethodInstruction extends CPInstruction {
    /**
     * @param opcode
     * @param info
     * @param cp
     */
    FieldMethodInstruction(Opcode opcode, ConstantMethodRef info, ConstantPool cp) {
        super(opcode, info, cp);
    }

    /**
     * @return
     */
    public Type getDesc() {
        final ConstantMethodRef methodRed =
            (ConstantMethodRef) info;
        final ConstantNameAndType nameAndType =
            methodRed.indexNameAndType(cp);
        return Type.getType(
            nameAndType.indexDesc(cp)
                       .bytes
        );
    }

    /**
     * @return
     */
    public String getName() {
        final ConstantMethodRef methodRed =
            (ConstantMethodRef) info;
        final ConstantNameAndType nameAndType =
            methodRed.indexNameAndType(cp);
        return nameAndType.indexName(cp)
                          .bytes;
    }
}
