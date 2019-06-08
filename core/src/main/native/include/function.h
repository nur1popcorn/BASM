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
#ifndef FUNCTION_H
#define FUNCTION_H

#include <linked_list.h>

struct LocalVariableTable {
    int local_count;
    struct LocalVariable {
        int start,
            length;
        char type;
        int index;
    } locals_table[];
};

/*!
 * \brief
 */
struct Function {
    struct LinkedList *instructions;
    struct LocalVariableTable *locals;
};

void Function_delete(struct Function *function);

void* Function_compile(struct Function *this);

#endif /* FUNCTION_H */
