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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.*;
import com.nur1popcorn.basm.utils.CodePrinter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.AbstractList;
import java.util.Arrays;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.tree.methods.Instruction.INSTRUCTION_TYPE_TABLE;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.BIPushInstruction.BIPUSH_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.IIncInstruction.IINC_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.JumpInstruction.JUMP_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.LDCInstruction.LDC_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.LocalVariableInstruction.LOCAL_VARIABLE_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.LookupSwitchInstruction.LOOKUPSWITCH_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.RefInstruction.REF_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.TableSwitchInstruction.TABLESWITCH_INSTRUCTION;

public final class Code extends AbstractList<Instruction> implements ICodeVisitor {

    private static final byte UNKNOWN_VALUE = 0x7f;

    /**
     * A table mapping each opcode to their predicted effect on the stack size.
     *
     * @see #visitMaxes()
     */
    public static final byte STACK_SIZE_MODIFIER_TABLE[] = {
         0, /* nop */                       1, /* aconst_null */                  1, /* iconst_m1 */                  1, /* iconst_0 */
         1, /* iconst_1 */                  1, /* iconst_2 */                     1, /* iconst_3 */                   1, /* iconst_4 */
         1, /* iconst_5 */                  2, /* lconst_0 */                     2, /* lconst_1 */                   1, /* fconst_0 */
         1, /* fconst_1 */                  1, /* fconst_2 */                     2, /* dconst_0 */                   2, /* dconst_1 */
         1, /* bipush */                    1, /* sipush */                       UNKNOWN_VALUE, /* ldc */            UNKNOWN_VALUE, /* ldc_w */
         UNKNOWN_VALUE, /* ldc2_w */        1, /* iload */                        2, /* lload */                      1, /* fload */
         2, /* dload */                     1, /* aload */                        1, /* iload_0 */                    1, /* iload_1 */
         1, /* iload_2 */                   1, /* iload_3 */                      2, /* lload_0 */                    2, /* lload_1 */
         2, /* lload_2 */                   2, /* lload_3 */                      1, /* fload_0 */                    1, /* fload_1 */
         1, /* fload_2 */                   1, /* fload_3 */                      2, /* dload_0 */                    2, /* dload_1 */
         2, /* dload_2 */                   2, /* dload_3 */                      1, /* aload_0 */                    1, /* aload_1 */
         1, /* aload_2 */                   1, /* aload_3 */                      1, /* iaload */                     1, /* laload */
         1, /* faload */                    1, /* daload */                       1, /* aaload */                     1, /* baload */
         1, /* caload */                    1, /* saload */                      -1, /* istore */                    -2, /* lstore */
        -1, /* fstore */                   -2, /* dstore */                      -1, /* astore */                    -1, /* istore_0 */
        -1, /* istore_1 */                 -1, /* istore_2 */                    -1, /* istore_3 */                  -2, /* lstore_0 */
        -2, /* lstore_1 */                 -2, /* lstore_2 */                    -2, /* lstore_3 */                  -1, /* fstore_0 */
        -1, /* fstore_1 */                 -1, /* fstore_2 */                    -1, /* fstore_3 */                  -2, /* dstore_0 */
        -2, /* dstore_1 */                 -2, /* dstore_2 */                    -2, /* dstore_3 */                  -1, /* astore_0 */
        -1, /* astore_1 */                 -1, /* astore_2 */                    -1, /* astore_3 */                  -2, /* iastore */
        -3, /* lastore */                  -2, /* fastore */                     -3, /* dastore */                   -2, /* aastore */
        -2, /* bastore */                  -2, /* castore */                     -2, /* sastore */                   -1, /* pop */
        -2, /* pop2 */                      1, /* dup */                          1, /* dup_x1 */                     1, /* dup_x2 */
         2, /* dup2 */                      2, /* dup2_x1 */                      2, /* dup2_x2 */                    0, /* swap */
        -1, /* iadd */                     -2, /* ladd */                        -1, /* fadd */                      -2, /* dadd */
        -1, /* isub */                     -2, /* lsub */                        -1, /* fsub */                      -2, /* dsub */
        -1, /* imul */                     -2, /* lmul */                        -1, /* fmul */                      -2, /* dmul */
        -1, /* idiv */                     -2, /* ldiv */                        -1, /* fdiv */                      -2, /* ddiv */
        -1, /* irem */                     -2, /* lrem */                        -1, /* frem */                      -2, /* drem */
         0, /* ineg */                      0, /* lneg */                         0, /* fneg */                       0, /* dneg */
        -1, /* ishl */                     -2, /* lshl */                        -1, /* ishr */                      -2, /* lshr */
        -1, /* iushr */                    -2, /* lushr */                       -1, /* iand */                      -2, /* land */
        -1, /* ior */                      -2, /* lor */                         -1, /* ixor */                      -2, /* lxor */
         0, /* iinc */                      1, /* i2l */                          0, /* i2f */                        1, /* i2d */
        -1, /* l2i */                      -1, /* l2f */                          0, /* l2d */                        0, /* f2i */
         1, /* f2l */                       1, /* f2d */                         -1, /* d2i */                        0, /* d2l */
        -1, /* d2f */                       0, /* i2b */                          0, /* i2c */                        0, /* i2s */
        -3, /* lcmp */                     -1, /* fcmpl */                       -1, /* fcmpg */                     -3, /* dcmpl */
        -3, /* dcmpg */                    -1, /* ifeq */                        -1, /* ifne */                      -1, /* iflt */
        -1, /* ifge */                     -1, /* ifgt */                        -1, /* ifle */                      -2, /* if_icmpeq */
        -2, /* if_icmpne */                -2, /* if_icmplt */                   -2, /* if_icmpge */                 -2, /* if_icmpgt */
        -2, /* if_icmple */                -2, /* if_acmpeq */                   -2, /* if_acmpne */                  0, /* goto */
         1, /* jsr */                       0, /* ret */                          UNKNOWN_VALUE, /* tableswitch */    UNKNOWN_VALUE, /* lookupswitch */
         UNKNOWN_VALUE, /* ireturn */       UNKNOWN_VALUE, /* lreturn */          UNKNOWN_VALUE, /* freturn */        UNKNOWN_VALUE, /* dreturn */
         UNKNOWN_VALUE, /* areturn */       UNKNOWN_VALUE, /* return */           UNKNOWN_VALUE, /* getstatic */      UNKNOWN_VALUE, /* putstatic */
         UNKNOWN_VALUE, /* getfield */      UNKNOWN_VALUE, /* putfield */         UNKNOWN_VALUE, /* invokevirtual */  UNKNOWN_VALUE, /* invokespecial */
         UNKNOWN_VALUE, /* invokestatic */  UNKNOWN_VALUE, /* invokeinterface */  UNKNOWN_VALUE, /* invokedynamic */  1, /* new */
         0, /* newarray */                  0, /* anewarray */                    0, /* arraylength */                UNKNOWN_VALUE, /* athrow */
         0, /* checkcast */                 0, /* instanceof */                  -1, /* monitorenter */              -1, /* monitorexit */
         UNKNOWN_VALUE, /* wide */          UNKNOWN_VALUE, /* multianewarray */  -1, /* ifnull */                    -1, /* ifnonnull */
         0, /* goto_w */                    1, /* jsr_w */                        0, /* breakpoint */                 UNKNOWN_VALUE, /* unimplemented instructions */
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        UNKNOWN_VALUE,                      UNKNOWN_VALUE,
         UNKNOWN_VALUE,                     UNKNOWN_VALUE,                        0, /* impdep1 */                    0, /* impdep2 */
    };

    private static final Instruction EMPTY_INSTRUCTIONS[] = {};

    /**
     * @see #grow(int)
     */
    private static final float SIZE_GROW_MODIFIER = 2f;

    private int size;
    private Instruction instructions[];

    private Instruction first,
                        last;

    public int maxStack,
               maxLocals;

    private Instruction currentInstruction = null;

    public Code(int size) {
        instructions = size > 0 ?
            new Instruction[size] :
            EMPTY_INSTRUCTIONS;
    }

    public Code(AttributeCode attributeCode, ConstantPool constantPool) {
        this(attributeCode.getByteCode().length);

        final byte byteCode[] = attributeCode.getByteCode();
        final Label labels[] = new Label[byteCode.length];
        for(int i = 0; i < byteCode.length; i++) {
            final byte opcode = byteCode[i];
            switch(INSTRUCTION_TYPE_TABLE[opcode & 0xff]) {
                case JUMP_INSTRUCTION:
                    int jumpIndex;
                    switch(opcode) {
                        // a 4 byte index must be constructed for the goto_w & jsr_w opcodes.
                        // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.goto_w
                        // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.jsr_w
                        case GOTO_W:
                        case JSR_W:
                            jumpIndex = (byteCode[++i] & 0xff) << 24 |
                                        (byteCode[++i] & 0xff) << 16 |
                                        (byteCode[++i] & 0xff) << 8  |
                                        (byteCode[++i] & 0xff);
                            break;
                        default:
                            jumpIndex = (byteCode[++i] & 0xff) << 8 |
                                        (byteCode[++i] & 0xff);
                            if((jumpIndex & 0x8000) != 0)
                                jumpIndex |= 0xffff0000;
                            break;
                    }
                    labels[i + jumpIndex - 2] = new Label();

                    // fallthrough.
                default:
                    // TODO: throw exception ? if valid to verify jump. unknown opcodes. (tableswitch)
                    i += OPCODE_PARAMETERS[opcode & 0xff];
                    break;
            }
        }

        for(int i = 0; i < byteCode.length; i++) {

            {
                final Label label = labels[i];
                if(label != null)
                    add(label);
            }

            final byte opcode = byteCode[i];
            switch(INSTRUCTION_TYPE_TABLE[opcode & 0xff]) {
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.bipush
                case BIPUSH_INSTRUCTION:
                    add(new BIPushInstruction(byteCode[++i]));
                    break;
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.sipush
                case SIPUSH:
                    add(
                        new SIPushInstruction((short) ((byteCode[++i] & 0xff) << 8 |
                                                       (byteCode[++i] & 0xff))
                    ));
                    break;
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w
                case LDC_INSTRUCTION: outer: {
                    Object data = null;
                    ConstantInfo constantInfo;
                    switch(opcode) {
                        default:
                        case LDC:
                            constantInfo = constantPool.getEntry(byteCode[++i] & 0xff);
                            break;
                        case LDC_W:
                            constantInfo = constantPool.getEntry((byteCode[++i] & 0xff) << 8 |
                                                                 (byteCode[++i] & 0xff));
                            break;
                        case LDC2_W: {
                            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w
                            final ConstantLong constantLong = (ConstantLong) constantPool.getEntry((byteCode[++i] & 0xff) << 8 |
                                                                                                   (byteCode[++i] & 0xff));
                            final byte tag = constantLong.getTag();
                            add(
                                tag == CONSTANT_LONG ?
                                    new LDCInstruction(
                                        opcode,
                                        constantLong.asLong(),
                                        tag
                                    ) :
                                    new LDCInstruction(
                                        opcode,
                                        constantLong.asDouble(),
                                        tag
                                    )
                            );
                        }   break outer;
                    }
                    final byte tag = constantInfo.getTag();
                    switch (tag) {
                        case CONSTANT_INTEGER:
                            data = ((ConstantInteger) constantInfo)
                                    .asInteger();
                            break;
                        case CONSTANT_FLOAT:
                            data = ((ConstantInteger) constantInfo)
                                    .asFloat();
                            break;
                        case CONSTANT_STRING:
                            data = ((ConstantName) constantInfo)
                                    .indexName(constantPool)
                                    .bytes;
                            break;
                        case CONSTANT_METHOD_TYPE:
                        case CONSTANT_CLASS:
                            data = ((ConstantName) constantInfo)
                                    .indexName(constantPool)
                                    .bytes;
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
                    add(new LDCInstruction(opcode, data, tag));
                }   break;
                case LOCAL_VARIABLE_INSTRUCTION:
                    add(new LocalVariableInstruction(opcode, byteCode[++i]));
                    break;
                case IINC_INSTRUCTION:
                    add(new IIncInstruction(byteCode[++i], byteCode[++i]));
                    break;
                case JUMP_INSTRUCTION: {
                    int jumpIndex;
                    switch (opcode) {
                        case GOTO_W:
                        case JSR_W:
                            jumpIndex = (byteCode[++i] & 0xff) << 24 |
                                        (byteCode[++i] & 0xff) << 16 |
                                        (byteCode[++i] & 0xff) << 8 |
                                        (byteCode[++i] & 0xff);
                            break;
                        default:
                            jumpIndex = (byteCode[++i] & 0xff) << 8 |
                                        (byteCode[++i] & 0xff);
                            if((jumpIndex & 0x8000) != 0)
                                jumpIndex |= 0xffff0000;
                            break;
                    }
                    add(new JumpInstruction(opcode, labels[i + jumpIndex - 2]));
                }   break;
                case TABLESWITCH_INSTRUCTION:
                    // TODO: impl
                    break;
                case LOOKUPSWITCH_INSTRUCTION:
                    // TODO: impl
                    break;
                case REF_INSTRUCTION: {
                    final ConstantMethodRef ref = ((ConstantMethodRef)constantPool.getEntry((byteCode[++i] & 0xff) << 8 |
                                                                                            (byteCode[++i] & 0xff)));
                    final ConstantNameAndType nameAndType = ref.indexNameAndType(constantPool);
                    add(new RefInstruction(opcode,
                                           ref.indexClass(constantPool)
                                              .indexName(constantPool)
                                              .bytes,
                                           nameAndType.indexName(constantPool)
                                                      .bytes,
                                           nameAndType.indexDesc(constantPool)
                                                      .bytes));
                }   break;
                default:
                    add(new NoParameterInstruction(opcode));
            }
        }

        this.maxStack = attributeCode.getMaxStack();
        this.maxLocals = attributeCode.getMaxLocals();
    }

    /**
     * Increments the modCount.
     */
    private void grow(int size) {
        modCount++;
        final Instruction old[] = instructions;
        if(old.length < size)
            instructions = Arrays.copyOf(
                old,
                old.length <= 0 ?
                    1 :
                    (int) (old.length * SIZE_GROW_MODIFIER)
            );
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Instruction set(int index, Instruction instruction) {
        rangeCheck(index);
        final Instruction old = instructions[index];
        instructions[index] = instruction;
        instruction.prev = old.prev;
        instruction.next = old.next;
        if(index == 0)
            first = instruction;
        else if(index == size - 1)
            last = instruction;
        return old;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, Instruction instruction) {
        rangeCheckAdd(index);
        grow(size + 1);
        System.arraycopy(
            instructions, index,
            instructions, index + 1, size - index
        );
        instructions[index] = instruction;
        size++;

        if(index == 0) {
            first = instruction;
            if(size != 1)
                instruction.next = instructions[1];
            first = instruction;
        } else if(index == size - 1) {
            last = instruction;
            if(size != 1) {
                final Instruction prev = instructions[index - 1];
                instruction.prev = prev;
                prev.next = instruction;
            }
            last = instruction;
        } else {
            instruction.prev = instructions[index - 1];
            instruction.next = instructions[index + 1];
        }
    }

    @Override
    public Instruction remove(int index) {
        rangeCheck(index);
        modCount++;
        final Instruction old = instructions[index];
        if(index != size - 1) {
            System.arraycopy(
                instructions,
                index + 1,
                instructions,
                index,
                size - index - 1
            );
            instructions[--size] = null;
            if(index == 0) {
                first = instructions[0];
                first.prev = null;
                if(size != 1)
                    first.next = instructions[1];
            } else {
                final Instruction replacement = instructions[index];
                final Instruction oldPrev = old.prev;
                replacement.prev = oldPrev;
                oldPrev.next = replacement;
            }
        } else {
            instructions[--size] = null;
            if(size != 0) {
                last = instructions[size - 1];
                last.next = null;
            }
        }
        return old;
    }

    @Override
    public Instruction get(int index) {
        rangeCheck(index);
        return instructions[index];
    }

    private void rangeCheckAdd(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void rangeCheck(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public boolean visitCodeAt(int index) {
        if(index >= 0 && index < size) {
            currentInstruction = instructions[index];
            return true;
        }
        return false;
    }

    @Override
    public void visitCodeAtEnd() {
        currentInstruction = null;
    }

    @Override
    public boolean visitPrevInstruction() {
        if(currentInstruction != null && currentInstruction.prev != null) {
            currentInstruction = currentInstruction.prev;
            return true;
        }
        return false;
    }

    @Override
    public boolean visitNextInstruction() {
        if(currentInstruction != null && currentInstruction.next != null) {
            currentInstruction = currentInstruction.next;
            return true;
        }
        return false;
    }

    @Override
    public Instruction visitCurrentInstruction() {
        return currentInstruction;
    }

    @Override
    public void visitMaxes(int maxStack, int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
    }

    @Override
    public void visitMaxes() {
        int stack = 0,
            locals = 0;
        for(Instruction instruction : this) {
            final byte opcode = instruction.getOpcode();
            switch(opcode) {
                default:
                    stack += STACK_SIZE_MODIFIER_TABLE[opcode & 0xff];
                    break;
            }
            maxStack = Math.max(stack, maxStack);
        }
    }

    @Override
    public void visitBiPushInstruction(byte data) {
    }

    @Override
    public void visitClassInstruction(byte opcode, String clazz) {
    }

    @Override
    public void visitIIncInstruction(byte index, byte constant) {
    }

    @Override
    public void visitInvokeDynamicInstruction() {

    }

    @Override
    public void visitInvokeInterfaceInstruction() {

    }

    @Override
    public void visitJumpInstruction() {

    }

    @Override
    public void visitLDCInstruction() {

    }

    @Override
    public void visitLocalVariableInstruction() {

    }

    @Override
    public void visitLookupSwitchInstruction() {

    }

    @Override
    public void visitMultiNewArrayInstruction() {

    }

    @Override
    public void visitNewArrayInstruction() {

    }

    @Override
    public void visitNoParameterInstruction() {

    }

    @Override
    public void visitRefInstruction() {

    }

    @Override
    public void visitSiPushInstruction() {

    }

    @Override
    public void visitTableSwitchInstruction() {

    }

    @Override
    public void visitWideInstruction() {

    }

    @Override
    public String toString() {
        final StringWriter stringWriter = new StringWriter();
        new CodePrinter(new PrintWriter(stringWriter))
                .accept(this);
        return stringWriter.toString();
    }
}
