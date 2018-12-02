package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class MethodIdItem {
    public final short classIdx, protoIdx;
    public final int nameIdx;

    public MethodIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        classIdx = byteBuffer.getShort();
        protoIdx = byteBuffer.getShort();
        nameIdx = byteBuffer.getInt();
    }
}
