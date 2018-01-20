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

import com.nur1popcorn.basm.classfile.ClassFile;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.*;
import java.util.List;

import static com.nur1popcorn.basm.utils.Constants.*;

public class Disassembler implements Transformer {
    @Override
    public void transform(List<File> in, File out) throws IOException {
        out.delete();
        if(!out.createNewFile())
            throw new IOException("Failed to create output file.");
        final PrintWriter writer = new PrintWriter(new FileOutputStream(out));
        in.forEach(file -> {
            if(file.exists() && file.getName().endsWith(".class")) {
                try {
                    final FileInputStream fin = new FileInputStream(file);
                    final ClassFile classFile = new ClassFile(new DataInputStream(fin));

                    for(int mask = 0x1, i = 0; mask != ACC_MANDATED; mask <<= 1, i++)
                        if(ACC_CLASS_NAMES[i] != null && (classFile.access & mask) != 0) {
                            writer.print(ACC_CLASS_NAMES[i]);
                            writer.print(" ");
                        }

                    writer.print("class extends ");
                    final ConstantPool constantPool = classFile.getConstantPool();
                    writer.print(classFile.getSuperClass().indexName(constantPool).bytes);

                    writer.println(" {");

                    for(FieldMethodInfo info : classFile.getFields()) {
                        writer.write("\t");
                        for(int mask = 0x1, i = 0; mask != ACC_MANDATED; mask <<= 1, i++)
                            if(ACC_FIELD_NAMES[i] != null && (info.access & mask) != 0) {
                                writer.print(ACC_FIELD_NAMES[i]);
                                writer.print(" ");
                            }
                        writer.print(info.indexName(constantPool).bytes);
                        writer.write(" ");
                        writer.println(info.indexDesc(constantPool).bytes);
                    }

                    for(FieldMethodInfo info : classFile.getMethods()) {
                        writer.print("\n\t");
                        for(int mask = 0x1, i = 0; mask != ACC_ANNOTATION; mask <<= 1, i++)
                            if(ACC_METHOD_NAMES[i] != null && (info.access & mask) != 0) {
                                writer.print(ACC_METHOD_NAMES[i]);
                                writer.print(" ");
                            }
                        writer.print(info.indexName(constantPool).bytes);
                        writer.print(info.indexDesc(constantPool).bytes);
                        writer.println(" {");
                        AttributeCode attributeCode = null;
                        for(AttributeInfo attribute : info.getAttributes())
                            if(attribute.indexName(constantPool).bytes.equals("Code")) {
                                attributeCode = (AttributeCode) attribute;
                                break;
                            }
                        if(attributeCode != null) {
                            final byte opcodes[] = attributeCode.getByteCode();
                            for(int i = 0; i < opcodes.length; i++) {
                                final int opcode = Byte.toUnsignedInt(opcodes[i]);
                                writer.print("\t\t");
                                writer.print(Integer.toHexString(i));
                                writer.print(": ");
                                writer.print(OPCODE_MNEMONICS[opcode]);
                                final int parameters = OPCODE_PARAMETERS[opcode];
                                for(int j = 0; j < parameters; j++) {
                                    writer.print(" $");
                                    writer.print(Integer.toHexString(Byte.toUnsignedInt(opcodes[i + j + 1])));
                                }
                                i += parameters;
                                writer.println();
                            }
                        } else
                            writer.println();
                        writer.println("\t}");
                    }

                    writer.println("}");
                    writer.println();
                    writer.flush();
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.close();
    }
}
