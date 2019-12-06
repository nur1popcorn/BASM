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

import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.IInstructionPointer;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;
import com.nur1popcorn.basm.utils.ByteDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SwitchInstruction extends Instruction implements IInstructionPointer {
    private int defaultIndex;
    private Instruction defaultTarget;
    private final List<KeyIndexPair> indices;

    /**
     * @param opcode
     */
    SwitchInstruction(Opcode opcode, int defaultIndex, KeyIndexPair indices[]) {
        super(opcode);
        this.defaultIndex = defaultIndex;
        this.indices = new ArrayList<>(indices.length);
        Collections.addAll(this.indices, indices);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitSwitchInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(ByteDataOutputStream os) throws IOException {
        final int offset = os.size();
        os.writeByte(opcode.getOpcode());
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
        os.writeInt(os.getOffset(defaultTarget) - offset);
        switch(opcode) {
            case TABLESWITCH:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
                final int low = indices.get(0).key;
                final int high = low + getCount() - 1;
                os.writeInt(low);
                os.writeInt(high);
                for(KeyIndexPair pair : indices)
                    os.writeInt(os.getOffset(pair.target) - offset);
                break;
            case LOOKUPSWITCH:
                os.writeInt(getCount());
                for(KeyIndexPair pair : indices) {
                    os.writeInt(pair.key);
                    os.writeInt(os.getOffset(pair.target) - offset);
                }
                break;
        }
    }

    public int getCount() {
        return indices.size();
    }

    public static class KeyIndexPair {
        public int key;
        public int index;
        public Instruction target;

        public KeyIndexPair(int key, int index) {
            this.key = key;
            this.index = index;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(InstructionList instructions) {
        (defaultTarget = instructions.get(defaultIndex))
            .addPointer(this);
        for(KeyIndexPair pair : indices)
            (pair.target = instructions.get(pair.index))
                .addPointer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(InstructionList instructions) {
        defaultTarget.removePointer(this);
        for(KeyIndexPair pair : indices)
           pair.target.removePointer(this);
    }
}
