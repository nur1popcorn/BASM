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

package com.nur1popcorn.basm.classfile.attributes.factory;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The {@link AttributeInfoFactory} is responsible for reading the various types of {@link AttributeInfo}s.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public interface AttributeInfoFactory<T extends AttributeInfo> {
    /**
     * @param in The {@link DataInputStream} from which the {@link AttributeInfo} should be read.
     * @param nameIndex The index which was used to identify the {@link AttributeInfo}
     * @param attributeLength The length of the {@link AttributeInfo} in bytes.
     * @param cp The {@link ConstantPool} associated with the class file.
     *
     * @return The read {@link AttributeInfo}.
     */
    T createAttribute(DataInputStream in, int nameIndex, int attributeLength, ConstantPool cp) throws IOException;
}
