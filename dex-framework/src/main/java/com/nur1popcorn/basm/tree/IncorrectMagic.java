package com.nur1popcorn.basm.tree;

public class IncorrectMagic extends RuntimeException {
    private final DexFileMagic dexMagic;

    public IncorrectMagic(DexFileMagic dexMagic) {
        super("incorrect dex magic number:" + dexMagic);
        this.dexMagic = dexMagic;
    }
}
