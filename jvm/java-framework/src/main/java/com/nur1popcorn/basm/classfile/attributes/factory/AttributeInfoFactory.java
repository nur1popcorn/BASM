package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link AttributeInfo} interface
 */
public interface AttributeInfoFactory<T extends AttributeInfo> {
    /**
     * @param in
     * @param nameIndex
     * @param attributeLength
     * @param cp
     *
     * @return
     */
    T createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException;
}
