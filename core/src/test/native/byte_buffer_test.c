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

int main() {
    struct ByteBuffer *buffer = ByteBuffer_new();
    assert(!ByteBuffer_length(buffer));
    for (int i = 0; i < BYTE_BUFFER_DEFAULT_SIZE * 3; i++) {
        assert(ByteBuffer_length(buffer) == i);
        ByteBuffer_write(buffer, (char) i);
    }

    for (int i = 0; i < BYTE_BUFFER_DEFAULT_SIZE * 3; i++) {
        char x;
        assert(!ByteBuffer_get(buffer, &x, i));
        assert((char) i == x);
    }

    {
        ByteBuffer_write_word(buffer, (int16_t) 0xff00);
        ByteBuffer_write_dword(buffer, 0xaabbccdd);
        ByteBuffer_write_qword(buffer, 0xee99112233445566);

        char x;
        assert(!ByteBuffer_get(buffer, &x, ByteBuffer_length(buffer) - 1));
        assert(x == (char) 0xee);
    }

    ByteBuffer_rewind(buffer);
    for(int i = 0; i < 20; i++) {
        assert(ByteBuffer_length(buffer) == i);
        ByteBuffer_write(buffer, (char) (i * 7));
    }

    char *mem = malloc_or_die(sizeof(char) * 20);
    assert(!ByteBuffer_copy(buffer, mem, 20));

    for(int i = 0; i < 20; i++) {
        char x;
        assert(!ByteBuffer_get(buffer, &x, i));
        assert((char) (i * 7) == x);
    }

    ByteBuffer_delete(buffer);
}
