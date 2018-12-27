package com.nur1popcorn.basm.root.items.data.annotation_set_ref_list;

import java.nio.ByteBuffer;

public class AnnotationSetRefItem {
    private final int annotationsOff;

    public AnnotationSetRefItem(ByteBuffer byteBuffer) {
        annotationsOff = byteBuffer.getInt();
    }
}
