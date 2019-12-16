package com.nur1popcorn.basm.util.graph.cfg;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.cfg.crossings.BarrierCrossings;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyListGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class BarrierCrossingsTest {
    @Test
    public void test() {
        final List<Integer> a = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            a.add(i);

        final List<Integer> b = new ArrayList<>();
        for(int i = 5; i < 9; i++)
            b.add(i);

        final DirectedGraph<Integer, Boolean> graph = new DirectedAdjacencyListGraph<>();

        assertEquals(0, BarrierCrossings.countCrossings(graph, a, b));
        graph.addEdge(0, 5, true);
        graph.addEdge(1, 6, true);
        graph.addEdge(2, 6, true);
        assertEquals(0, BarrierCrossings.countCrossings(graph, a, b));
        graph.addEdge(3, 8, true);
        graph.addEdge(3, 5, true);
        graph.addEdge(4, 8, true);
        assertEquals(2, BarrierCrossings.countCrossings(graph, a, b));
        graph.addEdge(4, 7, true);
        assertEquals(3, BarrierCrossings.countCrossings(graph, a, b));
        graph.addEdge(4, 6, true);
        assertEquals(4, BarrierCrossings.countCrossings(graph, a, b));
        graph.addEdge(4, 5, true);
        assertEquals(7, BarrierCrossings.countCrossings(graph, a, b));
    }
}
