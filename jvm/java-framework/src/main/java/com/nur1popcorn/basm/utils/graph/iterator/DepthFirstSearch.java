package com.nur1popcorn.basm.utils.graph.iterator;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.Set;

public final class DepthFirstSearch<V, E> extends AbstractGraphSearch<V, E> {
    public DepthFirstSearch(DirectedGraph<V, E> graph, V start) {
        super(graph, start);
    }

    @Override
    public V next() {
        // TODO: found a bug concerning the depth with this implementation of the algorithm.
        if(--count <= 0) {
            count = queue.size();
            depth++;
        }

        final V v = queue.remove();
        final Set<V> neighbours = graph.getOutNeighbours(v);
        for(V w : neighbours)
            if(!discovered.contains(w)) {
                discovered.add(w);
                queue.addFirst(w);
            }
        return v;
    }
}
