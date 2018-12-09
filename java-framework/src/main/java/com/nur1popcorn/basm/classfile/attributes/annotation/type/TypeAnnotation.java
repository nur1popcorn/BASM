package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import com.nur1popcorn.basm.classfile.attributes.annotation.element.ElementValuePair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypeAnnotation {
    private final byte targetType;
    private final TargetInfo targetInfo;
    private final int typeIndex;
    private final TypePath targetPath;
    private final ElementValuePair[] elementValuePairs;

    public TypeAnnotation(DataInputStream in) throws IOException {
        targetType = in.readByte();
        targetInfo = TargetInfo.read(targetType, in);
        targetPath = new TypePath(in);
        typeIndex = in.readUnsignedShort();
        elementValuePairs = new ElementValuePair[in.readUnsignedShort()];
        for (int i = 0; i < elementValuePairs.length; i++)
            elementValuePairs[i] = new ElementValuePair(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(targetType);
        targetInfo.write(os);
        targetPath.write(os);
        os.writeShort(typeIndex);
        os.writeShort(elementValuePairs.length);
        for (ElementValuePair elementValuePair : elementValuePairs)
            elementValuePair.write(os);
    }
}
