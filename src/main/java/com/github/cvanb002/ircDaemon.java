package com.github.cvanb002;


import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.mediator.CommandHandler;
import com.github.cvanb002.server.Server;
import com.github.cvanb002.server.State;

import java.io.IOException;

public class ircDaemon {
    public static void main(String[] args){
        int port = 6665;

        State state = new State();
        Scanner scanner = new Scanner();
        CommandHandler commandHandler = new CommandHandler(state);

        Server server = new Server(port, state, scanner, commandHandler);
        try{
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
