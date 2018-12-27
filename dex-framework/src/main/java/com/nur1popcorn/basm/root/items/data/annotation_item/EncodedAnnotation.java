package com.nur1popcorn.basm.root.items.data.annotation_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedAnnotation {
    private final int typeIdx, size;
    private final AnnotationElement[] elements;

    public EncodedAnnotation(ByteBuffer byteBuffer) {
        typeIdx = Leb128.readULeb128i(byteBuffer);
        size = Leb128.readULeb128i(byteBuffer);
        elements = new AnnotationElement[size];
        for (int i = 0; i < elements.length; i++)
            elements[i] = new AnnotationElement(byteBuffer);
    }
}
