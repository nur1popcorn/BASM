package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypeParameterTarget extends TargetInfo {
    private final int typeParameterIndex;

    public TypeParameterTarget(DataInputStream in) throws IOException  {
        typeParameterIndex = in.readUnsignedByte();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(typeParameterIndex);
    }
}
