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
import com.nur1popcorn.basm.classfile.tree.methods.Label;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.BIPushInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.ClassInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.IIncInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.JumpInstruction;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.tree.methods.Instruction.NOT_AN_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.Label.LABEL_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.BIPushInstruction.BIPUSH_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.ClassInstruction.CLASS_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.IIncInstruction.IINC_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.InvokeDynamicInstruction.INVOKEDYNAMIC_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.InvokeInterfaceInstruction.INVOKEINTERFACE_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.JumpInstruction.JUMP_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.NoParameterInstruction.NO_PARAMETER_INSTRUCTION;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CodePrinter {
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
                        pw.print(OPCODE_MNEMONICS[BIPUSH]);
                        pw.print(" ");
                        pw.println(Integer.toHexString(((BIPushInstruction)instruction).data & 0xff));
                        break;
                    case CLASS_INSTRUCTION:
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        pw.print(" ");
                        pw.println(((ClassInstruction)instruction).clazz);
                        break;
                    case IINC_INSTRUCTION: {
                        pw.print(OPCODE_MNEMONICS[IINC & 0xff]);
                        pw.print(" ");
                        final IIncInstruction iincInstruction = (IIncInstruction) instruction;
                        pw.print(Integer.toHexString(iincInstruction.index & 0xff));
                        pw.print(" ");
                        pw.println(Integer.toHexString(iincInstruction.constant & 0xff));
                    }   break;
                    case INVOKEDYNAMIC_INSTRUCTION:
                        // TODO: impl
                        pw.println();
                        break;
                    case INVOKEINTERFACE_INSTRUCTION:
                        // TODO: impl
                        pw.println();
                        break;
                    case JUMP_INSTRUCTION: {
                        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        final Label label = ((JumpInstruction)instruction).label;
                        Integer labelIndex = labelMap.get(label);
                        if(labelIndex == null)
                            labelMap.put(label, labelIndex = labelMap.size());
                        pw.print(" L");
                        pw.println(Integer.toHexString(labelIndex));
                    }   break;
                    case LABEL_INSTRUCTION: {
                        pw.print("L");
                        final Label label = (Label) instruction;
                        Integer labelIndex = labelMap.get(label);
                        if(labelIndex == null)
                            labelMap.put(label, labelIndex = labelMap.size());
                        pw.println(Integer.toHexString(labelIndex));
                    }   break;
                    case NO_PARAMETER_INSTRUCTION:
                        pw.println(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
                        break;
                    default:
                    case NOT_AN_INSTRUCTION:
                        pw.print("#");
                        pw.println(Integer.toHexString(instruction.getOpcode() & 0xff));
                        break;
                }
            } while(cv.visitNextInstruction());
        }
    }
}
