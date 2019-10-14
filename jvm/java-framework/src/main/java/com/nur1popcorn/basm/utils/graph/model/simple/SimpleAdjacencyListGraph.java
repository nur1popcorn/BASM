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

import com.nur1popcorn.basm.utils.graph.model.AdjacencyListGraph;

import java.util.Map;

public final class SimpleAdjacencyListGraph<V, E> extends AdjacencyListGraph<V, E> {
    @Override
    public void addEdge(V v, V w, E e) {
        addVertex(v);
        addVertex(w);

        adjList.get(v)
               .put(w, e);

        adjList.get(w)
               .put(v, e);
    }

    @Override
    public void removeEdge(V v, V w) {
        final Map<V, E> a = adjList.get(v);
        if(a != null)
            a.remove(w);

        final Map<V, E> b = adjList.get(w);
        if(b != null)
            b.remove(v);
    }

    @Override
    public boolean hasEdge(V v, V w) {
        return adjList.get(v).containsKey(w) ||
               adjList.get(w).containsKey(v);
    }

    @Override
    public E getEdge(V v, V w) {
        return adjList.get(v)
                      .getOrDefault(w, adjList.get(w)
                                              .get(v));
    }

    @Override
    public int degree(V v) {
        return adjList.get(v)
            .size();
    }
}
