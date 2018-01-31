package com.nur1popcorn.basm.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Constants.*;

public class ClassReader {
    public static final int READ_HEAD = 0x1;
    public static final int READ_BODY = 0x2;
    public static final int READ_FIELDS = 0x4;
    public static final int READ_METHODS = 0x8;

    private static final int CONSTANT_INFO_SKIP_TABLE[] = {
        0,
        0, // CONSTANT_Utf8
        0,
        4, // CONSTANT_Integer
        4, // CONSTANT_Float
        8, // CONSTANT_Long
        8, // CONSTANT_Double
        2, // CONSTANT_Class
        2, // CONSTANT_String
        4, // CONSTANT_Fieldref
        4, // CONSTANT_Methodref
        4, // CONSTANT_InterfaceMethodref
        4, // CONSTANT_NameAndType
        0,
        0,
        3, // CONSTANT_MethodHandle
        2, // CONSTANT_MethodType
        0,
        4, // CONSTANT_Invokedynamic
    };
    
    private DataInputStream in;

    private int minorVersion,
                majorVersion;

    private ConstantPool constantPool;

    private int access;

    private int thisClass,
            superClass;

    private int interfaces[];
    private FieldMethodInfo fields[];
    private FieldMethodInfo methods[];

    public ClassReader(DataInputStream in) throws IOException {
        final int magic = in.readInt();
        if(magic != MAGIC)
            throw new IOException("The class provided has an invalid file header: " + magic);
    }

    private void readHead() throws IOException {
        minorVersion = in.readUnsignedShort();
        majorVersion = in.readUnsignedShort();

        constantPool = new ConstantPool(in);
    }

    private void readBody() throws IOException {
        access = in.readUnsignedShort();
        thisClass = in.readUnsignedShort();
        superClass = in.readUnsignedShort();

        interfaces = new int[in.readUnsignedShort()];
        for(int i = 0; i < interfaces.length; i++)
            interfaces[i] = in.readUnsignedShort();
    }

    private void readFields() throws IOException {
        fields = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < fields.length; i++)
            fields[i] = new FieldMethodInfo(in, constantPool);
    }

    private void readMethods() throws IOException {
        methods = new FieldMethodInfo[in.readUnsignedShort()];
        for(int i = 0; i < methods.length; i++)
            methods[i] = new FieldMethodInfo(in, constantPool);
    }

    public void accept(IClassReaderVisitor visitor, int read) throws IOException {
        if((read & READ_HEAD) != 0) {
            readHead();
            visitor.visit(constantPool);
        } else {
            // skip minor/major version
            in.skipBytes(4);
            final int cpSize = in.readUnsignedShort();
            for(int i = 1 /* the cp's size is 1 less than given */; i < cpSize; i++)
                switch(in.readByte()) {
                    case CONSTANT_UTF8:
                        in.skipBytes(in.readUnsignedShort());
                        break;
                    case CONSTANT_LONG:
                    case CONSTANT_DOUBLE:
                        i++ /* padding */;
                        break;
                    default:
                        in.skipBytes(CONSTANT_INFO_SKIP_TABLE[i]);
                }
        }
    }
}
