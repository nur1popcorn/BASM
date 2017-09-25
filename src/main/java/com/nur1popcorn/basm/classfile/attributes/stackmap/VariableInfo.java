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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Opcodes.*;

public class VariableInfo {
    protected byte tag /* u1 */;

    public VariableInfo(byte tag) {
        this.tag = tag;
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(tag);
    }

    @Override
    public String toString() {
        return ITEM_INFO_NAMES[tag];
    }

    public final byte getTag() {
        return tag;
    }

    public static VariableInfo read(DataInputStream in) throws IOException {
        final byte tag = in.readByte();
        return tag == ITEM_OBJECT ?
                   new ObjectVariableInfo(tag, in.readUnsignedShort()) :
                   tag == ITEM_UNINITIALISED ?
                       new UninitializedVariableInfo(tag, in.readUnsignedShort()) :
                       new VariableInfo(tag);
    }
}
