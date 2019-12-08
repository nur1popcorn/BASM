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
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.*;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.SwitchInstruction.KeyIndexPair;
import com.nur1popcorn.basm.utils.ByteDataInputStream;
import com.nur1popcorn.basm.utils.WeakHashSet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import static com.nur1popcorn.basm.classfile.Opcode.*;

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
    protected Opcode opcode;

    /*
     *
     */
    private Set<IInstructionPointer> pointers;

    Instruction next,
                prev;

    /**
     * @param opcode
     */
    protected Instruction(Opcode opcode) {
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
        os.writeByte(opcode.getOpcode());
    }

    /**
     * @param opcode
     *
     * @throws MalformedClassFileException
     */
    public void setOpcode(Opcode opcode) {
        if(this.opcode.getType() != opcode.getType())
            // TODO: desc
            throw new MalformedClassFileException();
        this.opcode = opcode;
    }

    /**
     * @return
     */
    public final Opcode getOpcode() {
        return opcode;
    }

    /**
     * @param pointer
     */
    public void addPointer(IInstructionPointer pointer) {
        if(pointers == null)
            pointers = new WeakHashSet<>();
        pointers.add(pointer);
    }

    /**
     * @param pointer
     */
    public void removePointer(IInstructionPointer pointer) {
        pointers.remove(pointer);
    }

    /**
     * @return
     */
    public boolean hasPointers() {
        return pointers != null &&
            pointers.size() != 0;
    }

    /**
     * @return
     */
    public IInstructionPointer[] getPointers() {
        if(pointers == null)
            return new IInstructionPointer[0];
        final IInstructionPointer arr[] = new IInstructionPointer[pointers.size()];
        pointers.toArray(arr);
        return arr;
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
        final int offset = in.position();
        final Opcode opcode = Opcode.valueOf(in.readByte());
        switch(opcode.getType()) {
            case NO_PARAM_INS:
                return new NoParameterInstruction(opcode);
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
            case JUMP_INS:
                switch(opcode) {
                    // a 4 byte index must be constructed for the goto_w & jsr_w opcodes.
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.goto_w
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.jsr_w
                    case GOTO_W:
                    case JSR_W:
                        return new JumpInstruction(opcode,
                            in.readLabel(offset + in.readInt()));
                    default:
                        return new JumpInstruction(opcode,
                            in.readLabel(offset + in.readShort()));
                }
            case SWITCH_INS:
                // skip padding bytes and read default index.
                in.skipBytes(-in.position() & 0x3);
                final Label defaultTarget = in.readLabel(offset + in.readInt());
                switch(opcode) {
                    case TABLESWITCH: {
                        final int low = in.readInt();
                        final int high = in.readInt();

                        final int length = high - low + 1;

                        final KeyIndexPair[] indices = new KeyIndexPair[length];
                        for(int i = 0; i < length; i++)
                            indices[i] = new KeyIndexPair(
                                low + i,
                                in.readLabel(offset + in.readInt())
                            );

                        return new SwitchInstruction(
                            opcode, defaultTarget, indices);
                    }
                    case LOOKUPSWITCH: {
                        final int length = in.readInt();
                        final KeyIndexPair[] indices = new KeyIndexPair[length];
                        for(int i = 0; i < length; i++)
                            indices[i] = new KeyIndexPair(
                                in.readInt(),
                                in.readLabel(offset + in.readInt())
                            );
                        return new SwitchInstruction(
                            opcode, defaultTarget, indices);
                    }
                }
            case FIELD_INS:
                return new FieldInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case METHOD_INS:
                if (opcode == INVOKEINTERFACE) {
                    final int index = in.readUnsignedShort();
                    final int count = in.readUnsignedByte();
                    in.skipBytes(1);
                    return new MethodInstruction(
                        index, count, cp);
                }
                return new MethodInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case INVOKEDYNAMIC_INS:
                final int index = in.readUnsignedShort();
                in.skipBytes(2);
                return new InvokeDynamicInstruction(index, cp);
            case CLASS_INS:
                return new ClassInstruction(
                    opcode, in.readUnsignedShort(), cp);
            case NEWARRAY_INS:
                return new NewArrayInstruction(
                    Type.getType(in.readByte()));
            case WIDE_INS:
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.wide
                final Opcode wopcode = Opcode.valueOf(in.readByte());
                if(wopcode == IINC)
                    return new WideInstruction(
                        in.readUnsignedShort(), in.readUnsignedShort());
                else
                    return new WideInstruction(
                        wopcode, in.readUnsignedShort());
            case MULTIANEWARRAY_INS:
                return new MultiANewArrayInstruction(
                    in.readUnsignedShort(), in.readByte());
            default:
                throw new MalformedClassFileException();
        }
    }

    public int getLength() {
        switch(opcode.getType()) {
            case LABEL_INS:
                return 0;
            case SWITCH_INS:
                final SwitchInstruction switchInsn = (SwitchInstruction)this;
                return opcode == TABLESWITCH ?
                    ((-1 - offset) & 0x3) + 13 + (switchInsn.getCount() << 2) :
                    ((-1 - offset) & 0x3) + 9 + (switchInsn.getCount() << 3);
            case WIDE_INS:
                return ((WideInstruction)this)
                    .getOpcodeParameter() == IINC ? 6 : 4;
            default:
                final int parameters = opcode.getParameter();
                if(parameters == -0x1)
                    throw new MalformedClassFileException(
                        "The opcode=" + opcode.getMnemonic() + " is invalid."
                    );
                return parameters + 1;
        }
    }

    public int getOffset() {
        return offset;
    }

    void setOffset(int offset) {
        this.offset = offset;
    }
}
