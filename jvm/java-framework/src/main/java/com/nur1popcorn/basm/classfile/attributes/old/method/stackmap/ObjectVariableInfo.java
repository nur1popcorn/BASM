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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantName;

import java.io.DataOutputStream;
import java.io.IOException;

public final class ObjectVariableInfo extends VariableInfo {
    private int cpIndex /* u2 */;

    public ObjectVariableInfo(byte tag, int cpIndex) {
        super(tag);
        this.cpIndex = cpIndex;
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(cpIndex);
    }

    @Override
    public String toString() {
        return "Object_variable_info[" + cpIndex + "]";
    }

    public ConstantName indexClass(ConstantPool constantPool) {
        return (ConstantName)  constantPool.getEntry(cpIndex);
    }
}
