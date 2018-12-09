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

package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.tree.*;
import com.nur1popcorn.basm.utils.Alignment;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static com.nur1popcorn.basm.DexConstants.*;

public class HeaderItem extends Item {
    private final byte[] magic = new byte[8];
    private int checksum;
    private final byte signature[] = new byte[20];
    private int fileSize;
    private int headerSize;
    private int endianTag;
    private int linkSize, linkOff;
    public int mapOff;
    public int stringIdsSize, stringIdsOff;
    public int typeIdsSize, typeIdsOff;
    public int protoIdsSize, protoIdsOff;
    public int fieldIdsSize, fieldIdsOff;
    public int methodIdsSize, methodIdsOff;
    public int classDefsSize, classDefsOff;
    private int dataSize, dataOff;

    @Override
    public void read(ByteBuffer byteBuffer) throws IOException {
        super.read(byteBuffer);
        byteBuffer.get(magic);
        final String m = new String(magic);
        if (!m.matches(DEX_FILE_MAGIC))
            throw new IncorrectMagic(magic);

        checksum = byteBuffer.getInt();
        byteBuffer.get(signature);
        fileSize = byteBuffer.getInt();
        headerSize = byteBuffer.getInt();

        endianTag = byteBuffer.getInt();
        if (endianTag == REVERSE_ENDIAN_CONSTANT) {
            checksum = Integer.reverseBytes(checksum);
            fileSize = Integer.reverseBytes(fileSize);
            headerSize = Integer.reverseBytes(headerSize);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        } else if (endianTag != ENDIAN_CONSTANT)
            throw new InvalidEndianTag(endianTag);

        if (headerSize != HEADER_ITEM_SIZE)
            throw new IncorrectHeaderSize(headerSize);

        linkSize = byteBuffer.getInt();
        linkOff = byteBuffer.getInt();
        if (linkSize == 0 && linkOff != 0)
            throw new OffsetMismatch("link_offset", linkOff, 0);

        mapOff = byteBuffer.getInt();
        if (mapOff == 0)
            throw new InvalidOffset("`map_offset` was 0, and it can never be zero");

        stringIdsSize = byteBuffer.getInt();
        stringIdsOff = byteBuffer.getInt();
        if (stringIdsSize == 0 && stringIdsOff != 0)
            throw new OffsetMismatch("string_ids_offset", stringIdsOff, 0);

        typeIdsSize = byteBuffer.getInt();
        typeIdsOff = byteBuffer.getInt();
        if (typeIdsSize == 0 && typeIdsOff != 0)
            throw new OffsetMismatch("type_ids_offset", typeIdsOff, 0);

        protoIdsSize = byteBuffer.getInt();
        protoIdsOff = byteBuffer.getInt();
        if (protoIdsSize == 0 && protoIdsOff != 0)
            throw new OffsetMismatch("prototype_ids_offset", protoIdsOff, 0);

        fieldIdsSize = byteBuffer.getInt();
        fieldIdsOff = byteBuffer.getInt();
        if (fieldIdsSize == 0 && fieldIdsOff != 0)
            throw new OffsetMismatch("field_ids_offset", fieldIdsOff, 0);

        methodIdsSize = byteBuffer.getInt();
        methodIdsOff = byteBuffer.getInt();
        if (methodIdsSize == 0 && methodIdsOff != 0)
            throw new OffsetMismatch("method_ids_offset", methodIdsOff, 0);

        classDefsSize = byteBuffer.getInt();
        classDefsOff = byteBuffer.getInt();
        if (classDefsSize == 0 && classDefsOff != 0)
            throw new OffsetMismatch("class_defs_offset", classDefsOff, 0);

        dataSize = byteBuffer.getInt();
        if ((dataSize & 0b11) != 0)
            throw new RuntimeException("`data_size` must be a 4-byte multiple, but it was " + Integer.toHexString(dataSize));
        dataOff = byteBuffer.getInt();
    }

    @Override
    public void write(ByteBuffer byteBuffer) throws IOException {
        super.write(byteBuffer);
    }
}
