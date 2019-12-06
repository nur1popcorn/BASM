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

package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class ByteDataOutputStream extends DataOutputStream {
    private final int offsets[];

    public ByteDataOutputStream(InstructionList il, OutputStream out) {
        super(out);
        offsets = new int[il.size()];
        int offset = 0;
        for(int i = 0; i < offsets.length; i++) {
            final Instruction instruction = il.get(i);
            offsets[i] = offset;
            offset += instruction.getLength(offset);
        }
    }

    public int getOffset(int index) {
        if(index < 0 || index >= offsets.length || offsets[index] < 0)
            throw new IllegalArgumentException("Illegal target of jump or branch: " + index);
        return offsets[index];
    }
}
