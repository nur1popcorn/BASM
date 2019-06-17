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

#include "graph.h"

#include <limits.h>
#include <assert.h>

#include "globals.h"
#include "linked_list.h"

struct Graph *Graph_new(int vertex_count) {
    struct Graph *this = malloc_or_die(sizeof(struct Graph));
    this->vertex_count = vertex_count;
    /* This minimal form of the adj matrix can be derived as follows:
     * m_{adj} = \begin{bmatrix}
     *               x & x & x & x \\
     *               a & x & x & x \\
     *               b & d & x & x \\
     *               c & e & f & x
	 *           \end{bmatrix}
     *
     * The size of the adj matrix can be computed by summing the number of required bits per row.
     * 0 + 1 + 2 + ... (n-1) = \sum_{k=1}^{n-1} k = ((0 + (v - 1)) * v) / 2 = ((v - 1) * v) / 2
     */
    this->adj_matrix = BitVector_new();
    BitVector_ctor(
        this->adj_matrix,
        ((vertex_count - 1) * vertex_count) / 2);
    return this;
}

void Graph_delete(struct Graph *this) {
    free(this);
}

/*!
 * \brief
 */
#define EDGE_INDEX(v, w)            \
    (w > v ?                        \
        ((((w - 1) * w) / 2) + v) : \
        ((((v - 1) * v) / 2) + w)   \
    )

void Graph_add_edge(struct Graph *this, int v, int w) {
    const int index = EDGE_INDEX(v, w);
    if(index < 0 ||
       index >= this->adj_matrix->size)
        return;
    BitVector_set(this->adj_matrix, index);
}

int Graph_is_connected(struct Graph *this, int v, int w) {
    const int index = EDGE_INDEX(v, w);
    if(index < 0 ||
       index >= this->adj_matrix->size)
        return false;
    return BitVector_test(this->adj_matrix, index);
}

/*!
 * \brief
 */
#define EDGE_INDEX_UNSAFE(v, w) \
    ((((v - 1) * v) / 2) + w)

int *Graph_greedy_coloring(struct Graph *this) {
    const int vertex_count = this->vertex_count;

    int *colors = malloc_or_die(vertex_count * sizeof(int));
    memset(colors, -1, vertex_count * sizeof(int));

    struct BitVector *available = BitVector_new();
    BitVector_ctor(available, vertex_count);

    for(int i = 0; i < vertex_count; i++) {
        for(int j = 0; j < i; j++)
            if(BitVector_test(
                this->adj_matrix,
                EDGE_INDEX_UNSAFE(i, j)
               )
              )
                BitVector_set(available, colors[j] + 1);

        for(int j = i + 1; j < vertex_count; j++)
            if(BitVector_test(
                this->adj_matrix,
                EDGE_INDEX_UNSAFE(j, i)
               )
              )
                BitVector_set(available, colors[j] + 1);

        int color = 1;
        while(BitVector_test(available, color))
            color++;
        colors[i] = color - 1;

        BitVector_zero(available);
    }

    BitVector_delete(available);

    return colors;
}

/*!
 * \param this
 * \param v
 *
 * \return
 */
static int Graph_degree_unsafe(struct Graph *this, int v) {
    int degree = 0;
    for(int j = 0; j < v; j++)
        if(BitVector_test(
            this->adj_matrix,
            EDGE_INDEX_UNSAFE(v, j)
           )
          )
            degree++;

    const int vertex_count = this->vertex_count;
    for(int j = v + 1; j < vertex_count; j++)
        if(BitVector_test(
            this->adj_matrix,
            EDGE_INDEX_UNSAFE(j, v)
           )
          )
            degree++;

    return degree;
}

/*!
 * \brief
 */
struct Clique {
    int vertex_count,
        *vertices;
};

/*!
 * \param vertex_count
 * \return
 */
struct Clique *Clique_new(int vertex_count) {
    struct Clique *clique = malloc_or_die(sizeof(struct Clique));
    clique->vertex_count = vertex_count;
    clique->vertices = malloc_or_die(vertex_count * sizeof(int));
    return clique;
}

/*!
 * \param this
 */
void Clique_delete(struct Clique *this) {
    free(this->vertices);
    free(this);
}

/*!
 * \brief
 */
struct MapEntry {
    int vertex,
        degree;
};

/*!
 * \param vertex
 * \param degree
 *
 * \return
 */
struct MapEntry *MapEntry_new(int vertex, int degree) {
    struct MapEntry *this = malloc_or_die(sizeof(struct MapEntry));
    this->vertex = vertex;
    this->degree = degree;
    return this;
}

/*!
 * \param this
 */
void MapEntry_delete(struct MapEntry *this) {
    free(this);
}

/*!
 * \param a
 * \param b
 *
 * \return
 */
static int MapEntry_compare(const void *a, const void *b) {
    return ((struct MapEntry *) a)->degree -
           ((struct MapEntry *) b)->degree;
}

/*!
 * \param this
 * \return
 */
static struct Graph *Graph_copy(struct Graph *this) {
    struct Graph *copy = malloc_or_die(sizeof(struct Graph));
    copy->adj_matrix = BitVector_copy(this->adj_matrix);
    copy->vertex_count = this->vertex_count;
    return copy;
}

/*!
 * \param this
 * \param v
 *
 * \return
 */
static void Graph_neighbours(struct Graph *this, struct BitVector *vector, int vertex) {
    for(int j = 0; j < vertex; j++)
        if(BitVector_test(
            this->adj_matrix,
            EDGE_INDEX_UNSAFE(vertex, j)
           )
          )
            BitVector_set(vector, j);

    const int vertex_count = this->vertex_count;
    for(int j = vertex + 1; j < vertex_count; j++)
        if(BitVector_test(
            this->adj_matrix,
            EDGE_INDEX_UNSAFE(j, vertex)
           )
          )
            BitVector_set(vector, j);
}

/*!
 * Collects the cliques of the graph into a linked list.
 *
 * \param this
 * \return
 */
static struct LinkedList *Graph_list_cliques(struct Graph *this) {
    const int vertex_count = this->vertex_count;
    struct MapEntry **degree_map = malloc_or_die(vertex_count * sizeof(struct MapEntry *));

    for(int i = 0; i < vertex_count; i++)
        degree_map[i] = MapEntry_new(Graph_degree_unsafe(this, i), i);

    struct MapEntry **sorted_map = malloc_or_die(vertex_count * sizeof(struct MapEntry *));
    memcpy(sorted_map, degree_map, vertex_count * sizeof(struct MapEntry *));
    qsort(sorted_map, vertex_count, sizeof(struct MapEntry), &MapEntry_compare);

    struct LinkedList *result = LinkedList_new();



    for(int i = 0; i < vertex_count; i++)
        MapEntry_delete(sorted_map[i]);
    free(sorted_map);

    return result;
}

int *Graph_desperate_coloring(struct Graph *this) {
    /* Start pruning phase. */
    struct LinkedList *cliques = Graph_list_cliques(this);

    /* Reintroduce pruned cliques in the correct order and color. */
    const int vertex_count = this->vertex_count;

    int *colors = malloc_or_die(vertex_count * sizeof(int));
    memset(colors, -1, vertex_count * sizeof(int));

    struct BitVector *available = BitVector_new();
    BitVector_ctor(available, vertex_count);

    /*for(int i = 0; i < LinkedList_size(cliques); i++) {
        // ... introduce and color
    }

    BitVector_delete(available);
    LinkedList_delete(cliques);*/

    return NULL;
}

typedef int (*compute_spill_cost)(struct Graph *this, int v);

int *Graph_optimistic_coloring_(struct Graph *this, compute_spill_cost cost_func) {
    const int vertex_count = this->vertex_count;

    struct map_entry {
        int vertex,
            degree;
    } degree_map[vertex_count];

    int max_degree = INT_MIN;
    for(int i = 0; i < vertex_count; i++) {
        degree_map[i].vertex = i;
        degree_map[i].degree = Graph_degree_unsafe(this, i);
        //if() {

        //}
    }

    return NULL;
}
