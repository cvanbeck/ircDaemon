package com.github.cvanb002.server;

import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

import java.io.IOException;
import java.net.*;
import static java.lang.System.exit;


public class Server {
    private final int port;
    private final State state;
    private final Parser parser;
    private final CommandHandler handler;

    public Server(int port, State state, Parser parser, CommandHandler handler){
        this.port = port;
        this.state = state;
        this.parser = parser;
        this.handler = handler;
    }

    public void run() throws IOException {
        // First tries to create the socket on server side
        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null))){

            System.out.println("Server started");

            while(true){
                // Then listens to incoming connections on this socket. Creating a new client when connection is made
                Client client = new Client();
                Connection connection = new Connection(serverSocket.accept(), parser, handler, client);
                state.addClient(client);
                client.addConnection(connection);

                new Thread(connection).start();
                System.out.println("User Connected");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public State getState(){
        return state;
    }


}