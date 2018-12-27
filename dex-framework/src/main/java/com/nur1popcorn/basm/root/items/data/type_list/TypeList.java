package com.nur1popcorn.basm.root.items.data.type_list;

import java.nio.ByteBuffer;

public class TypeList {
    private final int size;
    private final TypeItem[] list;

    public TypeList(ByteBuffer byteBuffer) {
        // Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        list = new TypeItem[size];
        for (int i = 0; i < list.length; i++)
            list[i] = new TypeItem(byteBuffer);
    }
}
