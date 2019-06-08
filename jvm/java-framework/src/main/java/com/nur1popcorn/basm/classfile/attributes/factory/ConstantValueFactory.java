package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeConstantValue;

import java.io.DataInputStream;
import java.io.IOException;

final class ConstantValueFactory implements AttributeInfoFactory<AttributeConstantValue> {
    @Override
    public AttributeConstantValue read(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        return new AttributeConstantValue(
            nameIndex, attributeLength, in.readUnsignedShort());
    }
}
