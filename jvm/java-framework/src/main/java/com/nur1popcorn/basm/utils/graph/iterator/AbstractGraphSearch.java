package com.nur1popcorn.basm.utils.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.*;

abstract class AbstractGraphSearch<V, E> implements GraphIterator<V> {
    final DirectedGraph<V, E> graph;
    final LinkedList<V> queue = new LinkedList<>();
    final Set<V> discovered = new HashSet<>();

    int depth = -1, count = 1;

    AbstractGraphSearch(DirectedGraph<V, E> graph, V start) {
        this.graph = graph;
        discovered.add(start);
        queue.add(start);
    }

    @Override
    public final int getDepth() {
        return depth;
    }

    @Override
    public final boolean hasNext() {
        return !queue.isEmpty();
    }
}
