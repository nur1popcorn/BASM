package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class StringIdItem extends Item {
    private int stringDataOff;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        stringDataOff = in.readInt();
    }
}
