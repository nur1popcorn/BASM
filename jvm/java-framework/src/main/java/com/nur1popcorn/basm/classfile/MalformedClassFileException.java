package com.nur1popcorn.basm.classfile;

public class MalformedClassFileException extends RuntimeException {
    public MalformedClassFileException() {}
    public MalformedClassFileException(String message) {
        super(message);
    }
}
