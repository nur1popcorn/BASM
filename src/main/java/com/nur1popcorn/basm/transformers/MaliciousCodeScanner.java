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

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;
import com.sun.javadoc.*;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javadoc.JavadocTool;
import com.sun.tools.javadoc.Messager;
import com.sun.tools.javadoc.ModifierFilter;

import javax.tools.JavaFileObject;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.nur1popcorn.basm.Constants.CONSTANT_METHOD_REF;
import static com.nur1popcorn.basm.Constants.MAGIC;

public final class MaliciousCodeScanner implements ITransformer {

    private static final String MALICIOUS_PATHS[] = {
        "java/io",
        "java/lang/reflect",
        "java/net"
    };

    private final Map<String, RootDoc> DOC_MAP = new HashMap<>();

    private static final com.sun.tools.javac.util.List<?> EMPTY_LIST = new ListBuffer<>().toList();
    private static JavadocTool tool;

    static {
        final Context context = new Context();
        try {
            final Constructor constructor = Messager.class.getDeclaredConstructor(Context.class, String.class);
            constructor.setAccessible(true);
            constructor.newInstance(context, "MaliciousCodeScanner"); // instance is used by JavadocTool.
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        tool = JavadocTool.make0(context);
        if(tool == null) {
            System.out.println("shit.");
            System.exit(1);
        }
    }

    private RootDoc getRootDoc(String clazz) throws IOException {
        RootDoc doc = DOC_MAP.get(clazz);
        if(doc == null) {
            doc = tool.getRootDocImpl("", null, new ModifierFilter(0), ListBuffer.of(clazz).toList(), (com.sun.tools.javac.util.List<String[]>) EMPTY_LIST, (com.sun.tools.javac.util.List<JavaFileObject>) EMPTY_LIST, false, (com.sun.tools.javac.util.List<String>) EMPTY_LIST, (com.sun.tools.javac.util.List<String>) EMPTY_LIST, false, false, true);
            DOC_MAP.put(clazz, doc);
        }
        return doc;
    }

    private static String getMethodDesc(ExecutableMemberDoc method) {
        final StringBuilder stringBuilder = new StringBuilder()
                .append("(");
        for(Parameter parameter : method.parameters()) {
            final String type = parameter.type()
                    .qualifiedTypeName();
            // TODO: arrays.
            // based on table 4.3-A: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3.2
            switch(type) {
                case "byte":
                    stringBuilder.append("B");
                    break;
                case "char":
                    stringBuilder.append("C");
                    break;
                case "double":
                    stringBuilder.append("D");
                    break;
                case "float":
                    stringBuilder.append("F");
                    break;
                case "int":
                    stringBuilder.append("I");
                    break;
                case "long":
                    stringBuilder.append("J");
                    break;
                default:
                    stringBuilder.append("L")
                            .append(type.replace(".", "/"))
                            .append(";");
                    break;
                case "short":
                    stringBuilder.append("S");
                    break;
                case "boolean":
                    stringBuilder.append("Z");
                    break;
            }
        }
        return stringBuilder.append(")")
                .toString();
    }

    @Override
    public void transform(InputStream in, OutputStream out) throws IOException {
        final PrintWriter writer = new PrintWriter(out);
        DataInputStream din = new DataInputStream(in);
        final int magic = din.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);
        din.skipBytes(4);

        final ConstantPool constantPool = new ConstantPool(din);
        for(int i = 1 /* the cp's size is 1 less than given */; i < constantPool.getSize(); i++) {
            final ConstantInfo constantInfo = constantPool.getEntry(i);
            if(constantInfo != null)
                if(constantInfo.getTag() == CONSTANT_METHOD_REF) {
                    final ConstantMethodRef methodRef = (ConstantMethodRef) constantInfo;
                    final String path = methodRef.indexClass(constantPool)
                                                 .indexName(constantPool)
                                                 .bytes;
                    for(String malicious : MALICIOUS_PATHS)
                        if(path.startsWith(malicious)) {
                            writer.print(path);
                            writer.print("#");
                            final ConstantNameAndType nameAndType = methodRef.indexNameAndType(constantPool);
                            final String name = nameAndType.indexName(constantPool).bytes;
                            String desc = nameAndType.indexDesc(constantPool).bytes;
                            writer.print(name);
                            writer.println(desc);

                            final ClassDoc docs[] = getRootDoc("src/" + path + ".java").classes();
                            if(docs.length != 0) {
                                desc = desc.substring(0, desc.indexOf(')') + 1);
                                if(name.equals("<init>")) {
                                    for(ConstructorDoc constructor : docs[0].constructors())
                                        if(getMethodDesc(constructor).equals(desc)) {
                                            writer.println(constructor.getRawCommentText());
                                            break;
                                        }
                                } else
                                    for(MethodDoc method : docs[0].methods())
                                        if(method.name().equals(name) &&
                                           getMethodDesc(method).equals(desc)) {
                                            writer.println(method.getRawCommentText());
                                            break;
                                        }
                            }
                        }
                }
        }
        writer.flush();
    }
}
