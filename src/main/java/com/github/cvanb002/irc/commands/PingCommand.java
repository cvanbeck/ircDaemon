package com.github.cvanb002.irc.commands;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class PingCommand extends Command {
    State state;

    public PingCommand(State state){
        super(state);
    }

    @Override
    public void call(Message message, Client client) {
        client.send("PONG");
    }
}
