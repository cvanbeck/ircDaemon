package com.github.cvanb002;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Server {
    static List<ClientHandler> clients;
    static MessageCentre messages;

    public static void main(String[] args) throws IOException {
        clients = Collections.synchronizedList(new ArrayList<>());
        messages = new MessageCentre(clients);

        int port = 6665; // ports 6665-6669 are often reserved for IRC

        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null));){
            while(true){
                ClientHandler client = new ClientHandler(serverSocket.accept(), messages);
                clients.add(client);
                client.start();
                System.out.println("User Connected");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public static boolean removeClient(ClientHandler client) {
        try {
            clients.remove(client);
            return true;
        } catch (Exception e) {
            System.err.print("Failed to remove reference to client.");
            return false;
        }
    }
}