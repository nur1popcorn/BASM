package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;
import com.nur1popcorn.basm.utils.DexOutputStream;

import java.io.IOException;

public class MethodHandleItem extends Item {
    public static final int METHOD_HANDLE_TYPE_STATIC_PUT = 0x00;
    public static final int METHOD_HANDLE_TYPE_STATIC_GET = 0x01;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_PUT = 0x02;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_GET = 0x03;
    public static final int METHOD_HANDLE_TYPE_INVOKE_STATIC = 0x04;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INSTANCE = 0x05;
    public static final int METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR = 0x06;
    public static final int METHOD_HANDLE_TYPE_INVOKE_DIRECT = 0x07;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INTERFACE = 0x08;

    private int methodHandleType, fieldOrMethodId;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        methodHandleType = in.readUnsignedShort();
        in.skipBytes(2);
        fieldOrMethodId = in.readUnsignedShort();
        in.skipBytes(2);
    }

    @Override
    public void write(DexOutputStream out) throws IOException {
        super.write(out);
    }
}
