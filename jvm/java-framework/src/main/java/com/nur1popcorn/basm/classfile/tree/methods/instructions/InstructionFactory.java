/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.IINC;
import static com.nur1popcorn.basm.classfile.Opcode.INVOKEINTERFACE;

/*
 *
 */
public final class InstructionFactory {
    /**
     * @param in
     * @param cp
     *
     * @throws IOException
     * @throws MalformedClassFileException
     *
     * @return
     */
    public static Instruction read(ByteDataInputStream in, ConstantPool cp) throws IOException {
        final int offset = in.position();
        final Opcode opcode = Opcode.valueOf(in.readByte());
        switch(opcode.getType()) {
            case NO_PARAM_INS:
                return new NoParameterInstruction(opcode);
            case PUSH_INS:
                switch(opcode) {
                    case BIPUSH:
                        return new PushInstruction(
                            opcode, in.readByte());
                    case SIPUSH:
                        return new PushInstruction(
                            opcode, in.readShort());
                }
            case LDC_INS:
                switch(opcode) {
                    case LDC:
                        return new LDCInstruction(
                            opcode, in.readByte(), cp);
                    case LDC_W:
                    case LDC2_W:
                        return new LDCInstruction(
                            opcode, in.readShort(), cp);
                }
            case LOCAL_VARIABLE_INS:
                return new LocalVariableInstruction(
                    opcode, in.readByte());
            case IINC_INS:
                return new IIncInstruction(
                    in.readByte(), in.readByte());
            case JUMP_INS:
                switch(opcode) {
                    // a 4 byte index must be constructed for the goto_w & jsr_w opcodes.
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.goto_w
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.jsr_w
                    case GOTO_W:
                    case JSR_W:
                        return new JumpInstruction(opcode,
                            in.readLabel(offset + in.readInt()));
                    default:
                        return new JumpInstruction(opcode,
                            in.readLabel(offset + in.readShort()));
                }
            case SWITCH_INS:
                // skip padding bytes and read default index.
                in.skipBytes(-in.position() & 0x3);
                final Label defaultTarget = in.readLabel(offset + in.readInt());
                switch(opcode) {
                    case TABLESWITCH: {
                        final int low = in.readInt();
                        final int high = in.readInt();

                        final int length = high - low + 1;

                        final SwitchInstruction.KeyIndexPair[] indices = new SwitchInstruction.KeyIndexPair[length];
                        for(int i = 0; i < length; i++)
                            indices[i] = new SwitchInstruction.KeyIndexPair(
                                low + i,
                                in.readLabel(offset + in.readInt())
                            );

                        return new SwitchInstruction(
                            opcode, defaultTarget, indices);
                    }
                    case LOOKUPSWITCH: {
                        final int length = in.readInt();
                        final SwitchInstruction.KeyIndexPair[] indices = new SwitchInstruction.KeyIndexPair[length];
                        for(int i = 0; i < length; i++)
                            indices[i] = new SwitchInstruction.KeyIndexPair(
                                in.readInt(),
                                in.readLabel(offset + in.readInt())
                            );
                        return new SwitchInstruction(
                            opcode, defaultTarget, indices);
                    }
                }
            case FIELD_INS:
                return new FieldInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case METHOD_INS:
                if (opcode == INVOKEINTERFACE) {
                    final int index = in.readUnsignedShort();
                    final int count = in.readUnsignedByte();
                    in.skipBytes(1);
                    return new MethodInstruction(
                        index, count, cp);
                }
                return new MethodInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case INVOKEDYNAMIC_INS:
                final int index = in.readUnsignedShort();
                in.skipBytes(2);
                return new InvokeDynamicInstruction(index, cp);
            case CLASS_INS:
                return new ClassInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case NEWARRAY_INS:
                return new NewArrayInstruction(
                    Type.getType(in.readByte()));
            case WIDE_INS:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.wide
                final Opcode wopcode = Opcode.valueOf(in.readByte());
                if(wopcode == IINC)
                    return new WideInstruction(
                        in.readUnsignedShort(), in.readUnsignedShort());
                else
                    return new WideInstruction(
                        wopcode, in.readUnsignedShort());
            case MULTIANEWARRAY_INS:
                return new MultiANewArrayInstruction(
                    in.readUnsignedShort(), in.readByte());
            default:
                throw new MalformedClassFileException();
        }
    }
}
