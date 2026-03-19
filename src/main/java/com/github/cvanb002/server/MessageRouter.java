package com.github.cvanb002.server;

import java.util.List;

public class MessageRouter {
    List<ClientHandler> clients;

    public MessageRouter(List<ClientHandler> clients){
        this.clients = clients;
    }

    public void sendMessage(String message, ClientHandler sender){
        for(ClientHandler client : clients) {
            if (sender != client) {
                client.respond(message);
            }
        }
    }

}