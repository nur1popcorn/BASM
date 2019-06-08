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

#ifndef ASSEMBLER_H
#define ASSEMBLER_H

#include <stdint.h>

/*!
 * \brief
 */
#define INSTRUCTION_ENUM(o)                                           \
/*   <----------+-----------------+----+-----+--------+-------->   *  \
 *              |                 |    |     |        |            *  \
 *     Mnemonic | Mnemonic Format | Op | +Op | +Stack | -Stack     *  \
 *              |                 |    |     |        |            *  \
 *   <----------+-----------------+----+-----+--------+-------->   */ \
    o(nop,       "nop",            0x0, 0x0,  0x0,     0x0)           \
    o(ipush,     "ipush %1",       0x1, 0x1,  0x1,     0x0)           \
    o(pop,       "pop",            0x2, 0x0,  0x0,     0x1)           \
    o(iload,     "iload %i",       0x3, 0x1,  0x1,     0x0)           \
    o(istore,    "istore %i",      0x4, 0x1,  0x0,     0x1)           \
    o(iadd,      "iadd",           0x5, 0x0,  0x1,     0x2)           \
    o(iinc,      "iinc %i",        0x6, 0x1,  0x1,     0x1)           \
    o(icmp,      "icmp",           0x7, 0x0,  0x1,     0x2)           \
    o(jmp,       "jmp %i",         0x8, 0x1,  0x0,     0x0)           \
    o(ret,       "ret",            0x9, 0x0,  0x0,     0x0)           \
    o(iret,      "iret",           0xa, 0x0,  0x0,     0x1)

/*!
 * \brief
 */
enum Opcode {
    #define o(a, b, c, d, e, f) \
        a = c,
    INSTRUCTION_ENUM(o)
    #undef o
};

/*!
 * \brief
 */
extern const struct OpcodeInfo {
    char *code_format;
    int16_t parameters    : 6;
    int16_t stack_add_mod : 5;
    int16_t stack_sub_mod : 5;
} opcode_infos[];

/*!
 * \brief
 */
struct FunctionBuilder;

/*!
 * \return
 */
struct FunctionBuilder *FunctionBuilder_make();

/*!
 * \param this
 */
void FunctionBuilder_delete(struct FunctionBuilder *this);

/*!
 * \param this
 * \return
 */
struct Function *FunctionBuilder_build(struct FunctionBuilder *this);

#endif /* ASSEMBLER_H */
