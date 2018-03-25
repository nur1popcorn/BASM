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

import com.nur1popcorn.basm.classfile.tree.methods.instructions.*;

public interface ICodeVisitor {
    default void visitBIPushInstruction(BIPushInstruction instruction) {}
    default void visitClassInstruction(ClassInstruction instruction) {}
    default void visitIIncInstruction(IIncInstruction instruction) {}
    default void visitInvokeDynamicInstruction(InvokeDynamicInstruction instruction) {}
    default void visitJumpInstruction(JumpInstruction instruction) {}
    default void visitLabel(Label instruction) {}
    default void visitLDCInstruction(LDCInstruction instruction) {}
    default void visitLocalVariableInstruction(LocalVariableInstruction instruction) {}
    default void visitMultiNewArrayInstruction(MultiANewArrayInstruction instruction) {}
    default void visitNewArrayInstruction(NewArrayInstruction instruction) {}
    default void visitNoParameterInstruction(NoParameterInstruction instruction) {}
    default void visitRefInstruction(RefInstruction instruction) {}
    default void visitSIPushInstruction(SIPushInstruction instruction) {}
    default void visitSwitchInstruction(SwitchInstruction instruction) {}
    default void visitWideInstruction(WideInstruction instruction) {}
}
