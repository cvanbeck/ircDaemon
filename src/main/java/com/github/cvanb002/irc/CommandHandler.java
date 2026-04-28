package com.github.cvanb002.irc;


import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

import java.util.HashMap;



// Function responsible for translating incoming commands to the correct Handler subclass, this way the server only
// needs to know about this one singular class instead of each handler subclass. A singular version of this object gets
// created at application which registers all commands in IRC.Commands, a reference to this object is then passed to each
// new connection.
public class CommandHandler {
    private final State state;
    private final HashMap<String, Handler> commands = new HashMap<>();

    public CommandHandler(State state){
        this.state = state;
    }

    public void handle(Message message, Client client){
        String command = message.getCommand().toUpperCase();
        Handler handler = commands.get(command);

        // Until a client is registered they are only able to send NICK, USER, CAP, or PING commands
        if(!client.isRegistered()){
            // CAP command stands for capability negotiation and does not require implementation, clients still send it
            // however and needs to be handled gracefully
            if(command.equals("CAP")){
                return;
            }
            if(!command.equals("NICK") && !command.equals("USER") && !command.equals("PING")){
                Message response = new Message();
                response.addSource(":" + state.getSource());
                response.addCommand("451");
                response.addParameter(client.getNick());
                response.addParameter(":You have not registered");
                client.send(response.toString());
            }
        }

        if(handler != null){
            handler.call(message, client);
        }
        // Debugging
        // else {
        //    debugSending(message, client);
        // }
    }

    public void register(IRC.Commands[] commands){
        // Registers call commands in IRC.Commands
        for(IRC.Commands command: commands){
            Handler handlerClass = command.create(state);
            this.commands.put(command.name(), handlerClass);
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
