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
import java.util.*;

import static com.nur1popcorn.basm.Constants.LOOKUPSWITCH;
import static com.nur1popcorn.basm.Constants.TABLESWITCH;

public final class SwitchInstruction extends Instruction implements IInstructionPointer {
    int address;
    private InstructionHandle defaultTarget;
    final TreeMap<Integer, InstructionHandle> targets;

    /**
     * @param opcode
     */
    SwitchInstruction(byte opcode, int address, InstructionHandle defaultTarget, TreeMap<Integer, InstructionHandle> targets) {
        super(opcode);
        this.address = address;
        this.defaultTarget = defaultTarget;
        this.targets = targets;
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
        final int start = os.size();
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
        while((os.size() & 0x3) != 0)
            os.writeByte(0);
        os.writeInt(computeIndex(
            start, defaultTarget, instructions
        ));
        switch(opcode) {
            case TABLESWITCH:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
                final int low = targets.firstKey();
                final int high = low + targets.size() - 1;
                os.writeInt(low);
                os.writeInt(high);
                for(InstructionHandle target : targets.values())
                    os.writeInt(computeIndex(
                        start, target, instructions
                    ));
                break;
            case LOOKUPSWITCH:
                os.writeInt(targets.size());
                for(Map.Entry<Integer, InstructionHandle> entry : targets.entrySet()) {
                    os.writeInt(entry.getKey());
                    os.writeInt(computeIndex(
                        start, entry.getValue(), instructions
                    ));
                }
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach() {
        defaultTarget.addPointer(this);
        targets.values().forEach(h -> h.addPointer(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        defaultTarget.removePointer(this);
        targets.values().forEach(h -> h.removePointer(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(InstructionHandle oldHandle, InstructionHandle newHandle) {
        if(oldHandle.equals(defaultTarget))
            defaultTarget = newHandle;
        for(Map.Entry<Integer, InstructionHandle> entry : targets.entrySet())
            if(entry.getValue().equals(oldHandle))
                entry.setValue(newHandle);
    }

    public void updateAddress(InstructionHandle thisHandle, InstructionList instructions) {
        address =  computeAddress(thisHandle, instructions);
    }
}
