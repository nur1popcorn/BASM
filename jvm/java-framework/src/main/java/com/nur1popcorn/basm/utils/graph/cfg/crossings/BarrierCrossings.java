package com.nur1popcorn.basm.utils.graph.cfg.crossings;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.List;
import java.util.ListIterator;

public final class BarrierCrossings {
    // prevent construction :/
    private BarrierCrossings()
    {}

    /**
     * A simple way of counting the edge crossings in one section that I came up with.
     * The algorithm works by assigning each vertex a cost for being reached by another
     * vertex from the right. The cost is based on the number of barrier (edge) crossings
     * that such a connection would cause.
     *
     * @param graph The graph for which the crossings should be counted in one section.
     * @param a The 1st layer.
     * @param b The 2nd layer.
     *
     * @return The number of crossings between the two layers.
     */
    public static <V, E> int countCrossings(DirectedGraph<V, E> graph, List<V> a, List<V> b) {
        int cost = 0;
        final int costs[] = new int[b.size()];
        for(V v : a) {
            // compute the cost for this node.
            for(final ListIterator<V> itb = b.listIterator(); itb.hasNext(); ) {
                final V w = itb.next();
                if(graph.hasEdge(v, w))
                    cost += costs[itb.previousIndex()];
            }

            // increase the cost for connecting to all other nodes.
            for(V w : b)
                if(graph.hasEdge(v, w))
                    for(ListIterator<V> itb = b.listIterator(); itb.hasNext(); ) {
                        final V u = itb.next();
                        if(u == w)
                            break;
                        costs[itb.previousIndex()]++;
                    }
        }

        return cost;
    }
}
