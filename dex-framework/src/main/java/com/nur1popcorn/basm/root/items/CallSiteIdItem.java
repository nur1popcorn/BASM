package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class CallSiteIdItem extends Item {
    private int callSiteOff;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        callSiteOff = in.readInt();
    }
}
