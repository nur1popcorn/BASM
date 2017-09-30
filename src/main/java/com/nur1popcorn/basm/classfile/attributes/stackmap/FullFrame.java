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

package com.nur1popcorn.basm.classfile.attributes.stackmap;

public class FullFrame extends StackMapFrame {
    private int offsetDelta;
    private VariableInfo locals[] /* length: u2 */;
    private VariableInfo stack[] /* length: u2 */;

    public FullFrame(byte tag, int offsetDelta, VariableInfo locals[], VariableInfo stack[]) {
        super(tag);
        this.offsetDelta = offsetDelta;
        this.locals = locals;
        this.stack = stack;
    }
}
