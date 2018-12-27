package com.nur1popcorn.basm.root.items.data.annotation_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class AnnotationElement {
    private final int nameIdx;
    private final EncodedValue value;

    public AnnotationElement(ByteBuffer byteBuffer) {
        nameIdx = Leb128.readULeb128i(byteBuffer);
        value = new EncodedValue(byteBuffer);
    }
}
