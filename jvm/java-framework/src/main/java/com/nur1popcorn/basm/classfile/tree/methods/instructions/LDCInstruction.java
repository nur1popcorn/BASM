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
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantInteger;
import com.nur1popcorn.basm.classfile.constants.ConstantLong;
import com.nur1popcorn.basm.classfile.constants.ConstantName;
import com.nur1popcorn.basm.classfile.tree.ConstantPoolGenerator;
import com.nur1popcorn.basm.utils.ByteDataOutputStream;

import java.io.IOException;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_5;
import static com.nur1popcorn.basm.classfile.IClassVersionProvider.JAVA_7;
import static com.nur1popcorn.basm.classfile.Opcode.LDC;
import static com.nur1popcorn.basm.classfile.Opcode.LDC_W;

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
public final class LDCInstruction extends CPInstruction {
    /**
     * @param opcode
     * @param index
     * @param cp
     */
    LDCInstruction(Opcode opcode, int index, ConstantPool cp) {
        super(opcode, index, cp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(IInstructionVisitor visitor) {
        visitor.visitCPPointer(this);
        visitor.visitCPInstruction(this);
        visitor.visitLDCInstruction(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ensureVersion(IClassVersionProvider provider) {
        if(opcode == LDC ||
           opcode == LDC_W) {
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9.1-120-D
            switch(cp.getEntry(index)
                     .getTag()) {
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
    public void write(ByteDataOutputStream os) throws IOException {
        switch(opcode) {
            case LDC:
                if(index < 0x100) {
                    os.writeByte(opcode.getOpcode());
                    os.writeByte(index);
                    break;
                }
                opcode = LDC_W;
                // fallthrough.
            case LDC_W:
            case LDC2_W:
                os.writeByte(opcode.getOpcode());
                os.writeShort(index);
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int oldIndex, int newIndex) {
        index = newIndex;
        if(opcode == LDC &&
           index > 0xff)
            opcode = LDC_W;
        // TODO: remove useless check.
        if(cp.getEntry(newIndex) == null)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at index: index=" + index + " is null");
    }

    /**
     * @param cp
     *
     * @return
     */
    public Object getValue(ConstantPool cp) {
        final ConstantInfo info = cp.getEntry(index);
        if(info == null)
            throw new MalformedClassFileException(
                "The CONSTANT_Info at index: index=" + index + " is null");
        final byte tag = info.getTag();
        switch(opcode) {
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
                        return ((ConstantName) info)
                            .indexName(cp)
                            .bytes;
                    case CONSTANT_METHOD_TYPE:
                    case CONSTANT_METHOD_HANDLE:
                        // TODO: handle method handle and method type.
                        return info;
                    default:
                        throw new MalformedClassFileException(
                            "The CONSTANT_Info at given index has an invalid tag: index=" + index +
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
                            "The CONSTANT_Info at given index has an invalid tag: index=" + index +
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
                    opcode
                );
        }
    }
}
