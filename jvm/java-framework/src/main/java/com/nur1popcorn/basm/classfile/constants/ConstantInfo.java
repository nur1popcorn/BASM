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

import com.nur1popcorn.basm.utils.WeakHashSet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import static com.nur1popcorn.basm.Constants.*;

/**
 * The {@link ConstantInfo} class is the abstract super class which all constant pool entries base their
 * implementation on. The class mostly consists of two things: A tag, which is used to identify the
 * type of 'CONSTANT_Info' and a set of pointers, which denotes any kind of {@link ConstantPoolPointer}
 * pointing at this specific constant. The implementation closely follows this part of the JVM ClassFile document:
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     CONSTANT_Info 4.4
 * </a>
 *
 * @see ConstantPoolPointer
 * @see ConstantVisitor
 *
 * @see #accept(ConstantVisitor)
 *
 * @see #read(DataInputStream)
 * @see #write(DataOutputStream)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class ConstantInfo {
    /* This set denotes the various pointers pointing to this constant and may be modified using the various
     * 'xxxPointer()' methods.
     */
    private Set<ConstantPoolPointer> pointers;

    /* This tag denotes the type of 'CONSTANT_Info'.
     * The tag may be useful to index various tables such as the
     * 'CONSTANT_POOL_ENTRY_NAMES' table or the 'CONSTANT_INFO_SKIP_TABLE'.
     */
    private final byte tag /* u1 */;

    /**
     * @param tag A tag which denotes the type of 'CONSTANT_Info'. A table including all valid tags can be found here:
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-140">
     *                Table 4.4-A
     *            </a>
     */
    ConstantInfo(byte tag) {
        this.tag = tag;
    }

    /**
     * <p>Writes the 'CONSTANT_Info's' tag and contents to the given {@link DataOutputStream}.</p>
     * <p>
     *    <i>Note:</i>
     *    This method should be overwritten by all {@link ConstantInfo}s, as according to the specification
     *    any constant pool entry must consist of 3 (including the tag) or more bytes.
     *    <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-130">
     *        CONSTANT_Info 4.4-130
     *    </a>
     * </p>
     *
     * @param os The {@link DataOutputStream} to which the tag should be written to.
     *
     * @throws IOException If an error occurs during the process of writing to the {@link DataOutputStream}.
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeByte(tag);
    }

    /**
     * Accepts a {@link ConstantVisitor}, calls the for the 'CONSTANT_Info' appropriate 'visitXXX()'
     * methods to notify the visitor of what type of 'CONSTANT_Info' is being entered.
     *
     * @param visitor The {@link ConstantVisitor} whom's callbacks will be invoked.
     */
    public abstract void accept(ConstantVisitor visitor);

    /**
     * @return A tag that denotes the type of 'CONSTANT_Info'.
     */
    public final byte getTag() {
        return tag;
    }

    /**
     * Denotes this constant pool entry as not being referenced by the given pointer anymore.
     *
     * @param pointer The pointer, which should be removed from the set of pointers.
     */
    public final void removePointer(ConstantPoolPointer pointer) {
        pointers.remove(pointer);
    }

    /**
     * Denotes this constant pool entry as being referenced by the given pointer.
     *
     * @param pointer The pointer, which should be added to the set of pointers.
     */
    public final void addPointer(ConstantPoolPointer pointer) {
        if(pointers == null)
            pointers = new WeakHashSet<>();
        pointers.add(pointer);
    }

    /**
     * @return Whether or not the given 'CONSTANT_Info' has any pointers.
     */
    public final boolean hasPointers() {
        return pointers != null &&
               pointers.size() != 0;
    }

    /**
     * @return An array of pointers.
     */
    public final ConstantPoolPointer[] getPointers() {
        if(pointers == null)
            return new ConstantPoolPointer[0];
        final ConstantPoolPointer arr[] = new ConstantPoolPointer[pointers.size()];
        pointers.toArray(arr);
        return arr;
    }

    @Override
    public String toString() {
        return CONSTANT_POOL_ENTRY_NAMES[tag];
    }

    /**
     * This factory method reads the next 'CONSTANT_Info's' tag and contents from the provided
     * {@link DataInputStream} and then invokes the for the 'CONSTANT_Info' appropriate constructor.
     *
     * @param in The {@link DataInputStream} from which the appropriate type of 'CONSTANT_Info' should be read.
     * @throws IOException If an error occurs during the process of reading from the {@link DataInputStream}.
     * @return The next 'CONSTANT_Info' which was read and constructed based on the data provided by
     *         the given {@link DataInputStream}
     */
    public static ConstantInfo read(DataInputStream in) throws IOException {
        final byte identifier = in.readByte();
        switch(identifier) {
            case CONSTANT_UTF8:
                return new ConstantUTF8(in.readUTF());
            case CONSTANT_INTEGER:
                return new ConstantInteger(in.readInt());
            case CONSTANT_FLOAT:
                return new ConstantInteger(in.readFloat());
            case CONSTANT_LONG:
                return new ConstantLong(in.readLong());
            case CONSTANT_DOUBLE:
                return new ConstantLong(in.readDouble());
            case CONSTANT_CLASS:
            case CONSTANT_STRING:
            case CONSTANT_METHOD_TYPE:
            case CONSTANT_MODULE:
            case CONSTANT_PACKAGE:
                return new ConstantName(identifier,
                                        in.readUnsignedShort());
            case CONSTANT_FIELD_REF:
            case CONSTANT_METHOD_REF:
            case CONSTANT_INTERFACE_METHOD_REF:
                return new ConstantMethodRef(identifier,
                                             in.readUnsignedShort(),
                                             in.readUnsignedShort());
            case CONSTANT_NAME_AND_TYPE:
                return new ConstantNameAndType(in.readUnsignedShort(),
                                               in.readUnsignedShort());
            case CONSTANT_METHOD_HANDLE:
                return new ConstantMethodHandle(in.readByte(),
                                                in.readUnsignedShort());
            case CONSTANT_DYNAMIC:
            case CONSTANT_INVOKEDYNAMIC:
                return new ConstantInvokeDynamic(identifier,
                                                 in.readUnsignedShort(),
                                                 in.readUnsignedShort());
            default:
                throw new IOException("Invalid constant pool entry tag: " + Integer.toHexString(identifier));
        }
    }
}
