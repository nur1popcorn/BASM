package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;

import java.io.DataInputStream;

final class CodeFactory implements AttributeInfoFactory<AttributeCode> {
    @Override
    public AttributeCode read(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) {
        // TODO: basically move constructors here
        return null;
    }
}
