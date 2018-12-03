package com.nur1popcorn.basm.tree.item.data.annotations_directory_item;

import com.nur1popcorn.basm.utils.Alignment;

import java.nio.ByteBuffer;

public class AnnotationsDirectoryItem {
    private final int classAnnotationsOff,
        fieldsSize,
        annotatedMethodsSize,
        annotatedParametersSize;
    private final FieldAnnotation[] fieldAnnotations;
    private final MethodAnnotation[] methodAnnotations;
    private final ParameterAnnotation[] parameterAnnotations;

    public AnnotationsDirectoryItem(ByteBuffer byteBuffer) {
        Alignment.alignToFourBytes(byteBuffer);
        classAnnotationsOff = byteBuffer.getInt();
        fieldsSize = byteBuffer.getInt();
        annotatedMethodsSize = byteBuffer.getInt();
        annotatedParametersSize = byteBuffer.getInt();
        fieldAnnotations = new FieldAnnotation[fieldsSize];
        for (int i = 0; i < fieldAnnotations.length; i++) {
            fieldAnnotations[i] = new FieldAnnotation(byteBuffer);
        }

        methodAnnotations = new MethodAnnotation[annotatedMethodsSize];
        for (int i = 0; i < methodAnnotations.length; i++) {
            methodAnnotations[i] = new MethodAnnotation(byteBuffer);
        }

        parameterAnnotations = new ParameterAnnotation[annotatedParametersSize];
        for (int i = 0; i < parameterAnnotations.length; i++) {
            parameterAnnotations[i] = new ParameterAnnotation(byteBuffer);
        }
    }
}
