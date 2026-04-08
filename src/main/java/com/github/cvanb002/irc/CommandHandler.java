package com.github.cvanb002.irc;


import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHandler {
    private State state;
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandHandler(State state){
        this.state = state;
    }

    public void handle(Message message, Client client){
            debugSending(message, client);
    }

    public void register(IRC.Commands commands){
        for(IRC.Commands command: commands.values()){
            Command commandClass = command.create(state);
            this.commands.put(command.name(), commandClass);
        }
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
