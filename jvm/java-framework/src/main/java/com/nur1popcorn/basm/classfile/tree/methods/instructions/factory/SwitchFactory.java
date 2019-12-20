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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Label;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.SwitchInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.classfile.Opcode.TABLESWITCH;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.SwitchInstruction.KeyIndexPair;

public class SwitchFactory implements IInstructionFactory<SwitchInstruction> {
    @Override
    public SwitchInstruction createInstruction(ByteDataInputStream in, Opcode opcode, ConstantPool cp) throws IOException {
        final int offset = in.position() - 1;

        // skip padding bytes and read default index.
        in.skipBytes(-in.position() & 0x3);

        final Label defaultTarget = in.readLabel(offset + in.readInt());
        final int length = in.readInt();
        final KeyIndexPair[] indices = new KeyIndexPair[
            opcode == TABLESWITCH ?
                in.readInt() - length + 1 :
                length];
        for(int i = 0; i < indices.length; i++)
            indices[i] = new KeyIndexPair(
                opcode == TABLESWITCH ?
                    length + i : in.readInt(),
                in.readLabel(offset + in.readInt())
            );
        return new SwitchInstruction(
            opcode, defaultTarget, indices);
    }
}
