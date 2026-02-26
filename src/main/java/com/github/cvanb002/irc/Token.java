package com.github.cvanb002.irc;

public class Token {
    String token;
    IRC.Type type;

    public Token(String token, IRC.Type type) {
        this.token = token;
        this.type = type;
    }

    public Token(String token) {
        this(token, IRC.Type.NULL);
    }

    public IRC.Type getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}