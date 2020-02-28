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

public interface AttributeVisitor {
    void visit(AttributeUnknown attribute);

    void visit(AttributeConstantValue attribute);

    void visit(AttributeCode attribute);

    void visit(AttributeSourceFile attribute);

    void visit(AttributeLineNumberTable attribute);

    void visit(AttributeDeprecated attribute);

    void visit(AttributeBootstrapMethods attribute);

    void visit(AttributeStackMapTable attribute);

    void visit(AttributeSynthetic attribute);

    void visit(AttributeMethodParameters attribute);
}
