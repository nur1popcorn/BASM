package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValueEnumConstant extends ElementValue {
    private final int typeNameIndex,
                      constNameIndex;

    public ElementValueEnumConstant(int tag, DataInputStream in) throws IOException {
        super(tag);
        typeNameIndex = in.readUnsignedShort();
        constNameIndex = in.readUnsignedShort();
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(typeNameIndex);
        os.writeShort(constNameIndex);
    }
}
