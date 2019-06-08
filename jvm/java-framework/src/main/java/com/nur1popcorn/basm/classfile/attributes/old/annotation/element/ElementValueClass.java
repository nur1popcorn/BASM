package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValueClass extends ElementValue {
    private final int classInfoIndex;

    public ElementValueClass(int tag, DataInputStream in) throws IOException {
        super(tag);
        classInfoIndex = in.readUnsignedShort();
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeShort(classInfoIndex);
    }
}
