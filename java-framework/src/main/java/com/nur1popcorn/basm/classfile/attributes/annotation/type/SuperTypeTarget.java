package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SuperTypeTarget extends TargetInfo {
    private final int supertypeIndex;

    public SuperTypeTarget(DataInputStream in) throws IOException  {
        supertypeIndex = in.readUnsignedShort();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(supertypeIndex);
    }
}
