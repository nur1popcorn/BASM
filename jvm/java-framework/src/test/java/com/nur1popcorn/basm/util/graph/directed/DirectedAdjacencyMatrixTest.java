package com.nur1popcorn.basm.util.graph.directed;

import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyMatrixGraph;

public final class DirectedAdjacencyMatrixTest extends DirectedGraphTest<Integer, Boolean> {
    private int current;

    @Override
    protected SimpleGraph<Integer, Boolean> createGraph() {
        return new DirectedAdjacencyMatrixGraph<>();
    }

    @Override
    protected Integer createVertex() {
        return current++;
    }

    @Override
    protected Boolean createEdge() {
        return true;
    }
}
