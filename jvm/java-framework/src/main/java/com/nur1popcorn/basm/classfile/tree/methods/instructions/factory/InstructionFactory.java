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

package com.nur1popcorn.basm.classfile.tree.methods.instructions.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;
import java.util.Map;

import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.*;
import static java.util.Map.entry;

/*
 *
 */
public final class InstructionFactory {
    private static final Map<InstructionType, IInstructionFactory<? extends Instruction>> INSTRUCTION_FACTORY_MAP = Map.ofEntries(
        entry(NO_PARAM_INS, new NoParameterFactory()),
        entry(PUSH_INS, new PushFactory()),
        entry(LDC_INS, new LDCFactory()),
        entry(LOCAL_VARIABLE_INS, new LocalVariableFactory()),
        entry(IINC_INS, new IIncFactory()),
        entry(JUMP_INS, new JumpFactory()),
        entry(SWITCH_INS, new SwitchFactory()),
        entry(FIELD_INS, new FieldFactory()),
        entry(METHOD_INS, new MethodFactory()),
        entry(INVOKEDYNAMIC_INS, new InvokeDynamicFactory()),
        entry(CLASS_INS, new ClassFactory()),
        entry(NEWARRAY_INS, new NewArrayFactory()),
        entry(WIDE_INS, new WideFactory()),
        entry(MULTIANEWARRAY_INS, new MultiANewArrayFactory())
    );

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
        final Opcode opcode = Opcode.valueOf(in.readByte());
        return INSTRUCTION_FACTORY_MAP
            .getOrDefault(opcode.getType(), new UnknownFactory())
            .createInstruction(in, opcode, cp);
    }
}
