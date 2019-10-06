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

import com.nur1popcorn.basm.utils.graph.iterator.GraphIterator;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class LayoutSugiyama<V, E> implements LayoutStrategy {
    private static final Rectangle2D DUMMY_NODE = new Rectangle(0, 0, 0, 0);

    private final int hg, vg;
    private final GraphIterator<Rectangle2D> layerIterator;

    public LayoutSugiyama(int hg, int vg, GraphIterator<Rectangle2D> layerIterator) {
        this.hg = hg;
        this.vg = vg;

        this.layerIterator = layerIterator;
    }

    /*@Override
    public void reposition(DirectedGraph<Rectangle2D, SimpleEdge<Rectangle2D>> graph, Rectangle2D start) {
        final SimpleGraph<Rectangle2D, SimpleEdge<Rectangle2D>> dummy
            = new SimpleAdjacencyMatrixGraph<>();

        for(Rectangle2D v : graph)
            dummy.addVertex(v);

        int d = 0;
        while(layerIterator.hasNext()) {
            final Rectangle2D v = layerIterator.next();

        }
    }*/
}
