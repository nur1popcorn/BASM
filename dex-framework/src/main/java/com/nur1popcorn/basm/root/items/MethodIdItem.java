package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class MethodIdItem extends Item {
    private int classIdx, protoIdx, nameIdx;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        classIdx = in.readUnsignedShort();
        protoIdx = in.readUnsignedShort();
        nameIdx = in.readInt();
    }
}
