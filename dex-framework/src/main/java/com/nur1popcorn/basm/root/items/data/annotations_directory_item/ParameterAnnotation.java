package com.nur1popcorn.basm.root.items.data.annotations_directory_item;

import java.nio.ByteBuffer;

public class ParameterAnnotation {
    private final int methodIdx, annotationsOff;

    public ParameterAnnotation(ByteBuffer byteBuffer) {
        methodIdx = byteBuffer.getInt();
        annotationsOff = byteBuffer.getInt();
    }
}
