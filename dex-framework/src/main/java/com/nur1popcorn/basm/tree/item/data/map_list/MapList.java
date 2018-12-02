package com.nur1popcorn.basm.tree.item.data.map_list;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class MapList {
    private final int size;
    public final MapItem[] list;

    public MapList(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        list = new MapItem[size];
        for (int i = 0; i < list.length; i++)
            list[i] = new MapItem(byteBuffer);
    }
}
