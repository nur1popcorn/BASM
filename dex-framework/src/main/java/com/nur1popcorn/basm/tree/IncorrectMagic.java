package com.nur1popcorn.basm.tree;

public class IncorrectMagic extends RuntimeException {
    private final byte[] magic;

    public IncorrectMagic(byte[] magic) {
        super("incorrect dex magic number:" + magic);
        this.magic = magic;
    }
}
