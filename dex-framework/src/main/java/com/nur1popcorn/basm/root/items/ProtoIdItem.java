package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class ProtoIdItem extends Item {
    private int shortyIdx, returnTypeIdx, parametersOff;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        shortyIdx = in.readInt();
        returnTypeIdx = in.readInt();
        parametersOff = in.readInt();
    }
}
