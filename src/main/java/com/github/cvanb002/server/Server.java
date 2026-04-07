package com.github.cvanb002.server;

import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;

import java.io.IOException;
import java.net.*;
import static java.lang.System.exit;


public class Server {
    int port;
    State state;
    Parser parser;
    CommandHandler handler;


    public Server(int port, State state, Parser parser, CommandHandler handler){
        this.port = port;
        this.state = state;
        this.parser = parser;
        this.handler = handler;
    }


    public void run() throws IOException {
        // First trys to create the socket on server side
        try(
                ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null))
        ){
            System.out.print("Server started");

            while(true){
                // Then listens to incoming connections on this socket. Creating a new client when connection is made
                Client client = new Client();
                Connection connection = new Connection(serverSocket.accept(), parser, handler, client);
                client.addConnection(connection);
                state.addClient(client);

                new Thread(connection).start();
                System.out.println("User Connected");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            exit(1);
        }
    }

    public State getState(){
        return state;
    }


}