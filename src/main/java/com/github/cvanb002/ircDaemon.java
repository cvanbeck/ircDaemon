package com.github.cvanb002;


import com.github.cvanb002.server.MessageRouter;
import com.github.cvanb002.server.Server;
import com.github.cvanb002.server.State;

import java.io.IOException;

public class ircDaemon {
    public static void main(String[] args){
        int port = 6665;
        State state = new State();
        MessageRouter router = new MessageRouter(state);

        Server server = new Server(port, state, router);
        try{
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
