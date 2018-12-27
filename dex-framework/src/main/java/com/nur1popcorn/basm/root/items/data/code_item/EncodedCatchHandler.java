package com.nur1popcorn.basm.root.items.data.code_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedCatchHandler {
    private final int size;
    private final EncodedTypeAddrPair[] handlers;
    private int catchAllAddr;

    public EncodedCatchHandler(ByteBuffer byteBuffer) {
        size = Leb128.readLeb128i(byteBuffer);
        handlers = new EncodedTypeAddrPair[Math.abs(size)];
        for (int i = 0; i < handlers.length; i++)
            handlers[i] = new EncodedTypeAddrPair(byteBuffer);
        if (size <= 0)
            catchAllAddr = Leb128.readULeb128i(byteBuffer);
    }
}
