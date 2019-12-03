package com.nur1popcorn.basm.utils.graph.cfg.crossings;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;

import java.util.*;

import static com.nur1popcorn.basm.utils.graph.SimpleGraph.CountIterator;

public final class CrossingMatrix<V, E> {
    private Map<V, Integer> vertexMap = new HashMap<>();
    private int matrix[][];

    /**
     * Constructs the crossing matrix for this section.
     *
     * @param graph The graph for which the crossing matrix will be constructed.
     * @param a The 1st layer.
     * @param b The 2nd layer.
     */
    public void compute(DirectedGraph<V, E> graph, List<V> a, List<V> b) {
        final int n = b.size();
        if(matrix == null || matrix.length < n)
            matrix = new int[n][n];

        vertexMap.clear();
        for(CountIterator<V> ita = new CountIterator<>(a.iterator()); ita.hasNext(); )
            vertexMap.put(ita.next(), ita.count());
        for(CountIterator<V> itb = new CountIterator<>(b.iterator()); itb.hasNext(); )
            vertexMap.put(itb.next(), itb.count());

        final Set<V> as = new HashSet<>(a);
        final ListIterator<V> itb = b.listIterator();
        if(itb.hasNext())
            for(V v = itb.next(), w; itb.hasNext(); v = w) {
                w = itb.next();
                // construct all neighbour combinations.
                final Set<V> vns = graph.getInNeighbours(v);
                vns.retainAll(as);
                for(V vn : vns) {
                    final int l = vertexMap.get(vn);
                    final Set<V> wns = graph.getInNeighbours(w);
                    wns.retainAll(as);
                    for(V wn : wns) {
                        final int j = itb.previousIndex(), i = j - 1;
                        final int r = vertexMap.get(wn);
                        if(l > r)
                            matrix[i][j]++;
                        if(r > l)
                            matrix[j][i]++;
                    }
                }
            }
    }

    /**
     * @param v The 1st vertex.
     * @param w The 2nd vertex.
     *
     * @return The number of crossings caused by these vertices.
     */
    public int crossings(V v, V w) {
        final Integer vi = vertexMap.get(v), wi = vertexMap.get(w);
        if(vi == null || wi == null)
            throw new IllegalArgumentException("Either of the given vertices is invalid!");
        return matrix[vi][wi];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CrossingMatrix{ vertexMap=")
            .append(vertexMap)
            .append(", matrix=");
        for(int i = 0; i < matrix.length; i++)
            sb.append(Arrays.toString(matrix))
              .append(", ");
        sb.append("}");
        return sb.toString();
    }
}
