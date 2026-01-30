package com.github.cvanb002;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageCentre {
    List<ClientHandler> clients;

    public MessageCentre(List<ClientHandler> clients){
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
