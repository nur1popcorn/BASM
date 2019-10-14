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

import com.nur1popcorn.basm.util.graph.GraphTest;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.*;

public abstract class SimpleGraphTest<V, E> extends GraphTest<V, E> {
    @Test
    public void testAddEdge() {
        final V v = createVertex();
        final V w = createVertex();

        graph.addVertex(v);
        graph.addVertex(w);
        assertFalse(graph.hasEdge(v, w));
        assertFalse(graph.hasEdge(w, v));

        graph.addEdge(v, w, createEdge());
        assertTrue(graph.hasEdge(v, w));
        assertTrue(graph.hasEdge(w, v));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLoop() {
        final V v = createVertex();
        graph.addVertex(v);
        graph.addEdge(v, v, createEdge());
    }

    @Test
    public void testDegreeCircle() {
        final V first = createVertex();
        graph.addVertex(first);

        V v = first;
        for(int i = 0; i < 10; i++) {
            V w = createVertex();
            graph.addVertex(w);
            graph.addEdge(v, w, createEdge());
            v = w;
        }

        graph.addEdge(v, first, createEdge());

        for(V i : graph)
            assertEquals(2, graph.degree(i));
    }

    @Test
    public void testGetNeighbours() {
        final V root = createVertex();

        final Set<V> vertices = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            V v = createVertex();
            vertices.add(v);
            graph.addVertex(v);
            graph.addEdge(root, v, createEdge());
        }

        assertTrue(vertices.containsAll(graph.getNeighbours(root)));
    }


    /**
     * Creates a tree of depth degree.
     *
     * @param root The root vertex of the tree.
     * @param degree The depth of the tree.
     */
    private void spawn(V root, int degree) {
        if(degree < 0)
            return;

        for(int i = 0; i < degree; i++) {
            V next = createVertex();
            graph.addEdge(root, next, createEdge());
            spawn(next, degree - 1);
        }
    }

    /**
     * Transverses the tree making sure that is properly connected at every step.
     *
     * @param root The root vertex of the tree.
     * @param current The vertex which is currently being inspected.
     */
    private void transverse(V root, V current) {
        final Set<V> neighbours = graph.getNeighbours(current);
        if(neighbours.size() <= 1)
            return;

        neighbours.remove(root);
        for(V v : neighbours) {
            assertTrue(graph.hasEdge(current, v));
            transverse(current, v);
        }
    }

    @Test
    public void testTree() {
        final V root = createVertex();
        spawn(root, 3);
        transverse(root, root);
    }
}
