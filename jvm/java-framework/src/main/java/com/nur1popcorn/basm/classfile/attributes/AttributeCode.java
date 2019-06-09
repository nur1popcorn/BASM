package com.nur1popcorn.basm.classfile.attributes;

public final class AttributeCode extends AttributeInfo {
    private final int maxStack /* u2 */,
                      maxLocals /* u2 */;

    private final byte code[] /* length: u4
                           entries: u1 */;

    private final ExceptionTableEntry exceptionTable[] /* length: u2 */;
    private final AttributeInfo attributes[] /* length: u2 */;

    /**
     * @param nameIndex
     * @param attributeLength
     */
    public AttributeCode(int nameIndex, int attributeLength, int maxStack, int maxLocals, byte code[],
                         ExceptionTableEntry exceptionTable[], AttributeInfo attributes[]) {
        super(nameIndex, attributeLength);

        this.maxStack = maxStack;
        this.maxLocals = maxLocals;

        this.code = code;

        this.exceptionTable = exceptionTable;
        this.attributes = attributes;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public ExceptionTableEntry[] getExceptionTable() {
        return exceptionTable;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

}
