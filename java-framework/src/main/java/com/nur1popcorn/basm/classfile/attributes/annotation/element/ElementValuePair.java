package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValuePair {
    private final int elementNameIndex;
    private final ElementValue value;

    public ElementValuePair(DataInputStream in) throws IOException  {
        elementNameIndex = in.readUnsignedShort();
        value = ElementValue.read(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(elementNameIndex);
        value.write(os);
    }
}
