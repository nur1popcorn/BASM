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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AdjacencyListGraph<V, E> extends AbstractGraph<V, E> {
    protected final Map<V, Map<V, E>> adjList = new HashMap<>();

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
    public int size() {
        return adjList.size();
    }

    @Override
    public Set<V> getNeighbours(V v) {
        return Collections.unmodifiableSet(
            adjList.get(v)
                .keySet());
    }

    @Override
    public Set<V> getVertices() {
        return adjList.keySet();
    }
}
