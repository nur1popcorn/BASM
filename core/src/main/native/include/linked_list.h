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

#ifndef LINKED_LIST_H
#define LINKED_LIST_H

#include <stdbool.h>

/*!
 * \brief
 */
struct LinkedListInfo {
    /* Template functions. */
    void (*delete_entry) (void *entry);
    bool (*equals_entry) (void *a, void *b);
};

/*!
 * \brief
 */
struct LinkedList;

/*!
 * \return
 */
struct LinkedList *LinkedList_new();

/*!
 * \param this
 * \param info
 */
void LinkedList_ctor(struct LinkedList *this);

/*!
 * \param this
 * \param info
 */
void LinkedList_ctor_info(struct LinkedList *this, struct LinkedListInfo *info);

/*!
 * \param this
 */
void LinkedList_delete(struct LinkedList *this);

/*!
 * \param this
 * \return
 */
int LinkedList_size(struct LinkedList *this);

/*!
 * Inserts the given element at the end of the linked list.
 *
 * \param this
 * \param element
 */
void LinkedList_add(struct LinkedList *this, void *element);

/*!
 * Removes the given element from the linked list.
 */
void LinkedList_remove(struct LinkedList *this, void *element);

/*!
 * \param this
 * \param element
 *
 * \return True if the element is contained within the list false otherwise.
 */
bool LinkedList_contains(struct LinkedList *this, void *element);

#endif /* LINKED_LIST_H */
