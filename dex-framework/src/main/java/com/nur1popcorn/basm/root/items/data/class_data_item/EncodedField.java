package com.nur1popcorn.basm.root.items.data.class_data_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedField {
    private final int fieldIdxDiff,
                      accessFlags;

    public EncodedField(ByteBuffer byteBuffer) {
        fieldIdxDiff = Leb128.readULeb128i(byteBuffer);
        accessFlags = Leb128.readULeb128i(byteBuffer);
    }
}
