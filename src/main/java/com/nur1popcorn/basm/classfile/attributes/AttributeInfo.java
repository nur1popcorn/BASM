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

package com.nur1popcorn.basm.classfile.attributes;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantUtf8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@link AttributeInfo} is an abstract class containing information about the
 * attribute and ways to read them.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7">
 *     Attributes 4.7
 * </a>
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public abstract class AttributeInfo {
    private static Map<String, Constructor<? extends AttributeInfo>> ATTRIBUTE_CONSTRUCTOR_MAP; static {
        try { // setup constructor map for dynamic invocation.
            ATTRIBUTE_CONSTRUCTOR_MAP = Stream.of(
                /*
                 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.2">
                 *     ConstantValue 4.7.2
                 * </a>
                 */
                new SimpleEntry<>("ConstantValue", AttributeConstantValue.class.getDeclaredConstructor(DataInputStream.class)),
                /*
                 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3">
                 *     Code 4.7.3
                 * </a>
                 */
                new SimpleEntry<>("Code", AttributeCode.class.getDeclaredConstructor(DataInputStream.class, ConstantPool.class)),
                //TODO: impl
                new SimpleEntry<>("StackMapTable", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("Exceptions", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("InnerClasses", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("EnclosingMethod", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("Synthetic", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("Signature", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("SourceFile", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("SourceDebugExtension", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("LineNumberTable", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("LocalVariableTable", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("LocalVariableTypeTable", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("Deprecated", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleParameterAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleParameterAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleTypeAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleTypeAnnotations", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("AnnotationDefault", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("BootstrapMethods", AttributeCode.class.getDeclaredConstructor(DataInputStream.class)),
                new SimpleEntry<>("MethodParameters", AttributeCode.class.getDeclaredConstructor(DataInputStream.class))
            ).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected int attributeLength;

    /**
     * @param in the {@link DataInputStream} from which the {@link AttributeInfo}'s
     *           length should be read.
     */
    public AttributeInfo(DataInputStream in) throws IOException {
        attributeLength = in.readInt();
    }

    public void write(DataOutputStream os) throws IOException {
        //TODO: impl
    }

    /**
     * @param in the {@link DataInputStream} from which the {@link AttributeInfo}s
     *           should be read.
     * @param constantPool the {@link ConstantPool} is used to index the attribute's
     *                     identifier and may or may not be used to construct certain
     *                     attributes.
     */
    public static AttributeInfo[] read(DataInputStream in, ConstantPool constantPool) throws IOException {
        final AttributeInfo attributes[] = new AttributeInfo[in.readUnsignedShort()];
        try {
            for(int i = 0; i < attributes.length; i++) {
                final Constructor<? extends AttributeInfo> constructor = ATTRIBUTE_CONSTRUCTOR_MAP.get(
                    ((ConstantUtf8) constantPool.getEntry(in.readUnsignedShort())).bytes);
                attributes[i] = constructor.newInstance(
                    constructor.getParameters().length == 1 ?
                        new Object[] { in } :
                        /* some constructors like AttributeCode's need 2 parameters. */
                        new Object[] {
                            in,
                            constantPool
                        });
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            //rethrow IOException.
            final Throwable cause = e.getCause();
            if(cause instanceof IOException)
                throw (IOException) cause;
            else
                e.printStackTrace();
        }
        return attributes;
    }
}
