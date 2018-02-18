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

import static com.nur1popcorn.basm.Constants.*;

public class NewArrayInstruction extends Instruction {

    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.newarray.desc-120
    public static final byte T_BOOLEAN = 4;
    public static final byte T_CHAR = 5;
    public static final byte T_FLOAT = 6;
    public static final byte T_DOUBLE = 7;
    public static final byte T_BYTE = 8;
    public static final byte T_SHORT = 9;
    public static final byte T_INT = 10;
    public static final byte T_LONG = 11;

    public byte atype;

    public NewArrayInstruction(byte atype) {
        super(NEWARRAY);
        assert(atype == T_BOOLEAN ||
               atype == T_CHAR ||
               atype == T_FLOAT ||
               atype == T_DOUBLE ||
               atype == T_BYTE ||
               atype == T_SHORT ||
               atype == T_INT ||
               atype == T_LONG);
        this.atype = atype;
    }
}
