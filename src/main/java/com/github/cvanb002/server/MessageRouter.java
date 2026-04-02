package com.github.cvanb002.server;

import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.irc.Token;
import com.github.cvanb002.model.Message;

import java.util.List;
import java.util.Objects;


public class MessageRouter {
    State state;

    public MessageRouter(State state){
        this.state = state;
    }

    public void messageRecieved(String message, Client sender){
        Scanner scanner = new Scanner(message);
        Message scannedMessage = scanner.scanMessage();
        System.out.println(scannedMessage.toString());
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
        List<Client> clients = state.getClients();
        for(Client client : clients) {
            if(Objects.equals(client.getUser(), source)){
                return client;
            }
        }
        return null;
    }

    // Deprecated, can be used for testing purposes and sending message to all clients
    public void sendMessage(String message, Client sender){
        List<Client> clients = state.getClients();
        for(Client client : clients) {
            if (sender != client) {
                client.respond(message);
            }
        }
    }
}
