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

package com.nur1popcorn.basm.classfile.tree.methods;

public interface ICodeVisitor {
    boolean visitCodeAt(int index);
    void visitCodeAtEnd();

    boolean visitPrevInstruction();
    boolean visitNextInstruction();
    Instruction visitCurrentInstruction();

    void visitMaxes(int maxStack, int maxLocals);
    void visitMaxes();

    void visitBiPushInstruction(byte data);
    void visitClassInstruction(byte opcode, String clazz);
    void visitIIncInstruction(byte index, byte constant);
    void visitInvokeDynamicInstruction(); // TODO: impl
    void visitInvokeInterfaceInstruction(); // TODO: impl
    void visitJumpInstruction(byte opcode, Label label);
    void visitLDCInstruction(byte opcode, Object constant, byte tag);
    void visitLocalVariableInstruction(byte opcode, byte index);
    void visitLookupSwitchInstruction(); // TODO: impl
    void visitMultiNewArrayInstruction(String clazz, int dimensions);
    void visitNewArrayInstruction(byte atype);
    void visitNoParameterInstruction(byte opcode);
    void visitRefInstruction(byte opcode, String clazz, String name, String desc);
    void visitSiPushInstruction(short data);
    void visitTableSwitchInstruction(); //TODO: impl
    void visitWideInstruction(); //TODO: impl
}
