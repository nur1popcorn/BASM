package com.nur1popcorn.basm.tree.item.data.annotation_set_item;

import java.nio.ByteBuffer;

public class AnnotationOffItem {
    private final int annotationOff;

    public AnnotationOffItem(ByteBuffer byteBuffer) {
        annotationOff = byteBuffer.getInt();
    }
}
