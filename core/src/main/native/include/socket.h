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

#ifndef SOCKET_H
#define SOCKET_H

#include <stdbool.h>

#ifdef __linux__
    typedef int Socket;
#endif

struct SocketInfo {
    bool ipv6;
    char *address,
         *port;
};

enum SocketResult {
    SOCKET_OK = 0,
    SOCKET_ERROR_CREATE = 1,
    SOCKET_ERROR_BIND = 2,
    SOCKET_ERROR_CONNECT = 3,
    SOCKET_ERROR_CLOSE = 4,
    SOCKET_ERROR_LISTEN = 5,
    SOCKET_ERROR_ACCEPT = 6,
    SOCKET_ERROR_RECV = 7,
    SOCKET_ERROR_SEND = 8
};

/*!
 *
 */
enum SocketResult Socket_create(Socket *sock, char *address, char *port);

/*!
 *
 */
enum SocketResult Socket_server_create(Socket *sock, char *port);

/*!
 *
 */
enum SocketResult Socket_close(Socket sock);

/*!
 * \param backlog The number of connections the socket will be able to handle.
 */
enum SocketResult Socket_listen(Socket sock, int backlog);

/*!
 *
 */
enum SocketResult Socket_accept(Socket sock, Socket *new_sock, struct SocketInfo *info);

/*!
 *
 */
enum SocketResult Socket_recv(Socket socket, char *message, int length, int *received);

/*!
 *
 */
enum SocketResult Socket_send(Socket sock, char *message, int length);

/*!
 *
 */
char *Socket_error(enum SocketResult result);

#endif /* SOCKET_H */
