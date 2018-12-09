package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypeParameterBoundTarget extends TargetInfo {
    private final int typeParameterIndex,
                      boundIndex;

    public TypeParameterBoundTarget(DataInputStream in) throws IOException {
        typeParameterIndex = in.readUnsignedByte();
        boundIndex = in.readUnsignedByte();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(typeParameterIndex);
        os.writeByte(boundIndex);
    }
}
