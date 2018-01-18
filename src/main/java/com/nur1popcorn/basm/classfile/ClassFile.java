package com.nur1popcorn.basm.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.utils.Constants.MAGIC;

public class ClassFile {

    //TODO: clean up, rewrite ?

    private int minorVersion,
                majorVersion;

    private ConstantPool constantPool;

    public int access;

    private int thisClass,
            superClass;

    private int interfaces[];
    private FieldMethodInfo fields[];
    private FieldMethodInfo methods[];

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public FieldMethodInfo[] getMethods() {
        return methods;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccess() {
        return access;
    }

    public int getThisClass() {
        return thisClass;
    }

    public int getSuperClass() {
        return superClass;
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public FieldMethodInfo[] getFields() {
        return fields;
    }

    public static ClassReader classReader() {
        return new ClassReader();
    }

    public static class ClassReader {

        // prevent construction.
        private ClassReader()
        {}

        public ClassFile read(DataInputStream in) throws IOException {
            // read and check magic value.
            final int magic = in.readInt();
            if(magic != MAGIC)
                throw new IOException("The class provided has an invalid file header: " + magic);

            final ClassFile classFile = new ClassFile();

            // read compiler version used.
            classFile.minorVersion = in.readUnsignedShort();
            classFile.majorVersion = in.readUnsignedShort();

            // read cp.
            classFile.constantPool = new ConstantPool(in);

            classFile.access = in.readUnsignedShort();
            classFile.thisClass = in.readUnsignedShort();
            classFile.superClass = in.readUnsignedShort();

            classFile.interfaces = new int[in.readUnsignedShort()];
            for(int i = 0; i < classFile.interfaces.length; i++)
                classFile.interfaces[i] = in.readUnsignedShort();

            classFile.fields = new FieldMethodInfo[in.readUnsignedShort()];
            for(int i = 0; i < classFile.fields.length; i++)
                classFile.fields[i] = new FieldMethodInfo(in, classFile.constantPool);

            classFile.methods = new FieldMethodInfo[in.readUnsignedShort()];
            for(int i = 0; i < classFile.methods.length; i++)
                classFile.methods[i] = new FieldMethodInfo(in, classFile.constantPool);

            return classFile;
        }
    }
}
