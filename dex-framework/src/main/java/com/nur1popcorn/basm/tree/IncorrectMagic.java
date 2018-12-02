package com.nur1popcorn.basm.tree;

import java.util.Arrays;

public class IncorrectMagic extends RuntimeException {
    private final DexMagic dexMagic;

    public IncorrectMagic(DexMagic dexMagic) {
        super("incorrect dex magic number:" + dexMagic);
        this.dexMagic = dexMagic;
    }
}
