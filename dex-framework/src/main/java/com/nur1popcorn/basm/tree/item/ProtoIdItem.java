package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class ProtoIdItem {
    public final int shortyIdx, returnTypeIdx, parametersOff;

    public ProtoIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        shortyIdx = byteBuffer.getInt();
        returnTypeIdx = byteBuffer.getInt();
        parametersOff = byteBuffer.getInt();
    }
}
