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

public final class ApproximateEditDistance<V, E extends SimpleEdge<V>> implements EditDistanceStrategy<SimpleGraph<V, E>> {
    private final int edgeIns, edgeDel, edgeSub;
    private final int nodeIns, nodeDel, nodeSub;

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
     * @param e
     * @param f
     *
     * @return
     */
    private int getEdgeSubCost(E e, E f) {
        return e.equals(f) ? 0 : edgeSub;
    }

    /**
     * @param a
     * @param b
     */
    private double[][] createEdgeCostMatrix(Set<E> a, Set<E> b) {
        final int m = a.size();
        final int n = b.size();

        final double matrix[][] = new double[m + n][m + n];

        // substitute cost.
        for(CountIterator<E> ita = new CountIterator<>(a.iterator()); ita.hasNext(); ) {
            final E e = ita.next();
            for(CountIterator<E> itb = new CountIterator<>(b.iterator()); itb.hasNext(); ) {
                final E f = itb.next();
                matrix[ita.count()][itb.count()] =
                    getEdgeSubCost(e, f);
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
     * @param a
     * @param b
     */
    private int getEdgeEditCost(Set<E> a, Set<E> b) {
        final int m = a.size();
        final int n = b.size();

        if(m == 0 || n == 0)
            return Math.max(m, n);

        final double[][] matrix = createEdgeCostMatrix(a, b);
        final int assignment[] = new HungarianAlgorithm(matrix)
            .compute();

        int result = 0;
        for(int i = 0; i < matrix.length; i++)
            result += matrix[i][assignment[i]];
        return result;
    }

    /**
     * @return
     */
    private int getSubCost(V v, V w) {
        return v.equals(w) ?
            0 : nodeSub;
    }

    /**
     * @param a
     * @param b
     *
     * @return
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
                    getEdgeEditCost(a.getEdges(v),
                                    b.getEdges(w));
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
