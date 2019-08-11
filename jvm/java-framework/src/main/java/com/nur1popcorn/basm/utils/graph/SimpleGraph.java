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
 * The {@link SimpleGraph}
 *
 * @param <V> The type of vertex.
 * @param <E> The type of edge.
 */
public interface SimpleGraph<V, E extends SimpleEdge<V>> extends Iterable<V> {
    /**
     * Adds a vertex to the graph.
     * @param v The vertex which should be added.
     */
    void addVertex(V v);

    /**
     * Connects the two given vertices using an edge.
     */
    void addEdge(V v, V w);

    /**
     * @throws IllegalArgumentException if the edge is a self loop, hence v equals w.
     * @return True if the two given vertices are connected by an edge.
     */
    boolean hasEdge(V v, V w);

    /**
     * @param v The vertex whose degree should be calculated.
     * @return The degree of a given vertex.
     */
    int degree(V v);

    /**
     * @return The number of vertices in the graph.
     */
    int size();

    /**
     * @param v The vertex whose neighbours should be listed.
     * @return All neighbours of the given vertex.
     */
    Set<V> getNeighbours(V v);

    /**
     * @return All vertices of the graph.
     */
    Set<V> getVertices();

    /**
     * @return All edges of the graph.
     */
    Set<E> getEdges();

    /**
     * @param v The vertex whose edges should be returned.
     * @return All edges connected to a vertex.
     */
    Set<E> getEdges(V v);

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

        public int count() {
            return count;
        }
    }
}
