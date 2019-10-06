package com.nur1popcorn.basm.utils.graph;

public final class ImmutableGraphAdapter<V, E> extends GraphAdapter<V, E> {
    public ImmutableGraphAdapter(SimpleGraph<V, E> graph) {
        super(graph);
    }

    @Override
    public void addVertex(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeVertex(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEdge(V v, V w) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEdge(V v, V w) {
        throw new UnsupportedOperationException();
    }
}
