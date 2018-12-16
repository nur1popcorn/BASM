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

import com.nur1popcorn.basm.Constants;
import com.nur1popcorn.basm.classfile.tree.ConstantPoolGenerator;
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionHandle;

import java.util.TreeMap;

import static com.nur1popcorn.basm.Constants.LDC;
import static com.nur1popcorn.basm.Constants.LDC_W;
import static com.nur1popcorn.basm.classfile.tree.Type.*;

public final class InstructionFactory {

    /* stands for no operation and does nothing. */
    public static final NoParameterInstruction NOP = new NoParameterInstruction(Constants.NOP);

    /* puts null on the stack */
    public static final NoParameterInstruction ACONST_NULL = new NoParameterInstruction(Constants.ACONST_NULL);

    /* puts int value between -1 and 5 on the stack. */
    public static final NoParameterInstruction ICONST_M1 = new NoParameterInstruction(Constants.ICONST_M1);
    public static final NoParameterInstruction ICONST_0 = new NoParameterInstruction(Constants.ICONST_0);
    public static final NoParameterInstruction ICONST_1 = new NoParameterInstruction(Constants.ICONST_1);
    public static final NoParameterInstruction ICONST_2 = new NoParameterInstruction(Constants.ICONST_2);
    public static final NoParameterInstruction ICONST_3 = new NoParameterInstruction(Constants.ICONST_3);
    public static final NoParameterInstruction ICONST_4 = new NoParameterInstruction(Constants.ICONST_4);
    public static final NoParameterInstruction ICONST_5 = new NoParameterInstruction(Constants.ICONST_5);

    /* puts long value between 0L and 1L on the stack. */
    public static final NoParameterInstruction LCONST_0 = new NoParameterInstruction(Constants.LCONST_0);
    public static final NoParameterInstruction LCONST_1 = new NoParameterInstruction(Constants.LCONST_1);

    /* puts float value between 0f and 2f on the stack. */
    public static final NoParameterInstruction FCONST_0 = new NoParameterInstruction(Constants.FCONST_0);
    public static final NoParameterInstruction FCONST_1 = new NoParameterInstruction(Constants.FCONST_1);
    public static final NoParameterInstruction FCONST_2 = new NoParameterInstruction(Constants.FCONST_2);

    /* puts double value between 0.0 and 1.0 on the stack. */
    public static final NoParameterInstruction DCONST_0 = new NoParameterInstruction(Constants.DCONST_0);
    public static final NoParameterInstruction DCONST_1 = new NoParameterInstruction(Constants.DCONST_1);

    /* requires no parameters and loads the N th local variable of type int onto the stack. */
    public static final NoParameterInstruction ILOAD_0 = new NoParameterInstruction(Constants.ILOAD_0);
    public static final NoParameterInstruction ILOAD_1 = new NoParameterInstruction(Constants.ILOAD_1);
    public static final NoParameterInstruction ILOAD_2 = new NoParameterInstruction(Constants.ILOAD_2);
    public static final NoParameterInstruction ILOAD_3 = new NoParameterInstruction(Constants.ILOAD_3);

    /* of type long. */
    public static final NoParameterInstruction LLOAD_0 = new NoParameterInstruction(Constants.LLOAD_0);
    public static final NoParameterInstruction LLOAD_1 = new NoParameterInstruction(Constants.LLOAD_1);
    public static final NoParameterInstruction LLOAD_2 = new NoParameterInstruction(Constants.LLOAD_2);
    public static final NoParameterInstruction LLOAD_3 = new NoParameterInstruction(Constants.LLOAD_3);

    /* of type float. */
    public static final NoParameterInstruction FLOAD_0 = new NoParameterInstruction(Constants.FLOAD_0);
    public static final NoParameterInstruction FLOAD_1 = new NoParameterInstruction(Constants.FLOAD_1);
    public static final NoParameterInstruction FLOAD_2 = new NoParameterInstruction(Constants.FLOAD_2);
    public static final NoParameterInstruction FLOAD_3 = new NoParameterInstruction(Constants.FLOAD_3);

    /* of type double. */
    public static final NoParameterInstruction DLOAD_0 = new NoParameterInstruction(Constants.DLOAD_0);
    public static final NoParameterInstruction DLOAD_1 = new NoParameterInstruction(Constants.DLOAD_1);
    public static final NoParameterInstruction DLOAD_2 = new NoParameterInstruction(Constants.DLOAD_2);
    public static final NoParameterInstruction DLOAD_3 = new NoParameterInstruction(Constants.DLOAD_3);

    /* puts object reference onto the stack. */
    public static final NoParameterInstruction ALOAD_0 = new NoParameterInstruction(Constants.ALOAD_0);
    public static final NoParameterInstruction ALOAD_1 = new NoParameterInstruction(Constants.ALOAD_1);
    public static final NoParameterInstruction ALOAD_2 = new NoParameterInstruction(Constants.ALOAD_2);
    public static final NoParameterInstruction ALOAD_3 = new NoParameterInstruction(Constants.ALOAD_3);

    /* loads value of type int from given array at given index. (Constants.arrayref and index should lay on the stack before execution)
       index and arrayref will be popped of the stack after execution and the result will be pushed onto the stack.*/
    public static final NoParameterInstruction IALOAD = new NoParameterInstruction(Constants.IALOAD);
    /* of type long. */
    public static final NoParameterInstruction LALOAD = new NoParameterInstruction(Constants.LALOAD);
    /* of type float. */
    public static final NoParameterInstruction FALOAD = new NoParameterInstruction(Constants.FALOAD);
    /* of type double. */
    public static final NoParameterInstruction DALOAD = new NoParameterInstruction(Constants.DALOAD);
    /* puts object reference onto stack. */
    public static final NoParameterInstruction AALOAD = new NoParameterInstruction(Constants.AALOAD);
    /* of type boolean. */
    public static final NoParameterInstruction BALOAD = new NoParameterInstruction(Constants.BALOAD);
    /* of type char. */
    public static final NoParameterInstruction CALOAD = new NoParameterInstruction(Constants.CALOAD);
    /* of type short. */
    public static final NoParameterInstruction SALOAD = new NoParameterInstruction(Constants.SALOAD);

    /* stores value of type int in N th localvariable. */
    public static final NoParameterInstruction ISTORE_0 = new NoParameterInstruction(Constants.ISTORE_0);
    public static final NoParameterInstruction ISTORE_1 = new NoParameterInstruction(Constants.ISTORE_1);
    public static final NoParameterInstruction ISTORE_2 = new NoParameterInstruction(Constants.ISTORE_2);
    public static final NoParameterInstruction ISTORE_3 = new NoParameterInstruction(Constants.ISTORE_3);

    /* of type long. */
    public static final NoParameterInstruction LSTORE_0 = new NoParameterInstruction(Constants.LSTORE_0);
    public static final NoParameterInstruction LSTORE_1 = new NoParameterInstruction(Constants.LSTORE_1);
    public static final NoParameterInstruction LSTORE_2 = new NoParameterInstruction(Constants.LSTORE_2);
    public static final NoParameterInstruction LSTORE_3 = new NoParameterInstruction(Constants.LSTORE_3);

    /* of type float. */
    public static final NoParameterInstruction FSTORE_0 = new NoParameterInstruction(Constants.FSTORE_0);
    public static final NoParameterInstruction FSTORE_1 = new NoParameterInstruction(Constants.FSTORE_1);
    public static final NoParameterInstruction FSTORE_2 = new NoParameterInstruction(Constants.FSTORE_2);
    public static final NoParameterInstruction FSTORE_3 = new NoParameterInstruction(Constants.FSTORE_3);

    /* of type long. */
    public static final NoParameterInstruction DSTORE_0 = new NoParameterInstruction(Constants.DSTORE_0);
    public static final NoParameterInstruction DSTORE_1 = new NoParameterInstruction(Constants.DSTORE_1);
    public static final NoParameterInstruction DSTORE_2 = new NoParameterInstruction(Constants.DSTORE_2);
    public static final NoParameterInstruction DSTORE_3 = new NoParameterInstruction(Constants.DSTORE_3);

    /* stores object reference. */
    public static final NoParameterInstruction ASTORE_0 = new NoParameterInstruction(Constants.ASTORE_0);
    public static final NoParameterInstruction ASTORE_1 = new NoParameterInstruction(Constants.ASTORE_1);
    public static final NoParameterInstruction ASTORE_2 = new NoParameterInstruction(Constants.ASTORE_2);
    public static final NoParameterInstruction ASTORE_3 = new NoParameterInstruction(Constants.ASTORE_3);

    /* stores value of type int in at given index in given array
       (Constants.arrayref, index, value)*/
    public static final NoParameterInstruction IASTORE = new NoParameterInstruction(Constants.IASTORE);
    /* of type long. */
    public static final NoParameterInstruction LASTORE = new NoParameterInstruction(Constants.LASTORE);
    /* of type float. */
    public static final NoParameterInstruction FASTORE = new NoParameterInstruction(Constants.FASTORE);
    /* of type double. */
    public static final NoParameterInstruction DASTORE = new NoParameterInstruction(Constants.DASTORE);
    /* stores object reference. */
    public static final NoParameterInstruction AASTORE = new NoParameterInstruction(Constants.AASTORE);
    /* of type bool. */
    public static final NoParameterInstruction BASTORE = new NoParameterInstruction(Constants.BASTORE);
    /* of type char. */
    public static final NoParameterInstruction CASTORE = new NoParameterInstruction(Constants.CASTORE);
    /* of type short. */
    public static final NoParameterInstruction SASTORE = new NoParameterInstruction(Constants.SASTORE);

    /* discards top value on stack. */
    public static final NoParameterInstruction POP = new NoParameterInstruction(Constants.POP);
    /* discards top 2 values or 1st if the value is of type long or double. */
    public static final NoParameterInstruction POP2 = new NoParameterInstruction(Constants.POP2);

    /* puts a copy of the top value on the stack on the stack. */
    public static final NoParameterInstruction DUP = new NoParameterInstruction(Constants.DUP);
    /* a copy of the 2nd value on the stack. */
    public static final NoParameterInstruction DUP_X1 = new NoParameterInstruction(Constants.DUP_X1);
    /* a copy of the 3rd value on the stack. */
    public static final NoParameterInstruction DUP_X2 = new NoParameterInstruction(Constants.DUP_X2);

    /* puts a copy of the 1st and 2nd or 1st if the value of type long or double on the stack on the stack. */
    public static final NoParameterInstruction DUP2 = new NoParameterInstruction(Constants.DUP2);
    /* a copy of the 2nd and 3rd value on the stack. */
    public static final NoParameterInstruction DUP2_X1 = new NoParameterInstruction(Constants.DUP2_X1);
    /* a copy of the 3rd and 4th value on the stack. */
    public static final NoParameterInstruction DUP2_X2 = new NoParameterInstruction(Constants.DUP2_X2);

    /* swaps top 2 values on the stack. */
    public static final NoParameterInstruction SWAP = new NoParameterInstruction(Constants.SWAP);

    /* adds top two ints on the stack and puts result on the stack. */
    public static final NoParameterInstruction IADD = new NoParameterInstruction(Constants.IADD);
    /* top two longs. */
    public static final NoParameterInstruction LADD = new NoParameterInstruction(Constants.LADD);
    /* top two floats. */
    public static final NoParameterInstruction FADD = new NoParameterInstruction(Constants.FADD);
    /* top two doubles. */
    public static final NoParameterInstruction DADD = new NoParameterInstruction(Constants.DADD);

    /* subtracts top two ints on the stack and puts result on the stack. */
    public static final NoParameterInstruction ISUB = new NoParameterInstruction(Constants.ISUB);
    /* top two longs. */
    public static final NoParameterInstruction LSUB = new NoParameterInstruction(Constants.LSUB);
    /* top two floats. */
    public static final NoParameterInstruction FSUB = new NoParameterInstruction(Constants.FSUB);
    /* top two doubles. */
    public static final NoParameterInstruction DSUB = new NoParameterInstruction(Constants.DSUB);

    /* multiplies top two ints on the stack and puts result on the stack. */
    public static final NoParameterInstruction IMUL = new NoParameterInstruction(Constants.IMUL);
    /* top two longs. */
    public static final NoParameterInstruction LMUL = new NoParameterInstruction(Constants.LMUL);
    /* top two floats. */
    public static final NoParameterInstruction FMUL = new NoParameterInstruction(Constants.FMUL);
    /* top two doubles. */
    public static final NoParameterInstruction DMUL = new NoParameterInstruction(Constants.DMUL);

    /* divides top two ints on the stack and puts result on the stack. */
    public static final NoParameterInstruction IDIV = new NoParameterInstruction(Constants.IDIV);
    /* top two longs. */
    public static final NoParameterInstruction LDIV = new NoParameterInstruction(Constants.LDIV);
    /* top two floats. */
    public static final NoParameterInstruction FDIV = new NoParameterInstruction(Constants.FDIV);
    /* top two doubles. */
    public static final NoParameterInstruction DDIV = new NoParameterInstruction(Constants.DDIV);

    /* computes the remainder from division of the top two ints on the stack and puts result on the stack. */
    public static final NoParameterInstruction IREM = new NoParameterInstruction(Constants.IREM);
    /* top two longs. */
    public static final NoParameterInstruction LREM = new NoParameterInstruction(Constants.LREM);
    /* top two floats. */
    public static final NoParameterInstruction FREM = new NoParameterInstruction(Constants.FREM);
    /* top two doubles. */
    public static final NoParameterInstruction DREM = new NoParameterInstruction(Constants.DREM);

    /* negates the top value on the stack and puts the result on the stack. */
    public static final NoParameterInstruction INEG = new NoParameterInstruction(Constants.INEG);
    /* top long. */
    public static final NoParameterInstruction LNEG = new NoParameterInstruction(Constants.LNEG);
    /* top float. */
    public static final NoParameterInstruction FNEG = new NoParameterInstruction(Constants.FNEG);
    /* top double. */
    public static final NoParameterInstruction DNEG = new NoParameterInstruction(Constants.DNEG);

    /* shifts top int left by 2nd int on the stack and puts result on the stack. */
    public static final NoParameterInstruction ISHL = new NoParameterInstruction(Constants.ISHL);
    /* top long by 2nd long. */
    public static final NoParameterInstruction LSHL = new NoParameterInstruction(Constants.LSHL);

    /* arithmetically shifts top int right by 2nd int on the stack and puts result on the stack. */
    public static final NoParameterInstruction ISHR = new NoParameterInstruction(Constants.ISHR);
    /* top long by 2nd long. */
    public static final NoParameterInstruction LSHR = new NoParameterInstruction(Constants.LSHR);

    /* logically shifts top int right by 2nd int on the stack and puts result on the stack. */
    public static final NoParameterInstruction IUSHR = new NoParameterInstruction(Constants.IUSHR);
    /* top long by 2nd long. */
    public static final NoParameterInstruction LUSHR = new NoParameterInstruction(Constants.LUSHR);

    /* performs bitwise and on 1st and 2nd int on the stack. */
    public static final NoParameterInstruction IAND = new NoParameterInstruction(Constants.IAND);
    /* 1st and 2nd long. */
    public static final NoParameterInstruction LAND = new NoParameterInstruction(Constants.LAND);

    /* performs bitwise or on 1st and 2nd int on the stack. */
    public static final NoParameterInstruction IOR = new NoParameterInstruction(Constants.IOR);
    /* 1st and 2nd long. */
    public static final NoParameterInstruction LOR = new NoParameterInstruction(Constants.LOR);

    /* performs bitwise xor on 1st and 2nd int on the stack. */
    public static final NoParameterInstruction IXOR = new NoParameterInstruction(Constants.IXOR);
    /* 1st and 2nd long. */
    public static final NoParameterInstruction LXOR = new NoParameterInstruction(Constants.LXOR);

    /* converts the int on top of the stack to a long. */
    public static final NoParameterInstruction I2L = new NoParameterInstruction(Constants.I2L);
    /* to a float */
    public static final NoParameterInstruction I2F = new NoParameterInstruction(Constants.I2F);
    /* to a double */
    public static final NoParameterInstruction I2D = new NoParameterInstruction(Constants.I2D);

    /* converts the long on top of the stack to an int. */
    public static final NoParameterInstruction L2I = new NoParameterInstruction(Constants.L2I);
    /* to a float */
    public static final NoParameterInstruction L2F = new NoParameterInstruction(Constants.L2F);
    /* to a double */
    public static final NoParameterInstruction L2D = new NoParameterInstruction(Constants.L2D);

    /* converts the float on top of the stack to an int. */
    public static final NoParameterInstruction F2I = new NoParameterInstruction(Constants.F2I);
    /* to a long */
    public static final NoParameterInstruction F2L = new NoParameterInstruction(Constants.F2L);
    /* to a double */
    public static final NoParameterInstruction F2D = new NoParameterInstruction(Constants.F2D);

    /* converts the double on top of the stack to an int. */
    public static final NoParameterInstruction D2I = new NoParameterInstruction(Constants.D2I);
    /* to a long */
    public static final NoParameterInstruction D2L = new NoParameterInstruction(Constants.D2L);
    /* to a float */
    public static final NoParameterInstruction D2F = new NoParameterInstruction(Constants.D2F);

    /* converts the int on top of the stack to a NoParameterInstruction. */
    public static final NoParameterInstruction I2B = new NoParameterInstruction(Constants.I2B);
    /* to a char */
    public static final NoParameterInstruction I2C = new NoParameterInstruction(Constants.I2C);
    /* to a short */
    public static final NoParameterInstruction I2S = new NoParameterInstruction(Constants.I2S);

    /* pushes 0 on the stack if both longs on are equal, 1 if the 2nd on is greater than the top one and -1 otherwise.  */
    public static final NoParameterInstruction LCMP = new NoParameterInstruction(Constants.LCMP);

    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    public static final NoParameterInstruction FCMPL = new NoParameterInstruction(Constants.FCMPL);
    /* pushes 0 on the stack if both floats on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    public static final NoParameterInstruction FCMPG = new NoParameterInstruction(Constants.FCMPG);

    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and -1 otherwise.  */
    public static final NoParameterInstruction DCMPL = new NoParameterInstruction(Constants.DCMPL);
    /* pushes 0 on the stack if both doubles on are equal, 1 if the 2nd on is greater than the top one, -1 if either of them are equal to NaN and 1 otherwise.  */
    public static final NoParameterInstruction DCMPG = new NoParameterInstruction(Constants.DCMPG);

    /* returns an int from the method. */
    public static final NoParameterInstruction IRETURN = new NoParameterInstruction(Constants.IRETURN);
    /* a long */
    public static final NoParameterInstruction LRETURN = new NoParameterInstruction(Constants.LRETURN);
    /* a float */
    public static final NoParameterInstruction FRETURN = new NoParameterInstruction(Constants.FRETURN);
    /* a double */
    public static final NoParameterInstruction DRETURN = new NoParameterInstruction(Constants.DRETURN);
    /* an object */
    public static final NoParameterInstruction ARETURN = new NoParameterInstruction(Constants.ARETURN);
    /* returns from the method. */
    public static final NoParameterInstruction RETURN = new NoParameterInstruction(Constants.RETURN);

    public static final NoParameterInstruction ARRAYLENGTH = new NoParameterInstruction(Constants.ARRAYLENGTH);
    public static final NoParameterInstruction ATHROW = new NoParameterInstruction(Constants.ATHROW);
    public static final NoParameterInstruction MONITORENTER = new NoParameterInstruction(Constants.MONITORENTER);
    public static final NoParameterInstruction MONITOREXIT = new NoParameterInstruction(Constants.MONITOREXIT);

    // TODO: desc.
    public static final NoParameterInstruction BREAKPOINT = new NoParameterInstruction(Constants.BREAKPOINT);
    public static final NoParameterInstruction IMPDEP1 = new NoParameterInstruction(Constants.IMPDEP1);
    public static final NoParameterInstruction IMPDEP2 = new NoParameterInstruction(Constants.IMPDEP2);

    /**
     *
     */
    public static final NoParameterInstruction INSTRUCTIONS[] = new NoParameterInstruction[0x100];
    static {
        INSTRUCTIONS[Constants.NOP] = NOP;
        INSTRUCTIONS[Constants.ACONST_NULL] = ACONST_NULL;
        INSTRUCTIONS[Constants.ICONST_M1] = ICONST_M1;
        INSTRUCTIONS[Constants.ICONST_0] = ICONST_0;
        INSTRUCTIONS[Constants.ICONST_1] = ICONST_1;
        INSTRUCTIONS[Constants.ICONST_2] = ICONST_2;
        INSTRUCTIONS[Constants.ICONST_3] = ICONST_3;
        INSTRUCTIONS[Constants.ICONST_4] = ICONST_4;
        INSTRUCTIONS[Constants.ICONST_5] = ICONST_5;
        INSTRUCTIONS[Constants.LCONST_0] = LCONST_0;
        INSTRUCTIONS[Constants.LCONST_1] = LCONST_1;
        INSTRUCTIONS[Constants.FCONST_0] = FCONST_0;
        INSTRUCTIONS[Constants.FCONST_1] = FCONST_1;
        INSTRUCTIONS[Constants.FCONST_2] = FCONST_2;
        INSTRUCTIONS[Constants.DCONST_0] = DCONST_0;
        INSTRUCTIONS[Constants.DCONST_1] = DCONST_1;
        INSTRUCTIONS[Constants.ILOAD_0] = ILOAD_0;
        INSTRUCTIONS[Constants.ILOAD_1] = ILOAD_1;
        INSTRUCTIONS[Constants.ILOAD_2] = ILOAD_2;
        INSTRUCTIONS[Constants.ILOAD_3] = ILOAD_3;
        INSTRUCTIONS[Constants.LLOAD_0] = LLOAD_0;
        INSTRUCTIONS[Constants.LLOAD_1] = LLOAD_1;
        INSTRUCTIONS[Constants.LLOAD_2] = LLOAD_2;
        INSTRUCTIONS[Constants.LLOAD_3] = LLOAD_3;
        INSTRUCTIONS[Constants.FLOAD_0] = FLOAD_0;
        INSTRUCTIONS[Constants.FLOAD_1] = FLOAD_1;
        INSTRUCTIONS[Constants.FLOAD_2] = FLOAD_2;
        INSTRUCTIONS[Constants.FLOAD_3] = FLOAD_3;
        INSTRUCTIONS[Constants.DLOAD_0] = DLOAD_0;
        INSTRUCTIONS[Constants.DLOAD_1] = DLOAD_1;
        INSTRUCTIONS[Constants.DLOAD_2] = DLOAD_2;
        INSTRUCTIONS[Constants.DLOAD_3] = DLOAD_3;
        INSTRUCTIONS[Constants.ALOAD_0] = ALOAD_0;
        INSTRUCTIONS[Constants.ALOAD_1] = ALOAD_1;
        INSTRUCTIONS[Constants.ALOAD_2] = ALOAD_2;
        INSTRUCTIONS[Constants.ALOAD_3] = ALOAD_3;
        INSTRUCTIONS[Constants.IALOAD] = IALOAD;
        INSTRUCTIONS[Constants.LALOAD] = LALOAD;
        INSTRUCTIONS[Constants.FALOAD] = FALOAD;
        INSTRUCTIONS[Constants.DALOAD] = DALOAD;
        INSTRUCTIONS[Constants.AALOAD] = AALOAD;
        INSTRUCTIONS[Constants.BALOAD] = BALOAD;
        INSTRUCTIONS[Constants.CALOAD] = CALOAD;
        INSTRUCTIONS[Constants.SALOAD] = SALOAD;
        INSTRUCTIONS[Constants.ISTORE_0] = ISTORE_0;
        INSTRUCTIONS[Constants.ISTORE_1] = ISTORE_1;
        INSTRUCTIONS[Constants.ISTORE_2] = ISTORE_2;
        INSTRUCTIONS[Constants.ISTORE_3] = ISTORE_3;
        INSTRUCTIONS[Constants.LSTORE_0] = LSTORE_0;
        INSTRUCTIONS[Constants.LSTORE_1] = LSTORE_1;
        INSTRUCTIONS[Constants.LSTORE_2] = LSTORE_2;
        INSTRUCTIONS[Constants.LSTORE_3] = LSTORE_3;
        INSTRUCTIONS[Constants.FSTORE_0] = FSTORE_0;
        INSTRUCTIONS[Constants.FSTORE_1] = FSTORE_1;
        INSTRUCTIONS[Constants.FSTORE_2] = FSTORE_2;
        INSTRUCTIONS[Constants.FSTORE_3] = FSTORE_3;
        INSTRUCTIONS[Constants.DSTORE_0] = DSTORE_0;
        INSTRUCTIONS[Constants.DSTORE_1] = DSTORE_1;
        INSTRUCTIONS[Constants.DSTORE_2] = DSTORE_2;
        INSTRUCTIONS[Constants.DSTORE_3] = DSTORE_3;
        INSTRUCTIONS[Constants.ASTORE_0] = ASTORE_0;
        INSTRUCTIONS[Constants.ASTORE_1] = ASTORE_1;
        INSTRUCTIONS[Constants.ASTORE_2] = ASTORE_2;
        INSTRUCTIONS[Constants.ASTORE_3] = ASTORE_3;
        INSTRUCTIONS[Constants.IASTORE] = IASTORE;
        INSTRUCTIONS[Constants.LASTORE] = LASTORE;
        INSTRUCTIONS[Constants.FASTORE] = FASTORE;
        INSTRUCTIONS[Constants.DASTORE] = DASTORE;
        INSTRUCTIONS[Constants.AASTORE] = AASTORE;
        INSTRUCTIONS[Constants.BASTORE] = BASTORE;
        INSTRUCTIONS[Constants.CASTORE] = CASTORE;
        INSTRUCTIONS[Constants.SASTORE] = SASTORE;
        INSTRUCTIONS[Constants.POP] = POP;
        INSTRUCTIONS[Constants.POP2] = POP2;
        INSTRUCTIONS[Constants.DUP] = DUP;
        INSTRUCTIONS[Constants.DUP_X1] = DUP_X1;
        INSTRUCTIONS[Constants.DUP_X2] = DUP_X2;
        INSTRUCTIONS[Constants.DUP2] = DUP2;
        INSTRUCTIONS[Constants.DUP2_X1] = DUP2_X1;
        INSTRUCTIONS[Constants.DUP2_X2] = DUP2_X2;
        INSTRUCTIONS[Constants.SWAP] = SWAP;
        INSTRUCTIONS[Constants.IADD] = IADD;
        INSTRUCTIONS[Constants.LADD] = LADD;
        INSTRUCTIONS[Constants.FADD] = FADD;
        INSTRUCTIONS[Constants.DADD] = DADD;
        INSTRUCTIONS[Constants.ISUB] = ISUB;
        INSTRUCTIONS[Constants.LSUB] = LSUB;
        INSTRUCTIONS[Constants.FSUB] = FSUB;
        INSTRUCTIONS[Constants.DSUB] = DSUB;
        INSTRUCTIONS[Constants.IMUL] = IMUL;
        INSTRUCTIONS[Constants.LMUL] = LMUL;
        INSTRUCTIONS[Constants.FMUL] = FMUL;
        INSTRUCTIONS[Constants.DMUL] = DMUL;
        INSTRUCTIONS[Constants.IDIV] = IDIV;
        INSTRUCTIONS[Constants.LDIV] = LDIV;
        INSTRUCTIONS[Constants.FDIV] = FDIV;
        INSTRUCTIONS[Constants.DDIV] = DDIV;
        INSTRUCTIONS[Constants.IREM] = IREM;
        INSTRUCTIONS[Constants.LREM] = LREM;
        INSTRUCTIONS[Constants.FREM] = FREM;
        INSTRUCTIONS[Constants.DREM] = DREM;
        INSTRUCTIONS[Constants.INEG] = INEG;
        INSTRUCTIONS[Constants.LNEG] = LNEG;
        INSTRUCTIONS[Constants.FNEG] = FNEG;
        INSTRUCTIONS[Constants.DNEG] = DNEG;
        INSTRUCTIONS[Constants.ISHL] = ISHL;
        INSTRUCTIONS[Constants.LSHL] = LSHL;
        INSTRUCTIONS[Constants.ISHR] = ISHR;
        INSTRUCTIONS[Constants.LSHR] = LSHR;
        INSTRUCTIONS[Constants.IUSHR] = IUSHR;
        INSTRUCTIONS[Constants.LUSHR] = LUSHR;
        INSTRUCTIONS[Constants.IAND] = IAND;
        INSTRUCTIONS[Constants.LAND] = LAND;
        INSTRUCTIONS[Constants.IOR & 0xff] = IOR;
        INSTRUCTIONS[Constants.LOR & 0xff] = LOR;
        INSTRUCTIONS[Constants.IXOR & 0xff] = IXOR;
        INSTRUCTIONS[Constants.LXOR & 0xff] = LXOR;
        INSTRUCTIONS[Constants.I2L & 0xff] = I2L;
        INSTRUCTIONS[Constants.I2F & 0xff] = I2F;
        INSTRUCTIONS[Constants.I2D & 0xff] = I2D;
        INSTRUCTIONS[Constants.L2I & 0xff] = L2I;
        INSTRUCTIONS[Constants.L2F & 0xff] = L2F;
        INSTRUCTIONS[Constants.L2D & 0xff] = L2D;
        INSTRUCTIONS[Constants.F2I & 0xff] = F2I;
        INSTRUCTIONS[Constants.F2L & 0xff] = F2L;
        INSTRUCTIONS[Constants.F2D & 0xff] = F2D;
        INSTRUCTIONS[Constants.D2I & 0xff] = D2I;
        INSTRUCTIONS[Constants.D2L & 0xff] = D2L;
        INSTRUCTIONS[Constants.D2F & 0xff] = D2F;
        INSTRUCTIONS[Constants.I2B & 0xff] = I2B;
        INSTRUCTIONS[Constants.I2C & 0xff] = I2C;
        INSTRUCTIONS[Constants.I2S & 0xff] = I2S;
        INSTRUCTIONS[Constants.LCMP & 0xff] = LCMP;
        INSTRUCTIONS[Constants.FCMPL & 0xff] = FCMPL;
        INSTRUCTIONS[Constants.FCMPG & 0xff] = FCMPG;
        INSTRUCTIONS[Constants.DCMPL & 0xff] = DCMPL;
        INSTRUCTIONS[Constants.DCMPG & 0xff] = DCMPG;
        INSTRUCTIONS[Constants.IRETURN & 0xff] = IRETURN;
        INSTRUCTIONS[Constants.LRETURN & 0xff] = LRETURN;
        INSTRUCTIONS[Constants.FRETURN & 0xff] = FRETURN;
        INSTRUCTIONS[Constants.DRETURN & 0xff] = DRETURN;
        INSTRUCTIONS[Constants.ARETURN & 0xff] = ARETURN;
        INSTRUCTIONS[Constants.RETURN & 0xff] = RETURN;
        INSTRUCTIONS[Constants.ARRAYLENGTH & 0xff] = ARRAYLENGTH;
        INSTRUCTIONS[Constants.ATHROW & 0xff] = ATHROW;
        INSTRUCTIONS[Constants.MONITORENTER & 0xff] = MONITORENTER;
        INSTRUCTIONS[Constants.MONITOREXIT & 0xff] = MONITOREXIT;
        INSTRUCTIONS[Constants.BREAKPOINT & 0xff] = BREAKPOINT;
        INSTRUCTIONS[Constants.IMPDEP1 & 0xff] = IMPDEP1;
        INSTRUCTIONS[Constants.IMPDEP2 & 0xff] = IMPDEP2;
    }

    private final ConstantPoolGenerator gen;

    /**
     * @param gen
     */
    public InstructionFactory(ConstantPoolGenerator gen) {
        this.gen = gen;
    }

    /**
     * @param index
     * @return
     */
    public CPInstruction createLDC(int index) {
        return new LDCInstruction(
            index > 0x100 ?
                LDC_W:
                LDC,
            index, gen);
    }

    /**
     * @param value
     * @return
     *
     * @throws IllegalArgumentException
     */
    public Instruction createPush(Object value) {
        if(value instanceof Number) {
            if(value instanceof Integer) {
                final int item = (int) value;
                switch(item) {
                    case -1: return ICONST_M1;
                    case  0: return ICONST_0;
                    case  1: return ICONST_1;
                    case  2: return ICONST_2;
                    case  3: return ICONST_3;
                    case  4: return ICONST_4;
                    case  5: return ICONST_5;
                    default:
                        return createLDC(gen.findInt(item));
                }
            } else if(value instanceof Byte ||
                      value instanceof Short) {
                final short item = (short) value;
                switch(item) {
                    case -1: return ICONST_M1;
                    case  0: return ICONST_0;
                    case  1: return ICONST_1;
                    case  2: return ICONST_2;
                    case  3: return ICONST_3;
                    case  4: return ICONST_4;
                    case  5: return ICONST_5;
                    default:
                        return new PushInstruction(
                            item < 0x100 && item >= 0 ?
                                Constants.BIPUSH :
                                Constants.SIPUSH,
                            item
                        );
                }
            } else if(value instanceof Long) {
                final long item = (long) value;
                if(item == 0L)
                    return LCONST_0;
                else if(item == 1L)
                    return LCONST_1;
                else
                    return createLDC(gen.findLong(item));
            } else if(value instanceof Float) {
                final float item = (float) value;
                if(item == 0.0f)
                    return FCONST_0;
                else if(item == 1.0f)
                    return FCONST_1;
                else if(item == 2.0f)
                    return FCONST_2;
                else
                    return createLDC(gen.findFloat(item));
            } else if(value instanceof Double) {
                final double item = (double) value;
                if(item == 0.0d)
                    return DCONST_0;
                else if(item == 1.0d)
                    return DCONST_1;
                else
                    return createLDC(gen.findDouble(item));
            }
        } else if(value instanceof Character) {
            final char item = (char) value;
            switch(item) {
                case 0: return ICONST_0;
                case 1: return ICONST_1;
                case 2: return ICONST_2;
                case 3: return ICONST_3;
                case 4: return ICONST_4;
                case 5: return ICONST_5;
                default:
                    return item > '\u7fff' ?
                        createLDC(gen.findInt(item)) :
                        new PushInstruction(
                            item < '\u0100' ?
                                Constants.BIPUSH :
                                Constants.SIPUSH,
                            (short) item
                        );
            }
        } else if(value instanceof String)
            return createLDC(gen.findUTF8((String) value));
        else if(value instanceof Class)
            return createLDC(gen.findUTF8(Type.getInternalName((Class) value)));
        // TODO: method handle etc.
        throw new IllegalArgumentException(
            "The value provided is of invalid type: value=" + value);
    }

    /**
     * @param type
     * @param index
     *
     * @return
     *
     * @throws IllegalArgumentException
     */
    public Instruction createStore(Type type, byte index) {
        switch(type.getType()) {
            case T_BYTE:
            case T_BOOLEAN:
            case T_SHORT:
            case T_CHAR:
            case T_INT:
                switch(index) {
                    case 0: return ISTORE_0;
                    case 1: return ISTORE_1;
                    case 2: return ISTORE_2;
                    case 3: return ISTORE_3;
                    default:
                        return new LocalVariableInstruction(Constants.ISTORE, index);
                }
            case T_LONG:
                switch(index) {
                    case 0: return LSTORE_0;
                    case 1: return LSTORE_1;
                    case 2: return LSTORE_2;
                    case 3: return LSTORE_3;
                    default:
                        return new LocalVariableInstruction(Constants.LSTORE, index);
                }
            case T_FLOAT:
                switch(index) {
                    case 0: return FSTORE_0;
                    case 1: return FSTORE_1;
                    case 2: return FSTORE_2;
                    case 3: return FSTORE_3;
                    default:
                        return new LocalVariableInstruction(Constants.FSTORE, index);
                }
            case T_DOUBLE:
                switch(index) {
                    case 0: return DSTORE_0;
                    case 1: return DSTORE_1;
                    case 2: return DSTORE_2;
                    case 3: return DSTORE_3;
                    default:
                        return new LocalVariableInstruction(Constants.DSTORE, index);
                }
            case T_OBJECT:
                switch(index) {
                    case 0: return ASTORE_0;
                    case 1: return ASTORE_1;
                    case 2: return ASTORE_2;
                    case 3: return ASTORE_3;
                    default:
                        return new LocalVariableInstruction(Constants.AASTORE, index);
                }
            default:
                throw new IllegalArgumentException(
                    "The type provided is invalid: type=" + type);
        }
    }

    /**
     * @param type
     * @param index
     *
     * @return
     *
     * @throws IllegalArgumentException
     */
    public Instruction createLoad(Type type, byte index)  {
        switch(type.getType()) {
            case T_BYTE:
            case T_BOOLEAN:
            case T_SHORT:
            case T_CHAR:
            case T_INT:
                switch(index) {
                    case 0: return ILOAD_0;
                    case 1: return ILOAD_1;
                    case 2: return ILOAD_2;
                    case 3: return ILOAD_3;
                    default:
                        return new LocalVariableInstruction(
                            Constants.ILOAD, index);
                }
            case T_LONG:
                switch(index) {
                    case 0: return LLOAD_0;
                    case 1: return LLOAD_1;
                    case 2: return LLOAD_2;
                    case 3: return LLOAD_3;
                    default:
                        return new LocalVariableInstruction(
                            Constants.LLOAD, index);
                }
            case T_FLOAT:
                switch(index) {
                    case 0: return FLOAD_0;
                    case 1: return FLOAD_1;
                    case 2: return FLOAD_2;
                    case 3: return FLOAD_3;
                    default:
                        return new LocalVariableInstruction(
                            Constants.FLOAD, index);
                }
            case T_DOUBLE:
                switch(index) {
                    case 0: return DLOAD_0;
                    case 1: return DLOAD_1;
                    case 2: return DLOAD_2;
                    case 3: return DLOAD_3;
                    default:
                        return new LocalVariableInstruction(Constants.DLOAD, index);
                }
            case T_OBJECT:
                switch(index) {
                    case 0: return ALOAD_0;
                    case 1: return ALOAD_1;
                    case 2: return ALOAD_2;
                    case 3: return ALOAD_3;
                    default:
                        return new LocalVariableInstruction(Constants.AALOAD, index);
                }
            default:
                throw new IllegalArgumentException(
                    "The type provided is invalid: type=" + type);
        }
    }

    /**
     * @param opcode
     * @param target
     *
     * @return
     */
    public static JumpInstruction createJump(byte opcode, int target) {
        return new JumpInstruction(opcode, target);
    }

    public SwitchInstruction createSwitch(byte opcode,
                                          InstructionHandle defaultTarget,
                                          TreeMap<Integer, InstructionHandle> targets) {
        return null;
    }
}
