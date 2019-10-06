package com.nur1popcorn.basm.utils.graph;

public final class Graphs {
    // prevent construction :/
    private Graphs()
    {}

    public static <V, E> DirectedGraph<V, E> unmodifiableGraph(SimpleGraph<V, E> graph) {
        return new ImmutableGraphAdapter<>(graph);
    }
}
