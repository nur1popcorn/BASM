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
    private long position;

    private DexInputStream(InputStream in) {
        this.in = in;
    }

    public long position() {
        return position;
    }

    public void skipBytes(long n) throws IOException {
        long i = skip(n);
        while(i < n)
            i += skip(n);
        position += n;
    }

    public void readFully(byte bytes[]) throws IOException {
        int i = read(bytes);
        while(i < bytes.length)
            i += read(bytes, i, bytes.length);
        position += i;
    }

    /**
     * Skips bytes until the position is aligned to a multiple of 4.
     */
    public void alignToFourBytes() throws IOException {
        skipBytes(-position & 0x3L);
    }

    public byte readByte() throws IOException {
        return (byte) read();
    }

    public int readLeb128() throws IOException {
        int res = readByte();
        if((res & 0x80) == 0) {
            res = (res & 0x7f) | readByte() << 7;
            if((res & 0x4000) == 0) {
                res = (res & 0x7f) | readByte() << 14;
                if((res & 0x200000) == 0) {
                    res = (res & 0x7f) | readByte() << 21;
                    if((res & 0x10000000) == 0)
                        res = (res & 0x7f) | readByte() << 24;
                }
            }
        }
        return res;
    }

    public abstract short readShort() throws IOException;
    public abstract int readInt() throws IOException;
    public abstract long readLong() throws IOException;


    public int readUnsignedShort() throws IOException {
        return readShort() & 0xffff;
    }

    @Override
    public final int read() throws IOException {
        position++;
        return in.read();
    }

    @Override
    public int available() throws IOException {
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    public DexInputStream reverseOrder() {
        final DexInputStream din = this instanceof LittleEndianInputStream ?
            new BigEndianInputStream(in) : new LittleEndianInputStream(in);
        din.position = position;
        return din;
    }

    public static DexInputStream create(InputStream in, int constant) {
        if(constant == ENDIAN_CONSTANT)
            return new LittleEndianInputStream(in);
        return new BigEndianInputStream(in);
    }

    private static final class LittleEndianInputStream extends DexInputStream {
        private LittleEndianInputStream(InputStream in) {
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

    private static final class BigEndianInputStream extends DexInputStream {
        private BigEndianInputStream(InputStream in) {
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
                   (readByte() << 16) |
                   (readByte() << 8)  |
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
                   (((long) readByte() & 0xff) << 8)  |
                    ((long) readByte() & 0xff);
        }
    }
}
