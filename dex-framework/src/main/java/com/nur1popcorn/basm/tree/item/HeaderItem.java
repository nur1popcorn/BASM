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

import static com.nur1popcorn.basm.DexConstants.*;

public class HeaderItem {
    private final DexMagic magic;
    private int checksum;
    private final byte signature[] = new byte[20];
    private int fileSize;
    private int headerSize;
    private final int endianTag;
    private final int linkSize, linkOff;
    public final int mapOff;
    public final int stringIdsSize, stringIdsOff;
    public final int typeIdsSize, typeIdsOff;
    public final int protoIdsSize, protoIdsOff;
    public final int fieldIdsSize, fieldIdsOff;
    public final int methodIdsSize, methodIdsOff;
    public final int classDefsSize, classDefsOff;
    private final int dataSize, dataOff;

    public HeaderItem(ByteBuffer byteBuffer) throws IOException {
        Alignment.alignToFourBytes(byteBuffer);
        magic = new DexMagic(byteBuffer);
        if (!magic.validate()) throw new IncorrectMagic(magic);

        checksum = byteBuffer.getInt();
        byteBuffer.get(signature);
        fileSize = byteBuffer.getInt();
        headerSize = byteBuffer.getInt();
        endianTag = byteBuffer.getInt();

        // Check endianness
        if (endianTag == REVERSE_ENDIAN_CONSTANT) {
            checksum = Integer.reverseBytes(checksum);
            fileSize = Integer.reverseBytes(fileSize);
            headerSize = Integer.reverseBytes(headerSize);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        } else if (endianTag != ENDIAN_CONSTANT)
            throw new InvalidEndianTag(endianTag);
        if (headerSize != HEADER_ITEM_SIZE)
            throw new IncorrectHeaderSize(headerSize);

        int currentOffset = headerSize;

        linkSize = byteBuffer.getInt();
        linkOff = byteBuffer.getInt();
        if (linkSize == 0 && linkOff != 0)
            throw new OffsetMismatch("link_offset", linkOff, 0);
        mapOff = byteBuffer.getInt();
        if (mapOff == 0x00000000)
            throw new InvalidOffset("`map_offset` was 0x00000000, and it can never be zero");
        stringIdsSize = byteBuffer.getInt();
        stringIdsOff = byteBuffer.getInt();
        if (stringIdsSize > 0 && stringIdsOff != currentOffset)
            throw new OffsetMismatch("string_ids_offset", stringIdsOff, currentOffset);
        if (stringIdsSize == 0 && stringIdsOff != 0)
            throw new OffsetMismatch("string_ids_offset", stringIdsOff, 0);
        currentOffset += stringIdsSize * STRING_ID_ITEM_SIZE;

        typeIdsSize = byteBuffer.getInt();
        typeIdsOff = byteBuffer.getInt();
        if (typeIdsSize > 0 && typeIdsOff != currentOffset)
            throw new OffsetMismatch("type_ids_offset", typeIdsOff, currentOffset);
        if (typeIdsSize == 0 && typeIdsOff != 0)
            throw new OffsetMismatch("type_ids_offset", typeIdsOff, 0);
        currentOffset += typeIdsSize * TYPE_ID_ITEM_SIZE;

        protoIdsSize = byteBuffer.getInt();
        protoIdsOff = byteBuffer.getInt();
        if (protoIdsSize > 0 && protoIdsOff != currentOffset)
            throw new OffsetMismatch("prototype_ids_offset", protoIdsOff, currentOffset);
        if (protoIdsSize == 0 && protoIdsOff != 0)
            throw new OffsetMismatch("prototype_ids_offset", protoIdsOff, 0);
        currentOffset += protoIdsSize * PROTO_ID_ITEM_SIZE;

        fieldIdsSize = byteBuffer.getInt();
        fieldIdsOff = byteBuffer.getInt();
        if (fieldIdsSize > 0 && fieldIdsOff != currentOffset)
            throw new OffsetMismatch("field_ids_offset", fieldIdsOff, currentOffset);
        if (fieldIdsSize == 0 && fieldIdsOff != 0)
            throw new OffsetMismatch("field_ids_offset", fieldIdsOff, 0);
        currentOffset += fieldIdsSize * FIELD_ID_ITEM_SIZE;

        methodIdsSize = byteBuffer.getInt();
        methodIdsOff = byteBuffer.getInt();
        if (methodIdsSize > 0 && methodIdsOff != currentOffset)
            throw new OffsetMismatch("method_ids_offset", methodIdsOff, currentOffset);
        if (methodIdsSize == 0 && methodIdsOff != 0)
            throw new OffsetMismatch("method_ids_offset", methodIdsOff, 0);
        currentOffset += methodIdsSize * METHOD_ID_ITEM_SIZE;

        classDefsSize = byteBuffer.getInt();
        classDefsOff = byteBuffer.getInt();
        if (classDefsSize > 0 && classDefsOff != currentOffset)
            throw new OffsetMismatch("class_defs_offset", classDefsOff, currentOffset);
        if (classDefsSize == 0 && classDefsOff != 0)
            throw new OffsetMismatch("class_defs_offset", classDefsOff, 0);
        currentOffset += classDefsSize * CLASS_DEF_ITEM_SIZE;

        dataSize = byteBuffer.getInt();
        if ((dataSize & 0b11) != 0)
            throw new IOException("`data_size` must be a 4-byte multiple, but it was " + Integer.toHexString(dataSize));
        dataOff = byteBuffer.getInt();
        if (dataOff != currentOffset)
            throw new IOException();
        currentOffset += dataSize;
        if (mapOff < dataOff || mapOff > dataOff + dataSize)
            throw new InvalidOffset("`map_offset` section must be in the `data` section (between " +
                Integer.toHexString(dataOff) + " and \\ " + Integer.toHexString(currentOffset) + ") but it was at " +
                Integer.toHexString(mapOff));
        if (linkSize == 0 && currentOffset != fileSize)
            throw new IOException("`data` section must end at the EOF if there are no links in the file. Data \\ end: " +
                Integer.toHexString(currentOffset) + ", `file_size`: " + Integer.toHexString(fileSize));
        if (linkSize != 0 && linkOff == 0)
            throw new OffsetMismatch("link_offset", 0, currentOffset);
        if (linkSize != 0 && linkOff != 0) {
            if (linkOff != currentOffset)
                throw new OffsetMismatch("link_offset", linkOff, currentOffset);
            if (linkOff + linkSize != fileSize)
                throw new IOException("`link_data` section must end at the end of file");
        }
    }
}
