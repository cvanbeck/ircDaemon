package com.github.cvanb002.server;

import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.mediator.CommandHandler;

import java.net.Socket;


public class Client extends Thread {
    private Connection connection;

    private String user;
    private String nick;

    Client(Socket socket, Scanner scanner, CommandHandler commandHandler, Server server) {
        connection = new Connection(socket, scanner, commandHandler, this);
    }

    public void run() {
        // Creates new thread for input and output on client socket,
        try {
            connection.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
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



};



