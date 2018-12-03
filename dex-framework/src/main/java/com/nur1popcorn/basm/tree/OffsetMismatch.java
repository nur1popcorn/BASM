package com.nur1popcorn.basm.tree;

public class OffsetMismatch extends RuntimeException {
    public OffsetMismatch(String offset_name, int current_offset, int expected_offset) {
        super("mismatched `" + offset_name + "` offsets: expected " + Integer.toHexString(current_offset) +
            ", current offset " + Integer.toHexString(expected_offset));
    }
}
