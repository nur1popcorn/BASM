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
#include "cli.h"

static void JNICALL vm_init(jvmtiEnv *jvmti_env, JNIEnv *jni_env, jthread thread) {
}

/*!
 *
 */
JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM *vm, char *options, void *reserved) {
    /*if(is_running) {
        fprintf(stderr,
            "The JVMTI agent is already loaded on this JVM, "
            " therefore this instance will be disabled.\n");
        return JNI_ERR;
    }

    struct cli_options *parsed_options = cli_parse_options(options);

    cli_delete_options(parsed_options);

    {
        jint res = (*vm)->GetEnv(vm, (void**)&gdata->jvmti, JVMTI_VERSION);
        if(unlikely(res)) {
            fprintf(stderr,
                "Unable to access JVMTI version 0x%x.\n"
                "GetEnv() returned %d\n",
                    JVMTI_VERSION, res);
            return JNI_ERR;
        }
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
            "A JVMTI error with error occurred while trying to request certain capabilities.\n"
            "AddCapabilities() returned %d \"%s\".\n",
                error, error_str == NULL ?
                    "Unknown" :
                    error_str
               );
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
            "A JVMTI error with error occurred while trying to register the callbacks.\n"
            "SetEventCallbacks() returned %d \"%s\".\n",
                error, error_str == NULL ?
                    "Unknown" :
                    error_str
               );
        return JNI_ERR;
    }*/

    return JNI_OK;
}

/*!
 *
 */
JNIEXPORT jint JNICALL Agent_OnAttach(JavaVM *vm, char *options, void *reserved) {
    return Agent_OnLoad(vm, options, reserved);
}

/*!
 *
 */
JNIEXPORT void JNICALL Agent_OnUnload(JavaVM *vm) {
}
