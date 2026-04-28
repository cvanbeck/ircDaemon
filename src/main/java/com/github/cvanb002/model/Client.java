package com.github.cvanb002.model;


import com.github.cvanb002.server.Connection;

// Class responsible for storing all client info, the actual socket connection is initialised separately and must be added
// to the client after they have both been initialised
public class Client{
    private Connection connection;

    private String user;
    private String nick;
    private String realName;

    boolean hasNick = false;
    boolean hasUser = false;
    boolean isRegistered = false;

    public void addConnection(Connection connection){
        this.connection = connection;
    }

    public void closeConnection(){
        connection.close();
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

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
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

    public boolean isRegistered() {
        return isRegistered;
    }

    public boolean attemptRegistration(){
        if(hasNick && hasUser && !isRegistered){
            setRegistered(true);
            return true;
        }
        return false;
    }


};



