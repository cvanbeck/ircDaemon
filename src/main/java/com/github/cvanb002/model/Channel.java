package com.github.cvanb002.model;

import java.util.*;

public class Channel {
    private String name;
    private String topic;

    List<Client> users = Collections.synchronizedList(new ArrayList<>());
    Map<String, Client> operators = Collections.synchronizedMap(new HashMap<>());

    public Channel(String name) {
        this.name = name;
    }

    public Channel(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public void addUser(Client client){
        users.add(client);
    }

    public void removeUser(Client client){
        users.remove(client);
    }

    public void addOperator(Client client){
        operators.put(client.getNick(), client);
    }

    public synchronized void send(String message){
        for(Client user: users){
            user.send(message);
        }
    }
}
