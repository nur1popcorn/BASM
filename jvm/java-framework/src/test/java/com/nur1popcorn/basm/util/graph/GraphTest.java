package com.nur1popcorn.basm.util.graph;

import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public abstract class GraphTest<V, E> {
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
    public void testRemoveVertex() {
        final List<V> vertices = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            final V v = createVertex();
            vertices.add(v);
            graph.addVertex(v);
        }

        final Random random = new Random();
        final List<V> removed = new LinkedList<>();
        final ListIterator<V> iterator = vertices.listIterator();
        while(iterator.hasNext()) {
            final V v = iterator.next();

            if(random.nextBoolean()) {
                iterator.remove();
                removed.add(v);
                graph.removeVertex(v);
            }
        }

        for(V v : removed)
            assertFalse(graph.hasVertex(v));
    }

    @Test
    public void testRemoveAll() {
        V v = createVertex();
        graph.addVertex(v);
        graph.removeVertex(v);
        assertFalse(graph.hasVertex(v));
    }

    @Test
    public void testRemoveAdd() {
        final V v = createVertex();
        graph.addVertex(v);
        graph.removeVertex(v);
        graph.addVertex(v);
        assertTrue(graph.hasVertex(v));
        graph.removeVertex(v);
        assertFalse(graph.hasVertex(v));
    }

    @Test
    public void testRemoveEdge() {
        final V v = createVertex();
        final V w = createVertex();
        assertFalse(graph.hasEdge(v, w));
        graph.addEdge(v, w);
        assertTrue(graph.hasEdge(v, w));
    }

    @Test
    public void testHasVertex() {
        V v = createVertex();
        graph.addVertex(v);
        assertTrue(graph.hasVertex(v));

        v = createVertex();
        assertFalse(graph.hasVertex(v));

        graph.addVertex(v);
        assertTrue(graph.hasVertex(v));
    }

}
