package com.nur1popcorn.basm.tree.item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class ClassDefIdItem {
    public final int classIdx,
    accessFlags,
    superclassIdx,
    interfacesOff,
    sourceFileIdx,
    annotationsOff,
    classDataOff,
    staticValuesOff;

    public ClassDefIdItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        classIdx = byteBuffer.getInt();
        accessFlags = byteBuffer.getInt();
        superclassIdx = byteBuffer.getInt();
        interfacesOff = byteBuffer.getInt();
        sourceFileIdx = byteBuffer.getInt();
        annotationsOff = byteBuffer.getInt();
        classDataOff = byteBuffer.getInt();
        staticValuesOff = byteBuffer.getInt();
    }
}
