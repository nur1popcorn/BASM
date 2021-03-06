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

import com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction;
import com.nur1popcorn.basm.utils.WeakHashSet;

import java.util.Iterator;
import java.util.Set;

/**
 * The {@link InstructionHandle}
 */
public final class InstructionHandle implements Iterable<InstructionHandle> {
    /*
     *
     */
    private Set<IInstructionPointer> pointers;
    /*
     *
     */
    InstructionHandle next,
                      prev;


    private final Instruction handle;

    /**
     * @param handle
     */
    InstructionHandle(Instruction handle) {
        this.handle = handle;
    }

    /**
     * @param pointer
     */
    public final void addPointer(IInstructionPointer pointer) {
        if(pointers == null)
            pointers = new WeakHashSet<>();
        pointers.add(pointer);
    }

    /**
     * @param pointer
     */
    public final void removePointer(IInstructionPointer pointer) {
        pointers.remove(pointer);
    }

    /**
     * @return
     */
    public final boolean hasPointers() {
        return pointers != null &&
               pointers.size() != 0;
    }

    /**
     * @return
     */
    public final IInstructionPointer[] getPointers() {
        if(pointers == null)
            return new IInstructionPointer[0];
        final IInstructionPointer arr[] = new IInstructionPointer[pointers.size()];
        pointers.toArray(arr);
        return arr;
    }

    /**
     * @return
     */
    public Instruction getHandle() {
        return handle;
    }

    @Override
    public Iterator<InstructionHandle> iterator() {
        return new Iterator<InstructionHandle>() {
            private InstructionHandle cursor = InstructionHandle.this;

            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            @Override
            public InstructionHandle next() {
                final InstructionHandle ih = cursor;
                cursor = ih.next;
                return ih;
            }
        };
    }
}
