package com.github.cvanb002.server;

import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.mediator.CommandHandler;

import java.io.IOException;
import java.net.*;
import static java.lang.System.exit;


public class Server {
    int port;
    State state;
    Scanner scanner;
    CommandHandler handler;


    public Server(int port, State state, Scanner scanner, CommandHandler handler){
        this.port = port;
        this.state = state;
        this.scanner = scanner;
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
                Client client = new Client(serverSocket.accept(), scanner, handler, this);
                state.addClient(client);
                client.start();
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