package com.nur1popcorn.basm.classfile.constants;

/**
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public abstract class ConstantVisitorDecorator implements ConstantVisitor {
    private ConstantVisitor parent;

    public ConstantVisitorDecorator(ConstantVisitor parent) {
        this.parent = parent;
    }

    @Override
    public void visitInt(ConstantInteger info) {
        parent.visitInt(info);
    }

    @Override
    public void visitFloat(ConstantInteger info) {
        parent.visitFloat(info);
    }

    @Override
    public void visitInvokeDynamic(ConstantInvokeDynamic info) {
        parent.visitInvokeDynamic(info);
    }

    @Override
    public void visitDynamicConstant(ConstantInvokeDynamic info) {
        parent.visitDynamicConstant(info);
    }

    @Override
    public void visitLong(ConstantLong info) {
        parent.visitLong(info);
    }

    @Override
    public void visitDouble(ConstantLong info) {
        parent.visitDouble(info);
    }

    @Override
    public void visitMethodHandle(ConstantMethodHandle info) {
        parent.visitMethodHandle(info);
    }

    @Override
    public void visitMethodRef(ConstantMethodRef info) {
        parent.visitMethodRef(info);
    }

    @Override
    public void visitClass(ConstantName info) {
        parent.visitClass(info);
    }

    @Override
    public void visitString(ConstantName info) {
        parent.visitString(info);
    }

    @Override
    public void visitMethodType(ConstantName info) {
        parent.visitMethodType(info);
    }

    @Override
    public void visitNameAndType(ConstantNameAndType info) {
        parent.visitNameAndType(info);
    }

    @Override
    public void visitUTF8(ConstantUTF8 info) {
        parent.visitUTF8(info);
    }

    @Override
    public void visitModule(ConstantName info) {
        parent.visitModule(info);
    }

    @Override
    public void visitPackage(ConstantName info) {
        parent.visitPackage(info);
    }

    @Override
    public void visitCPPointer(ConstantPoolPointer pointer) {
        parent.visitCPPointer(pointer);
    }
}
