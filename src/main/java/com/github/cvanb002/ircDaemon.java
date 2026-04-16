package com.github.cvanb002;


import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.server.Server;
import com.github.cvanb002.model.State;

import java.io.IOException;

public class ircDaemon {
    public static void main(String[] args){
        int port;
        if(args.length == 1){
            port = Integer.parseInt(args[0]);
        } else {
            port = 6665;
        }

        State state = new State("127.0.0.1");
        Parser parser = new Parser();
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
