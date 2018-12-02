package com.nur1popcorn.basm.tree.item.data.class_data_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedMethod {
    private final int methodIdxDiff,
                      accessFlags,
                      codeOff;

    public EncodedMethod(ByteBuffer byteBuffer) {
        methodIdxDiff = Leb128.readULeb128i(byteBuffer);
        accessFlags = Leb128.readULeb128i(byteBuffer);
        codeOff = Leb128.readULeb128i(byteBuffer);
    }
}
