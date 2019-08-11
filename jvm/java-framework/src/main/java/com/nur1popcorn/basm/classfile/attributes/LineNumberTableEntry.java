package com.nur1popcorn.basm.classfile.attributes;

public final class LineNumberTableEntry {
    private final int startPc, lineNumber;

    public LineNumberTableEntry(int startPc, int lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }

    public int getStartPc() {
        return startPc;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
