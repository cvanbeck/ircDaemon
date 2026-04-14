package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class PingHandler extends Command {
    State state;

    public PingHandler(State state){

    }

    @Override
    public void call(Message message, Client client) {
        Message response = new Message();
        response.addCommand("PONG");
        
        if(!message.getParameters().isEmpty()){
            for(String param : message.getParameters()){
                response.addParameter(param);
            }
        }
        client.send(response.toString());
    }
}
