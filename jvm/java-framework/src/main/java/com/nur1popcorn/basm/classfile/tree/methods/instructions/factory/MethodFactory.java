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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.MethodInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_INTERFACE_METHOD_REF;
import static com.nur1popcorn.basm.Constants.CONSTANT_METHOD_REF;
import static com.nur1popcorn.basm.classfile.Opcode.INVOKEINTERFACE;

public class MethodFactory implements IInstructionFactory<MethodInstruction> {
    @Override
    public MethodInstruction createInstruction(ByteDataInputStream in, int offset, Opcode opcode, ConstantPool cp) throws IOException {
        if (opcode == INVOKEINTERFACE) {
            final int index = in.readUnsignedShort();
            final int count = in.readUnsignedByte();
            in.skipBytes(1);
            return new MethodInstruction(
                cp.getEntry(index, CONSTANT_INTERFACE_METHOD_REF), count, cp);
        }
        return new MethodInstruction(
            opcode, cp.getEntry(in.readUnsignedShort(), CONSTANT_METHOD_REF), cp);
    }
}
