package com.github.cvanb002;

import java.util.concurrent.CopyOnWriteArrayList;

public class MessageCentre {
    CopyOnWriteArrayList<ClientHandler> clients;

    public MessageCentre(CopyOnWriteArrayList<ClientHandler> clients){
        this.clients = clients;
    }

    public void sendMessage(String message){
        for(ClientHandler client : clients){
            client.respond(message);
        }
    }

}
