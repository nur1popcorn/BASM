package com.nur1popcorn.basm.root.items.data.annotation_set_item;

import java.nio.ByteBuffer;

public class AnnotationSetItem {
    private final int size;
    private final AnnotationOffItem[] entries;

    public AnnotationSetItem(ByteBuffer byteBuffer) {
        // Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        entries = new AnnotationOffItem[size];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new AnnotationOffItem(byteBuffer);
        }
    }
}
