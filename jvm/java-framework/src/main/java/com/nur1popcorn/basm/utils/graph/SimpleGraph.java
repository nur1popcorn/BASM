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

import java.util.Iterator;
import java.util.Set;

/**
 * The {@link SimpleGraph} has no sense of edge direction and can also not deal with self loops.
 *
 * @param <V> The type of vertex.
 * @param <E> The type of edge.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public interface SimpleGraph<V, E> extends Iterable<V> {
    /**
     * Adds a vertex to the graph.
     * @param v The vertex which should be added.
     */
    void addVertex(V v);

    /**
     * Removes the vertex from the graph.
     * @param v The vertex which should be removed.
     */
    void removeVertex(V v);

    /**
     * @param v The vertex which should be tested.
     * @return True if the if the graph contains the given vertex.
     */
    boolean hasVertex(V v);

    /**
     * Connects the two given vertices using an edge.
     *
     * @param v The start vertex.
     * @param w The end vertex.
     */
    void addEdge(V v, V w, E e);

    /**
     * Removes the given edge from the graph.
     *
     * @param v The start vertex.
     * @param w The end vertex.
     */
    void removeEdge(V v, V w);

    /**
     * @param v The start vertex.
     * @param w The end vertex.
     *
     * @return True if there exists an edge between the two nodes.
     */
    boolean hasEdge(V v, V w);

    /**
     * @param v The start vertex.
     * @param w The end vertex.
     *
     * @return The edge which connects the two vertices.
     */
    E getEdge(V v, V w);

    /**
     * <i>NOTE: In a directed graph this method returns the sum of in and out degree.</i>
     *
     * @param v The vertex whose degree should be calculated.
     * @return The degree of a given vertex.
     */
    int degree(V v);

    /**
     * @return The number of vertices in the graph.
     */
    int size();

    /**
     * <i>NOTE: In a directed graph this method returns the union of in and out neighbours.</i>
     *
     * @param v The vertex whose neighbours should be listed.
     * @return All neighbours of the given vertex.
     */
    Set<V> getNeighbours(V v);

    /**
     * @return All vertices of the graph.
     */
    Set<V> getVertices();

    /**
     * @return The number of edges in the graph.
     */
    int getEdgeCount();

    @Override
    default Iterator<V> iterator() {
        return getVertices()
            .iterator();
    }

    /**
     * @return An iterator which maps each vertex to a number.
     */
    default CountIterator<V> countIterator() {
        return new CountIterator<>(iterator());
    }

    /**
     * An iterator which maps each vertex to a number.
     */
    final class CountIterator<V> implements Iterator<V> {
        private final Iterator<V> parent;
        private int count = -1;

        public CountIterator(Iterator<V> parent) {
            this.parent = parent;
        }

        @Override
        public boolean hasNext() {
            return parent.hasNext();
        }

        @Override
        public V next() {
            count++;
            return parent.next();
        }

        /**
         * @return The number of vertices which have already been processed.
         */
        public int count() {
            return count;
        }
    }
}
