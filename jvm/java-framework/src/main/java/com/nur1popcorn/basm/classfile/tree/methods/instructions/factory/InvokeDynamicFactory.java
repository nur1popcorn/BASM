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
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.InvokeDynamicInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_INVOKEDYNAMIC;

public class InvokeDynamicFactory implements IInstructionFactory<InvokeDynamicInstruction> {
    @Override
    public InvokeDynamicInstruction createInstruction(ByteDataInputStream in, Opcode opcode, ConstantPool cp) throws IOException {
        final int index = in.readUnsignedShort();
        in.skipBytes(2);
        return new InvokeDynamicInstruction(cp.getEntry(index, CONSTANT_INVOKEDYNAMIC), cp);
    }
}
