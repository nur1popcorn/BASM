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

    int visitIndex();
    //TODO: visitFollowInstruction(); -> requires me to impl
    boolean visitNextInstruction();
    Instruction visitInstructionAtIndex();

    void visitMaxes(int maxStack, int maxLocals);
    void visitMaxes();

    void visitBiPushInstruction(byte data);
    void visitClassInstruction(byte opcode, String clazz);
    void visitIIncInstruction(byte index, byte constant);
    void visitInvokeDynamicInstruction();
    void visitInvokeInterfaceInstruction();
    void visitJumpInstruction();
    void visitLDCInstruction();
    void visitLocalVariableInstruction();
    void visitLookupSwitchInstruction();
    void visitMultiNewArrayInstruction();
    void visitNewArrayInstruction();
    void visitNoParameterInstruction();
    void visitRefInstruction();
    void visitSiPushInstruction();
    void visitTableSwitchInstruction();
    void visitWideInstruction();
}
