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
 * @see ConstantPool
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
                new SimpleEntry<>("ConstantValue", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                /*
                 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3">
                 *     Code 4.7.3
                 * </a>
                 */
                new SimpleEntry<>("Code", AttributeCode.class.getDeclaredConstructor(int.class, DataInputStream.class, ConstantPool.class)),
                /*
                 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.4">
                 *     StackMapTable 4.7.4
                 * </a>
                 */
                new SimpleEntry<>("StackMapTable", AttributeStackMapTable.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                //TODO: impl
                new SimpleEntry<>("Exceptions", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("InnerClasses", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("EnclosingMethod", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("Synthetic", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("Signature", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("SourceFile", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("SourceDebugExtension", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("LineNumberTable", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("LocalVariableTable", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("LocalVariableTypeTable", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("Deprecated", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleParameterAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleParameterAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeVisibleTypeAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("RuntimeInvisibleTypeAnnotations", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("AnnotationDefault", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("BootstrapMethods", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class)),
                new SimpleEntry<>("MethodParameters", AttributeConstantValue.class.getDeclaredConstructor(int.class, DataInputStream.class))
            ).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected int nameIndex /* u2 */,
                  attributeLength /* u4 */;

    /**
     * @param nameIndex is a entry into the {@link ConstantPool} and represents the
     *                  {@link AttributeInfo}'s identifier
     * @param in the {@link DataInputStream} from which the {@link AttributeInfo}'s
     *           length should be read.
     */
    public AttributeInfo(int nameIndex, DataInputStream in) throws IOException {
        this.nameIndex = nameIndex;
        attributeLength = in.readInt();
    }

    /**
     * @param attributeLength the size of the {@link AttributeInfo} written to disc.
     */
    public AttributeInfo(int nameIndex, int attributeLength) {
        this.nameIndex = nameIndex;
        this.attributeLength = attributeLength;
    }

    /**
     * @param os the {@link DataOutputStream} to which the {@link AttributeInfo}'s
     *           identifier index and the {@link AttributeInfo}'s size should be
     *           written.
     */
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        os.writeShort(nameIndex);
        os.writeInt(attributeLength);
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
                final int nameIndex = in.readUnsignedShort();
                final Constructor<? extends AttributeInfo> constructor = ATTRIBUTE_CONSTRUCTOR_MAP.get(
                    ((ConstantUtf8) constantPool.getEntry(nameIndex)).bytes);
                attributes[i] = constructor.newInstance(
                   constructor.getParameters().length == 2 ?
                       new Object[] {
                           nameIndex,
                           in
                       } :
                       /* some constructors like AttributeCode's require the constantpool
                          to be passed as a parameter. */
                       new Object[] {
                           nameIndex,
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
