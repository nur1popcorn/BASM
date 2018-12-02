package com.nur1popcorn.basm.tree;

import static com.nur1popcorn.basm.DexConstants.HEADER_ITEM_SIZE;

public class IncorrectHeaderSize extends RuntimeException {
    private final int headerSize;

    public IncorrectHeaderSize(int headerSize) {
        super("invalid dex header_size: " + headerSize + " bytes, it can only be " + HEADER_ITEM_SIZE + " bytes");
        this.headerSize = headerSize;
    }
}
