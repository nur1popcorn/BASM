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

package com.nur1popcorn.basm.classfile.attributes.clazz;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AttributeModule extends AttributeInfo {
    public static final int ACC_TRANSITIVE      =   0x20;
    public static final int ACC_STATIC_PHASE    =   0x40;
    public static final int ACC_OPEN            =   0x20;
    public static final int ACC_SYNTHETIC       = 0x1000;
    public static final int ACC_MANDATED        = 0x8000;

    private final int moduleName;
    private final int moduleFlags;
    private final int moduleVersionIndex;
    private final RequiresEntry[] requires;
    private final ExportsEntry[] exports;
    private final OpensEntry[] opens;
    private final int[] usesIndex;
    private final ProvidesEntry[] provides;

    public AttributeModule(int nameIndex, DataInputStream in) throws IOException {
        super(nameIndex, in);
        moduleName = in.readUnsignedShort();
        moduleFlags = in.readUnsignedShort();

        moduleVersionIndex = in.readUnsignedShort();

        requires = new RequiresEntry[in.readUnsignedShort()];
        for(int i = 0; i < requires.length; i++)
            requires[i] = new RequiresEntry(in);

        exports = new ExportsEntry[in.readUnsignedShort()];
        for(int i = 0; i < exports.length; i++)
            exports[i] = new ExportsEntry(in);

        opens = new OpensEntry[in.readUnsignedShort()];
        for(int i = 0; i < opens.length; i++)
            opens[i] = new OpensEntry(in);

        usesIndex = new int[in.readUnsignedShort()];
        for(int i = 0; i < usesIndex.length; i++)
            usesIndex[i] = in.readUnsignedShort();

        provides = new ProvidesEntry[in.readUnsignedShort()];
        for(int i = 0; i < provides.length; i++)
            provides[i] = new ProvidesEntry(in);
    }

    @Override
    public void write(DataOutputStream os, ConstantPool constantPool) throws IOException {
        super.write(os, constantPool);
        os.writeShort(moduleName);
        os.writeShort(moduleFlags);

        os.writeShort(moduleVersionIndex);

        os.writeShort(requires.length);
        for(RequiresEntry require : requires)
            require.write(os);

        os.writeShort(exports.length);
        for(ExportsEntry export : exports)
            export.write(os);

        os.writeShort(opens.length);
        for(OpensEntry open : opens)
            open.write(os);

        os.writeShort(usesIndex.length);
        for(int i : usesIndex)
            os.writeShort(i);

        os.writeShort(provides.length);
        for(ProvidesEntry provide : provides)
            provide.write(os);
    }

    public static class RequiresEntry {
        private final int requiresIndex,
                          requiresFlags,
                          requiresVersionIndex;

        public RequiresEntry(DataInputStream in) throws IOException {
            requiresIndex = in.readUnsignedShort();
            requiresFlags = in.readUnsignedShort();
            requiresVersionIndex = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(requiresIndex);
            os.writeShort(requiresFlags);
            os.writeShort(requiresVersionIndex);
        }
    }

    public static class ExportsEntry {
        private final int exportsIndex,
                          exportsFlags;
        private final int[] exportsToIndex;

        public ExportsEntry(DataInputStream in) throws IOException {
            exportsIndex = in.readUnsignedShort();
            exportsFlags = in.readUnsignedShort();
            exportsToIndex = new int[in.readUnsignedShort()];
            for(int i = 0; i < exportsToIndex.length; i++)
                exportsToIndex[i] = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(exportsIndex);
            os.writeShort(exportsFlags);
            os.writeShort(exportsToIndex.length);
            for(int i : exportsToIndex)
                os.writeShort(i);
        }
    }

    public static class OpensEntry {
        private final int opensIndex,
                          opensFlags;
        private final int[] opensToIndex;

        public OpensEntry(DataInputStream in) throws IOException {
            opensIndex = in.readUnsignedShort();
            opensFlags = in.readUnsignedShort();
            opensToIndex = new int[in.readUnsignedShort()];
            for(int i = 0; i < opensToIndex.length; i++)
                opensToIndex[i] = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(opensIndex);
            os.writeShort(opensFlags);
            os.writeShort(opensToIndex.length);
            for(int i : opensToIndex)
                os.writeShort(i);
        }
    }

    public static class ProvidesEntry {
        private final int providesIndex;
        private final int[] withIndex;

        public ProvidesEntry(DataInputStream in) throws IOException {
            providesIndex = in.readUnsignedShort();
            withIndex = new int[in.readUnsignedShort()];
            for(int i = 0; i < withIndex.length; i++)
                withIndex[i] = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(providesIndex);
            os.writeShort(withIndex.length);
            for(int i : withIndex)
                os.writeShort(i);
        }
    }
}
