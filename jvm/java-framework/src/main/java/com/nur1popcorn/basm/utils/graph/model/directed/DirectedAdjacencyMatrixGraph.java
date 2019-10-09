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

package com.nur1popcorn.basm.utils.graph.model.directed;

import com.nur1popcorn.basm.utils.graph.model.AdjacencyMatrixGraph;
import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.HashSet;
import java.util.Set;

public final class DirectedAdjacencyMatrixGraph<V> extends AdjacencyMatrixGraph<V> implements DirectedGraph<V, Boolean> {
    @Override
    protected int edgeIndex(int v, int w) {
        return size * v + w;
    }

    @Override
    public void removeVertex(V v) {
        final Integer vi = vertexMap.get(v);
        if(vi == null)
            return;

        // set in.
        for(int j = 0; j < vi; j++)
            matrix.set(edgeIndex(vi, j), matrix.get(edgeIndex(size - 1, j)));
        for(int j = vi + 1; j < size; j++)
            matrix.set(edgeIndex(vi, j), matrix.get(edgeIndex(size - 1, j)));

        // set out.
        for(int j = 0; j < vi; j++)
            matrix.set(edgeIndex(j, vi), matrix.get(edgeIndex(j, size - 1)));
        for(int j = vi + 1; j < size; j++)
            matrix.set(edgeIndex(j, vi), matrix.get(edgeIndex(j, size - 1)));

        // set loop.
        matrix.set(edgeIndex(vi, vi), matrix.get(edgeIndex(size - 1, size - 1)));

        matrix.clear(edgeIndex(size - 1, 0),
                     edgeIndex(size - 1, size - 1));

        vertexMap.remove(v);

        final V w = indexMap.remove(size - 1);
        if(v != w) {
            indexMap.set(vi, w);
            vertexMap.put(w, vi);
        }

        size--;
    }

    @Override
    public int degree(V v) {
        return getInDegree(v) +
               getOutDegree(v);
    }

    @Override
    public Set<V> getNeighbours(V v) {
        final Set<V> result = getInNeighbours(v);
        result.addAll(getOutNeighbours(v));
        return result;
    }

    /**
     * @param v The from vertex.
     * @param w The to vertex.
     *
     * @return True if the two given vertices are connected by an edge.
     */
    private boolean hasEdge(int v, int w) {
        return matrix.get(edgeIndex(v, w));
    }

    @Override
    public int getInDegree(V v) {
        int degree = 0;
        final int vi = vertexMap.get(v);
        for(int i = 0; i < size; i++)
            if(hasEdge(i, vi))
                degree++;
        return degree;
    }

    @Override
    public int getOutDegree(V v) {
        int degree = 0;
        final int vi = vertexMap.get(v);
        for(int i = 0; i < size; i++)
            if(hasEdge(vi, i))
                degree++;
        return degree;
    }

    @Override
    public Set<V> getInNeighbours(V v) {
        final Set<V> neighbours = new HashSet<>();
        final int vi = vertexMap.get(v);
        for(int i = 0; i < size; i++)
            if(hasEdge(i, vi))
                neighbours.add(indexMap.get(i));
        return neighbours;
    }

    @Override
    public Set<V> getOutNeighbours(V v) {
        final Set<V> neighbours = new HashSet<>();
        final int vi = vertexMap.get(v);
        for(int i = 0; i < size; i++)
            if(hasEdge(vi, i))
                neighbours.add(indexMap.get(i));
        return neighbours;
    }
}
