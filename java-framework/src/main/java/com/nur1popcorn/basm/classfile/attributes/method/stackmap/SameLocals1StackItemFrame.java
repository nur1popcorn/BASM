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

package com.nur1popcorn.basm.classfile.attributes.method.stackmap;

import java.io.DataOutputStream;
import java.io.IOException;

public final class SameLocals1StackItemFrame extends StackMapFrame {
    private VariableInfo verificationType;

    public SameLocals1StackItemFrame(byte tag, VariableInfo verificationType) throws IOException {
        super(tag);
        this.verificationType = verificationType;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        verificationType.write(os);
    }

    @Override
    public String toString() {
        return "same_locals_1_stack_item_frame[" +
                   verificationType.toString() +
               "]";
    }

    public VariableInfo getVerificationType() {
        return verificationType;
    }
}
