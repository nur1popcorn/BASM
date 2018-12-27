package com.nur1popcorn.basm.root.items.data.code_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedCatchHandlerList {
    private final int size;
    private final EncodedCatchHandler[] list;

    public EncodedCatchHandlerList(ByteBuffer byteBuffer) {
        size = Leb128.readULeb128i(byteBuffer);
        list = new EncodedCatchHandler[size];
        for (int i = 0; i < list.length; i++)
            list[i] = new EncodedCatchHandler(byteBuffer);
    }
}
