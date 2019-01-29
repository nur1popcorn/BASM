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
#include <string.h>

#include "globals.h"

#ifdef __linux__
    #include <sys/socket.h>
    #include <netinet/in.h>
    #include <unistd.h>
    #include <netdb.h>
    #include <arpa/inet.h>
#endif

enum SocketResult Socket_create(Socket *sock, char *address, char *port) {
#ifdef __linux__
    static struct addrinfo hints = {
        .ai_family = AF_UNSPEC,
        .ai_socktype = SOCK_STREAM
    };

    struct addrinfo *addr, *p;
    if(getaddrinfo(address, port, &hints, &addr))
        return SOCKET_ERROR_CONNECT;

    for(p = addr; p; p = p->ai_next) {
        if((*sock = socket(
            p->ai_family,
            p->ai_socktype,
            p->ai_protocol)) == -1)
            continue;

        /*static const int yes = 1;
        static const struct timeval timeout = {
            .tv_sec = 5
        };
        if(setsockopt(*sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1 ||
           setsockopt(*sock, SOL_SOCKET, SO_SNDTIMEO, &timeout, sizeof(struct timeval)))
            return SOCKET_ERROR_CREATE;*/

        if(connect(*sock, p->ai_addr, p->ai_addrlen) == -1) {
            close(*sock);
            continue;
        }
        break;
    }

    freeaddrinfo(addr);

    if(!p)
        return SOCKET_ERROR_CONNECT;
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_server_create(Socket *sock, char *port) {
#ifdef __linux__
    static struct addrinfo hints = {
        .ai_family = AF_UNSPEC,
        .ai_socktype = SOCK_STREAM,
        .ai_flags = AI_PASSIVE
    };

    struct addrinfo *addr, *p;
    if(getaddrinfo(NULL, port, &hints, &addr))
        return SOCKET_ERROR_BIND;

    for(p = addr; p; p = p->ai_next) {
        if((*sock = socket(
            p->ai_family,
            p->ai_socktype,
            p->ai_protocol)) == -1)
            continue;

        static const int yes = 1;
        if(setsockopt(*sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
            return SOCKET_ERROR_CREATE;

        if(bind(*sock, p->ai_addr, p->ai_addrlen) == -1) {
            close(*sock);
            continue;
        }
        break;
    }

    freeaddrinfo(addr);

    if(!p)
        return SOCKET_ERROR_BIND;
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_close(Socket sock) {
#ifdef __linux__
    if(close(sock))
        return SOCKET_ERROR_CLOSE;
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_listen(Socket sock, int backlog) {
#ifdef __linux__
    if(listen(sock, backlog) == -1)
        return SOCKET_ERROR_LISTEN;
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_accept(Socket sock, Socket *new_sock, struct SocketInfo *info) {
#ifdef __linux__
    struct sockaddr_storage addr;
    socklen_t sin_size = sizeof(struct sockaddr_storage);
    if((*new_sock = accept(sock, (struct sockaddr *) &addr, &sin_size)) == -1)
        return SOCKET_ERROR_ACCEPT;

    if(info)
        switch(addr.ss_family) {
            case AF_INET: {
                struct sockaddr_in *in = (struct sockaddr_in *) &addr;
                info->address = malloc_or_die(INET_ADDRSTRLEN);
                inet_ntop(AF_INET, &in->sin_addr, info->address, sizeof(char *));

                info->port = malloc_or_die(6 * sizeof(char));
                snprintf(info->port, 6, "%u", ntohs(in->sin_port));

                info->ipv6 = 0;
            }   break;
            case AF_INET6: {
                struct sockaddr_in6 *in = (struct sockaddr_in6 *) &addr;

                info->address = malloc_or_die(INET6_ADDRSTRLEN);
                inet_ntop(AF_INET6, &in->sin6_addr, info->address, sizeof(char *));

                info->port = malloc_or_die(6 * sizeof(char));
                snprintf(info->port, 6, "%u", ntohs(in->sin6_port));

                info->ipv6 = 1;
            }   break;
            default:
                return SOCKET_ERROR_ACCEPT;
        }
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_recv(Socket socket, char *message, int length, int *received) {
#ifdef __linux__
    int temp;
    if((temp = recv(socket, message, (size_t) length, 0) == -1))
        return SOCKET_ERROR_RECV;
    if(received)
        *received = temp;
#endif
    return SOCKET_OK;
}

enum SocketResult Socket_send(Socket sock, char *message, int length) {
#ifdef __linux__
    if(send(sock, message, (size_t) length, 0) == -1)
        return SOCKET_ERROR_SEND;
#endif
    return SOCKET_OK;
}

char *Socket_error(enum SocketResult result) {
    static char *error_codes[] =
    { "Everything is OK, no error occurred.",
      "An error occurred while creating the socket.",
      "An error occurred while trying to bind the socket.",
      "An error occurred while trying to connect to the server.",
      "An error occurred while trying to close the socket.",
      "An error occurred while trying to mark the socket as a listener.",
      "An error occurred while trying to accept an incoming socket's connection request.",
      "An error occurred while receiving a message.",
      "An error occurred while sending a message." };
    assert(result >= 0 && result < ARRAY_LENGTH(error_codes));
    return error_codes[result];
}
