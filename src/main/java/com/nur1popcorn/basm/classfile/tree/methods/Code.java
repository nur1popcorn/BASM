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

    private static final byte UNKNOWN_VALUE = 0x7f;

    /**
     * A table used to compute the max stack size.
     */
    private static final byte STACK_SIZE_TABLE[] = {
        0, /* nop */                       1, /* aconst_null */                 1, /* iconst_m1 */                 1, /* iconst_0 */
        1, /* iconst_1 */                  1, /* iconst_2 */                    1, /* iconst_3 */                  1, /* iconst_4 */
        1, /* iconst_5 */                  2, /* lconst_0 */                    2, /* lconst_1 */                  1, /* fconst_0 */
        1, /* fconst_1 */                  1, /* fconst_2 */                    2, /* dconst_0 */                  2, /* dconst_1 */
        1, /* bipush */                    1, /* sipush */                      UNKNOWN_VALUE, /* ldc */           UNKNOWN_VALUE, /* ldc_w */
        UNKNOWN_VALUE, /* ldc2_w */        1, /* iload */                       2, /* lload */                     1, /* fload */
        2, /* dload */                     1, /* aload */                       1, /* iload_0 */                   1, /* iload_1 */
        1, /* iload_2 */                   1, /* iload_3 */                     2, /* lload_0 */                   2, /* lload_1 */
        2, /* lload_2 */                   2, /* lload_3 */                     1, /* fload_0 */                   1, /* fload_1 */
        1, /* fload_2 */                   1, /* fload_3 */                     2, /* dload_0 */                   2, /* dload_1 */
        2, /* dload_2 */                   2, /* dload_3 */                     1, /* aload_0 */                   1, /* aload_1 */
        1, /* aload_2 */                   1, /* aload_3 */                     1, /* iaload */                    1, /* laload */
        1, /* faload */                    1, /* daload */                      1, /* aaload */                    1, /* baload */
        1, /* caload */                    1, /* saload */                     -1, /* istore */                   -2, /* lstore */
       -1, /* fstore */                   -2, /* dstore */                     -1, /* astore */                   -1, /* istore_0 */
       -1, /* istore_1 */                 -1, /* istore_2 */                   -1, /* istore_3 */                 -2, /* lstore_0 */
       -2, /* lstore_1 */                 -2, /* lstore_2 */                   -2, /* lstore_3 */                 -1, /* fstore_0 */
       -1, /* fstore_1 */                 -1, /* fstore_2 */                   -1, /* fstore_3 */                 -2, /* dstore_0 */
       -2, /* dstore_1 */                 -2, /* dstore_2 */                   -2, /* dstore_3 */                 -1, /* astore_0 */
       -1, /* astore_1 */                 -1, /* astore_2 */                   -1, /* astore_3 */                 -2, /* iastore */
       -3, /* lastore */                  -2, /* fastore */                    -3, /* dastore */                  -2, /* aastore */
       -2, /* bastore */                  -2, /* castore */                    -2, /* sastore */                  -1, /* pop */
       -2, /* pop2 */                      1, /* dup */                         1, /* dup_x1 */                    1, /* dup_x2 */
        2, /* dup2 */                      2, /* dup2_x1 */                     2, /* dup2_x2 */                   0, /* swap */
       -1, /* iadd */                     -2, /* ladd */                       -1, /* fadd */                     -2, /* dadd */
       -1, /* isub */                     -2, /* lsub */                       -1, /* fsub */                     -2, /* dsub */
       -1, /* imul */                     -2, /* lmul */                       -1, /* fmul */                     -2, /* dmul */
       -1, /* idiv */                     -2, /* ldiv */                       -1, /* fdiv */                     -2, /* ddiv */
       -1, /* irem */                     -2, /* lrem */                       -1, /* frem */                     -2, /* drem */
        0, /* ineg */                      0, /* lneg */                        0, /* fneg */                      0, /* dneg */
       -1, /* ishl */                     -2, /* lshl */                       -1, /* ishr */                     -2, /* lshr */
       -1, /* iushr */                    -2, /* lushr */                      -1, /* iand */                     -2, /* land */
       -1, /* ior */                      -2, /* lor */                        -1, /* ixor */                     -2, /* lxor */
        0, /* iinc */                      1, /* i2l */                         0, /* i2f */                       1, /* i2d */
       -1, /* l2i */                      -1, /* l2f */                         0, /* l2d */                       0, /* f2i */
        1, /* f2l */                       1, /* f2d */                        -1, /* d2i */                       0, /* d2l */
       -1, /* d2f */                       0, /* i2b */                         0, /* i2c */                       0, /* i2s */
       -3, /* lcmp */                     -1, /* fcmpl */                      -1, /* fcmpg */                    -3, /* dcmpl */
       -3, /* dcmpg */                    -1, /* ifeq */                       -1, /* ifne */                     -1, /* iflt */
       -1, /* ifge */                     -1, /* ifgt */                       -1, /* ifle */                     -2, /* if_icmpeq */
       -2, /* if_icmpne */                -2, /* if_icmplt */                  -2, /* if_icmpge */                -2, /* if_icmpgt */
       -2, /* if_icmple */                -2, /* if_acmpeq */                  -2, /* if_acmpne */                 0, /* goto */
        1, /* jsr */                       0, /* ret */                         UNKNOWN_VALUE, /* tableswitch */   UNKNOWN_VALUE, /* lookupswitch */
        UNKNOWN_VALUE, /* ireturn */       UNKNOWN_VALUE, /* lreturn */         UNKNOWN_VALUE, /* freturn */       UNKNOWN_VALUE, /* dreturn */
        UNKNOWN_VALUE, /* areturn */       UNKNOWN_VALUE, /* return */          UNKNOWN_VALUE, /* getstatic */     UNKNOWN_VALUE, /* putstatic */
        UNKNOWN_VALUE, /* getfield */      UNKNOWN_VALUE, /* putfield */        UNKNOWN_VALUE, /* invokevirtual */ UNKNOWN_VALUE, /* invokespecial */
        UNKNOWN_VALUE, /* invokestatic */  UNKNOWN_VALUE, /* invokeinterface */ UNKNOWN_VALUE, /* invokedynamic */ 1, /* new */
        0, /* newarray */                  0, /* anewarray */                   0, /* arraylength */               UNKNOWN_VALUE, /* athrow */
        0, /* checkcast */                 0, /* instanceof */                 -1, /* monitorenter */             -1, /* monitorexit */
        UNKNOWN_VALUE, /* wide */          UNKNOWN_VALUE, /* multianewarray */ -1, /* ifnull */                   -1, /* ifnonnull */
        0, /* goto_w */                    1, /* jsr_w */                       0, /* breakpoint */                UNKNOWN_VALUE, /* unimplemented instructions */
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       UNKNOWN_VALUE,                     UNKNOWN_VALUE,
        UNKNOWN_VALUE,                     UNKNOWN_VALUE,                       0, /* impdep1 */                   0, /* impdep2 */
    };
    
    public int maxStack,
               maxLocals;

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
        int stack = 0,
            locals = 0;
        for(Instruction instruction : code) {
            final byte opcode = instruction.getOpcode();
            switch(opcode) {
                default:
                    stack += STACK_SIZE_TABLE[Byte.toUnsignedInt(opcode)];
                    break;
            }
            maxStack = Math.max(stack, maxStack);
        }
        assert(maxStack >= 0 &&
               maxLocals >= 0);
    }

    public List<Instruction> getCode() {
        return code;
    }
}
