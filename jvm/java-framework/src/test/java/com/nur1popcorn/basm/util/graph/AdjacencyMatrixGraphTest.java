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

package com.nur1popcorn.basm.util.graph;

import com.nur1popcorn.basm.utils.graph.AdjacencyMatrixGraph;
import com.nur1popcorn.basm.utils.graph.SimpleEdge;
import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import org.junit.Test;

public final class AdjacencyMatrixGraphTest extends SimpleGraphTest<Integer, SimpleEdge<Integer>> {
    private int current;

    @Override
    protected SimpleGraph<Integer, SimpleEdge<Integer>> createGraph() {
        return new AdjacencyMatrixGraph<>();
    }

    @Override
    protected Integer createVertex() {
        return current++;
    }

    @Test(expected = NullPointerException.class)
    public void testAdd() {
        graph.hasEdge(null, null);
    }
}
