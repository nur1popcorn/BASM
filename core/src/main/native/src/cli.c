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

static void print_help() {
    puts("Usage: ...=--<option>=<parameter>,...\n"
         "    -h --help         Prints this usage message.\n"
         "    -b --bind <port>  Binds the native agent to any given port.\n"
    );
}

static void bind_port() {
    printf("Binding port: %s\n", optarg);
}

static char **arg_split(char *str, int *size) {
    int arg_count = 2;
    for(char *c = str; *c != '\0'; c++) {
        const char character = *c;
        if(character == ',' ||
           character == '=')
            arg_count++;
    }
    char **strs = malloc_or_die(sizeof(char *) * (*size = arg_count));

    strs[0] = "";
    strs[1] = str;
    for(int i = 2; (str = strpbrk(str, ",=")) != NULL; i++) {
        *str = '\0';
        strs[i] = ++str;
    }
    return strs;
}

void parse_options_or_die(char *options) {
    optind = 1;

    int argc;
    char **argv = arg_split(options = strdup_or_die(options), &argc);

    static struct option long_options[] =
        { { "help", no_argument, NULL, 'h' },
          { "bind", required_argument, NULL, 'b' },
          { 0, 0, 0, 0 }
        };

    /* a ghetto hash table implementation follows. */
    static void (*handlers[0x10])() =
        { NULL };
    {
        handlers['h' & 0xf] = &print_help;
        handlers['b' & 0xf] = &bind_port;
    }

    int c, opt_index = 0;
    while((c = getopt_long(argc, argv, "hb:", long_options, &opt_index)) != -1) {
        if(c == '?')
            abort();
        handlers[c & 0xf]();
    }

    free(argv);
    free(options);
}
