package com.github.cvanb002.model;

public abstract class Command {
    State state;

    public Command(State state){
        this.state = state;
    }

    public abstract void call(Message message, Client client);
}
