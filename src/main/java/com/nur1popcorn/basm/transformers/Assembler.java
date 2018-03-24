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

import com.nur1popcorn.basm.classfile.ClassWriter;
import com.nur1popcorn.basm.classfile.tree.ClassFile;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.nur1popcorn.basm.Constants.ACC_CLASS_NAMES;
import static com.nur1popcorn.basm.Constants.OPCODE_MNEMONICS;

public final class Assembler implements ITransformer {

    private static final Map<String, Byte> MNEMONIC_OPCODE_MAP = new HashMap<>(); static {
        for(int i = 0; i < OPCODE_MNEMONICS.length; i++) {
            final String mnemonic = OPCODE_MNEMONICS[i];
            if(mnemonic != null)
                MNEMONIC_OPCODE_MAP.put(mnemonic, (byte) i);
        }
    }

    @Override
    public void transform(InputStream in, OutputStream out) throws IOException {
        final ClassFile.ClassFileBuilder builder = ClassFile.builder();

        for(int i = 0; i < ACC_CLASS_NAMES.length; i++) {
            final String accName = ACC_CLASS_NAMES[i];
            //if(accName.equals())
        }

        builder.build()
               .accept(
            new ClassWriter(
                new DataOutputStream(out)
            ));
    }
}
