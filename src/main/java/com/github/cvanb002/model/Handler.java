package com.github.cvanb002.model;

public abstract class Handler {
    State state;


    public abstract void call(Message message, Client client);
}
