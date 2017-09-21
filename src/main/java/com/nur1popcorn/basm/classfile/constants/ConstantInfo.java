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

package com.nur1popcorn.basm.classfile.constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Opcodes.*;

/**
 * The {@link ConstantInfo} is an abstract class containing a tag used to identify what
 * kind of CONSTANT_Info one is dealing with.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     ConstantPool 4.4
 * </a>
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class ConstantInfo {
    private byte tag /* u1 */;

    /**
     * @param tag a valid identifier used to determine the type of CONSTANT_Info, valid tags are
     *            listed here:
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-140">
     *                Table 4.4-A
     *            </a>
     */
    public ConstantInfo(byte tag) {
        this.tag = tag;
    }

    /**
     * Writes the {@link ConstantInfo}'s tag to the provided {@link DataOutputStream}.
     *
     * @param os the {@link DataOutputStream} which the tag should be written to.
     *
     * @see #read(DataInputStream)
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeByte(tag);
    }

    /**
     * @return the {@link ConstantInfo}'s identifier tag.
     */
    public final byte getTag() {
        return tag;
    }

    /**
     * @param in the {@link DataInputStream} from which the {@link ConstantInfo} should be read.
     *
     * @return a {@link ConstantInfo} based on the data read from the provided by the
     *         {@link DataInputStream}.
     */
    public static ConstantInfo read(DataInputStream in) throws IOException {
        final byte identifier = in.readByte();
        switch(identifier) {
            case CONSTANT_CLASS:
            case CONSTANT_STRING:
            case CONSTANT_METHOD_TYPE:
                return new ConstantName(identifier,
                                        in.readUnsignedShort());
            case CONSTANT_NAME_AND_TYPE:
                return new ConstantNameAndType(in.readUnsignedShort(),
                                               in.readUnsignedShort());
            case CONSTANT_FIELD_REF:
            case CONSTANT_METHOD_REF:
            case CONSTANT_INTERFACE_METHOD_REF:
                return new ConstantMethodRef(identifier,
                                             in.readUnsignedShort(),
                                             in.readUnsignedShort());
            case CONSTANT_INTEGER:
            case CONSTANT_FLOAT:
                return new ConstantInteger(identifier,
                                           in.readInt());
            case CONSTANT_LONG:
            case CONSTANT_DOUBLE:
                return new ConstantLong(identifier,
                                        in.readLong());
            case CONSTANT_UTF8:
                return new ConstantUtf8(in.readUTF());
            case CONSTANT_METHOD_HANDLE:
                return new ConstantMethodHandle(in.readByte(),
                                                in.readUnsignedShort());
            case CONSTANT_INVOKEDYNAMIC:
                return new ConstantInvokedynamic(in.readUnsignedShort(),
                                                 in.readUnsignedShort());
            default:
                throw new IOException("Invalid constant pool entry tag: " + identifier);
        }
    }
}
