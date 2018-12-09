package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class Item {
    public void read(ByteBuffer data) throws IOException {
        Alignment.alignToFourBytes(data);
    }

    public void write(ByteBuffer data) throws IOException {
        Alignment.alignToFourBytesWithZeroFill(data);
    }
}
