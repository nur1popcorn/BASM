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
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.SwitchInstruction.KeyIndexPair;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.InstructionFactory.INSTRUCTIONS;

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
    /**
     * This constant denotes instructions, which are not implemented by the JVM. These instructions
     * are associated with opcodes ranging from xCB to xFD and are reserved for future use.
     *
     * TODO: mention quick instructions ?
     */
    public static final byte NOT_AN_INS = 0x0;

    /**
     * This constant denotes instructions, which solely consist of their opcode, they therefore tend
     * to also have a very predictable effect on the stack.
     */
    public static final byte NO_PARAM_INS = 0x1;

    /**
     * <p>This constant denotes instructions, which sign-extend their parameters and push onto the stack.
     *    The constant is employed by the following 2 instructions: </p>
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.bipush">
     *             bipush
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.sipush">
     *             sipush
     *         </a>
     *     </li>
     * </ul>
     */
    public static final byte PUSH_INS = 0x2;

    /**
     * <p>This constant denotes instructions, which push entries from the constant pool onto the stack.
     *    The constant is employed by the following 3 instructions: </p>
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc">
     *             ldc
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w">
     *             ldc_w
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w">
     *             ldc2_w
     *         </a>
     *     </li>
     * </ul>
     * <p>The instructions are very predictable in terms of their effect on the stack ldc & ldc_w will
     *    increment the stack size by 1 while ldc2_w will only increment it by 2.</p>
     */
    public static final byte LDC_INS = 0x3;

    /**
     * This constant denotes instruction, which require a local variable index to be present as a parameter.
     */
    public static final byte LOCAL_VARIABLE_INS = 0x4;

    /**
     *
     */
    public static final byte IINC_INS = 0x5;

    /**
     *
     */
    public static final byte JUMP_INS = 0x6;

    /**
     *
     */
    public static final byte SWITCH_INS = 0x7;

    /**
     *
     */
    public static final byte FIELD_INS = 0x8;

    /**
     *
     */
    public static final byte METHOD_INS = 0x9;

    /**
     *
     */
    public static final byte INVOKEDYNAMIC_INS = 0xa;

    /**
     *
     */
    public static final byte CLASS_INS = 0xb;

    /**
     *
     */
    public static final byte NEWARRAY_INS = 0xc;

    /**
     *
     */
    public static final byte WIDE_INS = 0xd;

    /**
     *
     */
    public static final byte MULTIANEWARRAY_INS = 0xe;

    /**
     *
     */
    public static final byte RESERVED_INS = 0xf;

    /* This table maps each opcode to their type. The table is made up of 4 bit entries stored in
     * 32 bit blocks. These 4 bit entries denote the type of instruction.
     *
     */
    private static final int INSTRUCTION_TYPE_TABLE[] = {
        0x11111111, 0x11111111, 0x44433322, 0x11111144,
        0x11111111, 0x11111111, 0x44111111, 0x11111444,
        0x11111111, 0x11111111, 0x11111111, 0x11111111,
        0x11111111, 0x11111111, 0x11111111, 0x11111111,
        0x11151111, 0x11111111, 0x11111111, 0x66666661,
        0x66666666, 0x11117746, 0x99888811, 0x11bcba99,
        0x66ed11bb, 0x00000166, 0x00000000, 0x00000000,
        0x00000000, 0x00000000, 0x00000000, 0x11000000,
    };

    /* This constant denotes an instruction whose effect on the stack is uncertain.
     */
    public static final byte UNKNOWN_VALUE = (byte) 0xf8;

    /* This table maps each opcode to their predicted effect on the stack size. The table is made
     * up of 4 bit entries stored in 32 bit blocks. These 4 bit entries represent a number between
     * -8 and 7 with the 4th bit denoting whether the number is signed. The number -8 is reserved
     * and denotes opcodes whose effect on the stack are not predictable.
     *
     */
    private static final int STACK_SIZE_MODIFIER_TABLE[] = {
        0x11111110, 0x22111221, 0x12121111, 0x22111112,
        0x22111122, 0x11111122, 0xef111111, 0xefffffef,
        0xeffffeee, 0xeffffeee, 0xfeeeeded, 0x0222111e,
        0xefefefef, 0xefefefef, 0x0000efef, 0xefefefef,
        0x1010efef, 0x0f1100ff, 0xdffd000f, 0xeffffffd,
        0x0eeeeeee, 0x8888ff01, 0x88888888, 0x80001888,
        0xff88ff00, 0x88888010, 0x88888888, 0x88888888,
        0x88888888, 0x88888888, 0x88888888, 0x00888888
    };

    /*
     *
     */
    protected byte opcode;

    /**
     * @param opcode
     */
    Instruction(byte opcode) {
        this.opcode = opcode;
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
        os.writeByte(opcode);
    }

    /**
     * @param opcode
     *
     * @return
     */
    public static byte indexType(int opcode) {
        return (byte) ((INSTRUCTION_TYPE_TABLE[(opcode &= 0xff) / 8] >> ((opcode % 8) * 4)) & 0xf);
    }

    /**
     *
     *
     * @return
     */
    public byte getStackModifier() {
        final int opcode = this.opcode & 0xff;
        byte value = (byte) ((STACK_SIZE_MODIFIER_TABLE[opcode / 8] >> ((opcode % 8) * 4)) & 0xf);
        if((value & 0x8) != 0)
            value |= 0xf0;
        return value;
    }

    /**
     * @return
     */
    public final byte getType() {
        return indexType(opcode);
    }

    /**
     * @param opcode
     *
     * @throws MalformedClassFileException
     */
    public void setOpcode(byte opcode) {
        if(getType() != indexType(opcode))
            // TODO: desc
            throw new MalformedClassFileException();
        this.opcode = opcode;
    }

    /**
     * @return
     */
    public final byte getOpcode() {
        return opcode;
    }

    /**
     * @param in
     * @param cp
     *
     * @throws IOException
     * @throws MalformedClassFileException
     *
     * @return
     */
    public static Instruction read(ByteDataInputStream in, ConstantPool cp) throws IOException {
        final byte opcode = in.readByte();
        switch(indexType(opcode)) {
            case NO_PARAM_INS:
                return INSTRUCTIONS[opcode & 0xff];
            case PUSH_INS:
                switch(opcode) {
                    case BIPUSH:
                        return new PushInstruction(
                            opcode, in.readByte());
                    case SIPUSH:
                        return new PushInstruction(
                            opcode, in.readShort());
                }
            case LDC_INS:
                switch(opcode) {
                    case LDC:
                        return new LDCInstruction(
                            opcode, in.readByte(), cp);
                    case LDC_W:
                    case LDC2_W:
                        return new LDCInstruction(
                            opcode, in.readShort(), cp);
                }
            case LOCAL_VARIABLE_INS:
                return new LocalVariableInstruction(
                    opcode, in.readByte());
            case IINC_INS:
                return new IIncInstruction(
                    opcode, in.readByte(), in.readByte());
            case JUMP_INS: {
                switch (opcode) {
                    // a 4 byte index must be constructed for the goto_w & jsr_w opcodes.
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.goto_w
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.jsr_w
                    case GOTO_W:
                    case JSR_W:
                        return new JumpInstruction(opcode, in.readInt());
                    default:
                        return new JumpInstruction(opcode, in.readShort());
                }
            }
            case SWITCH_INS: {
                // skip padding bytes and read default index.
                in.skipBytes(-in.position() & 0x3);
                final int defaultIndex = in.readInt();
                switch(opcode) {
                    case TABLESWITCH: {
                        final int low = in.readInt();
                        final int high = in.readInt();

                        final int length = high - low + 1;

                        final KeyIndexPair[] indices = new KeyIndexPair[length];
                        for(int i = 0; i < length; i++) {
                            indices[i] = new KeyIndexPair(
                                low + i,
                                in.readInt()
                            );
                        }

                        return new SwitchInstruction(
                            opcode, defaultIndex, indices);
                    }
                    case LOOKUPSWITCH: {
                        final int length = in.readInt();
                        final KeyIndexPair[] indices = new KeyIndexPair[length];
                        for(int i = 0; i < length; i++) {
                            indices[i] = new KeyIndexPair(
                                in.readInt(),
                                in.readInt()
                            );
                        }
                        return new SwitchInstruction(
                            opcode, defaultIndex, indices);
                    }
                }
            }
            case FIELD_INS:
                return new FieldInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case METHOD_INS: {
                if (opcode == INVOKEINTERFACE) {
                    final int index = in.readUnsignedShort();
                    final int count = in.readUnsignedByte();
                    in.skipBytes(1);
                    return new MethodInstruction(
                        index, count, cp);
                }
                return new MethodInstruction(
                    opcode, in.readUnsignedShort(), cp);
            }
            case INVOKEDYNAMIC_INS: {
                final int index = in.readUnsignedShort();
                in.skipBytes(2);
                return new InvokeDynamicInstruction(index, cp);
            }
            case CLASS_INS:
                return new ClassInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case NEWARRAY_INS:
                return new NewArrayInstruction(
                    Type.getType(in.readByte()));
            case WIDE_INS: {
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.wide
                final byte wopcode = in.readByte();
                if(wopcode == IINC)
                    return new WideInstruction(
                        in.readUnsignedShort(), in.readUnsignedShort());
                else
                    return new WideInstruction(
                        wopcode, in.readUnsignedShort());
            }
            case MULTIANEWARRAY_INS:
                return new MultiANewArrayInstruction(
                    in.readUnsignedShort(), in.readByte());
            default:
                throw new MalformedClassFileException();
        }
    }
}
