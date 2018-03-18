package com.nur1popcorn.basm.transformers.parser;

public final class Token {

    private String token;
    private TokenType type;

    public Token(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }

    public enum TokenType {
        ACC_FLAG,
        CLASS,
        EXTENDS,
        NAME,
        COLON,
        OPCODE,
        PARAMETER
    }
}
