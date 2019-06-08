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

#include "byte_buffer.h"

#include <assert.h>

#include "globals.h"

/*!
 * \brief
 *
 * \param this
 * \param size
 */
static void ByteBuffer_ensure_capacity(struct ByteBuffer *this, int size) {
    const int length = ByteBuffer_length(this);
    if((length + size) > this->size) {
        this->size *= 2;
        this->buffer = realloc_or_die(this->buffer, (size_t) this->size);
        this->current = this->buffer + length;
    }
}

/*!
 * \param this
 */
static void ctor(struct ByteBuffer *this) {
    this->size = BYTE_BUFFER_DEFAULT_SIZE;
    this->current = this->buffer =
        malloc_or_die(sizeof(char) * BYTE_BUFFER_DEFAULT_SIZE);
}

/*!
 * \param this
 */
static void dtor(struct ByteBuffer *this) {}

/*!
 * \param this
 */
static void delete(struct ByteBuffer *this) {
    free(this->buffer);
    free(this);
}

/*!
 * \param this
 * \param size
 */
static void write_word(struct ByteBuffer *this, int16_t x) {
    ByteBuffer_ensure_capacity(this, sizeof(x));
    *this->current++ = (char) x;
    *this->current++ = (char) (x >> 8);
}

/*!
 * \param this
 * \param size
 */
static void write_dword(struct ByteBuffer *this, int32_t x) {
    ByteBuffer_ensure_capacity(this, sizeof(x));
    *this->current++ = (char) x;
    *this->current++ = (char) (x >> 8);
    *this->current++ = (char) (x >> 16);
    *this->current++ = (char) (x >> 24);
}

/*!
 * \param this
 * \param size
 */
static void write_qword(struct ByteBuffer *this, int64_t x) {
    ByteBuffer_ensure_capacity(this, sizeof(x));
    *this->current++ = (char) x;
    *this->current++ = (char) (x >> 8);
    *this->current++ = (char) (x >> 16);
    *this->current++ = (char) (x >> 24);
    *this->current++ = (char) (x >> 32);
    *this->current++ = (char) (x >> 40);
    *this->current++ = (char) (x >> 48);
    *this->current++ = (char) (x >> 56);
}

const struct ByteBufferVTable
    little_endian_vtable = {
        .ctor = &ctor,
        .dtor = &dtor,
        .delete = &delete,

        .write_word = &write_word,
        .write_dword = &write_dword,
        .write_qword = &write_qword
    };

struct ByteBuffer *ByteBuffer_new() {
    struct ByteBuffer *this = malloc_or_die(sizeof(struct ByteBuffer));
    this->vtable = &little_endian_vtable;
    return this;
}

void ByteBuffer_write(struct ByteBuffer *this, char x) {
    ByteBuffer_ensure_capacity(this, sizeof(x));
    *this->current++ = x;
}

enum ByteBufferResult ByteBuffer_get(struct ByteBuffer *this, char *x, int index) {
    if(index < 0 || index >= ByteBuffer_length(this))
        return BYTE_BUFFER_ERROR_INDEX_OUT_OF_BOUNDS;
    *x = this->buffer[index];
    return BYTE_BUFFER_OK;
}

int ByteBuffer_length(struct ByteBuffer *this) {
    return (int) (this->current - this->buffer);
}

void ByteBuffer_rewind(struct ByteBuffer *this) {
    this->current = this->buffer;
}

enum ByteBufferResult ByteBuffer_copy(struct ByteBuffer *this, void *dest, int size) {
    if(size < 0 || ByteBuffer_length(this) > size)
        return BYTE_BUFFER_ERROR_COPY;
    memcpy(dest, this->buffer, (size_t) size);
    return BYTE_BUFFER_OK;
}

char *ByteBuffer_error(enum ByteBufferResult result) {
    static char *error_codes[] =
    { "Everything is OK, no error occurred.\n",
      "An out of bounds error occurred.\n",
      "An error occurred while copying data from the buffer." };
    assert(result >= 0 && result < ARRAY_LENGTH(error_codes));
    return error_codes[result];
}
