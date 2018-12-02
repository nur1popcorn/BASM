package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class StringIdItem {
    public final int stringDataOff;

    public StringIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        stringDataOff = byteBuffer.getInt();
    }
}
