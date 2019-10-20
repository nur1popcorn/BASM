package com.nur1popcorn.basm.util.graph.simple;

import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import com.nur1popcorn.basm.utils.graph.model.simple.SimpleAdjacencyListGraph;

public final class SimpleAdjacencyListTest extends SimpleGraphTest<Integer, Integer> {
    private int currentVertex, currentEdge;

    @Override
    protected SimpleGraph<Integer, Integer> createGraph() {
        return new SimpleAdjacencyListGraph<>();
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
