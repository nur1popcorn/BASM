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

import com.nur1popcorn.basm.classfile.tree.methods.InstructionHandle;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.LOOKUPSWITCH;
import static com.nur1popcorn.basm.Constants.TABLESWITCH;

public final class SwitchInstruction extends Instruction implements IInstructionPointer {
    protected int padding;
    private int defaultIndex;
    private final List<Integer> keys;
    protected final List<Integer> indices;

    /**
     * @param opcode
     */
    SwitchInstruction(byte opcode, int padding, int defaultIndex, int keys[], int indices[]) {
        super(opcode);
        this.padding = padding;
        this.defaultIndex = defaultIndex;
        this.keys = new ArrayList<>(keys.length);
        for(int i : keys)
            this.keys.add(i);
        this.indices = new ArrayList<>(indices.length);
        for(int i : indices)
            this.indices.add(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitInstructionPointer(this);
        visitor.visitSwitchInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os, InstructionList instructions) throws IOException {
        final int pc = os.size();
        os.writeByte(opcode);
        /*
            ^
          3 | \    \
          2 |  \    \
          1 |   \    \
          0 +----------->
         -1  \    \    \

            I:  y = 3 - mod(x + 3, 4)
            II: mod(x, 2^n) = x & (2^n - 1)

            => y = 3 - (x & 3)
        */
        final int padding = 4 - (os.size() & 0x3);
        for(int i = 0; i < padding; i++)
            os.writeByte(0);
        os.writeInt(computeIndex(
            pc, indexDefault(instructions), instructions
        ));
        final List<InstructionHandle> indexTargets = indexTargets(instructions);
        switch(opcode) {
            case TABLESWITCH:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
                final int low = keys.get(0);
                final int high = low + indices.size() - 1;
                os.writeInt(low);
                os.writeInt(high);
                for(InstructionHandle handle : indexTargets)
                    os.writeInt(computeIndex(
                        pc, handle, instructions
                    ));
                break;
            case LOOKUPSWITCH:
                os.writeInt(indices.size());
                for (int i = 0; i < indexTargets.size(); i++) {
                    os.writeInt(keys.get(i));
                    os.writeInt(computeIndex(
                        pc, indexTargets.get(i), instructions
                    ));
                }
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(InstructionList instructions) {
        indexDefault(instructions)
            .addPointer(this);
        indexTargets(instructions)
            .forEach(h -> h.addPointer(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(InstructionList instructions) {
        indexDefault(instructions)
            .removePointer(this);
        indexTargets(instructions)
            .forEach(h -> h.removePointer(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int newIndex) {
        //this.defaultIndex = newIndex;
    }

    /**
     * @param instructions
     *
     * @return
     */
    public InstructionHandle indexDefault(InstructionList instructions) {
        return instructions.get(defaultIndex);
    }

    /**
     * @param instructions
     *
     * @return
     */
    public List<InstructionHandle> indexTargets(InstructionList instructions) {
        final List<InstructionHandle> targets = new ArrayList<>(indices.size());
        for (int index : indices)
            targets.add(instructions.get(index));
        return targets;
    }
}
