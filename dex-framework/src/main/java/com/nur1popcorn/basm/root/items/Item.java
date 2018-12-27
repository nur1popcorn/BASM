package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;
import com.nur1popcorn.basm.utils.DexOutputStream;

import java.io.IOException;

public abstract class Item {
    public void read(DexInputStream in) throws IOException {
        in.alignToFourBytes();
    }

    public void write(DexOutputStream out) throws IOException {
        out.alignToFourBytesWithZeroFill();
    }
}
