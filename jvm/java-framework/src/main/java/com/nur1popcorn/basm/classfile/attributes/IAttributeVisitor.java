package com.nur1popcorn.basm.classfile.attributes;

public interface IAttributeVisitor {
    default void visit(AttributeConstantValue attribute) { }
    default void visit(AttributeCode attribute) { }
    default void visit(AttributeLineNumberTable attribute) { }
}
