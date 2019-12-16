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

import com.nur1popcorn.basm.classfile.tree.methods.IInstructionPointer;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.utils.WeakHashSet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import static com.nur1popcorn.basm.classfile.Opcode.INVALID;

public class Label extends Instruction {
    /*
     *
     */
    private Set<IInstructionPointer> pointers;

    public Label() {
        super(INVALID);
    }

    @Override
    public void accept(IInstructionVisitor visitor) {

    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        //Do not do anything
    }

    @Override
    public int getLength() {
        return 0;
    }

    /**
     * @param pointer
     */
    public void addPointer(IInstructionPointer pointer) {
        if(pointers == null)
            pointers = new WeakHashSet<>();
        pointers.add(pointer);
    }

    /**
     * @param pointer
     */
    public void removePointer(IInstructionPointer pointer) {
        pointers.remove(pointer);
    }

    /**
     * @return
     */
    public boolean hasPointers() {
        return pointers != null &&
            pointers.size() != 0;
    }

    /**
     * @return
     */
    public IInstructionPointer[] getPointers() {
        if(pointers == null)
            return new IInstructionPointer[0];
        final IInstructionPointer arr[] = new IInstructionPointer[pointers.size()];
        pointers.toArray(arr);
        return arr;
    }
}
