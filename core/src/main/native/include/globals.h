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

#ifndef GLOBALS_H
#define GLOBALS_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stddef.h>

#include <jvmti.h>

#ifdef __GNUC__
    #define likely(x) __builtin_expect((x), 1)
    #define unlikely(x) __builtin_expect((x), 0)
#else
    #define likely(x) (x)
    #define unlikely(x) (x)
#endif

/*!
 * \brief
 */
#define GET_CHILD_PTR(                 \
         child_class,                  \
         super_class,                  \
         super_ptr                     \
        )                              \
    (                                  \
     (child_class *)                   \
     (((char *) super_ptr)             \
        - offsetof(super_class, super) \
     )                                 \
    )

/*!
 * \brief performs a xor swap between the two parameters. <i>Note: </i> that this swap only works
 *        if both parameters hold a different value.
 */
#define SWAP_XOR(a, b) \
    do {               \
        a ^= b;        \
        b ^= a;        \
        a ^= b;        \
    } while(0)

/**
 * \brief
 */
#define MIN(a, b) \
    (a < b ? a : b)

/**
 * \brief
 */
#define MAX(a, b) \
    (a > b ? a : b)

/*!
 * \brief
 */
#define ARRAY_LENGTH(array)             \
    (                                   \
     sizeof(array) / sizeof((array)[0]) \
    )

/*!
 * \brief
 *
 * \param str
 * \param delim
 * \param size
 *
 * \return
 */
char **str_split(char *str, char delim, int *size);

/*!
 * \brief
 *
 * \param size
 * \return
 */
static inline void *malloc_or_die(size_t size) {
    void *mem = malloc(size);
    if(unlikely(!mem)) {
        perror("Could not allocate memory!\n");
        abort();
    }
    return mem;
}

/*!
 * \brief
 *
 * \param meb
 * \param size
 *
 * \return
 */
static inline void *calloc_or_die(size_t meb, size_t size) {
    void *mem = calloc(meb, size);
    if(unlikely(!mem)) {
        perror("Could not allocate memory!\n");
        abort();
    }
    return mem;
}

/*!
 * \brief
 *
 * \param ptr
 * \param size
 *
 * \return
 */
static inline void *realloc_or_die(void *ptr, size_t size) {
    void *mem = realloc(ptr, size);
    if(unlikely(!mem)) {
        perror("Could not reallocate memory!\n");
        abort();
    }
    return mem;
}

/*!
 * \brief
 *
 * \param s
 * \return
 */
static inline char *strdup_or_die(const char *s) {
    void *dup = strdup(s);
    if(unlikely(!dup)) {
        perror("Could not allocate memory!\n");
        abort();
    }
    return dup;
}

#endif /* GLOBALS_H */
