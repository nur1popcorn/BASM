package com.nur1popcorn.basm;

public class AccessFlags {
    // Public access.
    private static final int ACC_PUBLIC = 0x1;
    // Private access.
    private static final int ACC_PRIVATE = 0x2;
    // Protected access.
    private static final int ACC_PROTECTED = 0x4;
    // Static access.
    private static final int ACC_STATIC = 0x8;
    // Final element (non modifiable).
    private static final int ACC_FINAL = 0x10;
    // Thread - synchronized element.
    private static final int ACC_SYNCHRONIZED = 0x20;
    // Volatile element.
    private static final int ACC_VOLATILE = 0x40;
    // Bridge.
    private static final int ACC_BRIDGE = 0x40;
    // Transient.
    private static final int ACC_TRANSIENT = 0x80;
    // Varargs.
    private static final int ACC_VARARGS = 0x80;
    // Native element.
    private static final int ACC_NATIVE = 0x100;
    // Interface.
    private static final int ACC_INTERFACE = 0x200;
    // Abstract element.
    private static final int ACC_ABSTRACT = 0x400;
    // Strict.
    private static final int ACC_STRICT = 0x800;
    // Syntetic.
    private static final int ACC_SYNTHETIC = 0x1000;
    // Annotation.
    private static final int ACC_ANNOTATION = 0x2000;
    // Enum.
    private static final int ACC_ENUM = 0x4000;
    // Constructor.
    private static final int ACC_CONSTRUCTOR = 0x10000;
    // Declared as synchronized element.
    private static final int ACC_DECLARED_SYNCHRONIZED = 0x20000;
}
