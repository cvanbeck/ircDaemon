package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class NickHandler extends Command {
    State state;

    public NickHandler(State state){
        this.state = state;
    }

    @Override
    public void call(Message message, Client client){
        String nick = message.getParameters().get(0);
        if(state.nickExists(nick)){
            client.send("ERR_NICKNAMEINUSE");
            return;
        }

        client.setNick(nick);
        client.setHasNick(true);

        client.attemptRegistration();
        }
}
