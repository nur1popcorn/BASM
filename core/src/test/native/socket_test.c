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

#include "socket.h"

#include <assert.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

void* send_message(void *ignored) {
    Socket sock;
    
    {
        sleep(1);
        assert(!Socket_create(&sock, "localhost", "26969"));
        char buffer[255] = "Where keep rock ?\n";
        assert(!Socket_send(sock, buffer, 255));
        assert(!Socket_close(sock));
    }

    return NULL;
}

int main() {
    Socket sock;
    assert(!Socket_server_create(&sock, "26969"));
    assert(!Socket_listen(sock, 5));

    pthread_t thread;
    assert(!pthread_create(&thread, NULL, &send_message, NULL));

    {
        Socket new;
        assert(!Socket_accept(sock, &new, NULL));

        char buffer[256];
        assert(!Socket_recv(new, buffer, 255, NULL));
        printf("%s", buffer);

        assert(!Socket_close(new));
    }
    assert(!Socket_close(sock));

    assert(!pthread_join(thread, NULL));
}
