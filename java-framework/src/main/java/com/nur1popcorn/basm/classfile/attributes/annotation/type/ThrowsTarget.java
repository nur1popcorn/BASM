package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ThrowsTarget extends TargetInfo {
    private final int throwsTypeIndex;

    public ThrowsTarget(DataInputStream in) throws IOException  {
        throwsTypeIndex = in.readUnsignedShort();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(throwsTypeIndex);
    }
}
