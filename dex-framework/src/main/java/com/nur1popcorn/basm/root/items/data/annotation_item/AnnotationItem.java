package com.nur1popcorn.basm.root.items.data.annotation_item;

import java.nio.ByteBuffer;

public class AnnotationItem {
    // Build visibility.
    private final byte VISIBILITY_BUILD = 0x00;
    // Runtime visibility.
    private final byte VISIBILITY_RUNTIME = 0x01;
    // System visibility.
    private final byte VISIBILITY_SYSTEM = 0x02;

    private final byte visibility;
    private final EncodedAnnotation annotation;

    public AnnotationItem(ByteBuffer byteBuffer) {
        visibility = (byte) (byteBuffer.get() & 0xFF);
        annotation = new EncodedAnnotation(byteBuffer);
    }
}
