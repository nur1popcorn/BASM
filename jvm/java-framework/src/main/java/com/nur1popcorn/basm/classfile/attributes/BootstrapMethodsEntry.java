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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Ben Kinney
 * @since 1.0.0-alpha
 */
public final class BootstrapMethodsEntry {
    private int methodRef;
    private int[] arguments;

    public BootstrapMethodsEntry(int methodRef, int[] arguments)
    {
        this.methodRef = methodRef;
        this.arguments = arguments;
    }

    public int getMethodRef() {
        return methodRef;
    }

    public int[] getArguments() {
        return arguments;
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(methodRef);
        os.writeShort(arguments.length);
        for(int argument : arguments)
            os.writeShort(argument);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BootstrapMethodsEntry that = (BootstrapMethodsEntry) o;
        return methodRef == that.methodRef &&
            Arrays.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodRef);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }
}
