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
import com.nur1popcorn.basm.classfile.tree.FieldMethodNode;
import com.nur1popcorn.basm.classfile.tree.IFieldMethodNodeVisitor;

import java.io.IOException;

public final class MethodNode extends FieldMethodNode {
    //private final InstructionList instructionList;

    /**
     * @param access
     * @param nameIndex
     * @param descIndex
     * @param constantPool
     */
    public MethodNode(int access, int nameIndex, int descIndex, ConstantPool constantPool) {
        super(access, nameIndex, descIndex, constantPool);
        //instructionList = null;//new InstructionList();
    }

    /**
     * @param info
     * @param constantPool
     */
    public MethodNode(FieldMethodInfo info, ConstantPool constantPool) throws IOException {
        super(info, constantPool);
        //TODO: remove this
        //if (info.getCode() != null)
            //instructionList = new InstructionList(
            //    info.getCode().getByteCode(),
            //    constantPool
            //);
        //else
        //    instructionList = new InstructionList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IFieldMethodNodeVisitor visitor) {
        visitor.visitFieldMethodNode(this);
        visitor.visitFieldMethodInfo(null);
    }

    public InstructionList getInstructionList() {
        // TODO: remove
        //return instructionList;
        return null;
    }
}
