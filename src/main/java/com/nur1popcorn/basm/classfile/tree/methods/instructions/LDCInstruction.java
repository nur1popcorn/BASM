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

// TODO: consider rewriting this class.. and removing MethodHandle.
public final class LDCInstruction extends Instruction {
    public static final byte LDC_INSTRUCTION = 4;

    private byte tag;
    private Object constant;

    public LDCInstruction(byte opcode, Object constant, byte tag) {
        super(opcode);
        this.constant = constant;
        this.tag = tag;
    }

    public void setConstant(int constant) {
        tag = CONSTANT_INTEGER;
        this.constant = constant;
    }

    public void setConstant(float constant) {
        tag = CONSTANT_FLOAT;
        this.constant = constant;
    }

    public void setConstant(double constant) {
        tag = CONSTANT_DOUBLE;
        this.constant = constant;
    }

    public void setConstant(long constant) {
        tag = CONSTANT_LONG;
        this.constant = constant;
    }

    public void setConstant(String constant, byte tag) {
        assert(tag == CONSTANT_STRING ||
               tag == CONSTANT_CLASS ||
               tag == CONSTANT_METHOD_TYPE);
        this.tag = tag;
        this.constant = constant;
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
