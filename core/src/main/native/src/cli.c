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

typedef void (*cli_handler)(struct cli_options *options);

/*!
 * \param options
 */
static void print_help(struct cli_options *options) {
    puts("Usage: ...=--<option>=<parameter>,...\n"
         "    -h --help         Prints this usage message.\n"
         "    -b --bind <port>  Binds the native agent to any given port.\n"
    );
}

/*!
 * \param options
 */
static void parse_port(struct cli_options *options) {
    char *temp;
    const long port = strtol(optarg, &temp, 10);
    if(!*temp ||
       port < 0 || port > 65535) {
        fprintf(stderr,
            "Invalid port range (0 - 65535).");
        return;
    }
    options->port = strdup_or_die(optarg);
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

struct cli_options *cli_options_parse(char *options) {
    optind = 1;

    int argc;
    char **argv = arg_split(options = strdup_or_die(options), &argc);

    static struct option long_options[] =
        { { "help", no_argument, NULL, 'h' },
          { "bind", required_argument, NULL, 'b' },
          { 0, 0, 0, 0 }
        };

    /* a ghetto hash table implementation follows. */
    static cli_handler handlers[0x10]; {
        handlers['h' & 0xf] = &print_help;
        handlers['b' & 0xf] = &parse_port;
    }

    struct cli_options *parsed_options = malloc_or_die(sizeof(struct cli_options));
    memset(parsed_options, 0, sizeof(struct cli_options));

    int c, opt_index = 0;
    while((c = getopt_long(argc, argv, "hb:", long_options, &opt_index)) != -1) {
        if(c == '?')
            abort();
        handlers[c & 0xf](parsed_options);
    }

    free(argv);
    free(options);

    return parsed_options;
}

void cli_options_delete(struct cli_options *this) {
    if(this->port)
        free(this->port);
    free(this);
}
