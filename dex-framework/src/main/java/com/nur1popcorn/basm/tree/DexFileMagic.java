package com.nur1popcorn.basm.tree;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.nur1popcorn.basm.DexConstants.DEX_FILE_MAGIC;
import static com.nur1popcorn.basm.DexConstants.DEX_FILE_MAGIC_VERSIONS;

public class DexFileMagic {
    private final byte[] dex = new byte[3];
    private final byte newline;
    private final byte[] version = new byte[3];
    private final byte zero;

    public DexFileMagic(ByteBuffer byteBuffer) {
        byteBuffer.get(dex);
        newline = byteBuffer.get();
        byteBuffer.get(version);
        zero = byteBuffer.get();
    }

    public boolean validate() {
        if (!Arrays.equals(dex, DEX_FILE_MAGIC))
            return false;
        if (newline != '\n')
            return false;
        if (zero != '\0')
            return false;
        for (byte[] version : DEX_FILE_MAGIC_VERSIONS)
            if (Arrays.equals(version, this.version))
                return true;
        return false;
    }
}
