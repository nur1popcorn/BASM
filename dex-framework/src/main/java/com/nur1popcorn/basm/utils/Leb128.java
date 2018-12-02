package com.nur1popcorn.basm.utils;

import java.nio.ByteBuffer;

public class Leb128 {
    private Leb128() {}

    public static int readULeb128p1(ByteBuffer byteBuffer) {
        return readULeb128i(byteBuffer) - 1;
    }

    public static int readULeb128i(ByteBuffer byteBuffer) {
        int value = 0;
        int count = 0;
        int b = byteBuffer.get();
        while ((b & 0x80) != 0) {
            value |= (b & 0x7f) << count;
            count += 7;
            b = byteBuffer.get();
        }
        value |= (b & 0x7f) << count;
        return value;
    }

    public static int readLeb128i(ByteBuffer byteBuffer) {
        int bitpos = 0;
        int vln = 0;
        do {
            int inp = byteBuffer.get();
            vln |= (inp & 0x7F) << bitpos;
            bitpos += 7;
            if ((inp & 0x80) == 0) {
                break;
            }
        } while (true);
        if (((1L << (bitpos - 1)) & vln) != 0) {
            vln -= (1L << bitpos);
        }
        return vln;
    }
}
