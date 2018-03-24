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
import com.nur1popcorn.basm.classfile.tree.methods.*;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.*;
import com.nur1popcorn.basm.utils.ConstantPoolBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.classfile.ClassReader.*;
import static com.nur1popcorn.basm.classfile.tree.methods.Instruction.*;

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
            .accept(this, READ_ALL);
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
        final ConstantPoolBuilder builder = new ConstantPoolBuilder();

        // the first 254 entries of the constantpool are reserved for entries to whom an ldc instruction points.
        methodNodes.forEach(methodNode -> {
            if(methodNode.visitCodeAt(0))
                do {
                    final Instruction instruction = methodNode.visitCurrentInstruction();
                    if(instruction.getOpcode() == LDC) {
                        final LDCInstruction ldcInstruction = (LDCInstruction) instruction;
                        switch (ldcInstruction.getTag()) {
                            default:
                            case CONSTANT_STRING:
                                builder.addConstantString((String) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_INTEGER:
                                builder.addConstantInteger((int) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_FLOAT:
                                builder.addConstantFloat((float) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_CLASS:
                                builder.addConstantClass((String) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_METHOD_TYPE:
                                builder.addConstantMethodType((String) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_METHOD_HANDLE: {
                                final MethodHandle methodHandle = (MethodHandle) ldcInstruction.getConstant();
                                builder.addConstantMethodHandle(
                                        methodHandle.refKind,
                                        methodHandle.clazz,
                                        methodHandle.name,
                                        methodHandle.type);
                            }
                            break;
                            case CONSTANT_LONG:
                                builder.addConstantLong((long) ldcInstruction.getConstant());
                                break;
                            case CONSTANT_DOUBLE:
                                builder.addConstantDouble((double) ldcInstruction.getConstant());
                                break;
                        }
                    }
                } while(methodNode.visitNextInstruction());
        });

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
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            if(methodNode.visitCodeAt(0))
                do {
                    final Instruction instruction = methodNode.visitCurrentInstruction();
                    switch(instruction.getType()) {
                        case BIPUSH_INSTRUCTION:
                            bos.write(BIPUSH);
                            bos.write(((BIPushInstruction) instruction).data);
                            break;
                        case CLASS_INSTRUCTION: {
                            bos.write(instruction.getOpcode());
                            final ClassInstruction classInstruction = (ClassInstruction) instruction;
                            final int index = builder.addConstantClass(classInstruction.clazz);
                            bos.write(index >> 8);
                            bos.write(index);
                        }   break;
                        case IINC_INSTRUCTION: {
                            bos.write(IINC);
                            final IIncInstruction iincInstruction = (IIncInstruction) instruction;
                            bos.write(iincInstruction.index);
                            bos.write(iincInstruction.constant);
                        }   break;
                        case INVOKEDYNAMIC_INSTRUCTION:
                            // TODO: impl
                            break;
                        case JUMP_INSTRUCTION: {
                            final JumpInstruction jumpInstruction = (JumpInstruction) instruction;
                            final byte opcode = jumpInstruction.getOpcode();
                            bos.write(opcode);
                            final int jumpOffset =
                                methodNode.getCode()
                                          .indexOf(jumpInstruction) - bos.size() + 2;
                            switch(opcode) {
                                case GOTO_W:
                                case JSR_W:
                                    bos.write(jumpOffset >> 24);
                                    bos.write(jumpOffset >> 16);
                                    bos.write(jumpOffset >> 8);
                                    bos.write(jumpOffset);
                                    break;
                                default:
                                    bos.write(jumpOffset >> 8);
                                    bos.write(jumpOffset);
                                    break;
                            }
                        }   break;
                        case LDC_INSTRUCTION: {
                            int index;
                            final LDCInstruction ldcInstruction = (LDCInstruction) instruction;
                            switch(ldcInstruction.getTag()) {
                                default:
                                case CONSTANT_STRING:
                                    index = builder.addConstantString((String) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_INTEGER:
                                    index = builder.addConstantInteger((int) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_FLOAT:
                                    index = builder.addConstantFloat((float) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_CLASS:
                                    index = builder.addConstantClass((String) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_METHOD_TYPE:
                                    index = builder.addConstantMethodType((String) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_METHOD_HANDLE: {
                                    final MethodHandle methodHandle = (MethodHandle) ldcInstruction.getConstant();
                                    index = builder.addConstantMethodHandle(
                                        methodHandle.refKind,
                                        methodHandle.clazz,
                                        methodHandle.name,
                                        methodHandle.type);
                                }   break;
                                case CONSTANT_LONG:
                                    index = builder.addConstantLong((long) ldcInstruction.getConstant());
                                    break;
                                case CONSTANT_DOUBLE:
                                    index = builder.addConstantDouble((double) ldcInstruction.getConstant());
                                    break;
                            }

                            bos.write(ldcInstruction.getOpcode());
                            switch(ldcInstruction.getOpcode()) {
                                default:
                                case LDC:
                                    bos.write(index);
                                    break;
                                case LDC_W:
                                case LDC2_W:
                                    bos.write(index >> 8);
                                    bos.write(index);
                                    break;
                            }
                        }   break;
                        case LOCAL_VARIABLE_INSTRUCTION:
                            bos.write(instruction.getOpcode());
                            bos.write(((LocalVariableInstruction) instruction).index);
                            break;
                        case MULTIANEWARRAY_INSTRUCTION: {
                            bos.write(MULTIANEWARRAY);
                            final MultiANewArrayInstruction multiANewArrayInstruction = (MultiANewArrayInstruction) instruction;
                            final int index = builder.addConstantClass(multiANewArrayInstruction.clazz);
                            bos.write(index >> 8);
                            bos.write(index);
                            bos.write(multiANewArrayInstruction.dimensions);
                        }   break;
                        case NEWARRAY_INSTRUCTION:
                            bos.write(NEWARRAY);
                            bos.write(((NewArrayInstruction) instruction).atype);
                            break;
                        case NOT_AN_INSTRUCTION:
                        case NO_PARAMETER_INSTRUCTION:
                            bos.write(instruction.getOpcode());
                            break;
                        case REF_INSTRUCTION: {
                            final RefInstruction refInstruction = (RefInstruction) instruction;
                            bos.write(refInstruction.getOpcode());
                            switch(refInstruction.getOpcode()) {
                                case GETSTATIC:
                                case PUTSTATIC:
                                case GETFIELD:
                                case PUTFIELD: {
                                    final int index = builder.addConstantFieldRef(
                                        refInstruction.clazz,
                                        refInstruction.name,
                                        refInstruction.desc);
                                    bos.write(index >> 8);
                                    bos.write(index);
                                }   break;
                                case INVOKEVIRTUAL:
                                case INVOKESPECIAL:
                                case INVOKESTATIC:{
                                    final int index = builder.addConstantMethodRef(
                                        refInstruction.clazz,
                                        refInstruction.name,
                                        refInstruction.desc);
                                    bos.write(index >> 8);
                                    bos.write(index);
                                }   break;
                                case INVOKEINTERFACE:
                                    // TODO: impl
                                    break;
                            }
                        }   break;
                        case SIPUSH_INSTRUCTION: {
                            bos.write(SIPUSH);
                            final short value = ((SIPushInstruction) instruction).data;
                            bos.write(value >> 8);
                            bos.write(value);
                        }   break;
                        case SWITCH_INSTRUCTION:
                            // TODO: impl
                            break;
                        case WIDE_INSTRUCTION:
                            // TODO: impl
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
                        bos.toByteArray(),
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

        // TODO: impl.
        visitor.visitFooter(
            new AttributeInfo[0]
        );
    }

    @Override
    public void visitFooter(AttributeInfo[] attributes) throws IOException {
        // TODO: impl.
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

        public ClassFileBuilder setThisClass(String thisClass) {
            classFile.thisClass = thisClass;
            return this;
        }

        public ClassFileBuilder setSuperClass(String superClass) {
            classFile.superClass = superClass;
            return this;
        }

        public ClassFileBuilder addInterface(String itf) {
            classFile.interfaces.add(itf);
            return this;
        }

        public ClassFileBuilder addFieldNode(FieldNode fieldNode) {
            classFile.fieldNodes.add(fieldNode);
            return this;
        }

        public ClassFileBuilder addMethodNode(MethodNode methodNode) {
            classFile.methodNodes.add(methodNode);
            return this;
        }

        public ClassFile build() {
            return classFile;
        }
    }
}
