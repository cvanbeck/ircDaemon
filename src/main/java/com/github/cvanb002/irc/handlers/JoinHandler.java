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

        // Currently only regular channels are supported, the following part
        // ensures that all new channels default to this type.
        if(channelName.charAt(0) != '#'){
            StringBuilder addMode = new StringBuilder("#");
            addMode.append(channelName);
            channelName = addMode.toString();
        }

        if (!state.channelExists(channelName)) {
            // Creates new channel if name doesn't exist
            channel = new Channel(channelName);
            channel.addUser(client);
            channel.addOperator(client);
            state.addChannel(channel);
        } else {
            channel = state.getChannel(channelName);
            channel.addUser(client);
        }

        // Broadcasts new connection to all current clients in channel
        channel.broadcast(":" + client.getNick() + " JOIN " + channel.getName());

        // Confirms channel joining to client
        client.send(":" + state.getSource() + " 332 " + client.getNick() + " " + channel.getName() + " :" + channel.getTopic());
        client.send(":" + state.getSource() + " 353 " + client.getNick() + " = "  + channel.getName() + " :" + channel.usersToString());
        client.send(":" + state.getSource() + " 366 " + client.getNick() + " " + channel.getName() + " :End of /NAMES list");
    }

}
