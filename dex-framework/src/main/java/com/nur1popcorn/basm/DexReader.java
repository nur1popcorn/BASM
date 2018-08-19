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

package com.nur1popcorn.basm;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;
import java.io.InputStream;

import static com.nur1popcorn.basm.DexConstants.DEX_MAGIC;
import static com.nur1popcorn.basm.DexConstants.ENDIAN_CONSTANT;

public final class DexReader {
    public static final int READ_HEADER = 0x1;

    public static final int READ_ALL = READ_HEADER;

    private DexInputStream in;

    private HeaderItem header;

    public DexReader(InputStream in) {
        this.in = DexInputStream.create(in, ENDIAN_CONSTANT);
    }

    private void readHeader() throws IOException {
        HeaderItem header = new HeaderItem();

        final int magic = in.readInt();
        if(magic != DEX_MAGIC)
            throw new IOException("The dex file has an invalid magic number: " + magic);

    }

    public void accept(IDexVisitor visitor, int read) throws IOException {
        if((read & READ_HEADER) != 0) {
            readHeader();
            //visitor.visitHeader();
        } else {

        }
    }

    public static class HeaderItem {
        public final byte magic[] = new byte[8];
        public int checksum;
        public final byte signature[] = new byte[20];
        public int fileSize;
        public int headerSize;
        public int endianTag;
        public int linkSize, linkOff;

    }
}
