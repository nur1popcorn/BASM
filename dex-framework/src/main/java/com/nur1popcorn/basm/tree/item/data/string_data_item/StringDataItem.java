package com.nur1popcorn.basm.tree.item.data.string_data_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringDataItem {
    private final int utf16Size;
    private byte[] data;
    public String string;

    public StringDataItem(ByteBuffer byteBuffer) {
        utf16Size = Leb128.readULeb128i(byteBuffer);
        if (utf16Size > 0) {
            byte[] str = new byte[utf16Size * 3];
            int encodedSize = 0;
            byte rawByte = byteBuffer.get();
            while (rawByte != 0) {
                str[encodedSize++] = rawByte;
                rawByte = byteBuffer.get();
            }
            data = new byte[utf16Size];
            System.arraycopy(str, 0, data, 0, utf16Size);
            string = new String(data, StandardCharsets.US_ASCII);
            if (utf16Size != data.length)
                System.out.println("Don't have full support for decoding MUTF-8 yet, DEX file "
                    + "may be incorrectly mutated. Avoid using this test case for now.");
        } else
            byteBuffer.get();
    }
}
