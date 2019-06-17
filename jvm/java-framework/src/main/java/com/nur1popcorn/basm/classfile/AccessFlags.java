/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm.classfile;

public abstract class AccessFlags {
    /**
     * All valid access flags.
     * <ul>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1">
     *             Table 4.1-A Class access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5-200-A.1">
     *             Table 4.5-A. Field access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1">
     *             Table 4.6-A Method access flags
     *         </a>
     *     </li>
     *     <li>
     *         <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.6-300-D.1-D.1">
     *             Table 4.7.6-A Nested class access flags
     *         </a>
     *     </li>
     * </ul>
     */
    public static final int ACC_PUBLIC =                     0x1;
    public static final int ACC_PRIVATE =                    0x2;
    public static final int ACC_PROTECTED =                  0x4;

    public static final int ACC_STATIC =                     0x8;
    public static final int ACC_FINAL =                      0x10;

    public static final int ACC_SYNCHRONIZED =               0x20;
    public static final int ACC_VOLATILE =                   0x40;
    public static final int ACC_TRANSIENT =                  0x80;
    public static final int ACC_NATIVE =                     0x100;

    public static final int ACC_INTERFACE =                  0x200;
    public static final int ACC_ABSTRACT =                   0x400;

    public static final int ACC_STRICT =                     0x800;
    public static final int ACC_SYNTHETIC =                  0x1000;

    public static final int ACC_ANNOTATION =                 0x2000;
    public static final int ACC_ENUM =                       0x4000;

    // Changes how super methods are treated by the 'invokespecial' instruction.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
    public static final int ACC_SUPER =                      0x20;

    // Marks a bridge method generated by the compiler.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
    public static final int ACC_BRIDGE =                     0x40;

    // Marks a method with variable number of arguments.
    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
    public static final int ACC_VARARGS =                    0x80;

    // https://docs.oracle.com/javase/specs/jls/se8/html/jls-13.html#jls-13.1
    public static final int ACC_MANDATED =                   0x8000;

    /**
     * An array containing a string representation of the available access flags used by classes.
     */
    public static final String ACC_CLASS_NAMES[] = {
        "public", null, null, null,
        "final", "super", null, null,
        null, "interface", "abstract", null,
        "synthetic", "annotation", "enum"
    };

    /**
     * An array containing a string representation of the available access flags used by fields.
     */
    public static final String ACC_FIELD_NAMES[] = {
        "public", "private", "protected", "static",
        "final", null, "volatile", "transient",
        null, null, null, null,
        "synthetic", null, "enum"
    };

    /**
     * An array containing a string representation of the available access flags used by methods.
     */
    public static final String ACC_METHOD_NAMES[] = {
        "public", "private", "protected", "static",
        "final", "synchronized", "bridge", "varargs",
        "native", null, "abstract", "strict", "synthetic"
    };

    /**
     * An array containing a string representation of the available access flags used by nested classes.
     */
    public static final String ACC_NESTED_CLASS_NAMES[] = {
        "public", "private", "protected", "static",
        "final", null, null, null,
        null, "interface", "abstract", null,
        "synthetic", "annotation", "enum"
    };

    protected int access;

    /**
     * @param access
     */
    public AccessFlags(int access) {
        this.access = access;
    }

    /**
     * Empty constructor.
     */
    public AccessFlags()
    {}

    /**
     * @return
     */
    public int getAccessFlags() {
        return access;
    }

    /**
     * @param access
     */
    public void setAccessFlags(int access) {
        this.access = access;
    }

    public String toMethodAcc() {
        // TODO: impl
        return null;
    }

    /**
     *
     */
    public void setPublic() {
        access &= ~(ACC_PRIVATE | ACC_PROTECTED);
        access |= ACC_PUBLIC;
    }

    /**
     * @return
     */
    public boolean isPublic() {
        return (access & ACC_PUBLIC) != 0;
    }

    /**
     *
     */
    public void setPrivate() {
        access &= ~(ACC_PUBLIC | ACC_PROTECTED);
        access |= ACC_PRIVATE;
    }

    /**
     * @return
     */
    public boolean isPrivate() {
        return (access & ACC_PRIVATE) != 0;
    }

    /**
     *
     */
    public void setProtected() {
        access &= ~(ACC_PUBLIC | ACC_PRIVATE);
        access |= ACC_PRIVATE;
    }

    /**
     * @return
     */
    public boolean isProtected() {
        return (access & ACC_PROTECTED) != 0;
    }

    /**
     * @param flag
     */
    public void setStatic(boolean flag) {
        if(flag)
            access |= ACC_STATIC;
        else
            access &= ~ACC_STATIC;
    }

    /**
     * @return
     */
    public boolean isStatic() {
        return (access & ACC_STATIC) != 0;
    }

    /**
     * @param flag
     */
    public void setFinal(boolean flag) {
        if(flag)
            access |= ACC_FINAL;
        else
            access &= ~ACC_FINAL;
    }

    /**
     * @return
     */
    public boolean isFinal() {
        return (access & ACC_FINAL) != 0;
    }

    /**
     * @param flag
     */
    public void setSynchronized(boolean flag) {
        if(flag)
            access |= ACC_SYNCHRONIZED;
        else
            access &= ~ACC_SYNCHRONIZED;
    }

    /**
     * @return
     */
    public boolean isSynchronized() {
        return (access & ACC_SYNCHRONIZED) != 0;
    }

    /**
     * @param flag
     */
    public void setSuper(boolean flag) {
        if(flag)
            access |= ACC_SUPER;
        else
            access &= ~ACC_SUPER;
    }

    /**
     * @return
     */
    public boolean isSuper() {
        return (access & ACC_SUPER) != 0;
    }

    /**
     * @param flag
     */
    public void setVolatile(boolean flag) {
        if(flag)
            access |= ACC_VOLATILE;
        else
            access &= ~ACC_VOLATILE;
    }

    /**
     * @return
     */
    public boolean isVolatile() {
        return (access & ACC_VOLATILE) != 0;
    }

    /**
     * @param flag
     */
    public void setBridge(boolean flag) {
        if(flag)
            access |= ACC_BRIDGE;
        else
            access &= ~ACC_BRIDGE;
    }

    /**
     * @return
     */
    public boolean isBridge() {
        return (access & ACC_BRIDGE) != 0;
    }

    /**
     * @param flag
     */
    public void setTransient(boolean flag) {
        if(flag)
            access |= ACC_TRANSIENT;
        else
            access &= ~ACC_TRANSIENT;
    }

    /**
     * @return
     */
    public boolean isTransient() {
        return (access & ACC_TRANSIENT) != 0;
    }

    /**
     * @param flag
     */
    public void setVarArgs(boolean flag) {
        if(flag)
            access |= ACC_VARARGS;
        else
            access &= ~ACC_VARARGS;
    }

    /**
     * @return
     */
    public boolean isVarArgs() {
        return (access & ACC_VARARGS) != 0;
    }

    /**
     * @param flag
     */
    public void setNative(boolean flag) {
        if(flag)
            access |= ACC_NATIVE;
        else
            access &= ~ACC_NATIVE;
    }

    /**
     * @return
     */
    public boolean isNative() {
        return (access & ACC_NATIVE) != 0;
    }

    /**
     * @param flag
     */
    public void setInterface(boolean flag) {
        if(flag)
            access |= ACC_INTERFACE;
        else
            access &= ~ACC_INTERFACE;
    }

    /**
     * @return
     */
    public boolean isInterface() {
        return (access & ACC_INTERFACE) != 0;
    }

    /**
     * @param flag
     */
    public void setAbstract(boolean flag) {
        if(flag)
            access |= ACC_ABSTRACT;
        else
            access &= ~ACC_ABSTRACT;
    }

    /**
     * @return
     */
    public boolean isAbstract() {
        return (access & ACC_ABSTRACT) != 0;
    }

    /**
     * @param flag
     */
    public void setStrict(boolean flag) {
        if(flag)
            access |= ACC_STRICT;
        else
            access &= ~ACC_STRICT;
    }

    /**
     * @return
     */
    public boolean isStrict() {
        return (access & ACC_STRICT) != 0;
    }

    /**
     * @param flag
     */
    public void setSynthetic(boolean flag) {
        if(flag)
            access |= ACC_SYNTHETIC;
        else
            access &= ~ACC_SYNTHETIC;
    }

    /**
     * @return
     */
    public boolean isSynthetic() {
        return (access & ACC_SYNTHETIC) != 0;
    }

    /**
     * @param flag
     */
    public void setAnnotation(boolean flag) {
        if(flag)
            access |= ACC_ANNOTATION;
        else
            access &= ~ACC_ANNOTATION;
    }

    /**
     * @return
     */
    public boolean isAnnotation() {
        return (access & ACC_ANNOTATION) != 0;
    }

    /**
     * @param flag
     */
    public void setEnum(boolean flag) {
        if(flag)
            access |= ACC_ENUM;
        else
            access &= ~ACC_ENUM;
    }

    /**
     * @return
     */
    public boolean isEnum() {
        return (access & ACC_ENUM) != 0;
    }

    /**
     * @param flag
     */
    public void setMandated(boolean flag) {
        if(flag)
            access |= ACC_MANDATED;
        else
            access &= ~ACC_MANDATED;
    }

    /**
     * @return
     */
    public boolean isMandated() {
        return (access & ACC_MANDATED) != 0;
    }
}
