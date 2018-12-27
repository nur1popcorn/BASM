package com.nur1popcorn.basm.root.items.data.annotation_item;

import com.nur1popcorn.basm.root.DexReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EncodedValue {
    private static final byte VALUE_BYTE = 0x00;
    private static final byte VALUE_SHORT = 0x02;
    private static final byte VALUE_CHAR = 0x03;
    private static final byte VALUE_INT = 0x04;
    private static final byte VALUE_LONG = 0x06;
    private static final byte VALUE_FLOAT = 0x10;
    private static final byte VALUE_DOUBLE = 0x11;
    private static final byte VALUE_METHOD_TYPE = 0x15;
    private static final byte VALUE_METHOD_HANDLE = 0x16;
    private static final byte VALUE_STRING = 0x17;
    private static final byte VALUE_TYPE = 0x18;
    private static final byte VALUE_FIELD = 0x19;
    private static final byte VALUE_METHOD = 0x1a;
    private static final byte VALUE_ENUM = 0x1b;
    private static final byte VALUE_ARRAY = 0x1c;
    private static final byte VALUE_ANNOTATION = 0x1d;
    private static final byte VALUE_NULL = 0x1e;
    private static final byte VALUE_BOOLEAN = 0x1f;

    private final Object value;

    public EncodedValue(ByteBuffer byteBuffer) {
        //TODO: remove "0xFF", "0x7" and "0x3"?
        final byte valueArgAndType = (byte) (byteBuffer.get() & 0xFF);
        final int valueArg = valueArgAndType >>> 5;
        //valueArg = (byte) ((valueArgAndType & 0xe0) >> 5);
        final int valueType = valueArgAndType & 0x1f;
        final int size = (valueArg & 0x7) + 1;

        switch (valueType) {
            case VALUE_BYTE:
                if (valueArg != 0)
                    throw new RuntimeException("Wrong format: VALUE_BYTE must have value_arg of 0");
                value = byteBuffer.get();
                break;
            case VALUE_SHORT:
                checkRangeInclusive(valueArg, 0, 1);
                value = getPaddedBuffer(byteBuffer, size, 2).getShort();
                break;
            case VALUE_CHAR:
                checkRangeInclusive(valueArg, 0, 1);
                value = getPaddedBuffer(byteBuffer, size, 2).getChar();
                break;
            case VALUE_INT:
                checkRangeInclusive(valueArg, 0, 3);
                value = getPaddedBuffer(byteBuffer, size, 4).getInt();
                break;
            case VALUE_LONG:
                checkRangeInclusive(valueArg, 0, 7);
                value = getPaddedBuffer(byteBuffer, size, 8).getLong();
                break;
            case VALUE_FLOAT:
                checkRangeInclusive(valueArg, 0, 3);
                value = getPaddedBufferToTheRight(byteBuffer, valueArg, 4).getFloat();
                break;
            case VALUE_DOUBLE:
                checkRangeInclusive(valueArg, 0, 7);
                value = getPaddedBufferToTheRight(byteBuffer, valueArg, 8).getDouble();
                break;
            case VALUE_METHOD_TYPE:
                checkRangeInclusive(valueArg, 0, 3);
                final int protoId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.protoIds[protoId];
                break;
            case VALUE_METHOD_HANDLE:
                checkRangeInclusive(valueArg, 0, 3);
                final int methodHandle = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.methodHandles[methodHandle];
                break;
            case VALUE_STRING:
                checkRangeInclusive(valueArg, 0, 3);
                final int stringId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.stringIds[stringId];
                break;
            case VALUE_TYPE:
                checkRangeInclusive(valueArg, 0, 3);
                final int typeId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.typeIds[typeId];
                break;
            case VALUE_FIELD:
                checkRangeInclusive(valueArg, 0, 3);
                final int fieldId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.fieldIds[fieldId];
                break;
            case VALUE_METHOD:
                checkRangeInclusive(valueArg, 0, 3);
                final int methodId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.methodIds[methodId];
                break;
            case VALUE_ENUM:
                checkRangeInclusive(valueArg, 0, 3);
                final int enumId = getPaddedBuffer(byteBuffer, size, 4).getInt();
                value = DexReader.instance.fieldIds[enumId];
                break;
            case VALUE_ARRAY:
                if (valueArg != 0)
                    throw new RuntimeException("Wrong format: VALUE_ARRAY must have value_arg of 0");
                value = new EncodedArray(byteBuffer);
                break;
            case VALUE_ANNOTATION:
                if (valueArg != 0)
                    throw new RuntimeException("Wrong format: VALUE_ANNOTATION must have value_arg of 0");
                value = new EncodedAnnotation(byteBuffer);
                break;
            case VALUE_NULL:
                if (valueArg != 0)
                    throw new RuntimeException("Wrong format: VALUE_NULL must have value_arg of 0");
                value = null;
                break;
            case VALUE_BOOLEAN:
                checkRangeInclusive(valueArg, 0, 1);
                value = (valueArg & 0x3) == 1;
                break;
            default:
                throw new RuntimeException("invalid value type_list {" + Integer.toHexString(valueType) + "}");
        }
    }

    private static void checkRangeInclusive(int v, int min, int max) {
        if (v < min || v > max)
            throw new RuntimeException(v + "is outside of " + min + " to " + max + " range");
    }

    private static long readIntBits(ByteBuffer in, int length) {
        final long value = readUIntBits(in, length);
        final int shift = (8 - length) * 8;
        return value << shift >> shift;
    }

    private static long readUIntBits(ByteBuffer in, int length) {
        long value = 0;
        for (int i = 0; i < length; i++)
            value |= ((long) (0xff & in.get())) << (i * 8);
        return value;
    }

    private long readFloatBits(ByteBuffer in, int length) {
        long value = readUIntBits(in, length);
        value <<= (8 - length) * 8;
        return value;
    }

    private ByteBuffer getPaddedBuffer(ByteBuffer byteBuffer, int size, int fullSize) {
        ByteBuffer buffer = ByteBuffer.allocate(fullSize).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < size; i ++)
            buffer.put(byteBuffer.get());
        for (int j = size+1; j < fullSize; j++)
            buffer.put((byte) 0);
        buffer.position(0);
        return buffer;
    }

    private ByteBuffer getPaddedBufferToTheRight(ByteBuffer byteBuffer, int size, int fullSize) {
        ByteBuffer buffer = ByteBuffer.allocate(fullSize).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = size+1; i < fullSize; i++)
            buffer.put((byte) 0);
        for (int i = 0; i < size; i ++)
            buffer.put(byteBuffer.get());
        buffer.rewind();
        return buffer;
    }
}
