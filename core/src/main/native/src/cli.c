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

#include "cli.h"

#include "globals.h"

#include <string.h>
#include <getopt.h>

static void print_help(int argc, char **argv) {
    puts("Usage: ...=--<option>,<parameter>,...\n"
         "    -h --help    Prints this usage message.\n"
         "    -s --halt    Halts the program at the start of execution, the execution may then be manually resumed by the debugger.\n"
    );
}

static void halt(int argc, char **argv) {
    puts("halt!");
}

static char **arg_split(char *str, char delim, int *size) {
    int arg_count = 1;
    for(char *c = str; *c != '\0'; c++)
        if(*c == delim)
            arg_count++;
    char **strs = malloc_or_die(sizeof(char *) * (*size = arg_count + 1));

    strs[0] = "";
    strs[1] = str;
    for(int i = 2; (str = strchr(str, delim)) != NULL; i++) {
        *str = '\0';
        strs[i] = ++str;
    }
    return strs;
}

void parse_options_or_die(char *options) {
    int argc;
    char **argv = arg_split(options = strdup_or_die(options), ',', &argc);

    static struct option long_options[] =
        { { "help", no_argument, NULL, 'h' },
          { "halt", no_argument, NULL, 's' },
          { 0, 0, 0, 0 }
        };

    /* a ghetto hash table implementation follows. */
    static void (*handlers[0x10])(int argc, char **argv) =
        { NULL };
    {
        handlers['h' & 0xf] = &print_help;
        handlers['s' & 0xf] = &halt;
    }

    int c, opt_index = 0;
    while((c = getopt_long(argc, argv, "hs", long_options, &opt_index)) != -1) {
        if(c == '?')
            abort();
        handlers[c & 0xf](argc, argv);
    }

    free(argv);
    free(options);
}
