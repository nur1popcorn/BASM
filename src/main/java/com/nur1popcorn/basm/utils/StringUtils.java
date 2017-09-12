package com.nur1popcorn.basm.utils;

/**
 * BASM developed by nur1popcorn.
 */
public class StringUtils {

    private static final char HEX_CHARS[] = "0123456789abcdef".toCharArray();

    public static String byteArrayToHexString(byte bytes[], int start, int end) {
        final StringBuilder stringBuilder = new StringBuilder();
        while(start < end) {
            int b = bytes[start++] & 0xff;
            stringBuilder.append(HEX_CHARS[b >> 4]);
            stringBuilder.append(HEX_CHARS[b & 0xf]);
            if(start != 0 && start % 2 == 0)
                stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
