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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.constants.*;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.BIPushInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.LDCInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.LocalVariableInstructtion;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.NoParameterInstruction;

import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.*;

public final class Code {
    public int maxStack = 0,
               maxLocals = 0;

    private List<Instruction> code = new ArrayList<>();

    public Code(AttributeCode attributeCode, ConstantPool constantPool) {
        final byte byteCode[] = attributeCode.getByteCode();
        for(int i = 0; i < byteCode.length; i++) {
            final byte opcode = byteCode[i];
            switch(opcode) {
                case BIPUSH:
                    code.add(new BIPushInstruction(byteCode[++i]));
                    break;
                case LDC: {
                    Object data = null;

                    final ConstantInfo constantInfo = constantPool.getEntry(byteCode[++i]);
                    final byte tag = constantInfo.getTag();
                    switch (tag) {
                        case CONSTANT_INTEGER:
                            data = ((ConstantInteger) constantInfo).asInteger();
                            break;
                        case CONSTANT_FLOAT:
                            data = ((ConstantInteger) constantInfo).asFloat();
                            break;
                        case CONSTANT_STRING:
                            data = ((ConstantUtf8) constantInfo).bytes;
                            break;
                        case CONSTANT_METHOD_TYPE:
                        case CONSTANT_CLASS:
                            data = ((ConstantName) constantInfo).indexName(constantPool);
                            break;
                        case CONSTANT_METHOD_HANDLE:
                            final ConstantMethodHandle methodHandle = (ConstantMethodHandle) constantInfo;
                            final ConstantMethodRef methodRef = methodHandle.indexRef(constantPool);
                            final ConstantNameAndType nameAndType = methodRef.indexNameAndType(constantPool);
                            data = new MethodHandle(methodHandle.getRefKind(),
                                    methodRef.indexClass(constantPool)
                                             .indexName(constantPool)
                                             .bytes,
                                    nameAndType.indexName(constantPool)
                                               .bytes,
                                    nameAndType.indexDesc(constantPool)
                                               .bytes);
                            break;
                    }
                    code.add(new LDCInstruction(data, tag));
                }   break;
                case ILOAD:
                case LLOAD:
                case FLOAD:
                case DLOAD:
                case ALOAD:

                case ISTORE:
                case LSTORE:
                case FSTORE:
                case DSTORE:
                case ASTORE:

                case RET:
                    code.add(new LocalVariableInstructtion(opcode, byteCode[++i]));
                    break;
                default:
                    code.add(new NoParameterInstruction(opcode));
            }
        }
        computeMaxes();
    }

    public void computeMaxes() {
        int stack = 0, locals = 0;
        for(Instruction instruction : code) {

        }
    }

    public List<Instruction> getCode() {
        return code;
    }
}
