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

package com.nur1popcorn.basm.utils.graph.model.simple;

import com.nur1popcorn.basm.utils.graph.model.AdjacencyMatrixGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@link SimpleAdjacencyMatrixGraph} class is an implementation of a simple graph which utilizes an adjacency matrix.
 */
public final class SimpleAdjacencyMatrixGraph<V> extends AdjacencyMatrixGraph<V> {
    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the edge is a self loop, hence v equals w.
     */
    @Override
    protected int edgeIndex(int v, int w) {
        if(v == w)
            throw new IllegalArgumentException("Simple graphs don't contain self loops.");
        return (w > v ?
                   ((((w - 1) * w) / 2) + v) :
                   ((((v - 1) * v) / 2) + w)
               );
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

    @Override
    public void removeVertex(V v) {
        final Integer vi = vertexMap.get(v);
        if(vi == null)
            return;

        for(int j = 0; j < vi; j++)
            matrix.set(edgeIndexFast(vi, j), matrix.get(edgeIndexFast(size - 1, j)));
        for(int j = vi + 1; j < size; j++)
            matrix.set(edgeIndexFast(j, vi), matrix.get(edgeIndexFast(size - 1, j)));

        matrix.clear(edgeIndexFast(size - 1, 0),
                     edgeIndexFast(size - 1, size - 1));

        vertexMap.remove(v);

        final V w = indexMap.remove(size - 1);
        if(v != w) {
            indexMap.set(vi, w);
            vertexMap.put(w, vi);
        }

        size--;
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
    public Set<V> getNeighbours(V v) {
        final Set<V> result = new HashSet<>();
        final int vi = vertexMap.get(v);
        for(int i = 0; i < vi; i++)
            if(hasEdge(vi, i))
                result.add(indexMap.get(i));

        for(int i = vi + 1; i < size; i++)
            if(hasEdge(i, vi))
                result.add(indexMap.get(i));

        return result;
    }

    @Override
    public int degree(V v) {
        int degree = 0;
        final int vi = vertexMap.get(v);
        for(int i = 0; i < vi; i++)
            if(hasEdge(vi, i))
                degree++;

        for(int i = vi + 1; i < size; i++)
            if(hasEdge(i, vi))
                degree++;

        return degree;
    }
}
