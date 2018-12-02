package com.nur1popcorn.basm.tree;

public class InvalidOffset extends RuntimeException {
    public InvalidOffset(String desc) {
        super("invalid offset:" + desc);
    }
}
