package com.nur1popcorn.basm.utils.graph;

import java.util.Set;

public abstract class GraphAdapter<V, E> extends DirectedGraphAdapter<V, E> {
    private final DirectedGraph<V, E> graph;

    public GraphAdapter(DirectedGraph<V, E> graph) {
        super(graph);
        this.graph = graph;
    }

    public GraphAdapter(SimpleGraph<V, E> graph) {
        this(new DirectedGraphAdapter<>(graph));
    }

    @Override
    public int getInDegree(V v) {
        return graph.getInDegree(v);
    }

    @Override
    public int getOutDegree(V v) {
        return graph.getOutDegree(v);
    }

    @Override
    public Set<V> getInNeighbours(V v) {
        return graph.getInNeighbours(v);
    }

    @Override
    public Set<V> getOutNeighbours(V v) {
        return graph.getOutNeighbours(v);
    }
}
