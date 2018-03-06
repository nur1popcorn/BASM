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

import com.nur1popcorn.basm.classfile.constants.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nur1popcorn.basm.Constants.*;

/**
 * The {@link ConstantPool} stores all kind of constants embedded into the classfile.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4">
 *     ConstantPool 4.4.
 * </a>
 *
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantPool {
    private ConstantInfo cpEntries[];

    /**
     * @param cpEntries the {@link ConstantInfo}s from which the {@link ConstantPool}
     *                  is supposed to be constructed.
     */
    public ConstantPool(ConstantInfo[] cpEntries) {
        this.cpEntries = cpEntries;
    }

    // TODO: desc
    public static ConstantPoolBuilder builder() {
        return new ConstantPoolBuilder();
    }

    /**
     * @param in the {@link DataInputStream} from which the {@link ConstantPool} is
     *           supposed to be read.
     */
    public ConstantPool(DataInputStream in) throws IOException {
        final int cpSize = in.readUnsignedShort();
        cpEntries = new ConstantInfo[cpSize];
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++) {
            final ConstantInfo constantInfo = ConstantInfo.read(in);
            cpEntries[i] = constantInfo;
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = constantInfo.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
    }

    /**
     * @return the cp entry at given index.
     */
    public ConstantInfo getEntry(int index) {
       return cpEntries[index];
    }

    /**
     * @return the cp's size.
     */
    public int getSize() {
        return cpEntries.length;
    }

    /**
     * Writes the {@link ConstantPool} to the given {@link DataOutputStream}.
     *
     * @param os the {@link DataOutputStream} the {@link ConstantPool} should be written to.
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(cpEntries.length);
        for(int i = 1 /* the cp's size is 1 less than given */; i < cpEntries.length; i++) {
            final ConstantInfo constantInfo = cpEntries[i];
            constantInfo.write(os);
            // longs and doubles take up 2 spaces in the constant pool.
            final int tag = constantInfo.getTag();
            if(tag == CONSTANT_LONG ||
               tag == CONSTANT_DOUBLE)
                i++ /* padding */;
        }
        os.flush();
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

    // TODO: desc
    public static final class ConstantPoolBuilder {
        private final HashMap<ConstantInfo, Integer> constantInfoIndexMap = new HashMap<>();
        private final List<ConstantInfo> constantPool = new ArrayList<>();

        // prevent construction :/
        private ConstantPoolBuilder() {
            constantPool.add(null);
        }

        private int add(ConstantInfo constantInfo) {
            Integer index = constantInfoIndexMap.get(constantInfo);
            if(index != null)
                return index;
            constantInfoIndexMap.put(constantInfo, index = constantPool.size());
            constantPool.add(constantInfo);
            return index;
        }

        public int addConstantInteger(int bytes) {
            return add(
                new ConstantInteger(
                    CONSTANT_INTEGER,
                    bytes));
        }

        public int addConstantFloat(float bytes) {
            return add(
                new ConstantInteger(
                    CONSTANT_FLOAT,
                    bytes));
        }

        public int addConstantInvokeDynamic() {
            // TODO: impl
            return 0;
        }

        public int addConstantLong(long bytes) {
            final int index = add(
                new ConstantLong(
                    CONSTANT_LONG,
                    bytes));
            constantPool.add(null);
            return index;
        }

        public int addConstantDouble(double bytes) {
            final int index = add(
                new ConstantLong(
                    CONSTANT_DOUBLE,
                    bytes));
            constantPool.add(null);
            return index;
        }

        public int addConstantUtf8(String utf8) {
            return add(new ConstantUtf8(utf8));
        }

        public int addConstantClass(String clazz) {
            return add(
                new ConstantName(
                    CONSTANT_CLASS,
                    addConstantUtf8(clazz)));
        }

        public int addConstantString(String string) {
            return add(
                new ConstantName(
                    CONSTANT_STRING,
                    addConstantUtf8(string)));
        }

        public int addConstantMethodType(String methodType) {
            return add(
                new ConstantName(
                    CONSTANT_METHOD_TYPE,
                    addConstantUtf8(methodType)));
        }

        public int addConstantNameAndType(String name, String desc) {
            return add(
                new ConstantNameAndType(
                    addConstantUtf8(name),
                    addConstantUtf8(desc)));
        }

        public int addConstantFieldRef(String clazz, String name, String desc) {
            return add(
                new ConstantMethodRef(
                    CONSTANT_FIELD_REF,
                    addConstantClass(clazz),
                    addConstantNameAndType(name, desc)));
        }

        public int addConstantMethodRef(String clazz, String name, String desc) {
            return add(
                new ConstantMethodRef(
                    CONSTANT_METHOD_REF,
                    addConstantClass(clazz),
                    addConstantNameAndType(name, desc)));
        }

        public int addConstantInterfaceMethodRef(String clazz, String name, String desc) {
            return add(
                new ConstantMethodRef(
                    CONSTANT_INTERFACE_METHOD_REF,
                    addConstantClass(clazz),
                    addConstantNameAndType(name, desc)));
        }

        public int addConstantMethodHandle(byte refKind, String clazz, String name, String desc) {
            int refIndex;
            switch(refKind) {
                case REF_GETFIELD:
                case REF_GETSTATIC:
                case REF_PUTFIELD:
                case REF_PUTSTATIC:
                    refIndex = addConstantFieldRef(clazz, name, desc);
                    break;
                default:
                case REF_INVOKEVIRTUAL:
                case REF_NEWINVOKESPECIAL:
                case REF_INVOKESTATIC:
                case REF_INVOKESPECIAL:
                    refIndex = addConstantFieldRef(clazz, name, desc);
                    break;
                case REF_INVOKEINTERFACE:
                    refIndex = addConstantFieldRef(clazz, name, desc);
                    break;
            }
            return add(
                new ConstantMethodHandle(
                    refKind,
                    refIndex));
        }

        public ConstantPool build() {
            return new ConstantPool(
                constantPool.toArray(
                    new ConstantInfo[constantPool.size()]));
        }
    }
}
