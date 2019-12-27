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

import com.nur1popcorn.basm.classfile.*;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.IInstructionVisitor;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link Instruction} is the abstract super class which all other {@link Instruction}s are derived from.
 * The class consists of a number of constants which denote different kind of instructions, a #read(DataInputStream)
 * and #write(DataOutputStream) method which are supposed to be overwritten by other {@link Instruction}s and a set
 * of pointers denoting other instructions which are indexing this one.
 *
 * @see IInstructionVisitor
 * @see IInstructionPointer
 *
 * @see #read(DataInputStream)
 * @see #write(DataOutputStream)
 *
 * @see #accept(IInstructionVisitor)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */

/**
 * The class mostly consists of two things: A tag, which is used to identify the
 * type of 'CONSTANT_Info' and a set of pointers, which denotes any kind of {@link IConstantPoolPointer}
 * pointing at this specific constant. The implementation closely follows this part of the JVM ClassFile document:
 */

/**
 * The {@link Instruction} is the abstract super class which all other {@link Instruction}s are derived
 * from.
 *
 */
public abstract class Instruction {
    /*
     *
     */
    private int offset;

    /*
     *
     */
    private Opcode opcode;

    Instruction next,
                prev;

    /**
     * @param opcode
     */
    protected Instruction(Opcode opcode) {
        setOpcode(opcode);
    }

    protected Instruction(Opcode opcode, int offset) {
        this(opcode);
        this.offset = offset;
    }

    /**
     * @param visitor
     */
    public abstract void accept(IInstructionVisitor visitor);

    /**
     * Should be overwritten by super classes.
     *
     * @param provider
     */
    public void ensureVersion(IClassVersionProvider provider)
    {
        // TODO: make into interface and add support for constant pool
    }

    /**
     * @param os
     *
     * @throws IOException
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeByte(opcode.getOpcode());
    }

    /**
     * @return
     */
    public final Opcode getOpcode() {
        return opcode;
    }

    /**
     * @param opcode
     *
     * @throws MalformedClassFileException
     */
    public void setOpcode(Opcode opcode) {
        if(getType() != opcode.getType())
            throw new IllegalArgumentException();
        this.opcode = opcode;
    }

    public int getLength() {
        return 1 + opcode.getParameter();
    }

    public int getConsumeStack() {
        return opcode.getStackPop();
    }

    public int getProduceStack() {
        return opcode.getStackPush();
    }

    public abstract InstructionType getType();

    public int getOffset() {
        return offset;
    }

    void setOffset(int offset) {
        this.offset = offset;
    }
}
