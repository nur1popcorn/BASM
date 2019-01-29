package com.nur1popcorn.basm.classfile.attributes.annotation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParameterAnnotation {
    private final Annotation[] annotations;

    public ParameterAnnotation(DataInputStream in) throws IOException  {
        annotations = new Annotation[in.readUnsignedShort()];
        for (int i = 0; i < annotations.length; i++)
            annotations[i] = new Annotation(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(annotations.length);
        for (Annotation annotation : annotations)
            annotation.write(os);
    }
}
