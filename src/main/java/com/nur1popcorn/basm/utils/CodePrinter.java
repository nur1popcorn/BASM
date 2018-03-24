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

package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.tree.methods.ICodeVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Label;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.tree.methods.Instruction.*;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.NewArrayInstruction.T_MNEMONICS;

public class CodePrinter {
    private static final String TAB = "  ";
    
    private PrintWriter pw;

    public CodePrinter(PrintWriter pw) {
        this.pw = pw;
    }

    public void accept(ICodeVisitor cv) {
        if(cv.visitCodeAt(0)) {
            final Map<Label, Integer> labelMap = new HashMap<>();
            do {
                final Instruction instruction = cv.visitCurrentInstruction();
                switch(instruction.getType()) {
                    case BIPUSH_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[BIPUSH]);
                        pw.print(" ");
                        pw.println(Integer.toHexString(((BIPushInstruction)instruction).data & 0xff));
                        break;
                    case CLASS_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        pw.print(" ");
                        pw.println(((ClassInstruction)instruction).clazz);
                        break;
                    case IINC_INSTRUCTION: {
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[IINC & 0xff]);
                        pw.print(" ");
                        final IIncInstruction iincInstruction = (IIncInstruction) instruction;
                        pw.print(Integer.toHexString(iincInstruction.index & 0xff));
                        pw.print(" ");
                        pw.println(Integer.toHexString(iincInstruction.constant & 0xff));
                    }   break;
                    case INVOKEDYNAMIC_INSTRUCTION:
                        pw.print(TAB);
                        // TODO: impl
                        pw.println();
                        break;
                    case JUMP_INSTRUCTION: {
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        final Label label = ((JumpInstruction)instruction).label;
                        Integer labelIndex = labelMap.get(label);
                        if(labelIndex == null)
                            labelMap.put(label, labelIndex = labelMap.size());
                        pw.print(" L");
                        pw.println(Integer.toHexString(labelIndex));
                    }   break;
                    case LDC_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        pw.print(" ");
                        LDCInstruction ldcInstruction = (LDCInstruction) instruction;
                        switch(ldcInstruction.getTag()) {
                            case CONSTANT_CLASS:
                            case CONSTANT_METHOD_TYPE:
                            case CONSTANT_INTEGER:
                                pw.println(ldcInstruction.getConstant());
                                break;
                            case CONSTANT_STRING:
                                pw.print("\"");
                                pw.print(ldcInstruction.getConstant());
                                pw.println("\"");
                                break;
                            case CONSTANT_FLOAT:
                                pw.print(ldcInstruction.getConstant());
                                pw.println("f");
                                break;
                            case CONSTANT_DOUBLE:
                                pw.print(ldcInstruction.getConstant());
                                pw.println("d");
                                break;
                            case CONSTANT_LONG:
                                pw.print(ldcInstruction.getConstant());
                                pw.println("L");
                                break;
                        }
                        break;
                    case LOCAL_VARIABLE_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        pw.print(" ");
                        pw.println(Integer.toHexString(((LocalVariableInstruction)instruction).index & 0xff));
                        break;
                    case MULTIANEWARRAY_INSTRUCTION: {
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[MULTIANEWARRAY & 0xff]);
                        pw.print(" ");
                        final MultiANewArrayInstruction multiANewArrayInstruction = (MultiANewArrayInstruction) instruction;
                        pw.print(multiANewArrayInstruction.clazz);
                        pw.print(" ");
                        pw.println(multiANewArrayInstruction.dimensions);
                    }   break;
                    case NEWARRAY_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[NEWARRAY & 0xff]);
                        pw.print(" ");
                        pw.println(T_MNEMONICS[((NewArrayInstruction)instruction).atype]);
                        break;
                    case LABEL_INSTRUCTION: {
                        pw.print("L");
                        final Label label = (Label) instruction;
                        Integer labelIndex = labelMap.get(label);
                        if(labelIndex == null)
                            labelMap.put(label, labelIndex = labelMap.size());
                        pw.println(Integer.toHexString(labelIndex));
                    }   break;
                    case NO_PARAMETER_INSTRUCTION:
                        pw.print(TAB);
                        pw.println(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        break;
                    case REF_INSTRUCTION: {
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        pw.print(" ");
                        final RefInstruction refInstruction = (RefInstruction) instruction;
                        pw.print(refInstruction.clazz);
                        pw.print(" ");
                        pw.print(refInstruction.name);
                        pw.print(" ");
                        pw.println(refInstruction.desc);
                    }   break;
                    default:
                    case SIPUSH_INSTRUCTION:
                        pw.print(TAB);
                        pw.print(OPCODE_MNEMONICS[BIPUSH]);
                        pw.print(" ");
                        pw.println(Integer.toHexString(((SIPushInstruction)instruction).data));
                        break;
                    case SWITCH_INSTRUCTION:
                        pw.print(TAB);
                        // TODO: impl
                        pw.println();
                        break;
                    case WIDE_INSTRUCTION:
                        pw.print(TAB);
                        // TODO: impl
                        pw.println();
                        break;
                    case NOT_AN_INSTRUCTION:
                        pw.print("#");
                        pw.println(Integer.toHexString(instruction.getOpcode() & 0xff));
                        break;
                }
            } while(cv.visitNextInstruction());
            pw.flush();
        }
    }
}
