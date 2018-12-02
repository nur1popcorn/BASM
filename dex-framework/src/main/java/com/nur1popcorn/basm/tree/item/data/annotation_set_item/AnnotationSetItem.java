package com.nur1popcorn.basm.tree.item.data.annotation_set_item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class AnnotationSetItem {
    private final int size;
    private final AnnotationOffItem[] entries;

    public AnnotationSetItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        entries = new AnnotationOffItem[size];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new AnnotationOffItem(byteBuffer);
        }
    }
}
