package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeUnknown;

import java.io.DataInputStream;

final class UnknownFactory implements AttributeInfoFactory<AttributeUnknown> {
    @Override
    public AttributeUnknown createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) {
        return null;
    }
}
