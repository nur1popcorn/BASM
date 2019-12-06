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

import com.nur1popcorn.basm.classfile.ConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * The {@link AttributeCode} class stores the instructions of a method along with some other useful
 * information about it. The Code attributes also stores the exception table along with the maximal
 * stack and local variable size.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3">
 *     4.7.3. The Code Attribute
 * </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class AttributeCode extends AttributeInfo {
    private int maxStack /* u2 */,
                maxLocals /* u2 */;

    private byte code[] /* length: u4
                           entries: u1 */;

    private ExceptionTableEntry exceptionTable[] /* length: u2 */;
    private AttributeInfo attributes[] /* length: u2 */;

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public AttributeCode(int nameIndex, int attributeLength, int maxStack, int maxLocals, byte code[],
                         ExceptionTableEntry exceptionTable[], AttributeInfo attributes[]) {
        super(nameIndex, attributeLength);

        this.maxStack = maxStack;
        this.maxLocals = maxLocals;

        this.code = code;

        this.exceptionTable = exceptionTable;
        this.attributes = attributes;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(maxStack);
        os.writeShort(maxLocals);

        os.writeInt(code.length);
        os.write(code, 0, code.length);

        os.writeShort(exceptionTable.length);
        for(ExceptionTableEntry entry : exceptionTable)
            entry.write(os);

        os.write(attributes.length);
        for(AttributeInfo info : attributes)
            info.write(os, cp);
    }

    @Override
    public void accept(IAttributeVisitor v) {
        v.visit(this);
        for(AttributeInfo info : attributes)
            info.accept(v);
    }

    /**
     * Computes the length in bytes of this {@link AttributeInfo}.
     * @return The {@link AttributeInfo}'s length in bytes.
     */
    private int calculateLength() {
        int length = 0;
        for(AttributeInfo info : attributes)
            length += info.getAttributeLength();
        return 2 /* attribute_name_index */ +
               4 /* attribute_length */ +

               2 /* max_stack */ +
               2 /* max_locals */ +

               4 /* code_length */ +
               code.length /* code */ +

               2 /* code_length */ +
               exceptionTable.length * 8 /* exception_table */ +

               2 /* attributes_length */ +
               length /* attributes */;
    }

    /**
     * @return The actual bytes which make up the instructions which implement the method.
     */
    public byte[] getCode() {
        return code;
    }

    /**
     * @param code The actual bytes which make up the instructions that implement this method.
     *
     * @see #setAttributeLength(int)
     * @see #calculateLength()
     */
    public void setCode(byte code[]) {
        this.code = code;
        setAttributeLength(calculateLength());
    }

    /**
     * @return A set of attributes which further describe this <i>Code</i> attribute. The valid set of attributes
     *         includes { LineNumberTable, LocalVariableTable, LocalVariableTypeTable, StackMapTable }. All
     *         valid types of attributes can be found here:
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7-320">
     *             Table 4.7-C. Predefined class file attributes (by location)
     *         </a>
     */
    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    /**
     * @param attributes A set of attributes which further describe this <i>Code</i> attribute. The valid set of attributes
     *                   includes { LineNumberTable, LocalVariableTable, LocalVariableTypeTable, StackMapTable }. All
     *                   valid types of attributes can be found here:
     *                   <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7-320">
     *                      Table 4.7-C. Predefined class file attributes (by location)
     *                   </a>
     */
    public void setAttributes(AttributeInfo attributes[]) {
        this.attributes = attributes;
        setAttributeLength(calculateLength());
    }

    /**
     * Searches the attribute table for an instance of a <i>LineNumberTable</i>.
     * @return The 1st instance of a <i>LineNumberTable</i>.
     */
    public AttributeLineNumberTable getLineNumberTable() {
        for(AttributeInfo info : attributes)
            if(info instanceof AttributeLineNumberTable)
                return (AttributeLineNumberTable) info;
        return null;
    }

    /**
     * Searches the attribute table for an instance of a <i>LocalVariableTable</i>.
     * @return The 1st instance of a <i>LocalVariableTable</i>.
     */
    public AttributeLocalVariableTable getLocalVariableTable() {
        for(AttributeInfo info : attributes)
            if(info instanceof AttributeLocalVariableTable)
                return (AttributeLocalVariableTable) info;
        return null;
    }

    /**
     * Searches the attribute table for an instance of a <i>LocalVariableTypeTable</i>.
     * @return The 1st instance of a <i>LocalVariableTypeTable</i>.
     */
    public AttributeLocalVariableTypeTable getLocalVariableTypeTable() {
        for(AttributeInfo info : attributes)
            if(info instanceof AttributeLocalVariableTypeTable)
                return (AttributeLocalVariableTypeTable) info;
        return null;
    }

    /**
     * Searches the attribute table for an instance of a <i>StackMapTable</i>.
     * @return The 1st instance of a <i>StackMapTable</i>.
     */
    public AttributeStackMapTable getStackMapTable() {
        for(AttributeInfo info : attributes)
            if(info instanceof AttributeStackMapTable)
                return (AttributeStackMapTable) info;
        return null;
    }

    /**
     * @return The maximal depth of the operand stack of this method.
     */
    public int getMaxStack() {
        return maxStack;
    }

    /**
     * @param maxStack The maximal depth of the operand stack of this method.
     */
    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    /**
     * @return The maximal number of locals which may exists at one point in time.
     */
    public int getMaxLocals() {
        return maxLocals;
    }

    /**
     * @param maxLocals The maximal number of locals which may exists at one point in time.
     */
    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    /**
     *
     */
    public void computeMaxes() {
        maxStack = 0;
        maxLocals = 0;


    }

    /**
     * @return The array of all exception handlers.
     */
    public ExceptionTableEntry[] getExceptionTable() {
        return exceptionTable;
    }

    /**
     *  @param exceptionTable The array of all exception handlers.
     */
    public void setExceptionTable(ExceptionTableEntry exceptionTable[]) {
        this.exceptionTable = exceptionTable;
        setAttributeLength(calculateLength());
    }

    @Override
    public String toString() {
        return "Code[" +
            "maxStack=" + maxStack +
            ", maxLocals=" + maxLocals +
            ", code=" + Arrays.toString(code) +
            ", exceptionTable=" + Arrays.toString(exceptionTable) +
            ", attributes=" + Arrays.toString(attributes) +
        ']';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other instanceof AttributeCode) {
            final AttributeCode that = (AttributeCode) other;
            return maxStack == that.maxStack &&
                   maxLocals == that.maxLocals &&
                   Arrays.equals(code, that.code) &&
                   Arrays.equals(exceptionTable, that.exceptionTable) &&
                   Arrays.equals(attributes, that.attributes);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(maxStack, maxLocals);
        result = 31 * result + Arrays.hashCode(code);
        result = 31 * result + Arrays.hashCode(exceptionTable);
        result = 31 * result + Arrays.hashCode(attributes);
        return result;
    }
}
