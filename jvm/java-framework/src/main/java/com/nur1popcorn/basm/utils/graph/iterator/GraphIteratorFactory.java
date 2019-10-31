package com.nur1popcorn.basm.utils.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

public interface GraphIteratorFactory<V, E> {
    GraphIterator<V> create(DirectedGraph<V, E> graph, V start);
}
