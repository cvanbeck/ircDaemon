package com.github.cvanb002;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class Server {
    public static void main(String[] args) throws IOException {
        MessageCentre messages = new MessageCentre();
        CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

        int port = 6665; // ports 6665-6669 are often reserved for IRC

        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null));){
            while(true){
                ClientHandler client = new ClientHandler(serverSocket.accept(), messages);
                client.start();
                clients.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
}