package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValueArray extends ElementValue {
    private final ElementValue[] values;

    public ElementValueArray(int tag, DataInputStream in) throws IOException {
        super(tag);
        values = new ElementValue[in.readUnsignedShort()];
        for (int i = 0; i < values.length; i++)
            values[i] = ElementValue.read(in);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(values.length);
        for (ElementValue value : values)
            value.write(os);
    }
}
