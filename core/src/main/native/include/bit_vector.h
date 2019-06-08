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

#ifndef BIT_VECTOR_H
#define BIT_VECTOR_H

#include <stdint.h>
#include <stdbool.h>

/*!
 * \brief
 */
struct BitVector {
    int size;
    uint8_t *vector;
};

/*!
 * \param size
 * \return
 */
struct BitVector *BitVector_new();

/*!
 * \param this
 * \param size
 */
void BitVector_ctor(struct BitVector *this, int size);

/*!
 * \param this
 */
void BitVector_delete(struct BitVector *this);

/*!
 * \param this
 * \return
 */
int BitVector_length(struct BitVector *this);

/*!
 * \param this
 * \param index
 *
 * \return
 */
bool BitVector_test(struct BitVector *this, unsigned index);

/*!
 * \param this
 * \param index
 */
void BitVector_set(struct BitVector *this, unsigned index);

/*!
 * \param this
 * \param index
 */
void BitVector_clear(struct BitVector *this, unsigned index);

/*!
 * \param this
 * \param other
 */
void BitVector_and(struct BitVector *this, struct BitVector *other);

/*!
 * \param this
 */
void BitVector_zero(struct BitVector *this);

/*!
 * \param this
 * \return
 */
struct BitVector *BitVector_copy(struct BitVector *this);

#endif /* BIT_VECTOR_H */
