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

package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.tree.*;
import com.nur1popcorn.basm.utils.DexInputStream;
import com.nur1popcorn.basm.utils.DexOutputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

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
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        in.readFully(magic);
        if (!new String(magic, StandardCharsets.UTF_8).matches(DEX_FILE_MAGIC))
            throw new IncorrectMagic(magic);

        checksum = in.readInt();
        in.readFully(signature);
        fileSize = in.readInt();
        headerSize = in.readInt();
        endianTag = in.readInt();

        if (endianTag == REVERSE_ENDIAN_CONSTANT) {
            checksum = Integer.reverseBytes(checksum);
            fileSize = Integer.reverseBytes(fileSize);
            headerSize = Integer.reverseBytes(headerSize);
            in = in.reverseOrder();
        } else if (endianTag != ENDIAN_CONSTANT)
            throw new InvalidEndianTag(endianTag);

        if (headerSize != HEADER_ITEM_SIZE)
            throw new IncorrectHeaderSize(headerSize);

        linkSize = in.readInt();
        linkOff = in.readInt();
        if (linkSize == 0 && linkOff != 0)
            throw new OffsetMismatch("link_offset", linkOff, 0);

        mapOff = in.readInt();
        if (mapOff == 0)
            throw new InvalidOffset("`map_offset` was 0, and it can never be zero");

        stringIdsSize = in.readInt();
        stringIdsOff = in.readInt();
        if (stringIdsSize == 0 && stringIdsOff != 0)
            throw new OffsetMismatch("string_ids_offset", stringIdsOff, 0);

        typeIdsSize = in.readInt();
        typeIdsOff = in.readInt();
        if (typeIdsSize == 0 && typeIdsOff != 0)
            throw new OffsetMismatch("type_ids_offset", typeIdsOff, 0);

        protoIdsSize = in.readInt();
        protoIdsOff = in.readInt();
        if (protoIdsSize == 0 && protoIdsOff != 0)
            throw new OffsetMismatch("prototype_ids_offset", protoIdsOff, 0);

        fieldIdsSize = in.readInt();
        fieldIdsOff = in.readInt();
        if (fieldIdsSize == 0 && fieldIdsOff != 0)
            throw new OffsetMismatch("field_ids_offset", fieldIdsOff, 0);

        methodIdsSize = in.readInt();
        methodIdsOff = in.readInt();
        if (methodIdsSize == 0 && methodIdsOff != 0)
            throw new OffsetMismatch("method_ids_offset", methodIdsOff, 0);

        classDefsSize = in.readInt();
        classDefsOff = in.readInt();
        if (classDefsSize == 0 && classDefsOff != 0)
            throw new OffsetMismatch("class_defs_offset", classDefsOff, 0);

        dataSize = in.readInt();
        if ((dataSize & 0x3) != 0)
            throw new RuntimeException("`data_size` must be a 4-byte multiple, but it was " + Integer.toHexString(dataSize));
        dataOff = in.readInt();
    }

    @Override
    public void write(DexOutputStream out) throws IOException {
        super.write(out);
    }
}
