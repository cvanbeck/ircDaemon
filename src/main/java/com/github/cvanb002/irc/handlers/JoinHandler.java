package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.*;

public class JoinHandler extends Handler {
    private State state;

    public JoinHandler(State state){
        this.state = state;
    }

    @Override
    public void call(Message message, Client client) {
        String channelName = message.getParameters(0);
        Channel channel;

        if (!state.channelExists(channelName)) {
            channel = new Channel(channelName);
            channel.addUser(client);
            channel.addOperator(client);
            state.addChannel(channel);
        } else {
            channel = state.getChannel(channelName);
            channel.addUser(client);
        }

        channel.broadcast(":" + client.getNick() + " JOIN " + channel.getName());
        client.send(":" + state.getSource() + " 332 " + client.getNick() + " " + channel.getName() + " :" + channel.getTopic());
        client.send(":" + state.getSource() + " 353 " + client.getNick() + " = "  + channel.getName() + " :" + channel.usersToString());
        client.send(":" + state.getSource() + " 366 " + client.getNick() + " " + channel.getName() + " :End of /NAMES list");
    }

}
