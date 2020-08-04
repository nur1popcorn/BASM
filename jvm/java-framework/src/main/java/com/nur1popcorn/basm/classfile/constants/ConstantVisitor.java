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

public interface ConstantVisitor
{
    default void visitInt(ConstantInteger info) {}
    default void visitFloat(ConstantInteger info) {}
    default void visitInvokeDynamic(ConstantInvokeDynamic info) {}
    default void visitDynamicConstant(ConstantInvokeDynamic info) {}
    default void visitLong(ConstantLong info) {}
    default void visitDouble(ConstantLong info) {}
    default void visitMethodHandle(ConstantMethodHandle info) {}
    default void visitMethodRef(ConstantMethodRef info) {}
    default void visitClass(ConstantName info) {}
    default void visitString(ConstantName info) {}
    default void visitMethodType(ConstantName info) {}
    default void visitNameAndType(ConstantNameAndType info) {}
    default void visitUTF8(ConstantUTF8 info) {}
    default void visitModule(ConstantName info) {}
    default void visitPackage(ConstantName info) {}

    default void visitCPPointer(ConstantPoolPointer pointer) {}
}
