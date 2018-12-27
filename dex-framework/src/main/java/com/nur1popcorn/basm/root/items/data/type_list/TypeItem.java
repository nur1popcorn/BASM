package com.nur1popcorn.basm.root.items.data.type_list;

import java.nio.ByteBuffer;

public class TypeItem {
    private final short typeIdx;

    public TypeItem(ByteBuffer byteBuffer) {
        typeIdx = byteBuffer.getShort();
    }
}
