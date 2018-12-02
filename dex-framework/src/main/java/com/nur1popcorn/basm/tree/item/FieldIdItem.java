package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class FieldIdItem {
    public final short classIdx, typeIdx;
    public final int nameIdx;

    public FieldIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        classIdx = byteBuffer.getShort();
        typeIdx = byteBuffer.getShort();
        nameIdx = byteBuffer.getInt();
    }
}
