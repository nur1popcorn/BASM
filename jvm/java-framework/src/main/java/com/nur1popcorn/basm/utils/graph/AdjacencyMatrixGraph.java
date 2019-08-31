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

package com.nur1popcorn.basm.utils.graph;

import java.util.*;

/**
 * The {@link AdjacencyMatrixGraph} class is an implementation of a simple graph which utilizes an adjacency matrix.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class AdjacencyMatrixGraph<V> implements SimpleGraph<V, SimpleEdge<V>> {
    private final Map<V, Integer> vertexMap = new HashMap<>();
    private final List<V> indexMap = new ArrayList<>();

    /* This bit set models a NxN adjacency matrix each bit in the array corresponds with a connection
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
    private final BitSet matrix = new BitSet();

    private int size;

    @Override
    public void addVertex(V v) {
        vertexMap.computeIfAbsent(v, vertex -> {
            indexMap.add(vertex);
            return size++;
        });
    }

    /**
     * Computes the index of the edge inside of the adj matrix.
     *
     * @param v The from vertex.
     * @param w The to vertex.
     *
     * @throws IllegalArgumentException if the edge is a self loop, hence v equals w.
     *
     * @return The index of the edge.
     */
    private int edgeIndex(int v, int w) {
        if(v == w)
            throw new IllegalArgumentException("Simple graphs don't contain self loops.");

        return (w > v ?
                  ((((w - 1) * w) / 2) + v) :
                  ((((v - 1) * v) / 2) + w)
               );
    }

    @Override
    public void addEdge(V v, V w) {
        addVertex(v);
        addVertex(w);

        matrix.set(edgeIndex(vertexMap.get(v),
                             vertexMap.get(w)));
    }

    @Override
    public boolean hasEdge(V v, V w) {
        return matrix.get(edgeIndex(vertexMap.get(v),
                                    vertexMap.get(w)));
    }

    /**
     * <i>NOTE: </i> This implementation assumes that v is always less than w. It should
     * only be used if that can be guaranteed.
     *
     * @param v The from vertex.
     * @param w The to vertex.
     *
     * @return The index of the edge.
     */
    private int edgeIndexFast(int v, int w) {
        return ((((v - 1) * v) / 2) + w);
    }

    /**
     * <i>NOTE: </i> This implementation assumes that v is always less than w. It should
     * only be used if that can be guaranteed.
     *
     * @param v The from vertex.
     * @param w The to vertex.
     *
     * @see #edgeIndexFast(int, int)
     *
     * @return True if the two given vertices are connected by an edge.
     */
    private boolean hasEdge(int v, int w) {
        return matrix.get(edgeIndexFast(v, w));
    }

    @Override
    public int degree(V v) {
        int degree = 0;
        final int vi = vertexMap.get(v);
        for(int j = 0; j < vi; j++)
            if(hasEdge(vi, j))
                degree++;

        for(int j = vi + 1; j < size; j++)
            if(hasEdge(j, vi))
                degree++;

        return degree;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<V> getNeighbours(V v) {
        final Set<V> result = new HashSet<>();
        final int vi = vertexMap.get(v);
        for(int j = 0; j < vi; j++)
            if(hasEdge(vi, j))
                result.add(indexMap.get(j));

        for(int j = vi + 1; j < size; j++)
            if(hasEdge(j, vi))
                result.add(indexMap.get(j));

        return result;
    }

    @Override
    public Set<V> getVertices() {
        return Collections.unmodifiableSet(vertexMap.keySet());
    }

    @Override
    public Set<SimpleEdge<V>> getEdges() {
        final Set<SimpleEdge<V>> result = new HashSet<>();
        for(int i = 0; i < size; i++) {
            final V v = indexMap.get(i);
            for(int j = 0; j < i; j++)
                if(hasEdge(i, j))
                    result.add(new Edge<>(
                        v, indexMap.get(j)));

            for(int j = i + 1; j < size; j++)
                if(hasEdge(j, i))
                    result.add(new Edge<>(
                        v, indexMap.get(j)));
        }
        return result;
    }

    @Override
    public Set<SimpleEdge<V>> getEdges(V v) {
        final Set<SimpleEdge<V>> result = new HashSet<>();
        final int vi = vertexMap.get(v);
        for(int j = 0; j < vi; j++)
            if(hasEdge(vi, j))
                result.add(new Edge<>(v, indexMap.get(j)));

        for(int j = vi + 1; j < size; j++)
            if(hasEdge(j, vi))
                result.add(new Edge<>(v, indexMap.get(j)));

        return result;
    }

    private static final class Edge<V> implements SimpleEdge<V> {
        private final V from, to;

        private Edge(V from, V to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public V getFrom() {
            return from;
        }

        @Override
        public V getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o == null || getClass() != o.getClass())
                return false;

            Edge<?> edge = (Edge<?>) o;
            return Objects.equals(from, edge.from) &&
                   Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
}
