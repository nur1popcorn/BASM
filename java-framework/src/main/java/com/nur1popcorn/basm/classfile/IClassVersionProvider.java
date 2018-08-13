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

package com.nur1popcorn.basm.classfile;

public interface IClassVersionProvider {
    // TODO: dont really like this class..

    /* These constants denote the version of the JDK used to compile the given class.
     *
     * Sadly, I couldn't find an official reference which compiles all of these constants, so
     * wikipedia will have to suffice: https://en.wikipedia.org/wiki/Java_class_file
     */
    public static final int JAVA_10 = 0x36;
    public static final int JAVA_9 = 0x35;
    public static final int JAVA_8 = 0x34;
    public static final int JAVA_7 = 0x33;
    public static final int JAVA_6 = 0x32;
    public static final int JAVA_5 = 0x31;
    public static final int JAVA_4 = 0x30;
    public static final int JAVA_3 = 0x2f;
    public static final int JAVA_2 = 0x2e;
    public static final int JAVA_1 = 0x2d;

    void ensureMajorVersion(int majorVersion);
    void ensureMinorVersion(int minorVersion);
}
