package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class UserHandler extends Handler {
    State state;

    public UserHandler(State state){
        this.state = state;
    }


    // No IDENT server enabled, therefore usernames are always set by clients
    @Override
    public void call(Message message, Client client){
        String user = message.getParameters().get(0);
        String realName = message.getParameters().get(message.getParameters().size() - 1);

        if(client.isRegistered()){
            client.send("ERR_ALREADYREGISTERED");
        }
        if(user.length() < 2){
            client.send("ERR_NEEDMOREPARAMS");
            return;
        }

        client.setUser("~" + user);
        client.setRealName(realName);
        client.setHasUser(true);

        if(client.attemptRegistration()){
            Message response = new Message();
            response.addSource(":" + state.getSource());
            response.addCommand("001");
            response.addParameter(client.getNick());
            response.addParameter(":Welcome to the IRC network");
            client.send(response.toString());
        };
    }
}
