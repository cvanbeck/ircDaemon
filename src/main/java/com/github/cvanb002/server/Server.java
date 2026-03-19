package com.github.cvanb002.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.System.exit;


public class Server {
    List<ClientHandler> clients;
    MessageCentre messages;
    int port;

    public Server(int port){
        clients = Collections.synchronizedList(new ArrayList<>());
        messages = new MessageCentre(clients);
        this.port = port;

    }


    public void run() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null));){
            while(true){
                ClientHandler client = new ClientHandler(serverSocket.accept(), messages, this);
                clients.add(client);
                client.start();
                System.out.println("User Connected");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public boolean removeClient(ClientHandler client) {
        try {
            clients.remove(client);
            return true;
        } catch (Exception e) {
            System.err.print("Failed to remove reference to client.");
            return false;
        }
    }

    public void closeServer(){
        exit(1);
    }
}