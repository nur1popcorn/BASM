package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class FieldIdItem extends Item {
    private int classIdx, typeIdx, nameIdx;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        classIdx = in.readUnsignedShort();
        typeIdx = in.readUnsignedShort();
        nameIdx = in.readInt();
    }
}
