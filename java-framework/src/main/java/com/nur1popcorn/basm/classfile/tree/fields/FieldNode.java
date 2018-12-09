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
import com.nur1popcorn.basm.classfile.tree.FieldMethodNode;
import com.nur1popcorn.basm.classfile.tree.IFieldMethodNodeVisitor;

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
public class FieldNode extends FieldMethodNode {

    public FieldNode(int access, int nameIndex, int descIndex, ConstantPool constantPool) {
        super(access, nameIndex, descIndex, constantPool);
    }

    public FieldNode(FieldMethodInfo info, ConstantPool constantPool) {
        super(info, constantPool);
    }

    @Override
    public void accept(IFieldMethodNodeVisitor visitor) {

    }
}
