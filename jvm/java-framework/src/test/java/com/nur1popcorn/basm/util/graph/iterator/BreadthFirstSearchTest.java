package com.nur1popcorn.basm.util.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyMatrixGraph;
import com.nur1popcorn.basm.utils.graph.iterator.BreadthFirstSearch;
import org.junit.Assert;
import org.junit.Test;

public final class BreadthFirstSearchTest {
    @Test
    public void testSimple() {
        final DirectedGraph<Integer, Boolean> graph =
            new DirectedAdjacencyMatrixGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(1, 2, true);
        graph.addEdge(2, 3, true);
        graph.addEdge(3, 5, true);
        graph.addEdge(2, 4, true);

        final BreadthFirstSearch<Integer, Boolean> bfs =
            new BreadthFirstSearch<>(graph, 1);
        for(int i = 0; bfs.hasNext(); i++) {
            Assert.assertEquals(i + 1, (int) bfs.next());
            Assert.assertEquals(bfs.getDepth(),
                new int[] { 0, 1, 2, 2, 3 }[i]);
        }
    }
}