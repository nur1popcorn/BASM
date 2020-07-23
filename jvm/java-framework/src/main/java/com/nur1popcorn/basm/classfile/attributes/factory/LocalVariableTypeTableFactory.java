package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeLocalVariableTypeTable;
import com.nur1popcorn.basm.classfile.attributes.LocalVariableTypeTableEntry;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link LocalVariableTypeTableFactory} is responsible for reading {@link AttributeLocalVariableTypeTable}.
 *
 * @see AttributeLocalVariableTypeTable
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class LocalVariableTypeTableFactory implements AttributeInfoFactory<AttributeLocalVariableTypeTable> {
    @Override
    public AttributeLocalVariableTypeTable createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        LocalVariableTypeTableEntry[] entries = new LocalVariableTypeTableEntry[in.readUnsignedShort()];
        for(int i = 0; i < entries.length; i++) {
            entries[i] = new LocalVariableTypeTableEntry(in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort());
        }
        return new AttributeLocalVariableTypeTable(nameIndex, attributeLength, entries);
    }
}
