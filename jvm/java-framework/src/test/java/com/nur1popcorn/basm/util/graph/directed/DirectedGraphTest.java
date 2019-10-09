package com.nur1popcorn.basm.util.graph.directed;

import com.nur1popcorn.basm.util.graph.GraphTest;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public abstract class DirectedGraphTest<V, E> extends GraphTest<V, E> {
    @Test
    public void testAddEdge() {
        final V v = createVertex();
        final V w = createVertex();

        graph.addVertex(v);
        graph.addVertex(w);
        assertFalse(graph.hasEdge(v, w));

        graph.addEdge(v, w);
        assertTrue(graph.hasEdge(v, w));
    }
}
