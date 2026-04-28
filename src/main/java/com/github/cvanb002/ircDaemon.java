package com.github.cvanb002;


import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.server.Server;
import com.github.cvanb002.model.State;

import java.io.IOException;

public class ircDaemon {
    public static void main(String[] args){
        // Port traditionally used for IRC, this version of the application runs on
        // localhost, see outside-server-test git branch for a version that accepts
        // external connections
        int port = 6665;
        State state = new State("127.0.0.1");

        Parser parser = new Parser();

        // Initialises all IRC commands define in IRC.Commands, similar to state there should only be a single instance
        // of this class which is then passed as a reference to each new connection. Meaning the server, clients, and connections
        // do not need to know any actual details about how IRC is implemented.
        CommandHandler commandHandler = new CommandHandler(state);
        commandHandler.register(IRC.Commands.values());
        Server server = new Server(port, state, parser, commandHandler);
        try{
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
