package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class TypeIdItem extends Item {
    private int descriptorIdx;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        descriptorIdx = in.readInt();
    }
}
