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

package com.nur1popcorn.basm.util;

import com.nur1popcorn.basm.utils.HungarianAlgorithm;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public final class HungarianAlgorithmTest {
    @Test
    public void testSimple() {
        HungarianAlgorithm algorithm = new HungarianAlgorithm(
            new double[][] { { 1, 2, 3 },
                             { 4, 2, 1 },
                             { 7, 8, 3 } });
        assertArrayEquals(new int[] { 0, 1, 2 },
                          algorithm.compute());

        algorithm = new HungarianAlgorithm(
            new double[][] { { 1,   4,  7,  8 },
                             { 3,   2,  6,  4 },
                             { 21, 12, 10,  1 },
                             { 21, 30,  2, 17 } });
        assertArrayEquals(new int[] { 0, 1, 3, 2 },
                          algorithm.compute());

        algorithm = new HungarianAlgorithm(
            new double[][] { { 35, 49, 24 },
                             {  8, 18,  7 },
                             { 20, 46,  2 } });
        assertArrayEquals(new int[] { 0, 1, 2 },
                          algorithm.compute());

        algorithm = new HungarianAlgorithm(
            new double[][] { {  5, 21, 29 },
                             { 43, 28,  4 },
                             { 36, 42, 14 } });
        assertArrayEquals(new int[] { 0, 1, 2 },
                          algorithm.compute());

        algorithm = new HungarianAlgorithm(
            new double[][] { { 6, 23 },
                             { 3, 35 } });
        assertArrayEquals(new int[] { 1, 0 },
                          algorithm.compute());

        algorithm = new HungarianAlgorithm(
            new double[][] { { 0, 0 },
                             { 0, 0 } });
        assertArrayEquals(new int[] { 0, 1 },
                          algorithm.compute());
    }

    /**
     * @param n The dimension of the nxn matrix.
     * @return A matrix nxn filled with random values.
     */
    private static double[][] randomMatrix(int n) {
        final Random random = new Random();
        final double matrix[][] = new double[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                matrix[i][j] = random.nextDouble() * 1000000;
        return matrix;
    }

    /**
     * @param counter The combination which is being tested.
     * @return True if all combinations have been tried.
     */
    private static boolean done(int counter[]) {
        for(int i = 0; i < counter.length; i++)
            if(counter[i] != counter.length - 1)
                return false;
        return true;
    }

    /**
     * @param counter The combination which is being tested.
     * @return True if the current assignment is valid.
     */
    private static boolean valid(int counter[]) {
        for(int i = 0; i < counter.length; i++)
            for(int j = 0; j < counter.length; j++)
                if(counter[i] == counter[j] && i != j)
                    return false;
        return true;
    }

    /**
     * @param matrix The matrix whose values should be summed.
     * @param assignment The chosen assignment.
     *
     * @return The sum of all chosen assignments.
     */
    private static double sum(double matrix[][], int assignment[]) {
        double sum = 0;
        for(int i = 0; i < matrix.length; i++)
            sum += matrix[i][assignment[i]];
        return sum;
    }

    /**
     * A simple brute force solution for the optimization problem (not intended to be actually used).
     *
     * @param matrix The matrix for which the optimal assignment should be computed.
     * @return The optimal assignment for this matrix.
     */
    private static int[] optimize(double matrix[][]) {
        int res[] = new int[matrix.length];
        double min = Double.MAX_VALUE;

        final int counter[] = new int[matrix.length];
        while(!done(counter)) {
            if(valid(counter)) {
                double sum = sum(matrix, counter);
                if(sum < min) {
                    min = sum;
                    res = Arrays.copyOf(counter, matrix.length);
                }
            }

            for(int i = 0; i < matrix.length; i++) {
                if(counter[i]++ < matrix.length - 1)
                    break;
                counter[i] %= matrix.length;
            }
        }
        return res;
    }

    @Test
    public void testRandom() {
        final Random random = new Random();
        for(int i = 0; i < 5000; i++) {
            final double matrix[][] = randomMatrix(random.nextInt(5) + 2);
            final HungarianAlgorithm algorithm = new HungarianAlgorithm(matrix);
            assertEquals(sum(matrix, optimize(matrix)),
                         sum(matrix, algorithm.compute()), 0.01);
        }
    }

    @Test
    public void testLarge() {
        for(int i = 0; i < 100; i++)
            new HungarianAlgorithm(randomMatrix(i))
                .compute();
    }

}
