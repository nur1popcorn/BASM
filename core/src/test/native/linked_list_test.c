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

#include "linked_list.h"

#include <assert.h>

#include "globals.h"

int main(int argc, char **argv) {
    /*struct LinkedList *list = LinkedList_new();

    int *array = malloc_or_die(sizeof(int) * 100);
    for(int i = 0; i < 100; i++)
        array[i] = i;

    for(int i = 0; i < 100; i++)
        LinkedList_add(list, &array[i]);

    {
        struct LinkedListEntry *entry = list->first;
        for(int i = 0; entry; i++) {
            assert(i == *((int *) entry->value));
            entry = entry->next;
        }

        entry = list->last;
        for(int i = 100; i --> 0; ) {
            assert(i == *((int *) entry->value));
            entry = entry->prev;
        }
    }

    // assert(LinkedList_contains(list, ));

    LinkedList_delete(list);
    free(array);*/
}
