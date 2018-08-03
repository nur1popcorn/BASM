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

import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.IOException;

/**
 * The {@link IClassVisitor} TODO: desc.
 *
 * @see ClassReader
 * @see ConstantPool
 * @see FieldMethodInfo
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public interface IClassVisitor {

    /**
     * <p>Visits the head part of the class.</p>
     *
     * @param minorVersion The read-class's
     *                     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-B">
     *                         MinorVersion 4.1-200-B
     *                     </a>
     * @param majorVersion
     * @param constantPool
     *
     * @throws IOException if an error occurs while reading the JavaClass.
     */
    default void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) throws IOException
    {}

    /**
     * <p>Visits the body part of the class.</p>
     *
     * @param access <p>The read-class's access flags.</p>
     *               <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E">
     *                   AccessFlags 4.1-200-E
     *               </a>
     * @param thisClass <p>a pointer into the the {@link ConstantPool}, pointing to an instance of a
     *                     CONSTANT_Class representing the class read.</p>
     *                  <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-F">
     *                      ThisClass 4.1-200-I
     *                  </a>
     * @param superClass a pointer into the the {@link ConstantPool}, pointing to an instance of a
     *                   CONSTANT_Class representing the class-read's super class.
     *                   <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-G">
     *                       SuperClass 4.1-200-G
     *                   </a>
     * @param interfaces <p>a table of pointers pointing into the {@link ConstantPool}, to instances of
     *                      CONSTANT_Classes representing the classes-read's implemented interfaces.</p>
     *                   <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-I">
     *                       Interfaces 4.1-200-I
     *                   </a>
     *
     * @throws IOException if an error occurs while reading the JavaClass.
     */
    default void visitBody(int access, int thisClass, int superClass, int interfaces[]) throws IOException
    {}

    /**
     * Visits the methods of the JavaClass.
     *
     * @param methods
     *
     * @throws IOException if an error occurs while reading the JavaClass.
     */
    default void visitMethods(FieldMethodInfo methods[]) throws IOException
    {}

    /**
     * Visits the fields of the JavaClass.
     *
     * @param fields
     *
     * @throws IOException if an error occurs while reading the JavaClass.
     */
    default void visitFields(FieldMethodInfo fields[]) throws IOException
    {}

    /**
     * Visits the footer part of the JavaClass.
     *
     * @param attributes
     *
     * @throws IOException if an error occurs while reading the JavaClass.
     */
    default void visitFooter(AttributeInfo attributes[]) throws IOException
    {}
}
