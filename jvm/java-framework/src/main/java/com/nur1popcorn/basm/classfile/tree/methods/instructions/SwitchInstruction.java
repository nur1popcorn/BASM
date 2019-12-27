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
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.classfile.Opcode.TABLESWITCH;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.SWITCH_INS;

public final class SwitchInstruction extends Instruction implements IInstructionPointer  {
    private Label defaultTarget;
    private final List<KeyIndexPair> indices;

    /**
     * @param opcode
     */
    public SwitchInstruction(Opcode opcode, Label defaultTarget, KeyIndexPair indices[]) {
        super(opcode);
        (this.defaultTarget = defaultTarget)
            .addPointer(this);
        this.indices = new ArrayList<>(indices.length);
        for(KeyIndexPair element : indices) {
            element.target.addPointer(this);
            this.indices.add(element);
        }
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
        while((os.size() & 0x3) != 0)
            os.writeByte(0);
        os.writeInt(defaultTarget.getOffset() - getOffset());
        switch(getOpcode()) {
            case TABLESWITCH:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch
                final int low = indices.get(0).key;
                final int high = low + indices.size() - 1;
                os.writeInt(low);
                os.writeInt(high);
                for(KeyIndexPair pair : indices)
                    os.writeInt(pair.target.getOffset() - getOffset());
                break;
            case LOOKUPSWITCH:
                os.writeInt(indices.size());
                for(KeyIndexPair pair : indices) {
                    os.writeInt(pair.key);
                    os.writeInt(pair.target.getOffset() - getOffset());
                }
                break;
        }
    }

    public static class KeyIndexPair {
        public int key;
        public Label target;

        public KeyIndexPair(int key, Label target) {
            this.key = key;
            this.target = target;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        defaultTarget.removePointer(this);
        for(KeyIndexPair pair : indices)
           pair.target.removePointer(this);
    }

    public Label getDefaultTarget() {
        return defaultTarget;
    }

    public void setDefaultTarget(Label defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    @Override
    public int getLength() {
        return ((-1 - getOffset()) & 0x3) + 9 +
            (getOpcode() == TABLESWITCH ?
                4 + (indices.size() << 2) :
                     indices.size() << 3);
    }

    @Override
    public InstructionType getType() {
        return SWITCH_INS;
    }
}
