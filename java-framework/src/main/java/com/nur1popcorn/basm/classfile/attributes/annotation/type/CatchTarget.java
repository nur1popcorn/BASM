package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CatchTarget extends TargetInfo {
    private final int exceptionTableIndex;

    public CatchTarget(DataInputStream in) throws IOException  {
        exceptionTableIndex = in.readUnsignedShort();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(exceptionTableIndex);
    }
}
