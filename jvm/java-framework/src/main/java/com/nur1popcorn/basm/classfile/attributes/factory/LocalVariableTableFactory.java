package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeLocalVariableTable;
import com.nur1popcorn.basm.classfile.attributes.LocalVariableTableEntry;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link LocalVariableTableFactory} is responsible for reading {@link AttributeLocalVariableTable}.
 *
 * @see AttributeLocalVariableTable
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class LocalVariableTableFactory implements AttributeInfoFactory<AttributeLocalVariableTable> {
    @Override
    public AttributeLocalVariableTable createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        LocalVariableTableEntry[] entries = new LocalVariableTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < entries.length; i++) {
            entries[i] = new LocalVariableTableEntry(in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort());
        }
        return new AttributeLocalVariableTable(nameIndex, attributeLength, entries);
    }
}
