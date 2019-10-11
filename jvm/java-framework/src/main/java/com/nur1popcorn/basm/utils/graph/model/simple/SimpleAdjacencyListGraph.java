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

import com.nur1popcorn.basm.utils.graph.SimpleGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SimpleAdjacencyListGraph<V, E> implements SimpleGraph<V, E> {
    private final Map<V, Map<V, E>> adjList = new HashMap<>();

    @Override
    public void addVertex(V v) {
        adjList.putIfAbsent(v, new HashMap<>());
    }

    @Override
    public void removeVertex(V v) {
        adjList.remove(v);
        for(Map<V, E> w : adjList.values())
            w.remove(v);
    }

    @Override
    public boolean hasVertex(V v) {
        return adjList.containsKey(v);
    }

    @Override
    public void addEdge(V v, V w, E e) {
        final Map<V, E> map = adjList.get(v);
        if(map != null)
            map.put(w, e);
    }

    @Override
    public void removeEdge(V v, V w) {

    }

    @Override
    public boolean hasEdge(V v, V w) {
        return false;
    }

    @Override
    public E getEdge(V v, V w) {
        return null;
    }

    @Override
    public int degree(V v) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<V> getNeighbours(V v) {
        return null;
    }

    @Override
    public Set<V> getVertices() {
        return null;
    }

    @Override
    public int getEdgeCount() {
        return 0;
    }
}
