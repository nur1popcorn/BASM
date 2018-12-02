package com.nur1popcorn.basm.tree.item.data.type_list;

import java.nio.ByteBuffer;

public class TypeItem {
    private final short typeIdx;

    public TypeItem(ByteBuffer byteBuffer) {
        typeIdx = byteBuffer.getShort();
    }
}
