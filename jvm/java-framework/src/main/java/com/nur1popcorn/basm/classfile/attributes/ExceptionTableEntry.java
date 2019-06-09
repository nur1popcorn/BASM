package com.nur1popcorn.basm.classfile.attributes;

public final class ExceptionTableEntry {
    private final int startPc /* u2 */,
                      endPc /* u2 */,
                      handlerPc /* u2 */,
                      catchType /* u2 */;

    public ExceptionTableEntry(int startPc, int endPc, int handlerPc, int catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }

    public int getStartPc() {
        return startPc;
    }

    public int getEndPc() {
        return endPc;
    }

    public int getHandlerPc() {
        return handlerPc;
    }

    public int getCatchType() {
        return catchType;
    }
}
