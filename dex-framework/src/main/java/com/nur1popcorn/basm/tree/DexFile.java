package com.nur1popcorn.basm.tree;

import com.nur1popcorn.basm.root.DexReader;
import com.nur1popcorn.basm.root.IDexVisitor;
import com.nur1popcorn.basm.root.items.HeaderItem;
import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.nur1popcorn.basm.root.DexReader.READ_ALL;

public class DexFile implements IDexVisitor {
    // prevent construction :/
    private DexFile()
    {}

    public DexFile(DexInputStream in) throws IOException {
        new DexReader(in)
            .accept(
                this,
                READ_ALL
            );
    }

    @Override
    public void visitHeader(HeaderItem header) {

    }
}
