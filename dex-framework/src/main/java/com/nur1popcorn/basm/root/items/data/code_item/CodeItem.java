package com.nur1popcorn.basm.root.items.data.code_item;

import java.nio.ByteBuffer;

public class CodeItem {
    private final short registersSize,
                        insSize,
                        outsSize,
                        triesSize;
    public final int debugInfoOff,
                     insnsSize;
    private short[] insns;
    private TryItem[] tries;
    private EncodedCatchHandlerList handlers;

    public CodeItem(ByteBuffer byteBuffer) {
        // Alignment.alignToFourBytes(byteBuffer);
        registersSize = byteBuffer.getShort();
        insSize = byteBuffer.getShort();
        outsSize = byteBuffer.getShort();
        triesSize = byteBuffer.getShort();
        debugInfoOff = byteBuffer.getInt();
        insnsSize = byteBuffer.getInt();
        insns = new short[insnsSize];
        for (int i = 0; i < insns.length; i++)
            insns[i] = byteBuffer.getShort();
        if (triesSize != 0) {
            if (insnsSize % 2 != 0)
                byteBuffer.getShort(); // padding
            tries = new TryItem[triesSize];
            for (int i = 0; i < tries.length; i++)
                tries[i] = new TryItem(byteBuffer);
            handlers = new EncodedCatchHandlerList(byteBuffer);
        }
    }
}
