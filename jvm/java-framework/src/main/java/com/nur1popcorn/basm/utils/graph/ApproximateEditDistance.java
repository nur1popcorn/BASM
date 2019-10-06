/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm.utils.graph;

import com.nur1popcorn.basm.utils.EditDistanceStrategy;
import com.nur1popcorn.basm.utils.HungarianAlgorithm;

import java.util.Set;

import static com.nur1popcorn.basm.utils.graph.SimpleGraph.CountIterator;

/**
 * The {@link ApproximateEditDistance} class houses an algorithm for approximating graph edit distance.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class ApproximateEditDistance<V, E> implements EditDistanceStrategy<SimpleGraph<V, E>> {
    private final int edgeIns, edgeDel, edgeSub;
    private final int nodeIns, nodeDel, nodeSub;

    /**
     * @param edgeIns The cost of inserting an edge.
     * @param edgeDel The cost of deleting an edge.
     * @param edgeSub The cost of substituting an edge.
     * @param nodeIns The cost of inserting a vertex.
     * @param nodeDel The cost of deleting a vertex.
     * @param nodeSub The cost of substituting a vertex.
     */
    public ApproximateEditDistance(int edgeIns, int edgeDel, int edgeSub, int nodeIns, int nodeDel, int nodeSub) {
        this.edgeIns = edgeIns;
        this.edgeDel = edgeDel;
        this.edgeSub = edgeSub;
        this.nodeIns = nodeIns;
        this.nodeDel = nodeDel;
        this.nodeSub = nodeSub;
    }

    public ApproximateEditDistance() {
        this(1, 1, 1, 1, 1, 1);
    }

    /**
     * @param v The 1st pivot vertex.
     * @param u The 1st pivot neighbour.
     *
     * @param w The 2nd pivot vertex.
     * @param t The 2nd pivot neighbour.
     *
     * @return The cost of substituting one edge for the other.
     */
    private int getEdgeSubCost(V v, V u, V w, V t) {
        return (v == w && u == t) ||
               (v == t && u == w) ?
            0 : edgeSub;
    }

    /**
     * @param v The 1st starting vertex.
     * @param a The 1st of neighbours.
     *
     * @param w The 2nd starting vertex.
     * @param b The 2nd of neighbours.
     *
     * @return The edge cost matrix.
     */
    private double[][] createEdgeCostMatrix(V v, Set<V> a, V w, Set<V> b) {
        final int m = a.size();
        final int n = b.size();

        final double matrix[][] = new double[m + n][m + n];

        // substitute cost.
        for(CountIterator<V> ita = new CountIterator<>(a.iterator()); ita.hasNext(); ) {
            final V u = ita.next();
            for(CountIterator<V> itb = new CountIterator<>(b.iterator()); itb.hasNext(); ) {
                final V t = itb.next();
                matrix[ita.count()][itb.count()] =
                    getEdgeSubCost(v, u, w, t);
            }
        }

        // delete cost.
        for(int i = 0; i < m; i++)
            for(int j = 0; j < m; j++)
                matrix[i][j + n] = Double.MAX_VALUE;

        for(int i = 0; i < m; i++)
            matrix[i][i + n] = edgeDel;

        // insert cost.
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                matrix[i + m][j] = Double.MAX_VALUE;

        for(int i = 0; i < n; i++)
            matrix[i + m][i] = edgeIns;

        return matrix;
    }

    /**
     * @param v The 1st starting vertex.
     * @param a The 1st of neighbours.
     *
     * @param w The 2nd starting vertex.
     * @param b The 2nd of neighbours.
     *
     * @return The approximate cost of substituting the 1st set of edges for th 2nd.
     */
    private int getEdgeEditCost(V v, Set<V> a, V w, Set<V> b) {
        final int m = a.size();
        final int n = b.size();

        if(m == 0 || n == 0)
            return Math.max(m, n);

        final double[][] matrix = createEdgeCostMatrix(v, a, w, b);
        final int assignment[] = new HungarianAlgorithm(matrix)
            .compute();

        int result = 0;
        for(int i = 0; i < matrix.length; i++)
            result += matrix[i][assignment[i]];
        return result;
    }

    /**
     * @param v The 1st vertex which should be compared to the 2nd.
     * @param w The 2nd vertex which should be compared to the 1st.
     *
     * @return The cost of substituting one vertex for the other.
     */
    private int getSubCost(V v, V w) {
        return v.equals(w) ?
            0 : nodeSub;
    }

    /**
     * @param a The 1st graph.
     * @param b The 2nd graph.
     *
     * @return The final cost matrix.
     */
    private double[][] createCostMatrix(SimpleGraph<V, E> a, SimpleGraph<V, E> b) {
        final int m = a.size();
        final int n = b.size();

        final double matrix[][] = new double[m + n][m + n];

        // substitute cost.
        for(CountIterator<V> ita = a.countIterator(); ita.hasNext(); ) {
            final V v = ita.next();
            for(CountIterator<V> itb = b.countIterator(); itb.hasNext(); ) {
                final V w = itb.next();
                matrix[ita.count()][itb.count()] =
                    getSubCost(v, w) +
                    getEdgeEditCost(v, a.getNeighbours(v),
                                    w, b.getNeighbours(w));
            }
        }

        // delete cost.
        for(int i = 0; i < m; i++)
            for(int j = 0; j < m; j++)
                matrix[i][j + n] = Double.MAX_VALUE;

        for(int i = 0; i < m; i++)
            matrix[i][i + n] = nodeDel;

        // insert cost.
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                matrix[i + m][j] = Double.MAX_VALUE;

        for(int i = 0; i < n; i++)
            matrix[i + m][i] = nodeIns;

        return matrix;
    }

    @Override
    public int distance(SimpleGraph<V, E> a, SimpleGraph<V, E> b) {
        final double matrix[][] = createCostMatrix(a, b);
        final int assignment[] = new HungarianAlgorithm(matrix)
            .compute();

        int result = 0;
        for(int i = 0; i < matrix.length; i++)
            result += matrix[i][assignment[i]];
        return result;
    }
}
