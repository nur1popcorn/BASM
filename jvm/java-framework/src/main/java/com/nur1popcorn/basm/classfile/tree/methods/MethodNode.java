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

import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.ClassVisitor;
import com.nur1popcorn.basm.classfile.attributes.*;
import com.nur1popcorn.basm.classfile.tree.ConstantPoolGenerator;
import com.nur1popcorn.basm.classfile.tree.FieldMethodNode;

import java.io.IOException;

public final class MethodNode extends FieldMethodNode {
    private InstructionList instructionList = new InstructionList();

    public MethodNode(int access, String name, String desc, ConstantPoolGenerator constantPool) {
        super(access, name, desc, constantPool);
    }

    @Override
    public void visit(AttributeCode attribute) {
        try {
            instructionList = new InstructionList(attribute.getCode(), constantPool);
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
        attributes.add(attribute);
    }

    @Override
    public void visit(AttributeLineNumberTable attribute) {
        attributes.add(attribute);
        for(LineNumberTableEntry lineNumberTableEntry : attribute.getTable()) {

        }
    }

    @Override
    public void accept(ClassVisitor visitor) {
        /*final List<AttributeInfo> attributes = new ArrayList<>();
        if(instructionList.size() != 0) {
            final AttributeCode code = new AttributeCode(constantPool.findUTF8("Code"));
            attributes.add(code);
        }*/
        final AttributeVisitor methodVisitor =
            visitor.visitMethod(new FieldMethodInfo(
                getAccessFlags(),
                constantPool.findUTF8(getName()),
                constantPool.findUTF8(getDesc()))
            );
        for(AttributeInfo info : attributes)
            info.accept(methodVisitor);
    }

    public InstructionList getInstructionList() {
        return instructionList;
    }
}
