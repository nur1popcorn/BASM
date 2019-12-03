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

package com.nur1popcorn.basm.utils.graph.cfg.crossings;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.*;

/**
 * http://www.diva-portal.org/smash/get/diva2:796984/FULLTEXT01.pdf
 */
public final class BaryCenterReduction<V, E> implements CrossingReduction<V, E> {
    @Override
    public void reduce(DirectedGraph<V, E> graph, List<List<V>> vertices) {
        final Map<V, Float> map = new HashMap<>();
        for(List<V> vs : vertices) {
            for(V v : vs) {
                final Set<V> neighbours = graph.getInNeighbours(v);
                float bary = 0;
                for(V n : neighbours)
                    bary += map.get(n);
                final int nc = neighbours.size();
                map.put(v, bary / nc);
            }

            vs.sort((v, w) -> Float.compare(map.get(v), map.get(w)));

            float i = 0;
            for(V v : vs)
                map.put(v, i++);
        }
    }
}
