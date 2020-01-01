package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeSourceFile;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link SourceFileFactory} is responsible for reading {@link AttributeSourceFile}s.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class SourceFileFactory implements AttributeInfoFactory<AttributeSourceFile> {
    @Override
    public AttributeSourceFile createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException {
        return new AttributeSourceFile(
            nameIndex, attributeLength, in.readUnsignedShort());
    }
}
