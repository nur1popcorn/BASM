package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValue {
    private final byte tag;

    public ElementValue(byte tag) {
        this.tag = tag;
    }

    public static ElementValue read(DataInputStream in) throws IOException {
        final byte tag = in.readByte();
        switch(tag) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
            case 's':
                return new ElementValueConstant(tag, in);
            case 'e':
                return new ElementValueEnumConstant(tag, in);
            case 'c':
                return new ElementValueClass(tag, in);
            case '@':
                return new ElementValueNestedAnnotation(tag, in);
            case '[':
                return new ElementValueArray(tag, in);
            default:
                throw new RuntimeException("Unrecognized ASCII char label: " + tag + " (" + Integer.toHexString(Character.getNumericValue(tag)) + ")");
        }
    }

    public abstract void write(DataOutputStream os) throws IOException;
}
