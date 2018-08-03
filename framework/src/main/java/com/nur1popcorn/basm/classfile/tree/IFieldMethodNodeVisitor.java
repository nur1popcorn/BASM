package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.FieldMethodInfo;

public interface IFieldMethodNodeVisitor {
    default void visitFieldMethodNode(FieldMethodNode fieldMethodNode) {}
    default void visitFieldMethodInfo(FieldMethodInfo fieldMethodInfo) {}
}
