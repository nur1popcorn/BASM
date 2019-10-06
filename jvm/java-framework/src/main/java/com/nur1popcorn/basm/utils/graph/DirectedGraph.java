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

package com.nur1popcorn.basm.utils.graph;

import java.util.Set;

/**
 * The {@link DirectedGraph} has sense of edge direction and can deal with self loops.
 *
 * @param <V> The type of vertex.
 * @param <E> The type of edge.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public interface DirectedGraph<V, E> extends SimpleGraph<V, E> {
    /**
     * <i>NOTE: </i> Self loops are counted in the in and out degree.
     *
     * @param v The vertex whose "in" degree should be computed.
     * @return The "in" degree of the given vertex.
     */
    int getInDegree(V v);

    /**
     * <i>NOTE: </i> Self loops are counted in the in and out degree.
     *
     * @param v The vertex whose "in" degree should be computed.
     * @return The "in" degree of the given vertex.
     */
    int getOutDegree(V v);

    /**
     * @param v The vertex whose "in" neighbours should be listed.
     * @return All "in" neighbours of the given vertex.
     */
    Set<V> getInNeighbours(V v);

    /**
     * @param v The vertex whose "out" neighbours should be listed.
     * @return All "out" neighbours of the given vertex.
     */
    Set<V> getOutNeighbours(V v);
}
