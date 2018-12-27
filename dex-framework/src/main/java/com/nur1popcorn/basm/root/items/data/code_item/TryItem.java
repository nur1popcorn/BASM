package com.nur1popcorn.basm.root.items.data.code_item;

import java.nio.ByteBuffer;

public class TryItem {
    private final int startAddr;
    private final short insnCount,
                        handlerOff;

    public TryItem(ByteBuffer byteBuffer) {
        startAddr = byteBuffer.getInt();
        insnCount = byteBuffer.getShort();
        handlerOff = byteBuffer.getShort();
    }
}
