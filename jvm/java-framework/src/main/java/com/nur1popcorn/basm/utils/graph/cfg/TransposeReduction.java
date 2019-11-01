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

import java.util.*;

/**
 * The {@link TransposeReduction} class houses an implementation of the transpose reduction algorithm
 * based on this description:
 * <a href="http://www.diva-portal.org/smash/get/diva2:796984/FULLTEXT01.pdf"> Visualization of Code Flow </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class TransposeReduction<V, E> implements CrossingReduction<V, E> {
    @Override
    public void reduce(DirectedGraph<V, E> graph, List<List<V>> vertices) {
        final Iterator<List<V>> iterator = vertices.iterator();
        if(!iterator.hasNext())
            return;

        List<V> a = iterator.next();
        while(iterator.hasNext()) {
            final List<V> b = iterator.next();
            transpose(graph, a, b);
            a = b;
        }
    }

    /**
     * Greedily swap vertices until the result is improved.
     *
     * @param graph The graph which should be greedily optimized.
     * @param a The upper layer (lower depth).
     * @param b The lower layer (higher depth).
     */
    private static <V, E> void transpose(DirectedGraph<V, E> graph, List<V> a, List<V> b) {
        boolean changed = true;
        while(changed) {
            changed = false;
            final ListIterator<V> iterator = b.listIterator();
            while(iterator.hasNext()) {
                final V v = iterator.next();
                if(!iterator.hasNext())
                    break;

                final V w = iterator.next();

                // only collect the neighbours that would be affected by a swap.
                if(countCrossings(graph, a, v, w) >
                   countCrossings(graph, a, w, v)) {
                    // swap the two members to reduce the number of edge crossings.
                    iterator.set(v);
                    iterator.previous();
                    iterator.previous();
                    iterator.set(w);

                    iterator.next();
                    iterator.next();
                    changed = true;
                }
            }
        }
    }

    /**
     * A simple way of counting the edge crossings in one section that I came up with.
     * The algorithm works by assigning each vertex a cost for being reached by another
     * vertex from the right. The cost is based on the number of barrier (edge) crossings
     * that such a connection would cause.
     *
     * @param graph The graph in which the edge crossings are to be counted.
     * @param v The 1st of the two target vertices.
     * @param w The 2nd of the two target vertices.
     *
     * @return The number of edge crossings.
     */
    private static <V, E> int countCrossings(DirectedGraph<V, E> graph, List<V> a, V v, V w) {
        int cost = 0;
        int count = 0;
        for(V u : a) {
            if(graph.hasEdge(u, v))
                count += cost;
            if(graph.hasEdge(u, w))
                cost++;
        }
        return count;
    }
}
