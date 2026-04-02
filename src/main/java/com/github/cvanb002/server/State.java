package com.github.cvanb002.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {
    List<Client> clients = Collections.synchronizedList(new ArrayList<>());


    public List<Client> getClients(){
        return clients;
    }
    public void addClient(Client client){
        clients.add(client);
    }

    public void removeClient(Client client) {
        try {
            clients.remove(client);
        } catch (Exception e) {
            System.err.print("Failed to remove reference to client.");
        }
    }
}
