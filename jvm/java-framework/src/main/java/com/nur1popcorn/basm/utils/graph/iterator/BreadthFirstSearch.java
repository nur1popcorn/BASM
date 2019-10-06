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

package com.nur1popcorn.basm.utils.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.*;

/**
 * The {@link BreadthFirstSearch} class houses an implementation of the breadth first search algorithm based on
 * these descriptions of the algorithm:
 * <ul>
 *     <li><a href="https://en.wikipedia.org/wiki/Breadth-first_search"> Breadth-first search </a></li>
 *     <li><a href="https://www.tutorialspoint.com/data_structures_algorithms/breadth_first_traversal.htm"> Data Structure - Breadth First Traversal </a></li>
 *     <li><a href="http://www.ssw.uni-linz.ac.at/General/Staff/TW/Wuerthinger06Bachelor.pdf"> Visualization of Java Control Flow Graphs </a></li>
 * </ul>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class BreadthFirstSearch<V, E> extends AbstractBreadthFirstSearch<V, E> {
    public BreadthFirstSearch(DirectedGraph<V, E> graph, V start) {
        super(graph, start);
    }

    @Override
    public V next() {
        if(--count <= 0) {
            count = queue.size();
            depth++;
        }

        final V v = queue.remove();
        final Set<V> neighbours = graph.getOutNeighbours(v);
        for(V w : neighbours)
            if(!discovered.contains(w)) {
                discovered.add(w);
                queue.add(w);
            }
        return v;
    }
}
