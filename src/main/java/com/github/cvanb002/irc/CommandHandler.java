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
        // temp implementation
        client.send("DEBUG: Message recieved" + message.toString());
        client.send(message.getCommand());
        client.send(message.getSource());
        for(String param : message.getParameters()){
            client.send(param);
        }
    }

    private IRC.Numeric ping(String source, String token){
        if(token.isEmpty()){
            return IRC.Numeric.ERR_NEEDMOREPARAMS;
        }
        else if(source.isEmpty()){
            return IRC.Numeric.ERR_NOORIGIN;
        }

        return IRC.Numeric.SUCCESS;
    }

    private void oper(){};

    private void quit(){};

    private void error(){};

    private void join(){};

    private void kick(){};

    private void privmsg(){};

}
