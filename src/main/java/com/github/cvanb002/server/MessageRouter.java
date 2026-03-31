package com.github.cvanb002.server;

import java.util.List;
import java.util.Objects;
import com.github.cvanb002.model.NetworkProtocol;

public class MessageRouter {
    List<Client> clients;
    NetworkProtocol protocol;

    public MessageRouter(List<Client> clients, NetworkProtocol protocol){
        this.clients = clients;
        this.protocol = protocol;
    }


    public void messageRecieved(String message, Client sender){

    }

    public void sendMessage(String message, Client sender){
        // Deprecated, can be used for testing purposes and sending message to all clients
        for(Client client : clients) {
            if (sender != client) {
                client.respond(message);
            }
        }
    }

    public void sendMessage(String source, String message){
        try{
            Client client = resolveSource(source);
            client.respond(message);
        } catch (Exception e) {
            // Placeholder exception, needs to throw an error if client not found
            throw new RuntimeException(e);
        }
    }

    public Client resolveSource(String source){
        for(Client client : clients) {
            if(Objects.equals(client.getUser(), source)){
                return client;
            }
        }
        return null;
    }
}