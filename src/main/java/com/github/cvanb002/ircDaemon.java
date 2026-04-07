package com.github.cvanb002;


import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.server.Server;
import com.github.cvanb002.model.State;

import java.io.IOException;

public class ircDaemon {
    public static void main(String[] args){
        int port = 6665;

        State state = new State();
        Parser parser = new Parser();
        CommandHandler commandHandler = new CommandHandler(state);

        Server server = new Server(port, state, parser, commandHandler);
        try{
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
