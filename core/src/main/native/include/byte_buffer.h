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

#ifndef BYTE_BUFFER_H
#define BYTE_BUFFER_H

#include <stdint.h>

#define BYTE_BUFFER_DEFAULT_SIZE 0x80

enum ByteBufferResult {
    BYTE_BUFFER_OK,
    BYTE_BUFFER_ERROR_INDEX_OUT_OF_BOUNDS,
    BYTE_BUFFER_ERROR_COPY
};

struct ByteBuffer;

/*!
 * \brief Virtual function table for the byte buffer.
 */
extern const struct ByteBufferVTable {
    void (*ctor)        (struct ByteBuffer *this);
    void (*dtor)        (struct ByteBuffer *this);
    void (*delete)      (struct ByteBuffer *this);

    void (*write_word)  (struct ByteBuffer *this, int16_t x);
    void (*write_dword) (struct ByteBuffer *this, int32_t x);
    void (*write_qword) (struct ByteBuffer *this, int64_t x);
} little_endian_vtable;

/*!
 *
 */
struct ByteBuffer {
    const struct ByteBufferVTable *vtable;

    int size;

    char *buffer,
         *current;
};

/*!
 * \return
 */
struct ByteBuffer *ByteBuffer_new();

/*!
 * \param this
 */
static inline void ByteBuffer_ctor(struct ByteBuffer *this) {
    this->vtable->ctor(this);
}

/*!
 * \param this
 */
static inline void ByteBuffer_dtor(struct ByteBuffer *this) {
    this->vtable->dtor(this);
}

/*!
 * \param this
 */
static inline void ByteBuffer_delete(struct ByteBuffer *this) {
    const struct ByteBufferVTable *vtable = this->vtable;
    vtable->dtor(this);
    vtable->delete(this);
}

/*!
 * \param this
 * \param x
 */
static inline void ByteBuffer_write_word(struct ByteBuffer *this, int16_t x) {
    this->vtable->write_word(this, x);
}

/*!
 * \param this
 * \param x
 */
static inline void ByteBuffer_write_dword(struct ByteBuffer *this, int32_t x) {
    this->vtable->write_dword(this, x);
}

/*!
 * \param this
 * \param x
 */
static inline void ByteBuffer_write_qword(struct ByteBuffer *this, int64_t x) {
    this->vtable->write_qword(this, x);
}

/*!
 * \param this
 * \param x
 */
void ByteBuffer_write(struct ByteBuffer *this, char x);

/*!
 * \param this
 * \param x[out]
 * \param index
 *
 * \return
 */
enum ByteBufferResult ByteBuffer_get(struct ByteBuffer *this, char *x, int index);

/*!
 * \param this
 *
 * \return
 */
int ByteBuffer_length(struct ByteBuffer *this);

/*!
 * \param this
 */
void ByteBuffer_rewind(struct ByteBuffer *this);

/*!
 * \param this
 * \param dest
 * \param size
 *
 * \return
 */
enum ByteBufferResult ByteBuffer_copy(struct ByteBuffer *this, void *dest, int size);

/*!
 * \param result
 *
 * \return
 */
char *ByteBuffer_error(enum ByteBufferResult result);

#endif /* BYTE_BUFFER_H */
