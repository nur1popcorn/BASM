package com.nur1popcorn.basm.root.items.data.annotation_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedArray {
    private final int size;
    private final EncodedValue[] values;

    public EncodedArray(ByteBuffer byteBuffer) {
        size = Leb128.readULeb128i(byteBuffer);
        values = new EncodedValue[size];
        for (int i = 0; i < values.length; i++)
            values[i] = new EncodedValue(byteBuffer);
    }
}
