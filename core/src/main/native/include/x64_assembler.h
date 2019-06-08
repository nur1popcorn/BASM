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

#ifndef X64_ASSEMBLER_H
#define X64_ASSEMBLER_H

#include "assembler.h"
#include "allocation_algorithm.h"

#define X64_REGISTER_ENUM(o)   \
/*                          *  \
 *                          *  \
 *                          */ \
    o(rax, "rax", 0x1, ..)

struct X64Assembler {
//     struct Assembler super;

};

/*!
 * \brief
 */
enum Register {
    rax, rcx, rdx, rbx,
    rsp, rbp, rsi, rdi,
    r8,  r9,  r10, r11,
    r12, r13, r14, r15
};

/*!
 * \brief
 */
struct Assembler *Assembler_new();

/*!
 * \param this
 */
static inline void Assembler_delete(struct Assembler *this) {
    //ByteBuffer_delete(&this->super);
}

/*!
 * \param this
 * \param src
 */
void Assembler_pushq_reg(struct Assembler *this, enum Register src);

#endif /* X64_ASSEMBLER_H */
