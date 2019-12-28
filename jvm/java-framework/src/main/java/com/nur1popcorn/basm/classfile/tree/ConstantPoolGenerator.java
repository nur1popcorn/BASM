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

package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.IClassVersionProvider;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.constants.*;

import java.util.Arrays;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_7;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_8;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_9;

/**
 * The {@link ConstantPoolGenerator} is used to build a {@link ConstantPool} without duplicates.
 * One can add {@link ConstantInfo}s using the {@link #addConstant(ConstantInfo)} method and it's abstractions.
 * Finally one can obtain the constructed {@link ConstantPool} using the {@link #getConstantPool()} method.
 *
 * @see ConstantPool
 * @see ConstantInfo
 *
 * @see #lookupConstant(ConstantInfo)
 * @see #addConstant(ConstantInfo)
 *
 * @see #getConstantPool()
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */

/**
 *
 */
public final class ConstantPoolGenerator extends ConstantPool {
    private int index = cpEntries.length;

    private final IClassVersionProvider provider;

    /**
     * {@inheritDoc}
     */
    public ConstantPoolGenerator(ConstantInfo[] cpEntries) {
        super(cpEntries);
        // TODO: impl interface for verifying.
        this.provider = null;
    }

    /**
     *
     * @param info
     *
     * @return
     */
    private int findEntry(ConstantInfo info) {
        final int integer = indexOf(info);
        if(integer != -1)
            return integer;
        return 0;
    }

    /**
     *
     * @param info
     *
     * @return
     */
    private int addEntry(ConstantInfo info) {
        int ret = findEntry(info);
        if(ret != 0)
            return ret;
        if(index + 2 >= cpEntries.length)
            cpEntries = Arrays.copyOf(
                cpEntries,
                index << 1
            );
        cpEntries[index++] = info;
        if(info instanceof IConstantPoolPointer)
            ((IConstantPoolPointer) info)
                .attach(this);
        // longs and doubles take up 2 spaces in the constant pool.
        final int tag = info.getTag();
        if(tag == CONSTANT_LONG ||
           tag == CONSTANT_DOUBLE)
            index++ /* padding */;
        return index;
    }

    private void removeSafe(int index) {
        for(int i = index + 1; i < cpEntries.length; i++) {
            final ConstantInfo info = cpEntries[i];
            if(info != null) {
                final IConstantPoolPointer pointers[] = info.getPointers();
                for(IConstantPoolPointer pointer : pointers) {
                    pointer.update(i, i - 1);
                }
            }
        }

        final int moved = cpEntries.length - index - 1;
        if(moved > 0)
            System.arraycopy(
                cpEntries, index + 1,
                cpEntries, index, moved
            );
        cpEntries[this.index--] = null;
    }

    /**
     *
     * @param index
     * @throws ConstantLostException
     * @throws MalformedClassFileException
     */
    public void removeEntry(int index) throws ConstantLostException {
        final int length = cpEntries.length;
        if(index > length)
            throw new MalformedClassFileException(
                "Index out of bounds: index=" + index +
                ", length=" + length
            );
        final ConstantInfo old = cpEntries[index];
        if(old == null)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at index: index=" + index + " is null");
        final int tag = old.getTag();
        switch(tag) {
            case CONSTANT_CLASS:
            case CONSTANT_STRING:
            case CONSTANT_METHOD_TYPE:
            case CONSTANT_MODULE:
            case CONSTANT_PACKAGE: {
                final ConstantName name = (ConstantName) old;
                name.indexName(this)
                    .removePointer(name);
            }   break;
            case CONSTANT_NAME_AND_TYPE: {
                final ConstantNameAndType nameAndType = (ConstantNameAndType) old;
                nameAndType.indexName(this)
                           .removePointer(nameAndType);
                nameAndType.indexDesc(this)
                           .removePointer(nameAndType);
            }   break;
            case CONSTANT_FIELD_REF:
            case CONSTANT_METHOD_REF:
            case CONSTANT_INTERFACE_METHOD_REF: {
                final ConstantMethodRef methodRef = (ConstantMethodRef) old;
                methodRef.indexClass(this)
                         .removePointer(methodRef);
                methodRef.indexNameAndType(this)
                         .removePointer(methodRef);
            }   break;
            case CONSTANT_METHOD_HANDLE: {
                final ConstantMethodHandle methodHandle = (ConstantMethodHandle) old;
                methodHandle.indexRef(this)
                            .removePointer(methodHandle);
            }   break;
            case CONSTANT_INVOKEDYNAMIC: {
                final ConstantInvokeDynamic invokeDynamic = (ConstantInvokeDynamic) old;
                invokeDynamic.indexNameAndType(this)
                             .removePointer(invokeDynamic);
            }   break;
            case CONSTANT_LONG:
            case CONSTANT_DOUBLE:
                removeSafe(index + 1);
                break;
        }
        removeSafe(index);

        final IConstantPoolPointer pointers[] = old.getPointers();
        if(pointers.length != 0)
            throw new ConstantLostException(pointers);
    }

    /**
     *
     *
     * @param bytes
     */
    public int findInt(int bytes) {
        final ConstantInteger integer = new ConstantInteger(bytes);
        final int index = findEntry(integer);
        if(index != 0)
            return index;
        return addEntry(integer);
    }

    /**
     *
     *
     * @param bytes
     */
    public int findFloat(float bytes) {
        final ConstantInteger cFloat = new ConstantInteger(bytes);
        final int index = findEntry(cFloat);
        if(index != 0)
            return index;
        return addEntry(cFloat);
    }

    /**
     *
     *
     * @param str
     */
    public int findUTF8(String str) {
        final ConstantUTF8 utf8 = new ConstantUTF8(str);
        final int index = findEntry(utf8);
        if(index != 0)
            return index;
        return addEntry(utf8);
    }

    /**
     *
     * @param name
     * @param desc
     *
     * @see #findUTF8(String)
     */
    public int findNameAndType(String name, String desc) {
        final ConstantNameAndType nameAndType = new ConstantNameAndType(
            findUTF8(name),
            findUTF8(desc)
        );
        final int index = findEntry(nameAndType);
        if(index != 0)
            return index;
        return addEntry(nameAndType);
    }

    /**
     *
     * @param bootstrapMethodAttrIndex
     * @param name
     * @param desc
     *
     * @see #findNameAndType(String, String)
     */
    public int findInvokeDynamic(int bootstrapMethodAttrIndex, String name, String desc) {
        provider.ensureMajorVersion(JAVA_7);
        final ConstantInvokeDynamic invokeDynamic = new ConstantInvokeDynamic(
            CONSTANT_INVOKEDYNAMIC,
            bootstrapMethodAttrIndex,
            findNameAndType(
                name,
                desc
            )
        );
        final int index = findEntry(invokeDynamic);
        if(index != 0)
            return index;
        return addEntry(invokeDynamic);
    }

    /**
     *
     *
     * @param bytes
     */
    public int findLong(long bytes) {
        final ConstantLong cLong = new ConstantLong(bytes);
        final int index = findEntry(cLong);
        if(index != 0)
            return index;
        return addEntry(cLong);
    }

    /**
     *
     *
     * @param bytes
     */
    public int findDouble(double bytes) {
        final ConstantLong cDouble = new ConstantLong(bytes);
        final int index = findEntry(cDouble);
        if(index != 0)
            return index;
        return addEntry(cDouble);
    }

    /**
     *
     *
     * @param name
     */
    public int findClass(String name) {
        final ConstantName cName = new ConstantName(
            CONSTANT_CLASS,
            findUTF8(name)
        );
        final int index = findEntry(cName);
        if(index != 0)
            return index;
        return addEntry(cName);
    }

    /**
     *
     *
     * @param name
     */
    public int findString(String name) {
        final ConstantName cName = new ConstantName(
            CONSTANT_STRING,
            findUTF8(name)
        );
        final int index = findEntry(cName);
        if(index != 0)
            return index;
        return addEntry(cName);
    }

    /**
     *
     *
     * @param name
     */
    public int findMethodType(String name) {
        provider.ensureMajorVersion(JAVA_7);
        final ConstantName cName = new ConstantName(
            CONSTANT_METHOD_TYPE,
            findUTF8(name)
        );
        final int index = findEntry(cName);
        if(index != 0)
            return index;
        return addEntry(cName);
    }

    public int findModule(String name) {
        provider.ensureMajorVersion(JAVA_9);
        final ConstantName cName = new ConstantName(
            CONSTANT_MODULE,
            findUTF8(name)
        );
        final int index = findEntry(cName);
        if(index != 0)
            return index;
        return addEntry(cName);
    }

    public int findPackage(String name) {
        provider.ensureMajorVersion(JAVA_9);
        final ConstantName cName = new ConstantName(
            CONSTANT_PACKAGE,
            findUTF8(name)
        );
        final int index = findEntry(cName);
        if(index != 0)
            return index;
        return addEntry(cName);
    }


    /**
     *
     *
     * @param owner
     * @param name
     * @param desc
     */
    public int findFieldRef(String owner, String name, String desc) {
        provider.ensureMajorVersion(JAVA_7);
        final ConstantMethodRef fieldRef = new ConstantMethodRef(
            CONSTANT_FIELD_REF,
            findClass(owner),
            findNameAndType(
                name,
                desc
            )
        );
        final int index = findEntry(fieldRef);
        if(index != 0)
            return index;
        return addEntry(fieldRef);
    }

    /**
     *
     *
     * @param owner
     * @param name
     * @param desc
     */
    public int findMethodRef(String owner, String name, String desc) {
        provider.ensureMajorVersion(JAVA_7);
        final ConstantMethodRef methodRef = new ConstantMethodRef(
            CONSTANT_METHOD_REF,
            findClass(owner),
            findNameAndType(
                name,
                desc
            )
        );
        final int index = findEntry(methodRef);
        if(index != 0)
            return index;
        return addEntry(methodRef);
    }

    /**
     *
     *
     * @param owner
     * @param name
     * @param desc
     */
    public int findInterfaceMethodRef(String owner, String name, String desc) {
        provider.ensureMajorVersion(JAVA_7);
        final ConstantMethodRef interfaceMethodRef = new ConstantMethodRef(
            CONSTANT_INTERFACE_METHOD_REF,
            findClass(owner),
            findNameAndType(
                name,
                desc
            )
        );
        final int index = findEntry(interfaceMethodRef);
        if(index != 0)
            return index;
        return addEntry(interfaceMethodRef);
    }

    /**
     *
     *
     * @param refKind
     * @param owner
     * @param name
     * @param desc
     * @param itf
     */
    public int findMethodHandle(byte refKind, String owner, String name, String desc, boolean itf) {
        provider.ensureMajorVersion(JAVA_7);
        // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.8-200-C
        int refIndex;
        switch(refKind) {
            case REF_GETFIELD:
            case REF_GETSTATIC:
            case REF_PUTFIELD:
            case REF_PUTSTATIC:
                refIndex = findFieldRef(owner, name, desc);
                break;
            case REF_INVOKESTATIC:
            case REF_INVOKESPECIAL:
                if(itf) {
                    provider.ensureMajorVersion(JAVA_8);
                    refIndex = findInterfaceMethodRef(owner, name, desc);
                    break;
                }
                // fallthrough.
            case REF_INVOKEVIRTUAL:
            case REF_NEWINVOKESPECIAL:
                refIndex = findMethodRef(owner, name, desc);
                break;
            default:
                throw new IllegalArgumentException(
                    "Invalid REF_Kind: refKind=" + Integer.toHexString(refKind));
        }
        final ConstantMethodHandle methodHandle = new ConstantMethodHandle(refKind, refIndex);
        final int index = findEntry(methodHandle);
        if(index != 0)
            return index;
        return addEntry(methodHandle);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder()
            .append("ConstantPoolGenerator[");
        if(cpEntries.length != 0) {
            stringBuilder.append(cpEntries[0]);
            for(int i = 1; i < cpEntries.length; i++)
                stringBuilder.append(",")
                             .append(cpEntries[i]);
        }
        return stringBuilder.append("]")
            .toString();
    }
}
