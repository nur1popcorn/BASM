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

package com.nur1popcorn.basm.utils;

import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

/**
 * The {@link HungarianAlgorithm} class houses an implementation of the hungarian algorithm based on
 * these descriptions of the algorithm:
 * <a href="https://en.wikipedia.org/wiki/Hungarian_algorithm"> Hungarian algorithm </a>
 * <a href="http://www.netlib.org/utk/lsi/pcwLSI/text/node222.html"> 9.8.2 The Sequential Algorithm </a>
 * <a href="http://math.harvard.edu/archive/20_spring_05/handouts/assignment_overheads.pdf"> The Assignment Problem and the Hungarian Method </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class HungarianAlgorithm {
    private final BitSet colVector = new BitSet();
    private final BitSet rowVector = new BitSet();

    /* An nxn 2 bit matrix used to mark Z* and Z' values. */
    private final BitSet mask = new BitSet();

    private final double matrix[][];
    private final int n;

    /**
     * @param matrix The nxn matrix which should be optimized.
     */
    public HungarianAlgorithm(double matrix[][]) {
        this.n = matrix.length;
        this.matrix = new double[n][n];

        for(int i = 0; i < n; i++) {
            if(matrix[i].length != n)
                throw new IllegalArgumentException(
                    "The matrix provided must be square.");
            this.matrix[i] = Arrays.copyOf(matrix[i], n);
        }
    }

    /**
     * @return A vector which holds the assignments for each column.
     */
    public int[] compute() {
        reduce();
        mark();

        while(!covered()) {
            int z0[] = findZero(), z1[];
            while(createZeros(z0[1]) || coverZStarRow(z0[0]))
                z0 = findZero();

            final LinkedList<int[]> sequence = new LinkedList<>();
            sequence.push(z0);

            while((z1 = findZStarColumn(z0[1]))[0] != -1) {
                z0 = findZPrimeRow(z1[0]);
                sequence.push(z1);
                sequence.push(z0);
            }

            z0 = sequence.pop();
            star(z0[0], z0[1]);

            while(!sequence.isEmpty()) {
                z1 = sequence.pop();
                removeStar(z1[0], z1[1]);

                z0 = sequence.pop();
                star(z0[0], z0[1]);
            }

            clear();
            cover();
        }

        final int result[] = new int[n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(isStarred(j, i))
                    result[j] = i;

        return result;
    }

    /**
     * Performs the 1st and 2nd step of the hungarian algorithm.
     */
    private void reduce() {
        /* Find the minimum in each row / col and subtract the minimum from each entry in that row / col.
         * m = \begin{bmatrix}
         *         x_1 & y_1 & z_1 \\
         *         x_2 & y_2 & z_2 \\
         *         x_3 & y_3 & z_3
         *     \end{bmatrix}
         *
         * Step 1 / 2:
         * m_{r} = \begin{bmatrix}
         *             0    & y_1' & z_1' \\
         *             x_2' & y_2' & 0    \\
         *             x_3' & 0    & z_3'
         *         \end{bmatrix}
         */
        for(int i = 0; i < n; i++) {
            double min = Double.MAX_VALUE;
            for(int j = 0; j < n; j++)
                if(matrix[j][i] < min)
                    min = matrix[j][i];

            for(int j = 0; j < n; j++)
                matrix[j][i] -= min;
        }

        for(int i = 0; i < n; i++) {
            double min = Double.MAX_VALUE;
            for(int j = 0; j < n; j++)
                if(matrix[i][j] < min)
                    min = matrix[i][j];

            for(int j = 0; j < n; j++)
                matrix[i][j] -= min;
        }
    }

    /**
     * Marks the value in the matrix at the given position as Z*.
     *
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     */
    private void star(int row, int col) {
        mask.set(2 * n * row + col * 2);
    }

    /**
     * Removes the Z* mark from the value in the matrix at the given position.
     *
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     */
    private void removeStar(int row, int col) {
        mask.clear(2 * n * row + col * 2);
    }

    /**
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     *
     * @return True if the entry at that position is starred.
     */
    private boolean isStarred(int row, int col) {
        return mask.get(2 * n * row + col * 2);
    }

    /**
     * Marks the value in the matrix at the given position as Z'.
     *
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     */
    private void prime(int row, int col) {
        mask.set(2 * n * row + (col * 2 + 1));
    }

    /**
     * Removes the Z' mark from the value in the matrix at the given position.
     *
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     */
    private void removePrime(int row, int col) {
        mask.clear(2 * n * row + (col * 2 + 1));
    }

    /**
     * @param row The row of the matrix entry.
     * @param col The column of the matrix entry.
     *
     * @return True if the entry at that position is prime.
     */
    private boolean isPrime(int row, int col) {
        return mask.get(2 * n * row + (col * 2 + 1));
    }

    /**
     * Mark each zero "Z" with a star "Z*". If there is no other Z* in the matrix's row or column.
     */
    private void mark() {
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(matrix[i][j] == 0 && !colVector.get(j)) {
                    star(i, j);
                    colVector.set(j);
                    break;
                }
    }

    /**
     * Covers every column which contains a Z*.
     */
    private void cover() {
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(isStarred(i, j))
                    colVector.set(j);
    }

    /**
     * @return True if all columns are covered.
     */
    private boolean covered() {
        return colVector.cardinality() == n;
    }

    /**
     * @return An uncovered zero or [-1, -1] if there exists no uncovered zero.
     */
    private int[] findZero() {
        for(int i = 0; i < n; i++)
            if(!rowVector.get(i))
                for(int j = 0; j < n; j++)
                    if(!colVector.get(j) && matrix[i][j] == 0) {
                        prime(i, j);
                        return new int[] { i, j };
                    }
        return new int[] { -1, -1 };
    }

    /**
     * @param col The column of the newly created Z' in the matrix.
     * @return True if additional zeros were created.
     */
    private boolean createZeros(int col) {
        if(col == -1) {
            double min = Double.MAX_VALUE;
            for(int i = 0; i < n; i++)
                if(!rowVector.get(i))
                    for(int j = 0; j < n; j++)
                        if(!colVector.get(j) && matrix[i][j] < min)
                            min = matrix[i][j];

            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++) {
                    if(!colVector.get(j))
                        matrix[i][j] -= min;
                    if(rowVector.get(i))
                        matrix[i][j] += min;
                }

            return true;
        }
        return false;
    }

    /**
     * @param row The row of the newly created Z' in the matrix.
     * @return True if a Z* exists in the row of the Z'
     */
    private boolean coverZStarRow(int row) {
        for(int i = 0; i < n; i++)
            if(isStarred(row, i)) {
                rowVector.set(row);
                colVector.clear(i);
                return true;
            }
        return false;
    }

    /**
     * @param col The column in which the Z* should be searched.
     * @return The position at which the Z* is located or [-1, -1] if no Z* is located within that column.
     */
    private int[] findZStarColumn(int col) {
        for(int i = 0; i < n; i++)
            if(isStarred(i, col))
                return new int[] { i, col };
        return new int[] { -1, -1 };
    }

    /**
     * @param row The row in which the Z' should be searched.
     * @return The position at which the Z' is located or [-1, -1] if no Z' is located within that row.
     */
    private int[] findZPrimeRow(int row) {
        for(int i = 0; i < n; i++)
            if(isPrime(row, i))
                return new int[] { row, i };
        return new int[] { -1, -1 };
    }

    /**
     * Clear all primes, rows and columns.
     */
    private void clear() {
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                removePrime(i, j);

        rowVector.clear();
        colVector.clear();
    }
}
