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

#include "globals.h"

struct GlobalData *gdata = NULL;

char **str_split(char *str, char delim, int *size) {
    if(!*str) {
        *size = 0;
        return NULL;
    }

    int word_count = 1;
    for(char *c = str; *c != '\0'; c++)
        if(*c == delim)
            word_count++;
    char **strs = malloc_or_die(sizeof(char *) * (*size = word_count));

    strs[0] = str;
    for(int i = 1; (str = strchr(str, delim)) != NULL; i++) {
        *str = '\0';
        strs[i] = ++str;
    }
    return strs;
}
