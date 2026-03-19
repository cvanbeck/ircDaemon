package com.github.cvanb002.server;

import java.util.List;

public class MessageRouter {
    List<Client> clients;

    public MessageRouter(List<Client> clients){
        this.clients = clients;
    }

    public void sendMessage(String message, Client sender){
        for(Client client : clients) {
            if (sender != client) {
                client.respond(message);
            }
        }
    }

    public void sendMessage(String source, String message){
        Client client
    }

}