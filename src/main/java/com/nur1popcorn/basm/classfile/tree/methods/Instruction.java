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

/**
 *
 */
public abstract class Instruction {

    /**
     * A constant indicating that the given opcode is not implemented by the jvm.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte NOT_AN_INSTRUCTION = -1;

    /**
     * A constant used to identify the 'TABLESWITCH' and 'LOOKUPSWITCH' instructions.
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.lookupswitch }
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.tableswitch }
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte SWITCH_INSTRUCTION = 0;

    /**
     * A constant grouping together instructions which do not require any additional parameters.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte NO_PARAMETER_INSTRUCTION = 1;

    /**
     * A constant used to identify the 'NEWARRAY' instruction.
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.newarray }
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte NEWARRAY_INSTRUCTION = 2;

    /**
     * A constant used to identify the 'BIPUSH' instruction.
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.bipush }
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte BIPUSH_INSTRUCTION = 3;

    /**
     * A constant used to identify the 'LDC', 'LDC_W' and 'LDC2_W' instructions.
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc }
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc_w }
     * { https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc2_w }
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte LDC_INSTRUCTION = 4;

    /**
     * A constant used to identify instructions which require a localvariable index to be passed as a parameter.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte LOCAL_VARIABLE_INSTRUCTION = 5;

    /**
     * A constant used to identify instruction which manipulate the pc.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte JUMP_INSTRUCTION = 6;

    /**
     * A constant used to identify the 'SIPUSH' instruction.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte SIPUSH_INSTRUCTION = 7;

    /**
     * A constant used to identify the 'IINC' instruction.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte IINC_INSTRUCTION = 8;

    /**
     * A constant grouping together instructions which access fields or call methods.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final int REF_INSTRUCTION = 9;

    /**
     * A constant used to identify instructions which require a index into the constantpool pointing
     * to a 'CONSTANT_Class' as a parameter.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte CLASS_INSTRUCTION = 10;

    /**
     * A constant used to identify the 'WIDE' instruction.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte WIDE_INSTRUCTION = 11;

    /**
     * A constant used to identify the 'MULTINEWARRAY' instruction.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte MULTIANEWARRAY_INSTRUCTION = 12;

    /**
     * A constant used to indentify the 'INVOKEDYNAMIC' instruction.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte INVOKEDYNAMIC_INSTRUCTION = 13;

    /**
     * A constant used to identify a label.
     *
     * @see #INSTRUCTION_TYPE_TABLE
     */
    public static final byte LABEL_INSTRUCTION = 14;

    /**
     * A table mapping each instruction to their designated abstract type.
     */
    public static final byte INSTRUCTION_TYPE_TABLE[] = {
        NO_PARAMETER_INSTRUCTION, /* nop */            NO_PARAMETER_INSTRUCTION, /* aconst_null */ NO_PARAMETER_INSTRUCTION, /* iconst_m1 */
        NO_PARAMETER_INSTRUCTION, /* iconst_0 */       NO_PARAMETER_INSTRUCTION, /* iconst_1 */    NO_PARAMETER_INSTRUCTION, /* iconst_2 */
        NO_PARAMETER_INSTRUCTION, /* iconst_3 */       NO_PARAMETER_INSTRUCTION, /* iconst_4 */    NO_PARAMETER_INSTRUCTION, /* iconst_5 */
        NO_PARAMETER_INSTRUCTION, /* lconst_0 */       NO_PARAMETER_INSTRUCTION, /* lconst_1 */    NO_PARAMETER_INSTRUCTION, /* fconst_0 */
        NO_PARAMETER_INSTRUCTION, /* fconst_1 */       NO_PARAMETER_INSTRUCTION, /* fconst_2 */    NO_PARAMETER_INSTRUCTION, /* dconst_0 */
        NO_PARAMETER_INSTRUCTION, /* dconst_1 */       BIPUSH_INSTRUCTION, /* bipush */            SIPUSH_INSTRUCTION, /* sipush */
        LDC_INSTRUCTION, /* ldc */                     LDC_INSTRUCTION, /* ldc_w */                LDC_INSTRUCTION, /* ldc2_w */
        LOCAL_VARIABLE_INSTRUCTION, /* iload */        LOCAL_VARIABLE_INSTRUCTION, /* lload */     LOCAL_VARIABLE_INSTRUCTION, /* fload */
        LOCAL_VARIABLE_INSTRUCTION, /* dload */        LOCAL_VARIABLE_INSTRUCTION, /* aload */     NO_PARAMETER_INSTRUCTION, /* iload_0 */
        NO_PARAMETER_INSTRUCTION, /* iload_1 */        NO_PARAMETER_INSTRUCTION, /* iload_2 */     NO_PARAMETER_INSTRUCTION, /* iload_3 */
        NO_PARAMETER_INSTRUCTION, /* lload_0 */        NO_PARAMETER_INSTRUCTION, /* lload_1 */     NO_PARAMETER_INSTRUCTION, /* lload_2 */
        NO_PARAMETER_INSTRUCTION, /* lload_3 */        NO_PARAMETER_INSTRUCTION, /* fload_0 */     NO_PARAMETER_INSTRUCTION, /* fload_1 */
        NO_PARAMETER_INSTRUCTION, /* fload_2 */        NO_PARAMETER_INSTRUCTION, /* fload_3 */     NO_PARAMETER_INSTRUCTION, /* dload_0 */
        NO_PARAMETER_INSTRUCTION, /* dload_1 */        NO_PARAMETER_INSTRUCTION, /* dload_2 */     NO_PARAMETER_INSTRUCTION, /* dload_3 */
        NO_PARAMETER_INSTRUCTION, /* aload_0 */        NO_PARAMETER_INSTRUCTION, /* aload_1 */     NO_PARAMETER_INSTRUCTION, /* aload_2 */
        NO_PARAMETER_INSTRUCTION, /* aload_3 */        NO_PARAMETER_INSTRUCTION, /* iaload */      NO_PARAMETER_INSTRUCTION, /* laload */
        NO_PARAMETER_INSTRUCTION, /* faload */         NO_PARAMETER_INSTRUCTION, /* daload */      NO_PARAMETER_INSTRUCTION, /* aaload */
        NO_PARAMETER_INSTRUCTION, /* baload */         NO_PARAMETER_INSTRUCTION, /* caload */      NO_PARAMETER_INSTRUCTION, /* saload */
        LOCAL_VARIABLE_INSTRUCTION, /* istore */       LOCAL_VARIABLE_INSTRUCTION, /* lstore */    LOCAL_VARIABLE_INSTRUCTION, /* fstore */
        LOCAL_VARIABLE_INSTRUCTION, /* dstore */       LOCAL_VARIABLE_INSTRUCTION, /* astore */    NO_PARAMETER_INSTRUCTION, /* istore_0 */
        NO_PARAMETER_INSTRUCTION, /* istore_1 */       NO_PARAMETER_INSTRUCTION, /* istore_2 */    NO_PARAMETER_INSTRUCTION, /* istore_3 */
        NO_PARAMETER_INSTRUCTION, /* lstore_0 */       NO_PARAMETER_INSTRUCTION, /* lstore_1 */    NO_PARAMETER_INSTRUCTION, /* lstore_2 */
        NO_PARAMETER_INSTRUCTION, /* lstore_3 */       NO_PARAMETER_INSTRUCTION, /* fstore_0 */    NO_PARAMETER_INSTRUCTION, /* fstore_1 */
        NO_PARAMETER_INSTRUCTION, /* fstore_2 */       NO_PARAMETER_INSTRUCTION, /* fstore_3 */    NO_PARAMETER_INSTRUCTION, /* dstore_0 */
        NO_PARAMETER_INSTRUCTION, /* dstore_1 */       NO_PARAMETER_INSTRUCTION, /* dstore_2 */    NO_PARAMETER_INSTRUCTION, /* dstore_3 */
        NO_PARAMETER_INSTRUCTION, /* astore_0 */       NO_PARAMETER_INSTRUCTION, /* astore_1 */    NO_PARAMETER_INSTRUCTION, /* astore_2 */
        NO_PARAMETER_INSTRUCTION, /* astore_3 */       NO_PARAMETER_INSTRUCTION, /* iastore */     NO_PARAMETER_INSTRUCTION, /* lastore */
        NO_PARAMETER_INSTRUCTION, /* fastore */        NO_PARAMETER_INSTRUCTION, /* dastore */     NO_PARAMETER_INSTRUCTION, /* aastore */
        NO_PARAMETER_INSTRUCTION, /* bastore */        NO_PARAMETER_INSTRUCTION, /* castore */     NO_PARAMETER_INSTRUCTION, /* sastore */
        NO_PARAMETER_INSTRUCTION, /* pop */            NO_PARAMETER_INSTRUCTION, /* pop2 */        NO_PARAMETER_INSTRUCTION, /* dup */
        NO_PARAMETER_INSTRUCTION, /* dup_x1 */         NO_PARAMETER_INSTRUCTION, /* dup_x2 */      NO_PARAMETER_INSTRUCTION, /* dup2 */
        NO_PARAMETER_INSTRUCTION, /* dup2_x1 */        NO_PARAMETER_INSTRUCTION, /* dup2_x2 */     NO_PARAMETER_INSTRUCTION, /* swap */
        NO_PARAMETER_INSTRUCTION, /* iadd */           NO_PARAMETER_INSTRUCTION, /* ladd */        NO_PARAMETER_INSTRUCTION, /* fadd */
        NO_PARAMETER_INSTRUCTION, /* dadd */           NO_PARAMETER_INSTRUCTION, /* isub */        NO_PARAMETER_INSTRUCTION, /* lsub */
        NO_PARAMETER_INSTRUCTION, /* fsub */           NO_PARAMETER_INSTRUCTION, /* dsub */        NO_PARAMETER_INSTRUCTION, /* imul */
        NO_PARAMETER_INSTRUCTION, /* lmul */           NO_PARAMETER_INSTRUCTION, /* fmul */        NO_PARAMETER_INSTRUCTION, /* dmul */
        NO_PARAMETER_INSTRUCTION, /* idiv */           NO_PARAMETER_INSTRUCTION, /* ldiv */        NO_PARAMETER_INSTRUCTION, /* fdiv */
        NO_PARAMETER_INSTRUCTION, /* ddiv */           NO_PARAMETER_INSTRUCTION, /* irem */        NO_PARAMETER_INSTRUCTION, /* lrem */
        NO_PARAMETER_INSTRUCTION, /* frem */           NO_PARAMETER_INSTRUCTION, /* drem */        NO_PARAMETER_INSTRUCTION, /* ineg */
        NO_PARAMETER_INSTRUCTION, /* lneg */           NO_PARAMETER_INSTRUCTION, /* fneg */        NO_PARAMETER_INSTRUCTION, /* dneg */
        NO_PARAMETER_INSTRUCTION, /* ishl */           NO_PARAMETER_INSTRUCTION, /* lshl */        NO_PARAMETER_INSTRUCTION, /* ishr */
        NO_PARAMETER_INSTRUCTION, /* lshr */           NO_PARAMETER_INSTRUCTION, /* iushr */       NO_PARAMETER_INSTRUCTION, /* lushr */
        NO_PARAMETER_INSTRUCTION, /* iand */           NO_PARAMETER_INSTRUCTION, /* land */        NO_PARAMETER_INSTRUCTION, /* ior */
        NO_PARAMETER_INSTRUCTION, /* lor */            NO_PARAMETER_INSTRUCTION, /* ixor */        NO_PARAMETER_INSTRUCTION, /* lxor */
        IINC_INSTRUCTION, /* iinc */                   NO_PARAMETER_INSTRUCTION, /* i2l */         NO_PARAMETER_INSTRUCTION, /* i2f */
        NO_PARAMETER_INSTRUCTION, /* i2d */            NO_PARAMETER_INSTRUCTION, /* l2i */         NO_PARAMETER_INSTRUCTION, /* l2f */
        NO_PARAMETER_INSTRUCTION, /* l2d */            NO_PARAMETER_INSTRUCTION, /* f2i */         NO_PARAMETER_INSTRUCTION, /* f2l */
        NO_PARAMETER_INSTRUCTION, /* f2d */            NO_PARAMETER_INSTRUCTION, /* d2i */         NO_PARAMETER_INSTRUCTION, /* d2l */
        NO_PARAMETER_INSTRUCTION, /* d2f */            NO_PARAMETER_INSTRUCTION, /* i2b */         NO_PARAMETER_INSTRUCTION, /* i2c */
        NO_PARAMETER_INSTRUCTION, /* i2s */            NO_PARAMETER_INSTRUCTION, /* lcmp */        NO_PARAMETER_INSTRUCTION, /* fcmpl */
        NO_PARAMETER_INSTRUCTION, /* fcmpg */          NO_PARAMETER_INSTRUCTION, /* dcmpl */       NO_PARAMETER_INSTRUCTION, /* dcmpg */
        JUMP_INSTRUCTION, /* ifeq */                   JUMP_INSTRUCTION, /* ifne */                JUMP_INSTRUCTION, /* iflt */
        JUMP_INSTRUCTION, /* ifge */                   JUMP_INSTRUCTION, /* ifgt */                JUMP_INSTRUCTION, /* ifle */
        JUMP_INSTRUCTION, /* if_icmpeq */              JUMP_INSTRUCTION, /* if_icmpne */           JUMP_INSTRUCTION, /* if_icmplt */
        JUMP_INSTRUCTION, /* if_icmpge */              JUMP_INSTRUCTION, /* if_icmpgt */           JUMP_INSTRUCTION, /* if_icmple */
        JUMP_INSTRUCTION, /* if_acmpeq */              JUMP_INSTRUCTION, /* if_acmpne */           JUMP_INSTRUCTION, /* goto */
        JUMP_INSTRUCTION, /* jsr */                    LOCAL_VARIABLE_INSTRUCTION, /* ret */       SWITCH_INSTRUCTION, /* tableswitch */
        SWITCH_INSTRUCTION, /* lookupswitch */         NO_PARAMETER_INSTRUCTION, /* ireturn */     NO_PARAMETER_INSTRUCTION, /* lreturn */
        NO_PARAMETER_INSTRUCTION, /* freturn */        NO_PARAMETER_INSTRUCTION, /* dreturn */     NO_PARAMETER_INSTRUCTION, /* areturn */
        NO_PARAMETER_INSTRUCTION, /* return */         REF_INSTRUCTION, /* getstatic */            REF_INSTRUCTION, /* putstatic */
        REF_INSTRUCTION, /* getfield */                REF_INSTRUCTION, /* putfield */             REF_INSTRUCTION, /* invokevirtual */
        REF_INSTRUCTION, /* invokespecial */           REF_INSTRUCTION, /* invokestatic */         REF_INSTRUCTION, /* invokeinterface */
        INVOKEDYNAMIC_INSTRUCTION, /* invokedynamic */ CLASS_INSTRUCTION, /* new */                NEWARRAY_INSTRUCTION, /* newarray */
        CLASS_INSTRUCTION, /* anewarray */             NO_PARAMETER_INSTRUCTION, /* arraylength */ NO_PARAMETER_INSTRUCTION, /* athrow */
        CLASS_INSTRUCTION, /* checkcast */             CLASS_INSTRUCTION, /* instanceof */         NO_PARAMETER_INSTRUCTION, /* monitorenter */
        NO_PARAMETER_INSTRUCTION, /* monitorexit */    WIDE_INSTRUCTION, /* wide */                MULTIANEWARRAY_INSTRUCTION, /* multianewarray */
        JUMP_INSTRUCTION, /* ifnull */                 JUMP_INSTRUCTION, /* ifnonnull */           JUMP_INSTRUCTION, /* goto_w */
        JUMP_INSTRUCTION, /* jsr_w */                  NO_PARAMETER_INSTRUCTION, /* breakpoint */  NOT_AN_INSTRUCTION, /* unimplemented instructions */
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NOT_AN_INSTRUCTION,
        NOT_AN_INSTRUCTION,                            NOT_AN_INSTRUCTION,                         NO_PARAMETER_INSTRUCTION, /* impdep1 */
        NO_PARAMETER_INSTRUCTION, /* impdep2 */
    };

    private byte opcode;

    Instruction next,
                prev;

    /**
     * Empty constructor required by Label.
     */
    protected Instruction() {}

    public Instruction(byte opcode) {
        this.opcode = opcode;
    }

    public byte getOpcode() {
        return opcode;
    }

    public byte getType() {
        return INSTRUCTION_TYPE_TABLE[
            opcode & 0xff
        ];
    }
}
