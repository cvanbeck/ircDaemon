package com.github.cvanb002.server;

import java.io.IOException;
import java.net.*;
import static java.lang.System.exit;


public class Server {
    State state;
    MessageRouter messageRouter;
    int port;

    public Server(int port, State state, MessageRouter router){
        this.port = port;
        this.state = state;
    }


    public void run() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null))){
            while(true){
                Client client = new Client(serverSocket.accept(), messageRouter, this);
                state.addClient(client);
                client.start();
                System.out.println("User Connected");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public State getState(){
        return state;
    }


    public void closeServer(){
        exit(1);
    }
}