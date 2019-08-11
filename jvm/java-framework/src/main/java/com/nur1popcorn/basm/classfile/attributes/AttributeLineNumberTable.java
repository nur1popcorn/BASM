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

/**
 * The {@link AttributeLineNumberTable} is an optional attribute which may be used by debuggers,
 * decompilers, etc. to figure out which instruction corresponds with which linenumber.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.12">
 *     4.7.12. The LineNumberTable Attribute
 * </a>
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
    }

    @Override
    public void accept(IAttributeVisitor v) {
        v.visit(this);
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
    }
}
