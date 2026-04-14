package com.github.cvanb002.irc;


import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

import java.util.HashMap;

public class CommandHandler {
    private final State state;
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandHandler(State state){
        this.state = state;
    }

    public void handle(Message message, Client client){
        Command command = commands.get(message.getCommand().toUpperCase());
        if(command != null){
            command.call(message, client);
        }
        // Debugging
        // else {
        //    debugSending(message, client);
        // }
    }

    public void register(IRC.Commands[] commands){
        for(IRC.Commands command: commands){
            Command commandClass = command.create(state);
            this.commands.put(command.name(), commandClass);
        }
    }

    private void debugSending(Message message, Client client){
        client.send("DEBUG Message recieved: " + message.toString());
        client.send("COMMAND: " + message.getCommand());
        client.send("SOURCE: " + message.getSource());
        for(String param : message.getParameters()){
            client.send("PARAM: " + param);
        }
        System.out.print("\n");
    }
}
