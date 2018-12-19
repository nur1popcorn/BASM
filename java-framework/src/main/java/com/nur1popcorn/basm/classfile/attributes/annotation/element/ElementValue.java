package com.nur1popcorn.basm.classfile.attributes.annotation.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValue {
    private final int tag;

    public ElementValue(int tag) {
        this.tag = tag;
    }

    public static ElementValue read(DataInputStream in) throws IOException {
        final int tag = in.readUnsignedByte();
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
                return new ElementValueEnum(tag, in);
            case 'c':
                return new ElementValueClass(tag, in);
            case '@':
                return new ElementValueAnnotation(tag, in);
            case '[':
                return new ElementValueArray(tag, in);
            default:
                throw new RuntimeException("Unrecognized ASCII char label: " + tag + " (" + Integer.toHexString(Character.getNumericValue(tag)) + ")");
        }
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(tag);
    }
}
