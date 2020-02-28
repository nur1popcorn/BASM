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
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

/**
 * The {@link AttributeFactory} class, not to be confused with the IAttributeInfoFactory interface, is
 * responsible for bootstrapping the reading process of all {@link AttributeInfo}s.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class AttributeFactory {
    private static final UnknownFactory UNKNOWN_FACTORY = new UnknownFactory();

    private static final Map<String, AttributeInfoFactory<? extends AttributeInfo>> ATTRIBUTE_FACTORY_MAP = Map.of(
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2 */
        "ConstantValue", new ConstantValueFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3 */
        "Code", new CodeFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.8 */
        "Synthetic", new SyntheticFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.10 */
        "SourceFile", new SourceFileFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.12 */
        "LineNumberTable", new LineNumberTableFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.15 */
        "Deprecated", new DeprecatedFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.23 */
        "BootstrapMethods", new BootstrapMethodsFactory(),
        /* https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.24 */
        "MethodParameters", new MethodParametersFactory()
    );

    /**
     * @param in the {@link DataInputStream} from which the {@link AttributeInfo}s
     *           should be read.
     * @param cp the {@link ConstantPool} is used to index the attribute's identifier
     *           and may or may not be used to construct certain attributes.
     *
     * @throws IOException if an error occurs while reading any of the attributes.
     */
    public static AttributeInfo[] read(DataInputStream in, ConstantPool cp) throws IOException {
        final AttributeInfo attributes[] = new AttributeInfo[in.readUnsignedShort()];
        for(int i = 0; i < attributes.length; i++) {
            final int nameIndex = in.readUnsignedShort();
            final int attributeLength = in.readInt();
            final ConstantUTF8 name = cp.getEntry(nameIndex, CONSTANT_UTF8);

            attributes[i] = ATTRIBUTE_FACTORY_MAP
                .getOrDefault(name.bytes, UNKNOWN_FACTORY)
                .createAttribute(in, nameIndex, attributeLength, cp);
        }
        return attributes;
    }
}
