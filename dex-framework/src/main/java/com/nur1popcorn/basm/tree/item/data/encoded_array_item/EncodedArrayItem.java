package com.nur1popcorn.basm.tree.item.data.encoded_array_item;

import com.nur1popcorn.basm.tree.item.data.annotation_item.EncodedArray;

import java.nio.ByteBuffer;

public class EncodedArrayItem {
    private final EncodedArray value;

    public EncodedArrayItem(ByteBuffer byteBuffer) {
        value = new EncodedArray(byteBuffer);
    }
}
