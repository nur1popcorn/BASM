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

package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nur1popcorn.basm.Constants.*;

public final class ConstantPoolBuilder {
    private final Map<ConstantInfo, Integer> constantInfoIndexMap = new HashMap<>();
    private final List<ConstantInfo> constantPool = new ArrayList<>();

    public ConstantPoolBuilder() {
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
                bytes
            ));
    }

    public int addConstantFloat(float bytes) {
        return add(
            new ConstantInteger(
                CONSTANT_FLOAT,
                bytes
            ));
    }

    public int addConstantInvokeDynamic() {
        // TODO: impl
        return 0;
    }

    public int addConstantLong(long bytes) {
        final int index = add(
            new ConstantLong(
                CONSTANT_LONG,
                bytes
            ));
        constantPool.add(null);
        return index;
    }

    public int addConstantDouble(double bytes) {
        final int index = add(
            new ConstantLong(
                CONSTANT_DOUBLE,
                bytes
            ));
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
                addConstantUtf8(clazz)
            ));
    }

    public int addConstantString(String string) {
        return add(
            new ConstantName(
                CONSTANT_STRING,
                addConstantUtf8(string)
            ));
    }

    public int addConstantMethodType(String methodType) {
        return add(
            new ConstantName(
                CONSTANT_METHOD_TYPE,
                addConstantUtf8(methodType)
            ));
    }

    public int addConstantNameAndType(String name, String desc) {
        return add(
            new ConstantNameAndType(
                addConstantUtf8(name),
                addConstantUtf8(desc)
            ));
    }

    public int addConstantFieldRef(String clazz, String name, String desc) {
        return add(
            new ConstantMethodRef(
                CONSTANT_FIELD_REF,
                addConstantClass(clazz),
                addConstantNameAndType(name, desc)
            ));
    }

    public int addConstantMethodRef(String clazz, String name, String desc) {
        return add(
            new ConstantMethodRef(
                CONSTANT_METHOD_REF,
                addConstantClass(clazz),
                addConstantNameAndType(name, desc)
            ));
    }

    public int addConstantInterfaceMethodRef(String clazz, String name, String desc) {
        return add(
            new ConstantMethodRef(
                CONSTANT_INTERFACE_METHOD_REF,
                addConstantClass(clazz),
                addConstantNameAndType(name, desc)
            ));
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
                refIndex
            ));
    }

    public ConstantPool build() {
        return new ConstantPool(
            constantPool.toArray(
                new ConstantInfo[constantPool.size()]
            ));
    }
}