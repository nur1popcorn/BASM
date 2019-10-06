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

package com.nur1popcorn.basm.util.graph.simple;

import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import com.nur1popcorn.basm.utils.graph.model.simple.SimpleAdjacencyMatrixGraph;

public final class SimpleAdjacencyMatrixGraphTest extends SimpleGraphTest<Integer, Boolean> {
    private int current;

    @Override
    protected SimpleGraph<Integer, Boolean> createGraph() {
        return new SimpleAdjacencyMatrixGraph<>();
    }

    @Override
    protected Integer createVertex() {
        return current++;
    }
}
