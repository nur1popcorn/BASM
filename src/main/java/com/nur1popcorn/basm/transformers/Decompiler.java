package com.nur1popcorn.basm.transformers;

import com.nur1popcorn.basm.classfile.tree.ClassFile;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantName;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static com.nur1popcorn.basm.utils.Constants.*;

public final class Decompiler implements ITransformer {
    @Override
    public void transform(InputStream in, OutputStream out) throws IOException {
        final PrintWriter writer = new PrintWriter(out);
        final ClassFile classFile = new ClassFile(new DataInputStream(in));

        final ConstantPool constantPool = classFile.getConstantPool();
        final ConstantName thisClass = classFile.getThisClass();

        final String className[] = thisClass.indexName(constantPool).bytes.split("/");
        if(className.length > 1) {
            writer.print("package ");
            for(int i = 0; i < className.length - 1; i++) {
                writer.print(className[i]);
                if(i > className.length - 2)
                    writer.print(".");
            }
            writer.println(";");
        }
        writer.println();

        {
            final Set<String> paths = new HashSet<>();
            for(int i = 1 /* the cp's size is 1 less than given */; i < constantPool.getSize(); i++) {
                ConstantInfo constantInfo = constantPool.getEntry(i);
                if(constantInfo.getTag() == CONSTANT_CLASS && constantInfo != thisClass)
                    // TODO: filter java/lang
                    paths.add(((ConstantName)constantInfo).indexName(constantPool).bytes.replaceAll("[/$]", "."));
            }

            paths.forEach(path -> {
                writer.print("import ");
                writer.print(path);
                writer.println(";");
            });
        }

        writer.println();

        {
            final int access = classFile.getAccess();
            if((access & ACC_PUBLIC) != 0)
                writer.print("public ");

            if((access & ACC_INTERFACE) != 0)
                writer.print("interface ");
            else if((access & ACC_ABSTRACT) != 0)
                writer.print("abstract class ");
            else
                writer.print("class ");
        }

        writer.print(className[className.length - 1]);
        writer.println(" {");

        for(FieldMethodInfo field : classFile.getFields()) {
            final int access = classFile.getAccess();
            writer.print("\t");
            if((access & ACC_PUBLIC) != 0)
                writer.print("public ");
            else if((access & ACC_PROTECTED) != 0)
                writer.print("protected ");
            else if((access & ACC_PRIVATE) != 0)
                writer.print("private ");
            if((access & ACC_STATIC) != 0)
                writer.print("static ");
            if((access & ACC_FINAL) != 0)
                writer.print("final ");
            if((access & ACC_VOLATILE) != 0)
                writer.print("volatile ");
            if((access & ACC_TRANSIENT) != 0)
                writer.print("transient ");
            writer.print(field.indexDesc(constantPool).bytes);
            writer.print(" ");
            writer.print(field.indexName(constantPool).bytes);
            writer.println(";");
        }
        writer.println();

        for(FieldMethodInfo method : classFile.getMethods()) {
            final int access = classFile.getAccess();

        }
        writer.println("}");

        writer.flush();
    }
}
