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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.JumpInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.IOException;

public class JumpFactory implements IInstructionFactory<JumpInstruction> {
    @Override
    public JumpInstruction createInstruction(ByteDataInputStream in, int offset, Opcode opcode, ConstantPool cp) throws IOException {
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
    }
}
