package com.github.cvanb002.model;


import com.github.cvanb002.server.Connection;

public class Client{
    private Connection connection;

    private String user;
    private String nick;

    boolean hasNick = false;
    boolean hasUser = false;
    boolean isRegistered = false;

    public void addConnection(Connection connection){
        this.connection = connection;
    }

    public void send(String message){
        connection.send(message);
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return user;
    }

    public void setNick(String nick){
        this.nick = nick;
    }

    public String getNick(){
        return nick;
    }

    public void setHasNick(boolean hasNick) {
        this.hasNick = hasNick;
    }

    public void setHasUser(boolean hasUser) {
        this.hasUser = hasUser;
    }

    private void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean attemptRegistration(){
        if(hasNick && hasUser && !isRegistered){
            setRegistered(true);
            return true;
        }
        return false;
    }


};



