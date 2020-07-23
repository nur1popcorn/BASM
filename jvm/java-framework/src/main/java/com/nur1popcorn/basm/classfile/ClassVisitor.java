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
import com.nur1popcorn.basm.classfile.attributes.AttributeVisitor;
import com.nur1popcorn.basm.classfile.attributes.EmptyAttributeVisitor;;

import java.io.IOException;

/**
 * The {@link ClassVisitor} TODO: desc.
 *
 * @see ClassReader
 * @see ConstantPool
 * @see FieldMethodInfo
 * @see AttributeInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public interface ClassVisitor extends EmptyAttributeVisitor {

    /**
     * <p>Visits the head part of the class.</p>
     *
     * @param minorVersion The read-class's
     *                     <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-B">
     *                         MinorVersion 4.1-200-B
     *                     </a>
     * @param majorVersion
     * @param constantPool
     */
    default void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {}

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
     */
    default void visitBody(int access, int thisClass, int superClass) {}

    default void visitInterface(int index) {}

    /**
     * Visits the fields of the JavaClass.
     *
     * @param field
     */
    AttributeVisitor visitField(FieldMethodInfo field);

    /**
     * Visits the methods of the JavaClass.
     *
     * @param method
     */
    AttributeVisitor visitMethod(FieldMethodInfo method);
}
