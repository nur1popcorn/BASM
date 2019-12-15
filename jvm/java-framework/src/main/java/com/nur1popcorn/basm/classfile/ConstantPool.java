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

package com.nur1popcorn.basm.classfile;

import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.IConstantPoolPointer;
import com.nur1popcorn.basm.classfile.constants.IConstantVisitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static com.nur1popcorn.basm.Constants.CONSTANT_DOUBLE;
import static com.nur1popcorn.basm.Constants.CONSTANT_LONG;

/**
 * <p>
 *    The {@link ConstantPool} is a table made up of various 'CONSTANT_infos', these represent various
 *    string values, class names and other kinds of constants, embedded into a class file. This table may
 *    be indexed freely to obtain any constant located at the desired offset with exception to the 1st
 *    entry and entries which follow a 'CONSTANT_Long' or 'CONSTANT_Double'. The implementation closely
 *    follows these parts of the JVM ClassFile document:
 * </p>
 * <ul>
 *     <li>
 *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-C">
 *             Constant Pool 4.1-200-C
 *         </a>
 *     </li>
 *     <li>
 *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *             Constant Pool 4.4.
 *         </a>
 *     </li>
 * </ul>
 *
 * @see ConstantInfo
 * @see IConstantVisitor
 * @see ClassReader
 *
 * @see #read(DataInputStream)
 * @see #write(DataOutputStream)
 *
 * @see #accept(IConstantVisitor)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class ConstantPool implements Iterable<ConstantInfo> {
    /* This table represents the various constants embedded into the class file. The 1st and every entry
     * following a 'CONSTANT_Long' or 'CONSTANT_Double' are null.
     */
    protected ConstantInfo cpEntries[];

    /**
     * @param cpEntries The table representing the various constants embedded into the class file. The 1st and
     *                  every entry following a 'CONSTANT_Long' or 'CONSTANT_Double' are expected to be null.
     */
    public ConstantPool(ConstantInfo[] cpEntries) {
        this.cpEntries = cpEntries;
    }

    /**
     * Empty constructor required by {@link ClassReader}.
     */
    ConstantPool()
    {}

    /**
     * Reads the {@link ConstantPool}'s length and entries from the given {@link DataInputStream}.
     *
     * @param in The {@link DataInputStream} from which the {@link ConstantPool} should be read.
     * @throws IOException If an error occurs during the process of reading from the {@link DataOutputStream}.
     *
     * @see ConstantInfo#read(DataInputStream)
     */
    public final void read(DataInputStream in) throws IOException {
        final int cpSize = in.readUnsignedShort();
        cpEntries = new ConstantInfo[cpSize];
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++) {
            final ConstantInfo info = ConstantInfo.read(in);
            cpEntries[i] = info;
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = info.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }

        for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++) {
            final ConstantInfo info = cpEntries[i];
            if(info instanceof IConstantPoolPointer)
                ((IConstantPoolPointer) info)
                    .attach(this);
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = info.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
    }

    /**
     * Writes the {@link ConstantPool}'s length and entries to the given {@link DataOutputStream}.
     *
     * @param os The {@link DataOutputStream} the {@link ConstantPool} should be written to.
     * @throws IOException If an error occurs during the process of writing to the {@link DataOutputStream}.
     */
    public final void write(DataOutputStream os) throws IOException {
        os.writeShort(cpEntries.length);
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpEntries.length; i++) {
            final ConstantInfo info = cpEntries[i];
            info.write(os);
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = info.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
        os.flush();
    }

    /**
     * Accepts a {@link IConstantVisitor}, transverses the {@link ConstantPool} and calls the
     * for the 'CONSTANT_Info' appropriate 'visitXXX()' methods to notify the visitor of
     * what type of 'CONSTANT_Info' is being entered.
     *
     * @param visitor The {@link IConstantVisitor} whose callbacks will be invoked.
     */
    public final void accept(IConstantVisitor visitor) {
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpEntries.length; i++) {
            final ConstantInfo info = cpEntries[i];
            info.accept(visitor);
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = info.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
    }

    /**
     * Get the 'CONSTANT_Info' at the given offset.
     *
     * @param index The index of the 'CONSTANT_Info' which should be returned.
     * @throws MalformedClassFileException If the index is out of bounds.
     * @return The constant pool entry at the given index.
     */
    public final ConstantInfo getEntry(int index) throws MalformedClassFileException {
        if(index < 0 || index >= cpEntries.length)
            throw new MalformedClassFileException(
                "Index out of bounds: index=" + index +
                ", length=" + cpEntries.length
            );
        return cpEntries[index];
    }

    /**
     * Get the 'CONSTANT_Info' at the given offset with the expected tag.
     *
     * @param index The index of the 'CONSTANT_Info' which should be returned.
     * @param tag A tag which denotes the type of 'CONSTANT_Info' expected at this offset.
     *            A table including all valid tags can be found here:
     *            <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-140">
     *                Table 4.4-A
     *            </a>
     *
     * @throws MalformedClassFileException If the 'CONSTANT_Info' at the given offset is null or has a different tag.
     * @return The constant pool entry at the given index.
     *
     * @see #getEntry(int)
     */
    @SuppressWarnings("unchecked")
    public final <T extends ConstantInfo> T getEntry(int index, byte tag) {
        final ConstantInfo info = getEntry(index);
        if(info == null)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at index: index=" + index + " is null");
        if(info.getTag() != tag)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at given index has an invalid tag: index=" + index +
                ", expected_tag=" + tag +
                ", tag=" + info.getTag()
            );
        return (T) info;
    }

    /**
     * @return The constant pool.
     */
    public final ConstantInfo[] getEntries() {
        return cpEntries;
    }

    /**
     * @return The constant pool's size.
     */
    public final int getSize() {
        return cpEntries.length;
    }

    public int indexOf(ConstantInfo info) {
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpEntries.length; i++) {
            final ConstantInfo entry = cpEntries[i];
            if(entry.equals(info))
                return i;
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = entry.getTag();
            if(tag == CONSTANT_LONG ||
                tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
        return -1;
    }

    @Override
    public final Iterator<ConstantInfo> iterator() {
        return new Iterator<>() {
            private int cursor = 1;

            @Override
            public final boolean hasNext() {
                return cursor != cpEntries.length;
            }

            @Override
            public final ConstantInfo next() {
                final ConstantInfo info = getEntry(cursor++);
                // longs and doubles take up 2 spaces in the constant pool.
                final int tag = info.getTag();
                if(tag == CONSTANT_LONG ||
                   tag == CONSTANT_DOUBLE)
                    cursor++ /* padding */;
                return info;
            }
        };
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
            .append("ConstantPool[");
        if(cpEntries.length != 0) {
            stringBuilder.append(cpEntries[0]);
            for(int i = 1; i < cpEntries.length; i++)
                stringBuilder.append(", ")
                             .append(cpEntries[i]);
        }
        return stringBuilder.append("]")
            .toString();
    }
}
