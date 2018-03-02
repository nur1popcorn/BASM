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

package com.nur1popcorn.basm.transformers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Decompiler implements ITransformer {
    @Override
    public void transform(InputStream in, OutputStream out) throws IOException {
        /*final PrintWriter writer = new PrintWriter(out);
        final ClassFile classFile = new ClassFile(new DataInputStream(in));

        final ConstantPool constantPool = classFile.getConstantPool();
        final ConstantName thisClass = classFile.getThisClass();

        final String className[] = thisClass.indexName(constantPool).bytes.split("/");
        if(className.length > 1) {
            writer.print("package ");
            for(int i = 0; i < className.length - 1; i++) {
                writer.print(className[i]);
                if(i > className.length - 2)
                    writer.print(".");
            }
            writer.println(";");
        }
        writer.println();

        {
            final Set<String> paths = new HashSet<>();
            for(int i = 1 /* the cp's size is 1 less than given *//*; i < constantPool.getSize(); i++) {
                ConstantInfo constantInfo = constantPool.getEntry(i);
                if(constantInfo.getTag() == CONSTANT_CLASS && constantInfo != thisClass)
                    // TODO: filter java/lang
                    paths.add(((ConstantName)constantInfo).indexName(constantPool).bytes.replaceAll("[/$]", "."));
            }

            paths.forEach(path -> {
                writer.print("import ");
                writer.print(path);
                writer.println(";");
            });
        }

        writer.println();

        {
            final int access = classFile.getAccess();
            if((access & ACC_PUBLIC) != 0)
                writer.print("public ");

            if((access & ACC_INTERFACE) != 0)
                writer.print("interface ");
            else if((access & ACC_ABSTRACT) != 0)
                writer.print("abstract class ");
            else
                writer.print("class ");
        }

        writer.print(className[className.length - 1]);
        writer.println(" {");

        for(FieldMethodInfo field : classFile.getFields()) {
            final int access = classFile.getAccess();
            writer.print("\t");
            if((access & ACC_PUBLIC) != 0)
                writer.print("public ");
            else if((access & ACC_PROTECTED) != 0)
                writer.print("protected ");
            else if((access & ACC_PRIVATE) != 0)
                writer.print("private ");
            if((access & ACC_STATIC) != 0)
                writer.print("static ");
            if((access & ACC_FINAL) != 0)
                writer.print("final ");
            if((access & ACC_VOLATILE) != 0)
                writer.print("volatile ");
            if((access & ACC_TRANSIENT) != 0)
                writer.print("transient ");
            writer.print(field.indexDesc(constantPool).bytes);
            writer.print(" ");
            writer.print(field.indexName(constantPool).bytes);
            writer.println(";");
        }
        writer.println();

        for(FieldMethodInfo method : classFile.getMethods()) {
            final int access = classFile.getAccess();

        }
        writer.println("}");

        writer.flush();*/
    }
}
