package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class CallSiteIdItem {
    private final int callSiteOff;

    public CallSiteIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        callSiteOff = byteBuffer.getInt();
    }
}
