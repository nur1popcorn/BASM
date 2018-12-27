package com.nur1popcorn.basm.root.items.data.annotation_set_ref_list;

import java.nio.ByteBuffer;

public class AnnotationSetRefList {
    private final int size;
    private final AnnotationSetRefItem[] list;

    public AnnotationSetRefList(ByteBuffer byteBuffer) {
        // Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        list = new AnnotationSetRefItem[size];
        for (int i = 0; i < list.length; i++)
            list[i] = new AnnotationSetRefItem(byteBuffer);
    }
}
