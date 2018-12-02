package com.nur1popcorn.basm.tree.item.data.code_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class EncodedTypeAddrPair {
    private final int typeIdx,
                      addr;

    public EncodedTypeAddrPair(ByteBuffer byteBuffer) {
        typeIdx = Leb128.readULeb128i(byteBuffer);
        addr = Leb128.readULeb128i(byteBuffer);
    }
}
