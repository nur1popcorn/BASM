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
package com.nur1popcorn.basm.classfile.attributes;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodHandle;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.nur1popcorn.basm.Constants.CONSTANT_METHOD_HANDLE;

/**
 * The {@link BootstrapMethodsEntry} holds information about which method is called
 * to resolve the method that will be called by invokedynamic instruction. These
 * methods which resolve others are named 'bootstrap methods.' A bootstrap method
 * accepts at least the following three parameters, in this order:
 * - {@link java.lang.invoke.MethodHandles.Lookup}
 * - {@link String}
 * - {@link java.lang.invoke.MethodType}
 *
 * Additionally, a bootstrap method may accept more parameters. The value of these
 * parameters are static and are denoted by indices in the constant pool. Entries
 * are only valid if they are one of the following types in the constant pool:
 * - {@link com.nur1popcorn.basm.classfile.constants.ConstantName}
 * - {@link com.nur1popcorn.basm.classfile.constants.ConstantInteger}
 * - {@link com.nur1popcorn.basm.classfile.constants.ConstantLong}
 * - {@link com.nur1popcorn.basm.classfile.constants.ConstantMethodHandle}
 * In order to add these extra parameters, they must be specified in the
 * 'argumentIndices' array. The indices in this array will be the order that they
 * are given in the method and will assume their literal types in the JRE.
 *
 * Finally, a bootstrap method must return a {@link java.lang.invoke.CallSite}.
 * The value returned will be cached by the JVM and invoked anytime that the
 * invokedynamic instruction referencing this entry is performed.
 *
 * @author Ben Kinney
 * @since 1.0.0-alpha
 */
public final class BootstrapMethodsEntry {
    /* The value of the 'methodRefIndex' variable is an index into the constant pool
     * with the type of the entry being a ConstantMethodHandle.
     *
     * The method being referenced will be the bootstrap method called by the JVM.
     */
    private int methodHandleIndex;

    /* The value of the 'argumentIndices' variable determines additional parameters
     * that will be given to the bootstrap method by the JVM.
     */
    private int[] argumentIndices;

    public BootstrapMethodsEntry(int methodHandleIndex, int[] argumentIndices) {
        this.methodHandleIndex = methodHandleIndex;
        this.argumentIndices = argumentIndices;
    }

    /**
     * Get the index of the {@link ConstantMethodRef}.
     * @return The index.
     */
    public int getMethodHandleIndex() {
        return methodHandleIndex;
    }

    /**
     * Get the indices where the argument values lie in the constant pool.
     * @return The indices.
     */
    public int[] getArgumentIndices() {
        return argumentIndices;
    }

    /**
     * Get the index for a specific argument in the constant pool.
     * @param argument The argument number.
     * @return The index.
     */
    public int getArgumentIndex(int argument) {
        return argumentIndices[argument];
    }

    /**
     * Get the {@link ConstantMethodHandle} in the constant pool.
     * @param pool The constant pool.
     * @return The {@link ConstantMethodHandle}.
     */
    public ConstantMethodHandle indexMethodHandle(ConstantPool pool) {
        return pool.getEntry(methodHandleIndex, CONSTANT_METHOD_HANDLE);
    }

    /**
     * Get the constants of the arguments in the constant pool.
     * @param pool The constant pool.
     * @return A {@link ConstantInfo} array.
     */
    public ConstantInfo[] indexArguments(ConstantPool pool) {
        ConstantInfo[] arguments = new ConstantInfo[argumentIndices.length];
        for(int i = 0; i < argumentIndices.length; i++)
            arguments[i] = pool.getEntry(argumentIndices[i]);
        return arguments;
    }

    /**
     * Get the constant of a specific argument in the constant pool.
     * @param pool The constant pool.
     * @param argument The argument number.
     * @return The {@link ConstantInfo}.
     */
    public ConstantInfo indexArgument(ConstantPool pool, int argument) {
        return pool.getEntry(argumentIndices[argument]);
    }

    /**
     * Set the index that declares the bootstrap method.
     * @param methodHandleIndex The index.
     */
    public void setMethodHandleIndex(int methodHandleIndex) {
        this.methodHandleIndex = methodHandleIndex;
    }

    /**
     * Set the argument indices.
     * @param argumentIndices The indices.
     */
    public void setArgumentIndices(int[] argumentIndices) {
        this.argumentIndices = argumentIndices;
    }

    /**
     * Set a specific argument index.
     * @param argument The argument number.
     * @param entryIndex The index of the entry.
     */
    public void setArgumentIndex(int argument, int entryIndex) {
        argumentIndices[argument] = entryIndex;
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(methodHandleIndex);
        os.writeShort(argumentIndices.length);
        for(int argument : argumentIndices)
            os.writeShort(argument);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BootstrapMethodsEntry that = (BootstrapMethodsEntry) o;
        return methodHandleIndex == that.methodHandleIndex &&
               Arrays.equals(argumentIndices, that.argumentIndices);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodHandleIndex);
        result = 31 * result + Arrays.hashCode(argumentIndices);
        return result;
    }
}
