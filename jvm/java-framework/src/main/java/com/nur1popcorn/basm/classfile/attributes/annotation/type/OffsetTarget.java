package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OffsetTarget extends TargetInfo {
    private final int offset;

    public OffsetTarget(DataInputStream in) throws IOException  {
        offset = in.readUnsignedShort();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(offset);
    }
}
