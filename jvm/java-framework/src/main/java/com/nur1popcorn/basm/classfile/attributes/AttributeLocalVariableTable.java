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

/**
 * The {@link AttributeLocalVariableTable} stores
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.13">
 *     4.7.13. The LocalVariableTable Attribute
 * </a>
 *
 * @see LocalVariableTableEntry
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class AttributeLocalVariableTable extends AttributeInfo {
    private LocalVariableTableEntry entries[];

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public AttributeLocalVariableTable(int nameIndex, int attributeLength, LocalVariableTableEntry entries[]) {
        super(nameIndex, attributeLength);
        this.entries = entries;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException {
        super.write(os, cp);
        os.writeShort(entries.length);
        for(LocalVariableTableEntry entry : entries)
            entry.write(os);
    }

    /**
     * Get all the entries in this local variable table attribute.
     * @return The entries.
     */
    public LocalVariableTableEntry[] getEntries() {
        return entries;
    }

    /**
     * Set the entries for this local variable table attribute.
     * @param entries The entries.
     */
    public void setEntries(LocalVariableTableEntry[] entries) {
        this.entries = entries;
    }

    @Override
    public void accept(AttributeVisitor v) {
        v.visit(this);
    }
}
