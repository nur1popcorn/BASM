package com.nur1popcorn.basm.tree;

import com.nur1popcorn.basm.DexReader;
import com.nur1popcorn.basm.IDexVisitor;
import com.nur1popcorn.basm.tree.item.HeaderItem;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.nur1popcorn.basm.DexReader.READ_ALL;

public class DexFile implements IDexVisitor {
    // prevent construction :/
    private DexFile()
    {}

    public DexFile(ByteBuffer byteBuffer) throws IOException {
        new DexReader(byteBuffer)
            .accept(
                this,
                READ_ALL
            );
    }

    @Override
    public void visitHeader(HeaderItem header) {

    }
}
