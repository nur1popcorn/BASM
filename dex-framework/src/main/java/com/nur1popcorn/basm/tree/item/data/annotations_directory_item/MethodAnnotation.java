package com.nur1popcorn.basm.tree.item.data.annotations_directory_item;

import java.nio.ByteBuffer;

public class MethodAnnotation {
    private final int methodIdx, annotationsOff;

    public MethodAnnotation(ByteBuffer byteBuffer) {
        methodIdx = byteBuffer.getInt();
        annotationsOff = byteBuffer.getInt();
    }
}
