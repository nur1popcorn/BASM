package com.nur1popcorn.basm.classfile.attributes.stackmap;

import java.io.DataInputStream;
import java.io.IOException;

public class SameLocals1StackItemFrame extends StackMapFrame {
    public SameLocals1StackItemFrame(byte tag, DataInputStream in) throws IOException {
        super(tag);
    }
}
