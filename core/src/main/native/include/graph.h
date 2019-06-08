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

#ifndef GRAPH_H
#define GRAPH_H

#include <stdint.h>
#include <stdbool.h>

#include "bit_vector.h"

/*!
 * \brief An implementation of a "simple" graph.
 */
struct Graph {
    int vertex_count;

    /* This 1d array models a NxN adjacency matrix each bit in the array corresponds with a connection
     * in the graph.
     *
     * 	m_{adj} = \begin{bmatrix}
     *                0 & a & b & d \\
     *                a & 0 & c & e \\
     *                b & c & 0 & f \\
     *                d & e & f & 0
	 *            \end{bmatrix}
     *
     * Since the graph is "simple graph" two parts can be discarded direction. Direction comes in the
     * form of mirrored entries into the graph as seen above. These can simply be discarded as they are
     * not needed.
     *
     * 	m_{adj} = \begin{bmatrix}
     *                0 & x & x & x \\
     *                a & 0 & x & x \\
     *                b & c & 0 & x \\
     *                d & e & f & 0
	 *            \end{bmatrix}
     *
     * The loops which would be stored along the diagonal can also be discarded as simple graphs can
     * not contain any loops leaving us with the following reduced matrix.
     *
     * m_{adj} = \begin{bmatrix}
     *                x & x & x & x \\
     *                a & x & x & x \\
     *                b & d & x & x \\
     *                c & e & f & x
	 *           \end{bmatrix}
     *
     * After reducing the matrix to this we can simply reimagine it as a 1d array in order to preserve
     * memory in the form of padding bits.
     *
     * m_{adj} = \begin{bmatrix}
     *                a & b & c & d & e & f
	 *           \end{bmatrix}
     */
    struct BitVector *adj_matrix;
};

/*!
 * \param vertex_count
 * \return
 */
struct Graph *Graph_new(int vertex_count);

/*!
 * \param this
 */
void Graph_delete(struct Graph *this);

/*!
 * \param this
 * \param v
 * \param w
 */
void Graph_add_edge(struct Graph *this, int v, int w);

/*!
 * \param this
 * \param v
 * \param w
 *
 * \return
 */
int Graph_is_connected(struct Graph *this, int v, int w);

/*!
 * \param this
 * \return
 */
int *Graph_greedy_coloring(struct Graph *this);

/*!
 * \param this
 * \return
 */
int *Graph_desperate_coloring(struct Graph *this);

/*!
 * \param this
 * \return
 */
int *Graph_optimistic_coloring(struct Graph *this);

#endif /* GRAPH_H */
