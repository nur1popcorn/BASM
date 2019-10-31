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

package com.nur1popcorn.basm.utils.graph.cfg;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.iterator.GraphIterator;
import com.nur1popcorn.basm.utils.graph.iterator.GraphIteratorFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class LayerSearchAssignment<V, E> implements LayerAssignment<V, E> {
    private final GraphIteratorFactory<V, E> factory;

    public LayerSearchAssignment(GraphIteratorFactory<V, E> factory) {
        this.factory = factory;
    }

    @Override
    public List<List<V>> assign(DirectedGraph<V, E> graph, V start) {
        final List<List<V>> result = new ArrayList<>();
        result.add(new LinkedList<>());

        int md = 0;
        final GraphIterator<V> iterator =
            factory.create(graph, start);
        while(iterator.hasNext()) {
            final V v = iterator.next();
            final int d = iterator.getDepth();
            if(d > md) {
                md = d;
                result.add(new LinkedList<>());
            }
            result.get(d)
                  .add(v);
        }
        return result;
    }
}
