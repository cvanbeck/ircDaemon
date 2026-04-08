package com.github.cvanb002.irc;


import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

public class CommandHandler {
    private State state;

    public CommandHandler(State state){
        this.state = state;
    }

    public void handle(Message message, Client client){
            debugSending(message, client);
        }

    private void debugSending(Message message, Client client){
        client.send("DEBUG: Message recieved" + message.toString());
        client.send(message.getCommand());
        client.send(message.getSource());
        client.send("DEBUG Message recieved: " + message.toString());
        client.send(message.getCommand());
        client.send(message.getSource());
        for(String param : message.getParameters()){
            client.send(param);
        }
    }
}
