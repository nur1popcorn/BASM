package com.nur1popcorn.basm.utils.graph;

public abstract class SimpleGraphAdapter<V, E> implements SimpleGraph<V, E> {
    private final SimpleGraph<V, E> graph;

    public SimpleGraphAdapter(SimpleGraph<V, E> graph) {
        this.graph = graph;
    }

    @Override
    public void addVertex(V v) {

    }
}
