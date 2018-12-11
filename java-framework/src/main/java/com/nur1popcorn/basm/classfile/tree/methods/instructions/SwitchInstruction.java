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
    private int defaultIndex;
    private final List<Integer> keys;
    private final List<Integer> indices;

    /**
     * @param opcode
     */
    SwitchInstruction(byte opcode, int defaultIndex, int keys[], int indices[]) {
        super(opcode);
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
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
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
        final int padding = 3 - (os.size() & 0x3);
        for(int i = 0; i < padding; i++)
            os.write(0);
        os.writeInt(defaultIndex);
        //TODO: fix this
        switch(opcode) {
            case TABLESWITCH:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
                int length = indices.size();
                os.writeInt(length);
                os.writeInt(length);
                for (Integer index : indices)
                    os.writeInt(index);
                break;
            case LOOKUPSWITCH:
                os.writeInt(indices.size());
                for(int i = 0; i < indices.size(); i++) {
                    os.writeInt(keys.get(i));
                    os.writeInt(indices.get(i));
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
