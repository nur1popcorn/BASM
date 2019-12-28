package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.attributes.AttributeInfo;

public interface IFieldMethodNodeVisitor {
    default void visit(int access, int nameIndex, int descIndex, AttributeInfo attributes[], ConstantPool constantPool) {}
}
