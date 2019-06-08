package com.nur1popcorn.basm.classfile.attributes;

public final class AttributeUnkown extends AttributeInfo {
    private final byte info[];

    /**
     * @param nameIndex
     * @param attributeLength
     * @param info
     */
    public AttributeUnkown(int nameIndex, int attributeLength, byte info[]) {
        super(nameIndex, attributeLength);
        this.info = info;
    }

    public byte[] getInfo() {
        return info;
    }
}
