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

#include "globals.h"

// /*!
//  * \brief
//  */
// struct LinkedListEntry {
//     struct LinkedListEntry *prev,
//                            *next;
//     void *value;
// };
//
// /*!
//  * \brief
//  */
// struct LinkedList {
//     const struct LinkedListInfo *info;
//
//     struct LinkedListEntry *first,
//                            *last;
//     int size;
// };
//
// struct LinkedList *LinkedList_new() {
//     return malloc_or_die(sizeof(struct LinkedList));
// }
//
// /*!
//  * \param a
//  * \param b
//  *
//  * \return Whether or not the two parameters are equal.
//  */
// bool equals_entry(void *a, void *b) {
//     return a == b;
// }
//
// void LinkedList_ctor(struct LinkedList *this) {
//     static struct LinkedListInfo info = {
//         .delete_entry = &free,
//         .equals_entry = &equals_entry
//     };
//     LinkedList_ctor_info(this, &info);
// }
//
// void LinkedList_ctor_info(struct LinkedList *this, struct LinkedListInfo *info) {
//     this->info = info;
//     this->last = this->first = NULL;
//     this->size = 0;
// }
//
// void LinkedList_delete(struct LinkedList *this) {
//     const struct LinkedListInfo *info = this->info;
//     if(info->delete_entry) {
//         LINKED_LIST_FOREACH(node, this, {
//             info->delete_entry(node);
//         })
//     }
//     free(this);
// }
//
// int LinkedList_size(struct LinkedList *this) {
//     return this->size;
// }
//
// void LinkedListIterator_delete(struct LinkedListIterator *this) {
//     free(this);
// }
//
// void* LinkedListIterator_next(struct LinkedListIterator *this) {
//     return LinkedListIterator_has_next(this) ?
//         this->direction == LINKED_LIST_TAIL ?
//            (this->next = this->next->next) :
//            (this->next = this->next->prev) :
//         NULL;
// }
//
// bool LinkedListIterator_has_next(struct LinkedListIterator *this) {
//     return this->direction == LINKED_LIST_TAIL ?
//         (this->next->next != NULL) :
//         (this->next->prev != NULL);
// }
//
// struct LinkedListIterator *LinkedList_iterator(struct LinkedList *this) {
//     struct LinkedListIterator *iterator = malloc_or_die(sizeof(struct LinkedListIterator));
//     iterator->direction = direction;
//     iterator->next = this->first;
// }
