package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeUnkown;

import java.io.DataInputStream;

final class UnknownFactory implements AttributeInfoFactory<AttributeUnkown> {
    @Override
    public AttributeUnkown read(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) {
        return null;
    }
}
