package com.nur1popcorn.basm.root.items.data.annotations_directory_item;

import java.nio.ByteBuffer;

public class FieldAnnotation {
    private final int fieldIdx, annotationsOff;

    public FieldAnnotation(ByteBuffer byteBuffer) {
        fieldIdx = byteBuffer.getInt();
        annotationsOff = byteBuffer.getInt();
    }
}
