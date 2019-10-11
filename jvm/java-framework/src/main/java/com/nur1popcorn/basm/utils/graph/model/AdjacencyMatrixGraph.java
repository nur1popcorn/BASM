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

package com.nur1popcorn.basm.utils.graph.model;

import java.util.*;

/**
 *
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public abstract class AdjacencyMatrixGraph<V> extends AbstractGraph<V, Boolean> {
    protected final Map<V, Integer> vertexMap = new HashMap<>();
    protected final List<V> indexMap = new ArrayList<>();

    /* This bit set models a NxN adjacency matrix each bit in the array corresponds with a connection
     * in the graph.
     *
     * Directed Graph:
     *
     *  m_{adj} = \begin{bmatrix}
     *                a & b & c & d \\
     *                e & f & g & h \\
     *                h & i & j & k \\
     *                l & m & n & o
     *            \end{bmatrix}
     *
     * The matrix can then be reimagined to become a 1d array:
     *
     *  m_{adj} = \begin{bmatrix}
     *                 a & b & c & d & e & f & ... & o
     *            \end{bmatrix}
     *
     * Simple Graph:
     * 	m_{adj} = \begin{bmatrix}
     *                0 & a & b & d \\
     *                a & 0 & c & e \\
     *                b & c & 0 & f \\
     *                d & e & f & 0
     *            \end{bmatrix}
     *
     * Since the graph is a "simple graph" two parts can be discarded direction. Direction comes in the
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
     *  m_{adj} = \begin{bmatrix}
     *                 x & x & x & x \\
     *                 a & x & x & x \\
     *                 b & d & x & x \\
     *                 c & e & f & x
     *            \end{bmatrix}
     *
     * After reducing the matrix to this we can simply reimagine it as a 1d array in order to preserve
     * memory in the form of padding bits.
     *
     *  m_{adj} = \begin{bmatrix}
     *                 a & b & c & d & e & f
     *            \end{bmatrix}
     */
    protected final BitSet matrix = new BitSet();

    protected int size;

    /**
     * Computes the index of the edge inside of the adj matrix.
     *
     * @param v The from vertex.
     * @param w The to vertex.
     *
     * @return The index of the edge.
     */
    protected abstract int edgeIndex(int v, int w);

    @Override
    public void addVertex(V v) {
        vertexMap.computeIfAbsent(v, vertex -> {
            indexMap.add(vertex);
            return size++;
        });
    }

    @Override
    public boolean hasVertex(V v) {
        return vertexMap.containsKey(v);
    }

    @Override
    public void addEdge(V v, V w, Boolean e) {
        if(!e)
            return;

        addVertex(v);
        addVertex(w);

        matrix.set(edgeIndex(vertexMap.get(v),
                             vertexMap.get(w)));
    }

    @Override
    public void removeEdge(V v, V w) {
        matrix.clear(edgeIndex(vertexMap.get(v),
                               vertexMap.get(w)));
    }

    @Override
    public boolean hasEdge(V v, V w) {
        final Integer x = vertexMap.get(v);
        final Integer y = vertexMap.get(w);
        return x != null && y != null &&
               matrix.get(edgeIndex(x, y));
    }

    @Override
    public Boolean getEdge(V v, V w) {
        return hasEdge(v, w);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<V> getVertices() {
        return Collections.unmodifiableSet(vertexMap.keySet());
    }
}
