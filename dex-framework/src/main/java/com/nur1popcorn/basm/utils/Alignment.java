package com.nur1popcorn.basm.utils;

import java.nio.ByteBuffer;

public class Alignment {
    private Alignment() {}

    /**
     * Skips bytes until the position is aligned to a multiple of 4.
     */
    public static void alignToFourBytes(ByteBuffer data) {
        data.position((data.position() + 3) & ~3);
    }

    /**
     * Writes 0x00 until the position is aligned to a multiple of 4.
     */
    public void alignToFourBytesWithZeroFill(ByteBuffer data) {
        while ((data.position() & 3) != 0) {
            data.put((byte) 0);
        }
    }

    public void assertFourByteAligned(ByteBuffer data) {
        if ((data.position() & 3) != 0) {
            throw new IllegalStateException("Not four byte aligned!");
        }
    }
}
