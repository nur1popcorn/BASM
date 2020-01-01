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
import java.util.Comparator;

/**
 * The {@link AttributeLineNumberTable} is an optional attribute which may be used by debuggers,
 * decompilers, etc. to figure out which instruction corresponds with which linenumber.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.12">
 *     4.7.12. The LineNumberTable Attribute
 * </a>
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class AttributeLineNumberTable extends AttributeInfo {
    private LineNumberTableEntry table[];

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     * @param table The table which maps the instruction indices to line numbers.
     */
    public AttributeLineNumberTable(int nameIndex, int attributeLength, LineNumberTableEntry table[]) {
        super(nameIndex, attributeLength);
        this.table = table;
        Arrays.sort(table, Comparator.comparingInt(
            LineNumberTableEntry::getStartPc));
    }

    @Override
    public void accept(AttributeVisitor v) {
        v.visit(this);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(table.length);
        for(LineNumberTableEntry entry : table)
            entry.write(os);
    }

    /**
     * Computes the length in bytes of this {@link AttributeInfo}.
     * @return The {@link AttributeInfo}'s length in bytes.
     */
    private int calculateLength() {
        return  2 /* attribute_name_index */ +
                4 /* attribute_length */ +

                2 /* table_length */ +
                table.length * 4 /* line_number_table */;
    }

    /**
     * @return The table which maps the instruction indices to line numbers.
     */
    public LineNumberTableEntry[] getTable() {
        return table;
    }

    /**
     * @param table The table which maps the instruction indices to line numbers.
     */
    public void setTable(LineNumberTableEntry[] table) {
        this.table = table;
        setAttributeLength(calculateLength());
        Arrays.sort(table, Comparator.comparingInt(
            LineNumberTableEntry::getStartPc));
    }

    /**
     * Find the line number which corresponds with the byte code index.
     *
     * @param index The index of the instruction.
     * @return The line number which corresponds with that instruction.
     */
    public int getLineNumber(int index) {
        int low = 0;
        int high = table.length - 1;
        while(low <= high) {
            final int mid = (low + high) >>> 1;

            final int pc = table[mid]
                .getStartPc();

            if(index < pc)
                high = mid - 1;
            else if(index > pc)
                low = mid + 1;
            else
                return table[mid]
                    .getLineNumber();
        }

        return table[(low + high) >>> 1]
            .getLineNumber();
    }

    @Override
    public boolean equals(Object other) {
        return this == other ||
            (other instanceof AttributeLineNumberTable &&
             Arrays.equals(table, ((AttributeLineNumberTable) other).table));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(table);
    }
}
