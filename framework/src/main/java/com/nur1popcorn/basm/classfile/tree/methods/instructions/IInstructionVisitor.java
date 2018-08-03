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

package com.nur1popcorn.basm.classfile.tree.methods.instructions;

import com.nur1popcorn.basm.classfile.constants.IConstantPoolPointer;

public interface IInstructionVisitor {
    default void visitNoParameterInstruction(NoParameterInstruction instruction) {}
    default void visitPushInstruction(PushInstruction instruction) {}

    default void visitCPPointer(IConstantPoolPointer pointer) {}
    default void visitCPInstruction(CPInstruction instruction) {}
    default void visitLDCInstruction(LDCInstruction instruction) {}

    default void visitLocalVariableInstruction(LocalVariableInstruction instruction) {}
    default void visitIIncInstruction(IIncInstruction instruction) {}

    default void visitInstructionPointer(IInstructionPointer instruction) {}
    default void visitJumpInstruction(JumpInstruction instruction) {}
    default void visitSwitchInstruction(SwitchInstruction instruction) {}

    default void visitFieldMethodInstruction(FieldMethodInstruction instruction) {}
    default void visitMethodInstruction(MethodInstruction instruction) {}
    default void visitFieldInstruction(FieldInstruction instruction) {}

    default void visitClassInstruction(ClassInstruction instruction) {}

    default void visitWideInstruction(WideInstruction instruction) {}
}
