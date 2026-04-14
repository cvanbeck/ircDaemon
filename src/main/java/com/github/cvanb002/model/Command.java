package com.github.cvanb002.model;

public abstract class Command {
    State state;


    public abstract void call(Message message, Client client);
}
