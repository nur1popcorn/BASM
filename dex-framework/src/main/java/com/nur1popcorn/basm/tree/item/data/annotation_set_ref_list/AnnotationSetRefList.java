package com.nur1popcorn.basm.tree.item.data.annotation_set_ref_list;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class AnnotationSetRefList {
    private final int size;
    private final AnnotationSetRefItem[] list;

    public AnnotationSetRefList(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        size = byteBuffer.getInt();
        list = new AnnotationSetRefItem[size];
        for (int i = 0; i < list.length; i++)
            list[i] = new AnnotationSetRefItem(byteBuffer);
    }
}
