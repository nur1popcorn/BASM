package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;
import com.nur1popcorn.basm.classfile.tree.Type;

import static com.nur1popcorn.basm.Constants.*;

public abstract class FieldMethodInstruction extends CPInstruction {
    /**
     * @param cp
     * @param opcode
     * @param index
     */
    FieldMethodInstruction(byte opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
    }

    /**
     * @return
     */
    public Type getDesc() {
        final ConstantMethodRef methodRed =
            (ConstantMethodRef) cp.getEntry(index);
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
            (ConstantMethodRef) cp.getEntry(index);
        final ConstantNameAndType nameAndType =
            methodRed.indexNameAndType(cp);
        return nameAndType.indexName(cp)
                          .bytes;
    }

    /**
     * {@inheritDoc}
     *
     * @throws MalformedClassFileException
     */
    @Override
    public byte getStackModifier() {
        byte result = 0;
        switch(opcode) {
            case GETFIELD:
            case PUTFIELD:
                result--;
                // fallthrough.
            case GETSTATIC:
            case PUTSTATIC:
                result += getDesc()
                    .getStackModifier();
                return result;
            case INVOKEVIRTUAL:
            case INVOKESPECIAL:
            case INVOKEINTERFACE:
                result--;
                // fallthrough.
            case INVOKESTATIC: {
                final Type desc = getDesc();
                for(Type parameter : desc.getParameters())
                    result += parameter.getStackModifier();
                result += desc.getReturnType()
                              .getStackModifier();
                return result;
            }
            default:
                throw new MalformedClassFileException(
                    "The opcode provided is invalid: opcode=" + Integer.toHexString(opcode));
        }
    }
}
