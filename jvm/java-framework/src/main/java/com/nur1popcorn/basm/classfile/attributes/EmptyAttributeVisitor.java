package com.nur1popcorn.basm.classfile.attributes;

public interface EmptyAttributeVisitor extends AttributeVisitor {
    @Override
    default void visit(AttributeUnknown attribute) {}

    @Override
    default void visit(AttributeConstantValue attribute) {}

    @Override
    default void visit(AttributeCode attribute) {}

    @Override
    default void visit(AttributeSourceFile attribute) {}

    @Override
    default void visit(AttributeLineNumberTable attribute) {}

    @Override
    default void visit(AttributeDeprecated attribute) {}

    @Override
    default void visit(AttributeBootstrapMethods attribute) {}

    @Override
    default void visit(AttributeStackMapTable attribute) {}

    @Override
    default void visit(AttributeSynthetic attribute) {}

    @Override
    default void visit(AttributeMethodParameters attribute) {}

    @Override
    default void visit(AttributeLocalVariableTable attribute) {}

    @Override
    default void visit(AttributeLocalVariableTypeTable attribute) {}
}
