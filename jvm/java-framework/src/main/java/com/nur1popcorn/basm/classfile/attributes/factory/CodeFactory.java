package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.ExceptionTableEntry;

import java.io.DataInputStream;
import java.io.IOException;

final class CodeFactory implements AttributeInfoFactory<AttributeCode> {
    @Override
    public AttributeCode createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        final int maxStack = in.readUnsignedShort();
        final int maxLocals = in.readUnsignedShort();

        final byte code[] = new byte[in.readInt()];
        in.readFully(code);

        final ExceptionTableEntry table[] = new ExceptionTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < table.length; i++)
            table[i] = new ExceptionTableEntry(in.readUnsignedShort(), in.readUnsignedShort(),
                                               in.readUnsignedShort(), in.readUnsignedShort());

        return new AttributeCode(
            nameIndex, attributeLength, maxStack, maxLocals,
            code, table, Attributes.read(in, cp));
    }
}
