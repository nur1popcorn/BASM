package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class TypeIdItem {
    public final int descriptorIdx;

    public TypeIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        descriptorIdx = byteBuffer.getInt();
    }
}
