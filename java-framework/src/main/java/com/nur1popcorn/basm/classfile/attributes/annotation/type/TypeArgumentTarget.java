package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypeArgumentTarget extends TargetInfo {
    private final int offset,
                      typeArgumentIndex;

    public TypeArgumentTarget(DataInputStream in) throws IOException {
        offset = in.readUnsignedShort();
        typeArgumentIndex = in.readUnsignedByte();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(offset);
        os.writeByte(typeArgumentIndex);
    }
}
