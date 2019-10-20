package com.nur1popcorn.basm.util.graph.directed;

import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyListGraph;

public final class DirectedAdjacencyListTest extends DirectedGraphTest<Integer, Integer> {
    private int currentVertex, currentEdge;

    @Override
    protected SimpleGraph<Integer, Integer> createGraph() {
        return new DirectedAdjacencyListGraph<>();
    }

    @Override
    protected Integer createVertex() {
        return currentVertex++;
    }

    @Override
    protected Integer createEdge() {
        return currentEdge++;
    }
}
