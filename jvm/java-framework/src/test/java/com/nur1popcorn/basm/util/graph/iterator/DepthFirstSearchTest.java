package com.nur1popcorn.basm.util.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.iterator.DepthFirstSearch;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyMatrixGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class DepthFirstSearchTest {
    @Test
    public void testSimple() {
        final DirectedGraph<Integer, Boolean> graph =
            new DirectedAdjacencyMatrixGraph<>();
        final Set<Integer> vs = new TreeSet<>();
        for(int i = 1; i < 6; i++) {
            graph.addVertex(i);
            vs.add(i);
        }

        graph.addEdge(1, 2, true);
        graph.addEdge(2, 3, true);
        graph.addEdge(3, 4, true);
        graph.addEdge(2, 5, true);

        final DepthFirstSearch<Integer, Boolean> dfs =
            new DepthFirstSearch<>(graph, 1);
        for(int i = 0; dfs.hasNext(); i++) {
            assertTrue(vs.remove(dfs.next()));
            assertEquals(new int[] { 0, 1, 2, 2, 3 }[i], dfs.getDepth());
        }
        assertTrue(vs.isEmpty());
    }
}
