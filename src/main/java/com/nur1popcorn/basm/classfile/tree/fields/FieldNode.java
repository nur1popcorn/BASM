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

package com.nur1popcorn.basm.classfile.tree.fields;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.attributes.AttributeConstantValue;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantInteger;
import com.nur1popcorn.basm.classfile.constants.ConstantLong;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import static com.nur1popcorn.basm.Constants.*;

/**
 * The {@link FieldNode} provides an abstraction layer between the bytecode representation of a
 *                       field and its user.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7-320">
 *     JavaClass attributes ordered by location Table 4.7-C.
 * </a>
 *
 * @see FieldMethodInfo
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class FieldNode {
    public int access;

    public String name,
                  desc;

    private int valueTag;
    private Object value;

    public FieldNode(FieldMethodInfo fieldInfo, ConstantPool constantPool) throws FieldNodeParseException {
        access = fieldInfo.getAccess();

        name = fieldInfo.indexName(constantPool).bytes;
        desc = fieldInfo.indexDesc(constantPool).bytes;

        final AttributeInfo attributeInfos[] = fieldInfo.getAttributes();
        for(AttributeInfo attributeInfo : attributeInfos)
            switch(attributeInfo.indexName(constantPool).bytes) {
                // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2
                case "ConstantValue":
                    final ConstantInfo constantInfo = ((AttributeConstantValue) attributeInfo)
                            .indexConstantValue(constantPool);
                    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2-300-C.1
                    switch(valueTag = constantInfo.getTag()) {
                        case CONSTANT_LONG:
                            value = ((ConstantLong) constantInfo).asLong();
                            break;
                        case CONSTANT_FLOAT:
                            value = ((ConstantInteger) constantInfo).asFloat();
                            break;
                        case CONSTANT_DOUBLE:
                            value = ((ConstantLong) constantInfo).asDouble();
                            break;
                        case CONSTANT_INTEGER: {
                            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3.2
                            final int value = ((ConstantInteger) constantInfo).asInteger();
                            switch (desc) {
                                case "I":
                                    this.value = value;
                                    break;
                                case "S":
                                    this.value = (short) value;
                                    break;
                                case "C":
                                    this.value = (char) value;
                                    break;
                                case "B":
                                    this.value = (byte) value;
                                    break;
                                case "Z":
                                    this.value = value != 0;
                                    break;
                                default:
                                    throw new FieldNodeParseException(); //TODO: desc
                            }
                            break;
                        }
                        case CONSTANT_STRING:
                            value = ((ConstantUtf8) constantInfo).bytes;
                            break;
                    }
                    break;
                case "Synthetic":
                    break;
                case "Deprecated":
                    break;
                case "Signature":
                    break;
                case "RuntimeVisibleAnnotations":
                    break;
                case "RuntimeInvisibleAnnotations":
                    break;
                case "RuntimeVisibleTypeAnnotations":
                    break;
                case "RuntimeInvisibleTypeAnnotations":
                    break;
                default:
                    throw new FieldNodeParseException(); //TODO: add info.
            }
    }

    public void setValue(int tag, Object value) {
    }

    public int getValueTag() {
        return valueTag;
    }

    public Object getValue() {
        return value;
    }
}
