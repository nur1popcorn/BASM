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

import java.io.IOException;

public interface IClassVisitor {

    /**
     * Visits the head part of the class.
     */
    default void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) throws IOException
    {}

    /**
     * Visits the body part of the class.
     *
     * @param access The read-class's access flag mask.
     *               <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E">
     *                   AccessFlags 4.1-200-E
     *               </a>
     * @param thisClass a pointer into the the {@link ConstantPool}, pointing to an instance of a
     *                  CONSTANT_Class representing the class read.
     *                  <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-F">
     *                      ThisClass 4.1-200-I
     *                  </a>
     * @param superClass a pointer into the the {@link ConstantPool}, pointing to an instance of a
     *                   CONSTANT_Class representing the class-read's super class.
     *                   <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-G">
     *                      SuperClass 4.1-200-G
     *                   </a>
     * @param interfaces a table of pointers pointing into the {@link ConstantPool}, to instances of
     *                   CONSTANT_Classes representing the classes-read's implemented interfaces.
     *                   <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-I">
     *                      Interfaces 4.1-200-I
     *                   </a>
     */
    default void visitBody(int access, int thisClass, int superClass, int interfaces[]) throws IOException
    {}

    default void visitMethods(FieldMethodInfo methods[]) throws IOException
    {}

    default void visitFields(FieldMethodInfo fields[]) throws IOException
    {}
}
