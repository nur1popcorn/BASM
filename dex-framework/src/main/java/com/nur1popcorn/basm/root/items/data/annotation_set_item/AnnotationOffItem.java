package com.nur1popcorn.basm.root.items.data.annotation_set_item;

import java.nio.ByteBuffer;

public class AnnotationOffItem {
    private final int annotationOff;

    public AnnotationOffItem(ByteBuffer byteBuffer) {
        annotationOff = byteBuffer.getInt();
    }
}
