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

#include "x64_assembler.h"

struct AllocationInfo x64_allocation_info =
    { 0x10, { rax, rcx, rdx, rbx,
              rsp, rbp, rsi, rdi,
              r8,  r9,  r10, r11,
              r12, r13, r14, r15 } };

/*!
 * \param this
 * \param x
 */
static inline void Assembler_emit(struct Assembler *this, int x) {
    //ByteBuffer_write(&this->super, (char) x);
}

/*!
 * \brief Emits REX.W 64-bit operand size prefix.
 * \param this
 */
static void Assembler_emit_rex_w(struct Assembler *this) {
    Assembler_emit(this, 0x48);
}

static void ctor(void *this) {

}

struct Assembler *Assembler_new() {
    //struct Assembler *this = malloc_or_die(sizeof(struct Assembler));
    //static struct ByteBufferVTable vtable = {
    //    .ctor = &ctor
    //};
    //ByteBuffer_ctor(&this->super);
    return 0;
}

//void Assembler_delete(struct Assembler *this) {
    //ByteBuffer_delete(this->super);
  //  free(this);
//}

void Assembler_pushq_reg(struct Assembler *this, enum Register src) {
    Assembler_emit(this, 0x50 | src);
}

