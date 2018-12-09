package com.nur1popcorn.basm.classfile.attributes.annotation;

import com.nur1popcorn.basm.classfile.attributes.annotation.element.ElementValuePair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Annotation {
    private final int typeIndex;
    private final ElementValuePair[] elementValuePairs;

    public Annotation(DataInputStream in) throws IOException {
        typeIndex = in.readUnsignedShort();
        elementValuePairs = new ElementValuePair[in.readUnsignedShort()];
        for(int i = 0; i < elementValuePairs.length; i++)
            elementValuePairs[i] = new ElementValuePair(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(typeIndex);
        os.writeShort(elementValuePairs.length);
        for(ElementValuePair elementValuePair : elementValuePairs)
            elementValuePair.write(os);
    }
}
