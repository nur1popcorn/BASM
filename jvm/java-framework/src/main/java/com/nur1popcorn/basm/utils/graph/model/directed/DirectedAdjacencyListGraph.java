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

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.model.AdjacencyListGraph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DirectedAdjacencyListGraph<V, E> extends AdjacencyListGraph<V, E> implements DirectedGraph<V, E> {
    @Override
    public void addEdge(V v, V w, E e) {
        addVertex(v);
        addVertex(w);

        adjList.get(v)
               .put(w, e);
    }

    @Override
    public void removeEdge(V v, V w) {
        final Map<V, E> a = adjList.get(v);
        if(a != null)
            a.remove(w);
    }

    @Override
    public boolean hasEdge(V v, V w) {
        final Map<V, E> target = adjList.get(v);
        if(target != null)
            return target.containsKey(w);
        return false;
    }

    @Override
    public E getEdge(V v, V w) {
        final Map<V, E> target = adjList.get(v);
        if(target != null)
            return target.get(w);
        return null;
    }

    @Override
    public int degree(V v) {
        return getInDegree(v) +
               getOutDegree(v);
    }

    @Override
    public int getInDegree(V v) {
        int degree = 0;
        for(Map<V, E> map : adjList.values())
            if(map.containsKey(v))
                degree++;
        return degree;
    }

    @Override
    public int getOutDegree(V v) {
        return adjList.get(v)
            .size();
    }

    @Override
    public Set<V> getInNeighbours(V v) {
        final Set<V> neighbours = new HashSet<>();
        for(Map.Entry<V, Map<V, E>> entry : adjList.entrySet())
            if(entry.getValue()
                    .containsKey(v))
                neighbours.add(entry.getKey());
        return neighbours;
    }

    @Override
    public Set<V> getOutNeighbours(V v) {
        return Collections.unmodifiableSet(
            adjList.get(v).keySet());
    }
}
