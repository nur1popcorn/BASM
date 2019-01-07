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

package com.nur1popcorn.basm.classfile.tree;

import com.nur1popcorn.basm.classfile.MalformedClassFileException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedList;

public final class Type {

    // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.newarray.desc-120
    public static final byte T_BOOLEAN = 4;
    public static final byte T_CHAR =    5;
    public static final byte T_FLOAT =   6;
    public static final byte T_DOUBLE =  7;
    public static final byte T_BYTE =    8;
    public static final byte T_SHORT =   9;
    public static final byte T_INT =     10;
    public static final byte T_LONG =    11;

    /* The following types are not explicitly defined by the JVM.
     *
     */
    public static final byte T_OBJECT =  12;
    public static final byte T_ARRAY =   13;
    public static final byte T_METHOD =  14;
    public static final byte T_VOID =    15;

    /*
     *
     */
    public static final String T_MNEMONICS[] = {
        null,        null,      null,      null,
        "t_boolean", "t_char",  "t_float", "t_double",
        "t_byte",    "t_short", "t_int",   "t_long",
        "t_object",  "t_array", "t_method", "t_void",
        "t_field"
    };

    /*
     *
     */
    public static final Type BOOLEAN = new Type(T_BOOLEAN, "Z");
    public static final Type CHAR = new Type(T_CHAR, "C");
    public static final Type FLOAT = new Type(T_FLOAT, "F");
    public static final Type DOUBLE = new Type(T_DOUBLE, "D");
    public static final Type BYTE = new Type(T_BYTE, "B");
    public static final Type SHORT = new Type(T_SHORT, "S");
    public static final Type INT = new Type(T_INT, "I");
    public static final Type LONG = new Type(T_LONG, "J");
    public static final Type VOID = new Type(T_VOID, "V");

    /*
     *
     */
    private final byte type;

    /*
     *
     */
    private final String descriptor;

    /*
     *
     */
    private final int offset;

    /**
     * @param type
     * @param descriptor
     * @param offset
     */
    private Type(byte type, String descriptor, int offset) {
        this.type = type;
        this.descriptor = descriptor;
        this.offset = offset;
    }

    /**
     * @param type
     * @param descriptor
     */
    private Type(byte type, String descriptor) {
        this(type, descriptor, 0);
    }

    /**
     * @param clazz
     *
     * @return
     */
    public static String getInternalName(Class clazz) {
        return clazz.getName()
                    .replace('.',  '/');
    }

    private static final Type TYPES[] = {
        null, null, null, null,
        BOOLEAN, CHAR, FLOAT, DOUBLE,
        BYTE, SHORT, INT, LONG,
        null, null, null, null,
        VOID
    };

    /**
     * @param type
     *
     * @return
     */
    public static Type getType(byte type) {
        switch(type) {
            case T_BOOLEAN:
            case T_CHAR:
            case T_FLOAT:
            case T_DOUBLE:
            case T_BYTE:
            case T_SHORT:
            case T_INT:
            case T_LONG:
                return TYPES[type];
            default:
                // TODO: desc.
                throw new MalformedClassFileException();
        }
    }

    /**
     * @param descriptor
     *
     * @return
     *
     * @throws MalformedClassFileException
     */
    private static Type getType(String descriptor, int offset) {
        switch(descriptor.charAt(offset)) {
            // https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3.2-200
            case 'B': return BYTE;
            case 'C': return CHAR;
            case 'D': return DOUBLE;
            case 'F': return FLOAT;
            case 'I': return INT;
            case 'J': return LONG;
            case 'S': return SHORT;
            case 'Z': return BOOLEAN;
            case 'V': return VOID;
            case 'L':
                return new Type(T_OBJECT, descriptor, offset);
            case '[':
                return new Type(T_ARRAY, descriptor, offset);
            case '(':
                return new Type(T_METHOD, descriptor, offset);
            default:
                throw new MalformedClassFileException(
                    "Invalid type descriptor: descriptor=" + descriptor);
        }
    }

    /**
     * @param descriptor
     *
     * @return
     *
     * @see #getType(String, int)
     */
    public static Type getType(String descriptor) {
        return getType(descriptor, 0);
    }

    /**
     * @param clazz
     *
     * @return
     */
    public static Type getType(Class clazz) {
        if(clazz.isPrimitive()) {
            if(clazz == Boolean.TYPE)
                return BOOLEAN;
            else if(clazz == Character.TYPE)
                return CHAR;
            else if(clazz == Float.TYPE)
                return FLOAT;
            else if(clazz == Double.TYPE)
                return DOUBLE;
            else if(clazz == Byte.TYPE)
                return BYTE;
            else if(clazz == Short.TYPE)
                return SHORT;
            else if(clazz == Integer.TYPE)
                return INT;
            else if(clazz == Long.TYPE)
                return LONG;
            else // if(clazz == Void.TYPE)
                return VOID;
        } else if(clazz.isArray())
            return getType(getInternalName(clazz));
        else
            return getType("L" + getInternalName(clazz) + ";");
    }

    /**
     * @param method
     *
     * @return
     */
    public static Type getType(Method method) {
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        for(Class clazz : method.getParameterTypes())
            sb.append(
                Type.getType(clazz)
                    .getDescriptor());
        sb.append(')');
        sb.append(
            Type.getType(method.getReturnType())
                .getDescriptor());
        return Type.getType(sb.toString());
    }

    /**
     * @param constructor
     *
     * @return
     */
    public static Type getType(Constructor constructor) {
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        for(Class clazz : constructor.getParameterTypes())
            sb.append(
                Type.getType(clazz)
                    .getDescriptor());
        sb.append(")V");
        return Type.getType(sb.toString());
    }

    /**
     *
     *
     * @return
     */
    public LinkedList<Type> getParameters() {
        final LinkedList<Type> types = new LinkedList<>();
        for(int i = 1; i < descriptor.length() && descriptor.charAt(i) != ')'; i++) {
            types.add(Type.getType(descriptor, i));
            switch(descriptor.charAt(i)) {
                case '[':
                    while(descriptor.charAt(i) == '[')
                        i++;
                    if(descriptor.charAt(i) != 'L')
                        break;
                    // fallthrough.
                case 'L':
                    while(descriptor.charAt(i) != ';')
                        i++;
            }
        }
        return types;
    }

    /**
     * @return
     */
    public int getDimensions() {
        int i = 1;
        while(descriptor.charAt(i + offset) == '[')
            i++;
        return i;
    }

    /**
     * @return
     */
    public int getStackModifier() {
        switch(type) {
            case T_LONG:
            case T_DOUBLE:
                return 2;
            default:
                return 1;
            case T_VOID:
                return 0;
        }
    }

    /**
     * @return
     */
    public Type getReturnType() {
        return getType(descriptor, descriptor.indexOf(')') + 1);
    }

    /**
     * @return
     */
    public byte getType() {
        return type;
    }

    /**
     * @return
     */
    public String getDescriptor() {
        int end = offset;
        switch(descriptor.charAt(end++)) {
            case '(':
                while(descriptor.charAt(end++) != ')');
                // fallthrough.
            case '[':
                while(descriptor.charAt(end) == '[')
                    end++;
                if(descriptor.charAt(end++) != 'L')
                    break;
                // fallthrough.
            case 'L':
                while(descriptor.charAt(end++) != ';');
        }
        return descriptor.substring(offset, end);
    }

    @Override
    public String toString() {
        return T_MNEMONICS[type] + "[" +
                   descriptor +
               "]";
    }
}
