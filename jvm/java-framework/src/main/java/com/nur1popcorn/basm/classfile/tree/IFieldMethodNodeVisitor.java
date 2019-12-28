package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.FieldMethodInfo;

public interface IFieldMethodNodeVisitor {
    default void visit(int access, int nameIndex, int descIndex, ConstantPool constantPool) {}
}
