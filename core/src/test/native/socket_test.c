#include "socket.h"

#include <assert.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

void* send_message(void *ignored) {
    Socket sock;
    {
        sleep(1);
        assert(!Socket_create(&sock, "127.0.0.1", "26969"));
        char buffer[255] = "Where keep rock ?\n";
        assert(!Socket_send(sock, buffer, 255));
        assert(!Socket_close(sock));
    }

    /*{
        sleep(1);
        assert(!Socket_create(&sock, "::1", "26969"));
        char buffer[255] = "Where keep rock ?\n";
        assert(!Socket_send(sock, buffer, 255));
        assert(!Socket_close(sock));
    }*/

    return NULL;
}

int main() {
    Socket sock;
    assert(!Socket_server_create(&sock, "26969"));
    assert(!Socket_listen(sock, 5));

    pthread_t thread;
    assert(!pthread_create(&thread, NULL, &send_message, NULL));

    for(int i = 0; i < 1; i++) {
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
