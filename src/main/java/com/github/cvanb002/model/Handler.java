package com.github.cvanb002.model;

// Abstract class that all command classes must implement, ensures that each command provides the call method
public abstract class Handler {
    State state;


    public abstract void call(Message message, Client client);
}
