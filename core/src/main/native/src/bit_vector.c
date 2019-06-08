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

#include "bit_vector.h"

#include <globals.h>

struct BitVector *BitVector_new() {
    return calloc_or_die(1, sizeof(struct BitVector));
}

void BitVector_ctor(struct BitVector *this, int size) {
    this->size = size;
    this->vector = calloc_or_die(
        (size + 7) / sizeof(uint8_t), sizeof(uint8_t));
}

void BitVector_delete(struct BitVector *this) {
    free(this->vector);
    free(this);
}

int BitVector_length(struct BitVector *this) {
    return this->size * sizeof(uint8_t);
}

#define REAL_SIZE(size) \
    ((size + 7) / sizeof(uint8_t))

/*!
 * \param this
 * \param index
 */
static void BitVector_ensure_capacity(struct BitVector *this, unsigned index) {
    if(unlikely(index >= BitVector_length(this))) {
        const int old_size = this->size;
        const int new_size = REAL_SIZE(index);
        this->size = new_size;
        this->vector = realloc_or_die(this->vector, (size_t) new_size);
        memset(((char *) this->vector) + old_size, 0, new_size - old_size);
    }
}

/*!
 * \brief
 */
#define BIT_INDEX(index) \
    (index / sizeof(uint8_t))

/*!
 * \brief
 */
#define BIT_MASK(index) \
    (0x1u << (index & (sizeof(uint8_t) - 1)))

bool BitVector_test(struct BitVector *this, unsigned index) {
    BitVector_ensure_capacity(this, index);
    return this->vector[BIT_INDEX(index)] & BIT_MASK(index);
}

void BitVector_set(struct BitVector *this, unsigned index) {
    BitVector_ensure_capacity(this, index);
    this->vector[BIT_INDEX(index)] |= BIT_MASK(index);
}

void BitVector_clear(struct BitVector *this, unsigned index) {
    BitVector_ensure_capacity(this, index);
    this->vector[BIT_INDEX(index)] &= ~BIT_MASK(index);
}

void BitVector_and(struct BitVector *this, struct BitVector *other) {
    const int this_size = REAL_SIZE(this->size);
    const int other_size = REAL_SIZE(other->size);
    const int min = MIN(this_size, other_size);
    for(int i = 0; i < min; i++)
        this->vector[i] &= other->vector[i];

    if(this_size > other_size)
        memset(&this->vector[min], 0, this_size - other_size);
}

void BitVector_zero(struct BitVector *this) {
    memset(this->vector, 0, REAL_SIZE(this->size));
}

struct BitVector *BitVector_copy(struct BitVector *this) {
    struct BitVector *copy = malloc_or_die(sizeof(struct BitVector));
    memcpy(copy->vector, this->vector, REAL_SIZE(this->size));
    copy->size = this->size;
    return copy;
}
