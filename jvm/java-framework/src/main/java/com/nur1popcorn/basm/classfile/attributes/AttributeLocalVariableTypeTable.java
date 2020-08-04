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
 * The {@link AttributeLocalVariableTypeTable} stores
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.14">
 *     4.7.13. The LocalVariableTypeTable Attribute
 * </a>
 *
 * @see LocalVariableTypeTableEntry
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class AttributeLocalVariableTypeTable extends AttributeInfo {
    private LocalVariableTypeTableEntry entries[];

    /**
     * @param nameIndex The index of the CONSTANT_UTF8 which identifies the type of {@link AttributeInfo}.
     * @param attributeLength The {@link AttributeInfo}'s length in bytes.
     */
    public AttributeLocalVariableTypeTable(int nameIndex, int attributeLength, LocalVariableTypeTableEntry entries[]) {
        super(nameIndex, attributeLength);
        this.entries = entries;
    }

    @Override
    public void write(DataOutputStream os, ConstantPool cp) throws IOException
    {
        super.write(os, cp);
        os.writeShort(entries.length);
        for(LocalVariableTypeTableEntry entry : entries)
            entry.write(os);
    }

    /**
     * Get all the entries in this local variable type table attribute.
     * @return The entries.
     */
    public LocalVariableTypeTableEntry[] getEntries() {
        return entries;
    }

    /**
     * Set the entries for this local variable type table attribute.
     * @param entries The entries.
     */
    public void setEntries(LocalVariableTypeTableEntry[] entries) {
        this.entries = entries;
        setAttributeLength(10 * entries.length);
    }

    @Override
    public void accept(AttributeVisitor v) {
        v.visit(this);
    }
}
