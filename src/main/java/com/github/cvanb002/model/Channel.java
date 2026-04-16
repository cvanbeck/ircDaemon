package com.github.cvanb002.model;

import java.util.*;

public class Channel {
    private String name;
    private String topic = "";

    List<Client> users = Collections.synchronizedList(new ArrayList<>());
    Map<String, Client> operators = Collections.synchronizedMap(new HashMap<>());

    public Channel(String name) {
        this.name = name;
        this.topic = "Welcome to " + name;
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

    public List<Client> getUsers() {
        return users;
    }

    public void removeUser(Client client){
        users.remove(client);
        operators.remove(client);
    }

    public void addOperator(Client client){
        operators.put(client.getNick(), client);
    }

    public synchronized void broadcast(String message){
        for(Client user: users){
            user.send(message);
        }
    }

    public synchronized void send(String message, Client sender){
        for(Client user: users){
            if (sender.equals(user)){
                continue;
            }
            user.send(message);
        }
    }

    public String usersToString(){
        StringBuilder usersString = new StringBuilder();
        StringBuilder name = new StringBuilder();
        for(Client user: users){
            name.setLength(0);
            name.append(user.getNick());
            if(operators.containsKey(name.toString())) {
                name.insert(0, "@");
            }
            usersString.append(name);
            usersString.append(" ");
        }
        return usersString.toString();
    }
}
