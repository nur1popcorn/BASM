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

import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.MethodHandle;

import static com.nur1popcorn.basm.Constants.*;

public final class LDCInstruction extends Instruction {
    public static final byte LDC_INSTRUCTION = 4;

    private byte tag;
    private Object constant;

    public LDCInstruction(byte opcode, Object constant, byte tag) {
        super(opcode);
        this.constant = constant;
        this.tag = tag;
    }

    //TODO: ldc2_W
    public void setConstant(int i) {
        tag = CONSTANT_INTEGER;
        this.constant = i;
    }

    public void setConstant(float f) {
        tag = CONSTANT_FLOAT;
        this.constant = f;
    }

    public void setConstant(String s, byte tag) {
        assert(tag == CONSTANT_STRING ||
               tag == CONSTANT_CLASS ||
               tag == CONSTANT_METHOD_TYPE);
        this.tag = tag;
        this.constant = s;
    }

    public void setConstant(MethodHandle methodHandle) {
        this.tag = CONSTANT_METHOD_HANDLE;
        this.constant = methodHandle;
    }

    public byte getTag() {
        return tag;
    }

    public Object getConstant() {
        return constant;
    }
}
