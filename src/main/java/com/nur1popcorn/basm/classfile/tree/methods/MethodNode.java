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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.BIPushInstruction;

public class MethodNode implements IMethodVisitor {
    public int access;

    public String name,
                  desc;

    private Code code;

    private int visitorInstructionIndex = -1;

    public MethodNode(FieldMethodInfo methodInfo, ConstantPool constantPool) throws MethodNodeParseException {
        this.access = methodInfo.getAccess();

        name = methodInfo.indexName(constantPool).bytes;
        desc = methodInfo.indexDesc(constantPool).bytes;

        final AttributeInfo attributeInfos[] = methodInfo.getAttributes();
        for(AttributeInfo attributeInfo : attributeInfos)
            switch(attributeInfo.indexName(constantPool).bytes) {
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3
                case "Code":
                    code = new Code((AttributeCode)attributeInfo, constantPool);
                    break;
                default:
                    throw new MethodNodeParseException(); //TODO: make useful.
            }
    }

    public Code getCode() {
        return code;
    }

    @Override
    public boolean visitCodeAt(int index) {
        return false;
    }

    @Override
    public void visitCodeAtEnd() {

    }

    @Override
    public int visitIndex() {
        return 0;
    }

    @Override
    public boolean visitNextInstruction() {
        return false;
    }

    @Override
    public Instruction visitInstructionAtIndex() {
        return null;
    }

    @Override
    public void visitMaxes(int maxStack, int maxLocals) {

    }

    @Override
    public void visitMaxes() {

    }

    @Override
    public void visitBiPushInstruction(byte data) {

    }

    @Override
    public void visitClassInstruction(byte opcode, String clazz) {

    }

    @Override
    public void visitIIncInstruction(byte index, byte constant) {

    }

    @Override
    public void visitInvokeDynamicInstruction() {

    }

    @Override
    public void visitInvokeInterfaceInstruction() {

    }

    @Override
    public void visitJumpInstruction() {

    }

    @Override
    public void visitLDCInstruction() {

    }

    @Override
    public void visitLocalVariableInstruction() {

    }

    @Override
    public void visitLookupSwitchInstruction() {

    }

    @Override
    public void visitMultiNewArrayInstruction() {

    }

    @Override
    public void visitNewArrayInstruction() {

    }

    @Override
    public void visitNoParameterInstruction() {

    }

    @Override
    public void visitRefInstruction() {

    }

    @Override
    public void visitSiPushInstruction() {

    }

    @Override
    public void visitTableSwitchInstruction() {

    }

    @Override
    public void visitWideInstruction() {

    }
}
