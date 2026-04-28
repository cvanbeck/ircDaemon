package com.github.cvanb002.model;

import java.util.*;

/*
 A singular instance of state is initialised at server start and is shared between the server and all clients.

 Responsible for both storing all references to client/channels and providing the methods for accessing them
 */

public class State {
    String source;
    // Clients and channels both get synchronised due to the fact there are multiple threads
    List<Client> clients = Collections.synchronizedList(new ArrayList<>());
    Map<String, Channel> channels = Collections.synchronizedMap(new HashMap<>());

    public State(String source){
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public List<Client> getClient(){
        return clients;

    }
    public Client getClient(String nick){
        for(Client client : clients) {
            if(nick.equals(client.getNick())){
                return client;
            }
        } return null;
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

    public void addChannel(Channel channel){
        channels.put(channel.getName(), channel);
    }

    public Map<String, Channel> getChannel() {
        return channels;
    }

    public Channel getChannel(String name){
        return  channels.get(name);
    }

    public boolean channelExists(String name){
        return channels.containsKey(name);
    }


}
