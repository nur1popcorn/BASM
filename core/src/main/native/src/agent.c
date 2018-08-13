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

#include <string.h>
#include <getopt.h>

static void print_help() {
    puts("Usage: ...=--<option>,<parameter>\n"
         "   --help      Prints this usage message.\n"
         "   -h --halt   Halts the program at the start of execution, the execution may then be manually resumed by the debugger.\n"
        );
}

static void halt() {
    gdata->flags |= HALT_FLAG;
}

static int parse_options(char *options) {
    int argc = 0;
    for(char *c = options; *c != '\0'; c++)
        if(*c == ',')
            argc++;
    char *argv[argc];
    argc = 0;
    for(char *tok = strtok(options, ","); tok; tok = strtok(NULL, ","))
        argv[argc++] = tok;

    static struct option long_options[] =
        { { "help", no_argument, NULL,  0 },
          { "halt", no_argument, NULL, 'h'}
        };

    static void (*handlers[])() =
        { &print_help,
          &halt
        };

    for(int c, opt_index; (c = getopt_long(argc, argv, "h", long_options, &opt_index)); )
        if(c != '?' &&
           c != ':')
            handlers[opt_index]();
}

static void JNICALL vm_init(jvmtiEnv *jvmti_env, JNIEnv *jni_env, jthread thread) {

}

/*!
 * \brief
 *
 * \param vm
 * \param options
 */
static jint global_init(JavaVM *vm, char *options) {
    if(unlikely(gdata != NULL && gdata->loaded)) {
        perror("The JVMTI agent is already loaded on this JVM and will be disabled.\n");
        return JNI_ERR;
    }

    static struct GlobalData data;
    gdata = &data;

    gdata->loaded = JNI_TRUE;
    gdata->jvm = vm;

    parse_options(options);

    jint res = (*vm)->GetEnv(vm, (void**)&gdata->jvmti, JVMTI_VERSION);
    if(unlikely(res)) {
        fprintf(stderr,
            "Unable to access JVMTI version 0x%x, "
            "GetEnv() returned %d\n",
                JVMTI_VERSION, res);
        return JNI_ERR;
    }

    static jvmtiCapabilities capabilities = {
        .can_get_bytecodes =              JNI_TRUE,
        .can_generate_breakpoint_events = JNI_TRUE,
        .can_redefine_any_class =         JNI_TRUE,
    };

    jvmtiError error = (*gdata->jvmti)->AddCapabilities(gdata->jvmti, &capabilities);
    if(unlikely(error != JVMTI_ERROR_NONE)) {
        char *error_str = NULL;
        (*gdata->jvmti)->GetErrorName(gdata->jvmti, error, &error_str);
        fprintf(stderr,
            "A JVMTI error with error occurred while trying to request certain capabilities, "
            "AddCapabilities() returned %d \"%s\".\n",
                error, error_str == NULL ?
                    "Unknown" :
                    error_str);
        return JNI_ERR;
    }

    static jvmtiEventCallbacks callbacks = {
        .VMInit = &vm_init
    };

    error = (*gdata->jvmti)->SetEventCallbacks(gdata->jvmti, &callbacks, sizeof(callbacks));
    if(unlikely(error != JVMTI_ERROR_NONE)) {
        char *error_str = NULL;
        (*gdata->jvmti)->GetErrorName(gdata->jvmti, error, &error_str);
        fprintf(stderr,
            "A JVMTI error with error occurred while trying to register the callbacks, "
            "SetEventCallbacks() returned %d \"%s\".\n",
                error, error_str == NULL ?
                   "Unknown" :
                   error_str);
        return JNI_ERR;
    }

    return JNI_OK;
}

/*!
 *
 */
JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM *vm, char *options, void *reserved) {
    return global_init(vm, options);
}

/*!
 *
 */
JNIEXPORT jint JNICALL Agent_OnAttach(JavaVM *vm, char *options, void *reserved) {
    return global_init(vm, options);
}

/*!
 *
 */
JNIEXPORT void JNICALL Agent_OnUnload(JavaVM *vm) {
    //TODO: fill
}
