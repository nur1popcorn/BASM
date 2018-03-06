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

package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.ClassReader;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.IClassVisitor;
import com.nur1popcorn.basm.classfile.attributes.AttributeCode;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantName;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNode;
import com.nur1popcorn.basm.classfile.tree.fields.FieldNodeParseException;
import com.nur1popcorn.basm.classfile.tree.methods.Code;
import com.nur1popcorn.basm.classfile.tree.methods.Instruction;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNode;
import com.nur1popcorn.basm.classfile.tree.methods.MethodNodeParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.classfile.ClassReader.*;
import static com.nur1popcorn.basm.classfile.tree.methods.Instruction.NOT_AN_INSTRUCTION;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.NoParameterInstruction.NO_PARAMETER_INSTRUCTION;

/**
 * The {@link ClassFile} provides an abstraction layer between bytecode and user.
 *
 * @see IClassVisitor
 * @see ConstantPool
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ClassFile implements IClassVisitor {

    private ConstantPool constantPool;

    public int access;

    private int minorVersion,
                majorVersion;

    public String thisClass,
                  superClass;

    private List<String> interfaces = new ArrayList<>();

    private List<FieldNode> fieldNodes = new ArrayList<>();
    private List<MethodNode> methodNodes = new ArrayList<>();

    // prevent construction :/
    private ClassFile()
    {}

    public ClassFile(DataInputStream din) throws IOException {
        new ClassReader(din)
                .accept(this, READ_HEAD |
                              READ_BODY |
                              READ_METHODS |
                              READ_FIELDS);
    }

    public static ClassFileBuilder builder() {
        return new ClassFileBuilder();
    }

    @Override
    public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
    }

    @Override
    public void visitBody(int access, int thisClass, int superClass, int interfaces[]) {
        this.access = access;
        this.thisClass = ((ConstantName)constantPool.getEntry(thisClass))
                .indexName(constantPool).bytes;
        this.superClass = ((ConstantName)constantPool.getEntry(superClass))
                .indexName(constantPool).bytes;
        this.interfaces = new ArrayList<>(interfaces.length);
        for (int anInterface : interfaces)
            this.interfaces.add(
                ((ConstantName)constantPool.getEntry(anInterface))
                    .indexName(constantPool).bytes
            );
    }

    @Override
    public void visitFields(FieldMethodInfo[] fields) throws FieldNodeParseException {
        for(FieldMethodInfo fieldInfo : fields)
            fieldNodes.add(new FieldNode(fieldInfo, constantPool));
    }

    @Override
    public void visitMethods(FieldMethodInfo[] methods) throws MethodNodeParseException {
        for(FieldMethodInfo methodInfo : methods)
            methodNodes.add(new MethodNode(methodInfo, constantPool));
    }

    public void accept(IClassVisitor visitor) throws IOException {
        final ConstantPool.ConstantPoolBuilder builder = ConstantPool.builder();

        final int thisClass = builder.addConstantClass(this.thisClass);
        final int superClass = builder.addConstantClass(this.superClass);

        final int interfaces[] = new int[this.interfaces.size()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = builder.addConstantUtf8(this.interfaces.get(i));

        final FieldMethodInfo fields[] = new FieldMethodInfo[fieldNodes.size()];
        for(int i = 0; i < fieldNodes.size(); i++) {
            final FieldNode fieldNode = fieldNodes.get(i);
            fields[i] = new FieldMethodInfo(
                fieldNode.access,
                builder.addConstantUtf8(fieldNode.name),
                builder.addConstantUtf8(fieldNode.desc),
                new AttributeInfo[0] // TODO: add to FieldNode.
            );
        }

        final int codeIndex = builder.addConstantUtf8("Code");
        final FieldMethodInfo methods[] = new FieldMethodInfo[methodNodes.size()];
        for(int i = 0; i < methodNodes.size(); i++) {
            final MethodNode methodNode = methodNodes.get(i);
            byte byteCode[] = new byte[0];
            if(methodNode.visitCodeAt(0))
                do {
                    final Instruction instruction = methodNode.visitCurrentInstruction();
                    switch(instruction.getType()) {
                        case NOT_AN_INSTRUCTION:
                        case NO_PARAMETER_INSTRUCTION:
                            
                            break;
                    }
                } while(methodNode.visitNextInstruction());
            final Code code = methodNode.getCode();
            methods[i] = new FieldMethodInfo(
                methodNode.access,
                builder.addConstantUtf8(methodNode.name),
                builder.addConstantUtf8(methodNode.desc),
                new AttributeInfo[] {
                    new AttributeCode(
                        codeIndex,
                        code.maxStack,
                        code.maxLocals,
                        byteCode,
                        new AttributeCode.ExceptionTableEntry[0], // TODO: add to MethodNode.
                        new AttributeInfo[0]
                    )
                });
        }

        visitor.visitHead(
            minorVersion,
            majorVersion,
            builder.build()
        );

        visitor.visitBody(
            access,
            thisClass,
            superClass,
            interfaces
        );

        visitor.visitFields(
            fields
        );

        visitor.visitMethods(
            methods
        );
    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }

    /**
     * @see #builder()
     */
    public static final class ClassFileBuilder {
        private ClassFile classFile;

        // prevent construction :/
        private ClassFileBuilder() {
            classFile = new ClassFile();
        }

        public ClassFileBuilder setAccess(int access) {
            classFile.access = access;
            return this;
        }
    }
}
