package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.*;

public class PrivMsgHandler extends Handler {
    State state;

    public PrivMsgHandler(State state) {
        this.state = state;
    }

    @Override
    public void call(Message message, Client client) {
        String nick = message.getParameters(0);
        Message response = new Message();


        if(nick.charAt(0) == '#'){
            Channel destination = state.getChannel(nick);
            response.addSource(":" + client.getNick());
            response.addCommand("PRIVMSG");
            StringBuilder text = new StringBuilder(nick + " :");
            String param;

            for(int i = 1; i < message.getParameters().size(); i++){
                param = message.getParameters(i);
                text.append(param);
                text.append(" ");
            }
            response.addParameter(String.valueOf(text));
            destination.send(response.toString(), client);
        }


        else if(state.nickExists(nick)){
            Client destination = state.getClient(nick);
            response.addSource(":" + client.getNick());
            response.addCommand("PRIVMSG");
            StringBuilder text = new StringBuilder(nick + " :");
            String param;

            for(int i = 1; i < message.getParameters().size(); i++){
                param = message.getParameters(i);
                text.append(param);
                text.append(" ");
            }

            response.addParameter(String.valueOf(text));
            destination.send(response.toString());
        }

    }

}
