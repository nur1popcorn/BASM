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
import com.nur1popcorn.basm.classfile.IClassVersionProvider;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.constants.*;
import com.nur1popcorn.basm.classfile.tree.ConstantPoolGenerator;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionType;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_5;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_7;
import static com.nur1popcorn.basm.classfile.Opcode.LDC;
import static com.nur1popcorn.basm.classfile.Opcode.LDC_W;
import static com.nur1popcorn.basm.classfile.tree.methods.InstructionType.LDC_INS;

/**
 * The {@link LDCInstruction}
 *
 * @see ConstantPoolGenerator
 * @see ConstantInfo
 * @see Instruction
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class LDCInstruction extends CPInstruction<ConstantInfo> {
    /**
     * @param opcode
     * @param info
     * @param cp
     */
    public LDCInstruction(Opcode opcode, ConstantInfo info, ConstantPool cp) {
        super(opcode, info, cp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        super.accept(visitor);
        visitor.visitLDCInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ensureVersion(IClassVersionProvider provider) {
        if(getOpcode() == LDC ||
           getOpcode() == LDC_W) {
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9.1-120-D
            switch(info.getTag()) {
                case CONSTANT_CLASS:
                    provider.ensureMajorVersion(JAVA_5);
                    break;
                case CONSTANT_METHOD_TYPE:
                case CONSTANT_METHOD_HANDLE:
                    provider.ensureMajorVersion(JAVA_7);
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        switch(getOpcode()) {
            case LDC:
                final int index = cp.indexOf(info);
                if(index < 0x100) {
                    os.writeByte(getOpcode().getOpcode());
                    os.writeByte(index);
                    break;
                }
                setOpcode(LDC_W);
                // fallthrough.
            case LDC_W:
            case LDC2_W:
                super.write(os);
                break;
        }
    }

    /**
     * @param cp
     *
     * @return
     */
    public Object getValue(ConstantPool cp) {
        if(info == null)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at index: index=" + " is null");
        final byte tag = info.getTag();
        switch(getOpcode()) {
            case LDC:
            case LDC_W:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9.1-120-D
                switch(tag) {
                    case CONSTANT_INTEGER:
                        return ((ConstantInteger) info)
                            .asInteger();
                    case CONSTANT_FLOAT:
                        return ((ConstantInteger) info)
                            .asFloat();
                    case CONSTANT_STRING:
                    case CONSTANT_CLASS:
                    case CONSTANT_METHOD_TYPE:
                        return ((ConstantName) info)
                            .indexName(cp)
                            .bytes;
                    case CONSTANT_METHOD_HANDLE:
                        //TODO: change this
                        return ((ConstantMethodHandle)info)
                            .indexRef(cp);
                    default:
                        throw new MalformedClassFileException(
                            "The CONSTANT_Info at given index has an invalid tag: index=" +
                                ", expected_tag="
                                + "{" +
                                    CONSTANT_INTEGER + "|" + CONSTANT_FLOAT + "|" +
                                    CONSTANT_STRING + "|" + CONSTANT_CLASS + "|" +
                                    CONSTANT_METHOD_TYPE + "|" + CONSTANT_METHOD_HANDLE
                                + "}" +
                                ", tag=" + tag
                        );
                }
            case LDC2_W:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9.1-120-E
                switch(tag) {
                    case CONSTANT_LONG:
                        return ((ConstantLong) info)
                            .asLong();
                    case CONSTANT_DOUBLE:
                        return ((ConstantLong) info)
                            .asDouble();
                    default:
                        throw new MalformedClassFileException(
                            "The CONSTANT_Info at given index has an invalid tag: index=" +
                                ", expected_tag="
                                + "{" +
                                    CONSTANT_LONG + "|" + CONSTANT_DOUBLE
                                + "}" +
                                ", tag=" + tag
                        );
                }
            default:
                throw new MalformedClassFileException(
                    "The instruction's opcode is invalid: opcode=" +
                    getOpcode()
                );
        }
    }

    @Override
    public InstructionType getType() {
        return LDC_INS;
    }
}
