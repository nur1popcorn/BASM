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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The {@link CPInstruction} is an {@link Instruction} which makes use of the constantpool.
 *
 * @see Instruction
 *
 * @see ConstantPool
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class CPInstruction<T extends ConstantInfo> extends Instruction {
    /*
     *
     */
    protected final ConstantPool cp;

    protected T info;

    CPInstruction(Opcode opcode, T info, ConstantPool cp) {
        super(opcode);
        this.info = info;
        this.cp = cp;
    }

    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(cp.indexOf(info));
    }
}
