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

import com.nur1popcorn.basm.utils.graph.SimpleEdge;
import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.*;

public abstract class SimpleGraphTest<V, E extends SimpleEdge<V>> {
    protected SimpleGraph<V, E> graph;

    /**
     * A factory method to create the different types of graphs.
     * @return The graph which should be tested.
     */
    protected abstract SimpleGraph<V, E> createGraph();

    /**
     * A factory method which creates vertices.
     * @return A vertex which can be used for testing.
     */
    protected abstract V createVertex();

    @Before
    public void setup() {
        graph = createGraph();
    }

    @Test
    public void testAddVertex() {
        final List<V> vertices = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            final V v = createVertex();
            vertices.add(v);
            graph.addVertex(v);
        }

        final Set<V> result = graph.getVertices();
        for(V v : vertices)
            assertTrue(result.contains(v));
    }

    @Test
    public void testAddEdge() {
        V v = createVertex();
        V w = createVertex();

        graph.addVertex(v);
        graph.addVertex(w);
        assertFalse(graph.hasEdge(v, w));
        assertFalse(graph.hasEdge(w, v));

        graph.addEdge(v, w);
        assertTrue(graph.hasEdge(v, w));
        assertTrue(graph.hasEdge(w, v));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLoop() {
        V v = createVertex();
        graph.addVertex(v);
        graph.addEdge(v, v);
    }

    @Test
    public void testDegreeCircle() {
        V first = createVertex();
        graph.addVertex(first);

        V v = first;
        for(int i = 0; i < 10; i++) {
            V w = createVertex();
            graph.addVertex(w);
            graph.addEdge(v, w);
            v = w;
        }

        graph.addEdge(v, first);

        for(V i : graph)
            assertEquals(2, graph.degree(i));
    }

    @Test
    public void testGetNeighbours() {
        V root = createVertex();

        final Set<V> vertices = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            V v = createVertex();
            vertices.add(v);
            graph.addVertex(v);
            graph.addEdge(root, v);
        }

        assertTrue(vertices.containsAll(graph.getNeighbours(root)));
    }


    @Test
    public void testGetEdges() {
        V root = createVertex();

        final Set<V> vertices = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            V v = createVertex();
            vertices.add(v);
            graph.addVertex(v);
            graph.addEdge(root, v);
        }

        final Set<E> edges = graph.getEdges(root);
        for(E e : edges) {
            assertEquals(root, e.getFrom());
            assertTrue(vertices.contains(e.getTo()));
        }
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
            graph.addEdge(root, next);
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
        V root = createVertex();
        spawn(root, 3);
        transverse(root, root);
    }
}
