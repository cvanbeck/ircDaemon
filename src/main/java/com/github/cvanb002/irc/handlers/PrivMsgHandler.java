package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class PrivMsgHandler extends Handler {
    State state;

    public PrivMsgHandler(State state) {
        this.state = state;
    }

    @Override
    public void call(Message message, Client client) {
        String nick = message.getParameters(0);

        Client destination;

        if(state.nickExists(nick)){
            destination = state.getClient(nick);
            Message response = new Message();
            response.addSource(":" + client.getNick());
            response.addCommand("PRIVMSG");
            StringBuilder text = new StringBuilder(nick + " :");
            String param;

            for(int i = 1; i < message.getParameters().size(); i++){
                param = message.getParameters(i);
                text.append(" ");
                text.append(param);
            }

            response.addParameter(String.valueOf(text));
            destination.send(response.toString());
        }

    }

}
