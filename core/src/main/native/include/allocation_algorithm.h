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

#ifndef ALLOCATION_ALGORITHM_H
#define ALLOCATION_ALGORITHM_H

#include "function.h"

/*!
 * \brief
 */
struct AllocationInfo {
    int register_count;
    int registers[];
};

/*!
 * \brief
 */
struct AllocationTable {
    int allocation_count;
    struct Allocation {
        int index,
            reg;
    } *allocation_table;
};

void AllocationTable_delete(struct AllocationTable *table);

/*!
 * \brief
 *
 * \param info
 * \return
 */
struct AllocationTable *AllocationAlgorithm_allocate_registers(struct AllocationInfo *info, struct Function *function);

#endif /* ALLOCATION_ALGORITHM_H */
