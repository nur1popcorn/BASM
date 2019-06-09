package com.nur1popcorn.basm.classfile.attributes;

public final class AttributeUnknown extends AttributeInfo {
    private final byte info[];

    /**
     * @param nameIndex
     * @param attributeLength
     * @param info
     */
    public AttributeUnknown(int nameIndex, int attributeLength, byte info[]) {
        super(nameIndex, attributeLength);
        this.info = info;
    }

    public byte[] getInfo() {
        return info;
    }
}
