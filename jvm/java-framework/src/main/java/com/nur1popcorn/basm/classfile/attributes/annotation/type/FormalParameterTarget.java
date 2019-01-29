package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FormalParameterTarget extends TargetInfo {
    private final int formalParameterIndex;

    public FormalParameterTarget(DataInputStream in) throws IOException  {
        formalParameterIndex = in.readUnsignedByte();
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(formalParameterIndex);
    }
}
