/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm.classfile.attributes.clazz;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AttributeSourceDebugExtension extends AttributeInfo {
    private final String debugExtension;

    public AttributeSourceDebugExtension(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        final byte[] debugExtensionBytes = new byte[attributeLength];
        in.readFully(debugExtensionBytes);
        debugExtension = readUtf8(debugExtensionBytes);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.write(encodeUtf8(debugExtension));
    }

    private static String readUtf8(byte bytes[]) {
        int strLen = 0;
        //obtain string length for buffer.
        for(int i = 0; i < bytes.length; i++) {
            final int b = bytes[i] & 0xff;
            if((b & 0x80) != 0) /* 0111 1111 -> 7f */
                i += ((b & 0x60) == 0x60) ? 2 /* 1110 ???? */ : 1 /* 110? ???? */;
            strLen++;
        }
        final char buff[] = new char[strLen];
        //read encoded string.
        int cp = 0;
        for(int i = 0; i < bytes.length; i++) {
            final int b = bytes[i] & 0xff;
            buff[cp++] = (char) ((b & 0x80 /* 0111 1111 -> 7f */) == 0 ?
                // 1 byte.
                b :
                (b & 0x60) == 0x60 /* 1110 ???? */ ?
                    // 3 bytes.
                    ((bytes[i++] & 0xf /* 1110 ???? */) << 12) +
                    ((bytes[i++] & 0x3f /* 10?? ???? */) << 6) +
                     (bytes[i] & 0x3f /* 10?? ???? */) :
                    // 2 bytes.
                    ((bytes[i++] & 0x1f /* 110? ???? */) << 6) +
                     (bytes[i] & 0x3f /* 10?? ???? */));
        }
        return new String(buff);
    }

    private static byte[] encodeUtf8(String s) {
        int strLen = 0;
        final char chars[] = s.toCharArray();
        // obtain size needed for buffer.
        for(char c : chars)
            strLen += c <= '\u007f' ? 1 : c <= '\u07ff' ? 2 : 3;
        final byte buff[] = new byte[strLen];
        // fill buffer.
        int offset = 0;
        for(int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if(c <= '\u007f')
                // only 1 byte needed.
                buff[offset++] = (byte)c;
            else if(c <= '\u07ff' /* 1 1111 11 1111‬ */) {
                // b0 = 110? ????
                buff[offset++] = (byte)(0xc0 | c >> 6 /* 7ff SHR 6 -> 1f */);
                // b1 = 10?? ????
                buff[offset++] = (byte)(0x80 | c & 0x3f);
                /* 11 1111 1111 */
            } else /* if(c <= '\uffff') /* 1111 11 1111 11 1111 */ {
                // b0 = ‭1110 ????
                buff[offset++] = (byte)(0xe0 | c >> 12 /* ffff SHR 12 -> f */);
                // b1 = ‭10?? ????
                buff[offset++] = (byte)(0x80 | c >> 6 & 0x3f);
                // b2 = ‭10?? ????
                buff[offset++] = (byte)(0x80 | c & 0x3f);
            }
        }
        return buff;
    }
}
