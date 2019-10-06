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

/**
 * <a href="http://www.ssw.uni-linz.ac.at/General/Staff/TW/Wuerthinger06Bachelor.pdf"> Visualization of Java Control Flow Graphs </a>
 */
public final class LayoutBreadthFirstSearch implements LayoutStrategy {
    /*private final double hg, vg;

    public LayoutBreadthFirstSearch(double hg, double vg) {
        this.hg = hg;
        this.vg = vg;
    }

    @Override
    public void reposition(DirectedGraph<Rectangle2D, SimpleEdge<Rectangle2D>> graph, Rectangle2D start) {
        final BreadthFirstSearch<Rectangle2D, SimpleEdge<Rectangle2D>> iterator
            = new BreadthFirstSearch<>(graph, start);
        int d = 0;
        double x = 0, y = 0, hm = 0;
        while(iterator.hasNext()) {
            final Rectangle2D next = iterator.next();
            final int dc = iterator.getDepth();
            if(dc > d) {
                d = dc;
                x = 0;
                y += hm + vg;
            }
            final double w = next.getWidth();
            final double h = next.getHeight();
            hm = Math.max(hm, h);
            next.setFrame(x, y, w, h);
            x += w + hg;
        }
    }*/
}
