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

#ifndef CU_TEST_H
#define CU_TEST_H

#include <stddef.h>

typedef void (*cu_test_func)(void);

struct CUTestInfo {
    char *test_name;
    cu_test_func test;
};

#define CU_TEST(name)                            \
    static void name_test(void);                 \
    static struct CUTestInfo name_info = {       \
        .test_name = "name",                     \
        .test = &name_test                       \
    };                                           \
    static struct CUTestInfo *name = &name_info; \
    static void name_test(void)

struct CUTestSuite {
    cu_test_func setup;
    cu_test_func teardown;

    struct CUTestInfo *tests[];
};

#define CU_TEST_SUITE_START(name) \
    struct CUTestSuite name = {

#define CU_TEST_SUITE_END \
    };

#define CU_TEST_SUITE_ADD(...) \
    .tests = {                 \
        __VA_ARGS__,           \
        NULL,                  \
    },

#define CU_TEST_SUITE_TEARDOWN(name) \
    .teardown = &name,

#define CU_TEST_SUITE_SETUP(name) \
    .setup = &name,

#define CU_MAIN(...)                         \
    int main() {                             \
        struct CUTestSuite test_suites[] = { \
            __VAR_ARGS__,                    \
            NULL                             \
        };                                   \
    }

/* Begin Asserts */



/* End Asserts */

#endif /* CU_TEST_H */
