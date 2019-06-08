package com.nur1popcorn.basm.classfile.attributes;

public class AttributeConstantValue extends AttributeInfo {
    private final int constantValueIndex /* u2 */;

    /**
     * @param nameIndex
     * @param attributeLength
     */
    public AttributeConstantValue(int nameIndex, int attributeLength, int constantValueIndex) {
        super(nameIndex, attributeLength);
        this.constantValueIndex = constantValueIndex;
    }
}
