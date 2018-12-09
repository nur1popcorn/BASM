package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValueClass extends ElementValue {
    private final int classInfoIndex;

    public ElementValueClass(byte tag, DataInputStream in) throws IOException {
        super(tag);
        classInfoIndex = in.readUnsignedShort();
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(classInfoIndex);
    }
}
