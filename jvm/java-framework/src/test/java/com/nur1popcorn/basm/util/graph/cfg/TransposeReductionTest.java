package com.nur1popcorn.basm.util.graph.cfg;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.cfg.TransposeReduction;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyListGraph;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyMatrixGraph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public final class TransposeReductionTest {
    @Test
    public void test() {
        final TransposeReduction<Integer, Boolean> reduction = new TransposeReduction<>();
        final DirectedGraph<Integer, Boolean> graph = new DirectedAdjacencyListGraph<>();
        graph.addVertex(0);

        final List<List<Integer>> layers = new ArrayList<>();

        final List<Integer> zero = new LinkedList<>();
        zero.add(0);
        layers.add(zero);

        {
            final List<Integer> layer = new LinkedList<>();
            for(int j = 1; j < 11; j++) {
                graph.addVertex(j);
                graph.addEdge(0, j, true);
                layer.add(j);
            }
            layers.add(layer);
        }

        for(int i = 1; i < 12; i++) {
            final List<Integer> layer = new LinkedList<>();
            for(int j = 1; j < 11; j++) {
                final int v = j + ((i - 1) * 10);
                final int w = j + (i * 10);
                graph.addVertex(w);
                graph.addEdge(v, w, true);
                layer.add(w);
            }

            final ListIterator<Integer> iterator = layer.listIterator();
            while(iterator.hasNext()) {
                int a = iterator.next();
                int b = iterator.next();
                iterator.set(a);
                iterator.previous();
                iterator.previous();
                iterator.set(b);
                iterator.next();
                iterator.next();
            }

            layers.add(layer);
        }

        reduction.reduce(graph, layers);

        int i = 0;
        for(List<Integer> layer : layers)
            for(int j : layer)
                assertEquals(i++, j);
    }
}
