package com.github.cvanb002.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Channel {
    String name;
    String topic;
    List<Client> users = Collections.synchronizedList(new ArrayList<>());

    public Channel(String name) {
        this.name = name;
    }

    public Channel(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    public void addUser(Client client){
        users.add(client);
    }

    public void removeUser(Client client){
        users.remove(client);
    }

    public synchronized void send(String message){
        for(Client user: users){
            user.send(message);
        }
    }
}
