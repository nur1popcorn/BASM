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

public final class Disassembler implements Transformer {
    @Override
    public void transform(InputStream in, OutputStream out) throws IOException {

    }
    /*@Override
    public void transform(InputStream in, OutputStream out) throws IOException {
        final PrintWriter writer = new PrintWriter(out);
        final ClassFile classFile = new ClassFile(new DataInputStream(in));

        {
            final int access = classFile.access;
            for(int mask = 0x1, i = 0; mask != ACC_MANDATED; mask <<= 1, i++)
                if(ACC_CLASS_NAMES[i] != null && (access & mask) != 0) {
                    writer.print(ACC_CLASS_NAMES[i]);
                    writer.print(" ");
                }
        }

        writer.print("class extends ");
        writer.print(classFile.superClass);
        writer.println(":");

        for(FieldNode node : classFile.getFieldNodes()) {
            writer.write("  ");
            final int access = node.access;
            for(int mask = 0x1, i = 0; mask != ACC_MANDATED; mask <<= 1, i++)
                if(ACC_FIELD_NAMES[i] != null && (access & mask) != 0) {
                    writer.print(ACC_FIELD_NAMES[i]);
                    writer.print(" ");
                }
            writer.print(node.desc);
            writer.write(" ");
            writer.print(node.name);
            writer.println(";");
        }

        final CodePrinter codePrinter = new CodePrinter(new PrintWriter(writer) {
            @Override
            public void println() {
                super.println();
                print("    ");
            }
        });

        classFile.getMethodNodes().forEach(methodNode -> {
            writer.print("  ");
            final int access = methodNode.access;
            for(int mask = 0x1, i = 0; mask != ACC_ANNOTATION; mask <<= 1, i++)
                if(ACC_METHOD_NAMES[i] != null && (access & mask) != 0) {
                    writer.print(ACC_METHOD_NAMES[i]);
                    writer.print(" ");
                }
            writer.print(methodNode.name);
            writer.print(" ");
            writer.print(methodNode.desc);
            writer.println(":");
            writer.print("    ");
            methodNode.getCode()
                .accept(codePrinter);
            writer.println();
        });

        writer.println();
        writer.flush();
    }*/
}
