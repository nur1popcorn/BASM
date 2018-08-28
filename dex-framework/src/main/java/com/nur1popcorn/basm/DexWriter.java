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

package com.nur1popcorn.basm;

public final class DexWriter {
    private static final int MOD_ADLER = 65521;

    /**
     * Computes the adler32 checksum for the given data set.
     *
     * @param data The data set for which the checksum should be calculated.
     * @return The adler32 checksum.
     */
    private static int fastAdler32(byte data[]) {
        int a = 1, b = 0, i = 0;
        /* The number 4103 (0x1007) can be calculated by assuming the starting index 1 and a data set
         * consisting of 0xff values { 0xff, 0xff, 0xff, ... }. Through making these assumptions one
         * can deduce the following formula which represents the sum of the local variable 'b' over time
         * where 'n' is the number of iterations completed: \sum_{i=1}^{n}\left(1+\sum_{j=1}^{i}255\right)
         * Simplifying this formula to n+((255n+255)*n)/2 then setting it equal to 0x7fffffff and finally
         * solving it for 'n' will then reveal 'n' to be 4103.519623.
         */
        if(data.length >= 0x1007) {
            for(; i < 0x1007; i++) {
                a += data[i] & 0xff;
                b += a;
            }
            // 'a' overflows after 0x808080 iterations therefore no modulo operation is needed here.
            // a %= MOD_ADLER;
            b %= MOD_ADLER;
        }

        while(i < data.length) {
            /* The number 3854 (0xf0e) can be calculated by assuming the starting index 65521 and a data set
             * consisting of 0xff values { 0xff, 0xff, 0xff, ... }. Through making these assumptions one
             * can deduce the following formula which represents the sum of the local variable 'b' over time
             * where 'n' is the number of iterations completed: \sum_{i=1}^{n}\left(65521+\sum_{j=1}^{i}255\right)
             * Simplifying this formula to 65521n+((255n+255)*n)/2 then setting it equal to 0x7fffffff and finally
             * solving it for 'n' will then reveal 'n' to be 3854.645242.
             */
            final int block = Math.min(data.length, i + 0xf0e);
            for(; i < block; i++) {
                a += data[i] & 0xff;
                b += a;
            }
            a %= MOD_ADLER;
            b %= MOD_ADLER;
        }
        return b << 16 | a;
    }
}
