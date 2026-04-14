package com.github.cvanb002.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {
    String source;
    List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    public State(String source){
        this.source = source;
    }

    public List<Client> getClients(){
        return clients;
    }
    public void addClient(Client client){
        clients.add(client);
    }

    public void removeClient(Client client) {
        try {
            clients.remove(client);
            client.closeConnection();
        } catch (Exception e) {
            System.err.print("Failed to remove reference to client.");
        }
    }

    public String getSource() {
        return source;
    }

    public boolean nickExists(String nick){
        String clientNick;
        for(Client client : clients){
            clientNick = client.getNick();
            if(nick.equals(clientNick)){
                return true;
            }
        }
        return false;
    }
}
