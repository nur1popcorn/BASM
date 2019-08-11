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

public final class LevenshteinDistance implements EditDistanceStrategy<CharSequence> {
    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    @Override
    public int distance(CharSequence a, CharSequence b) {
        int m = a.length();
        int n = b.length();

        if(m == 0)
            return n;
        if(n++ == 0)
            return m;

        int v0[] = new int[++m];
        int v1[] = new int[m];

        for(int i = 0; i < m; i++)
            v0[i] = i;

        for(int i = 1; i < n; i++) {
            v1[0] = i + 1;

            final char pivot = b.charAt(i - 1);
            for(int j = 1; j < m; j++)
                v1[j] = min(
                    v1[j - 1] + 1,
                    v0[j] + 1,
                    a.charAt(j - 1) == pivot ?
                        v0[j - 1] : v0[j - 1] + 1);

            {
                final int tmp[] = v0;
                v0 = v1;
                v1 = tmp;
            }
        }

        return v0[m - 1];
    }
}
