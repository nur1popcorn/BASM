package com.nur1popcorn.basm.util.graph.cfg;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.cfg.BaryCenterReduction;
import com.nur1popcorn.basm.utils.graph.cfg.CrossingReduction;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyMatrixGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class BaryCenterReductionTest {
    @Test
    public void test() {
        final List<List<Integer>> layers = new ArrayList<>();
        for(int i = 0; i < 3; i++)
            layers.add(new LinkedList<>());

        final DirectedGraph<Integer, Boolean> graph
            = new DirectedAdjacencyMatrixGraph<>();
        for(int i = 0; i < 11; i++)
            graph.addVertex(i);
        layers.get(0)
              .add(0);

        for(int i = 1; i < 6; i++) {
            layers.get(1)
                  .add(i);
            graph.addEdge(0, i, true);
        }

        for(int i = 1; i < 6; i++) {
            graph.addEdge(i, new int[] { 7, 6, 9, 10, 8 }[i - 1], true);
            layers.get(2)
                  .add(i + 5);
        }

        final CrossingReduction<Integer, Boolean> reduction
            = new BaryCenterReduction<>();
        reduction.reduce(graph, layers);

        int i = 0;
        for(int v : layers.get(2))
            assertEquals(new int[] { 7, 6, 9, 10, 8 }[i++], v);
    }
}
