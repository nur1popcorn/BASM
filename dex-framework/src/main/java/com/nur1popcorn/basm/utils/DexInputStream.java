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

import java.io.IOException;
import java.io.InputStream;

import static com.nur1popcorn.basm.DexConstants.*;

public abstract class DexInputStream extends InputStream {
    private InputStream in;

    private DexInputStream(InputStream in) {
        this.in = in;
    }

    public abstract short readShort() throws IOException;
    public abstract int readInt() throws IOException;
    public abstract long readLong() throws IOException;

    public byte readByte() throws IOException {
        return (byte) read();
    }

    public int readLeb128() throws IOException {
        int res = 0;
        byte read;
        do {
            read = readByte();
            res = (res << 7) | (read & 0x7f);
        } while((read & 0x80) != 0);
        return res;
    }

    @Override
    public final int read() throws IOException {
        return in.read();
    }

    public static DexInputStream create(InputStream in, int constant) {
        if(constant == ENDIAN_CONSTANT)
            return new LEDexInputStream(in);
        else // if(constant == REVERSE_ENDIAN_CONSTANT)
            return new BEDexInputStream(in);
    }

    private static final class LEDexInputStream extends DexInputStream {
        public LEDexInputStream(InputStream in) {
            super(in);
        }

        @Override
        public short readShort() throws IOException {
            return (short) (readByte() |
                           (readByte() << 8));
        }

        @Override
        public int readInt() throws IOException {
            return readByte()        |
                  (readByte() << 8)  |
                  (readByte() << 16) |
                  (readByte() << 24);
        }

        @Override
        public long readLong() throws IOException {
            return ((long) readByte() & 0xff)        |
                  (((long) readByte() & 0xff) << 8)  |
                  (((long) readByte() & 0xff) << 16) |
                  (((long) readByte() & 0xff) << 24) |
                  (((long) readByte() & 0xff) << 32) |
                  (((long) readByte() & 0xff) << 40) |
                  (((long) readByte() & 0xff) << 48) |
                  (((long) readByte() & 0xff) << 56);
        }
    }

    private static final class BEDexInputStream extends DexInputStream {
        public BEDexInputStream(InputStream in) {
            super(in);
        }

        @Override
        public short readShort() throws IOException {
            return (short) ((readByte() << 8) |
                             readByte());
        }

        @Override
        public int readInt() throws IOException {
            return (readByte() << 24) |
                   (readByte() << 16)  |
                   (readByte() << 8) |
                    readByte();
        }

        @Override
        public long readLong() throws IOException {
            return (((long) readByte() & 0xff) << 56) |
                   (((long) readByte() & 0xff) << 48) |
                   (((long) readByte() & 0xff) << 40) |
                   (((long) readByte() & 0xff) << 32) |
                   (((long) readByte() & 0xff) << 24) |
                   (((long) readByte() & 0xff) << 16) |
                   (((long) readByte() & 0xff) << 8) |
                    ((long) readByte() & 0xff);
        }
    }
}
