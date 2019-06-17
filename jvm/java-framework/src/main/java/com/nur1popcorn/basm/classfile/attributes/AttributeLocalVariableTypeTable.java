package com.nur1popcorn.basm.classfile.attributes;

public final class AttributeLocalVariableTypeTable extends AttributeInfo {
    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public AttributeLocalVariableTypeTable(int nameIndex, int attributeLength) {
        super(nameIndex, attributeLength);
    }

    @Override
    public void accept(IAttributeVisitor v) {

    }
}