package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class MethodHandleItem {
    public static final int METHOD_HANDLE_TYPE_STATIC_PUT = 0x00;
    public static final int METHOD_HANDLE_TYPE_STATIC_GET = 0x01;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_PUT = 0x02;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_GET = 0x03;
    public static final int METHOD_HANDLE_TYPE_INVOKE_STATIC = 0x04;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INSTANCE = 0x05;
    public static final int METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR = 0x06;
    public static final int METHOD_HANDLE_TYPE_INVOKE_DIRECT = 0x07;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INTERFACE = 0x08;

    private final short methodHandleType,
        fieldOrMethodId;

    public MethodHandleItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        methodHandleType = (short) (byteBuffer.getShort() & 0xFFFF);
        byteBuffer.getShort(); // unused
        fieldOrMethodId = (short) (byteBuffer.getShort() & 0xFFFF);
        byteBuffer.getShort(); // unused
    }
}
