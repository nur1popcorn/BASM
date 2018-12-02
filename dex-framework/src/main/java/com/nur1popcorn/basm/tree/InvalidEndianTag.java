package com.nur1popcorn.basm.tree;

import static com.nur1popcorn.basm.DexConstants.ENDIAN_CONSTANT;
import static com.nur1popcorn.basm.DexConstants.REVERSE_ENDIAN_CONSTANT;

public class InvalidEndianTag extends RuntimeException {
    private final int endianTag;

    public InvalidEndianTag(int endianTag) {
        super("invalid dex endian tag: " + Integer.toHexString(endianTag) + "," +
            " it can only be `ENDIAN_CONSTANT` (" + Integer.toHexString(ENDIAN_CONSTANT) + ") or \\" +
            "`REVERSE_ENDIAN_CONSTANT` ("+ Integer.toHexString(REVERSE_ENDIAN_CONSTANT) + ")");
        this.endianTag = endianTag;
    }
}
    
