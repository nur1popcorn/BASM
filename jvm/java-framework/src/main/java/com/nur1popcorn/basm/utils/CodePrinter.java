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

public final class CodePrinter {/* implements IInstructionVisitor {
    private static final String TAB = "  ";

    private PrintWriter pw;
    private Map<Label, String> labelNameMap = new HashMap<>();

    public CodePrinter(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void visitBIPushInstruction(BIPushInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[BIPUSH]);
        pw.print(" ");
        pw.println(Integer.toHexString(instruction.data & 0xff));
    }

    @Override
    public void visitClassInstruction(ClassInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
        pw.print(" ");
        pw.println(instruction.clazz);
    }

    @Override
    public void visitIIncInstruction(IIncInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[IINC & 0xff]);
        pw.print(" ");
        pw.print(Integer.toHexString(instruction.index & 0xff));
        pw.print(" ");
        pw.println(Integer.toHexString(instruction.constant & 0xff));
    }

    @Override
    public void visitInvokeDynamicInstruction(InvokeDynamicInstruction instruction) {
        pw.print(TAB);
        // TODO: impl
        pw.println();
    }

    @Override
    public void visitJumpInstruction(JumpInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
        final Label label = instruction.label;
        String name = labelNameMap.get(label);
        if(name == null)
            labelNameMap.put(label, Integer.toHexString(labelNameMap.size()));
        pw.print(" L");
        pw.println(name);
    }

    @Override
    public void visitLabel(Label instruction) {
        pw.print("L");
        String name = labelNameMap.get(instruction);
        if(name == null)
            labelNameMap.put(instruction, Integer.toHexString(labelNameMap.size()));
        pw.println(name);
    }*/

    /**
     * Escapes the escape codes within the given string.
     *
     * @param str the string which should be escaped.
     *
     * @return the escaped string.
     *
     * @see #visitLDCInstruction(LDCInstruction)
     */
    private static String escape(String str) {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            switch(c) {
                case '\t': sb.append("\\t"); break;
                case '\b': sb.append("\\b"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\f': sb.append("\\f"); break;
                case '\"': sb.append("\\\""); break;
                default: sb.append(c); break;
            }
        }
        return sb.toString();
    }
/*
    @Override
    public void visitLDCInstruction(LDCInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
        pw.print(" ");*/
        /*switch(instruction.getTag()) {
            case CONSTANT_CLASS:
            case CONSTANT_METHOD_TYPE:
            case CONSTANT_INTEGER:
                pw.println(instruction.getConstant());
                break;
            case CONSTANT_STRING:
                pw.print("\"");
                // TODO: escape string.
                pw.print(instruction.getConstant());
                pw.println("\"");
                break;
            case CONSTANT_FLOAT:
                pw.print(instruction.getConstant());
                pw.println("f");
                break;
            case CONSTANT_DOUBLE:
                pw.print(instruction.getConstant());
                pw.println("d");
                break;
            case CONSTANT_LONG:
                pw.print(instruction.getConstant());
                pw.println("L");
                break;
        }*/
    /*}

    @Override
    public void visitLocalVariableInstruction(LocalVariableInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
        pw.print(" ");
        pw.println(Integer.toHexString(instruction.index & 0xff));
    }

    @Override
    public void visitMultiNewArrayInstruction(MultiANewArrayInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[MULTIANEWARRAY & 0xff]);
        pw.print(" ");
        pw.print(instruction.clazz);
        pw.print(" ");
        pw.println(instruction.dimensions);
    }

    @Override
    public void visitNewArrayInstruction(NewArrayInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[NEWARRAY & 0xff]);
        pw.print(" ");
        pw.println(T_MNEMONICS[instruction.atype]);
    }

    @Override
    public void visitNoParameterInstruction(NoParameterInstruction instruction) {
        pw.print(TAB);
        pw.println(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
    }

    @Override
    public void visitRefInstruction(RefInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[instruction.getOpcode() & 0xff]);
        pw.print(" ");
        pw.print(instruction.clazz);
        pw.print(" ");
        pw.print(instruction.name);
        pw.print(" ");
        pw.println(instruction.desc);
    }

    @Override
    public void visitSIPushInstruction(SIPushInstruction instruction) {
        pw.print(TAB);
        pw.print(OPCODE_MNEMONICS[BIPUSH]);
        pw.print(" ");
        pw.println(Integer.toHexString(instruction.data));
    }

    @Override
    public void visitSwitchInstruction(SwitchInstruction instruction) {
        pw.print(TAB);
        // TODO: impl
        pw.println();
    }

    @Override
    public void visitWideInstruction(WideInstruction instruction) {
        pw.print(TAB);
        // TODO: impl
        pw.println();
    }*/
}
