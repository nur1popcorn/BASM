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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@link LineNumberTableEntry} class is responsible for mapping the position of certain bytecode
 * instructions to the line numbers of the original source file.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class LineNumberTableEntry {
    private final int startPc /* u2 */, lineNumber /* u2 */;

    public LineNumberTableEntry(int startPc, int lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }

    /**
     * @return The bytecode position.
     */
    public int getStartPc() {
        return startPc;
    }

    /**
     * The line number which corresponds with the bytecode position.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Writes the {@link LineNumberTableEntry} entry to the given {@link DataOutputStream}.
     *
     * @param os The {@link DataOutputStream} to which the line number table entry should be written to.
     * @throws IOException if an error occurs during the process of writing to the {@link DataOutputStream}.
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(startPc);
        os.writeShort(lineNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if(other instanceof LineNumberTableEntry) {
            final LineNumberTableEntry that = (LineNumberTableEntry) other;
            return startPc == that.startPc &&
                   lineNumber == that.lineNumber;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPc, lineNumber);
    }
}
