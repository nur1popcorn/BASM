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

import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class StackMapFrame {
    protected byte tag /* u1 */;

    public StackMapFrame(byte tag) {
        this.tag = tag;
    }

    /**
     * @param os the {@link DataOutputStream} to which the {@link StackMapFrame}'s
     *           tag should be written to.
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeByte(tag);
    }

    //TODO: desc
    public final byte getTag() {
        return tag;
    }

    /**
     * 0-63    same_frame
     * 64-127  same_locals_1_stack_item_frame
     * 128-246 not used
     * 247     same_locals_1_stack_item_frame_extended
     * 248-250 chop_frame
     * 251     same_frame_extended
     * 252-254 append_frame
     * 255     full_frame
     *
     * @param in the {@link DataInputStream} from which the {@link AttributeInfo}s
     *           should be read.
     */
    public static StackMapFrame read(DataInputStream in) throws IOException {
        final byte tag = in.readByte();
        final int u1 = Byte.toUnsignedInt(tag);
        if(u1 < 248) {
            if(u1 == 247)
                // same_locals_1_stack_item_frame_extended
                return new SameLocals1StackItemFrameExtended(tag, in.readUnsignedShort(), VariableInfo.read(in));
            else if(u1 < 64)
                // same_frame
                return new StackMapFrame(tag) {
                    @Override
                    public String toString() {
                        return "same_frame[" + Byte.toUnsignedInt(this.tag) + "]";
                    }
                };
            else /* if(u1 < 127) */
                // same_locals_1_stack_item_frame
                return new SameLocals1StackItemFrame(tag, VariableInfo.read(in));
        } else if(u1 < 252) {
            if(u1 == 251) {
                // same_frame_extended
            } else {
                // chop_frame
            }
        } else if(u1 == 255) {
            // full_frame
        } else {
            // appended_frame
        }
        return null;
    }
}
