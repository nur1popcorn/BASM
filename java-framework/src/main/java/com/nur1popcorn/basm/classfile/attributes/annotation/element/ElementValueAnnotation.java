package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import com.nur1popcorn.basm.classfile.attributes.annotation.Annotation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValueAnnotation extends ElementValue {
    private final Annotation annotation;

    public ElementValueAnnotation(int tag, DataInputStream in) throws IOException {
        super(tag);
        this.annotation = new Annotation(in);
    }

    @Override
    public void write(DataOutputStream os) throws IOException {
        annotation.write(os);
    }
}
