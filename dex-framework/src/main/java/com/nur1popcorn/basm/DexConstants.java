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

public final class DexConstants {
    public static final byte[] DEX_FILE_MAGIC = { 'd', 'e', 'x' };
    public static final byte[][] DEX_FILE_MAGIC_VERSIONS = {
        {'0', '3', '5'},
        // Dex version 036 skipped because of an old dalvik bug on some versions of android where dex
        // files with that version number would erroneously be accepted and run.
        {'0', '3', '7'},
        // Dex version 038: Android "O" and beyond.
        {'0', '3', '8'},
        // Dex verion 039: Beyond Android "O".
        {'0', '3', '9'}
    };

    public static final int ENDIAN_CONSTANT = 0x12345678;
    public static final int REVERSE_ENDIAN_CONSTANT = 0x78563412;

    public static final int HEADER_ITEM_SIZE = 0x70;
    public static final int STRING_ID_ITEM_SIZE = 0x04;
    public static final int TYPE_ID_ITEM_SIZE = 0x04;
    public static final int PROTO_ID_ITEM_SIZE = 0x0c;
    public static final int FIELD_ID_ITEM_SIZE = 0x08;
    public static final int METHOD_ID_ITEM_SIZE = 0x08;
    public static final int CLASS_DEF_ITEM_SIZE = 0x20;

    // prevent construction :/
    private DexConstants()
    {}
}
