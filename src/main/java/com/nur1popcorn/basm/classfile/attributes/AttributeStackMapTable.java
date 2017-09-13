package com.nur1popcorn.basm.classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeStackMapTable extends AttributeInfo {


    public AttributeStackMapTable(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
    }
}
