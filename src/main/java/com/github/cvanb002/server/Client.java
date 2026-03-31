package com.github.cvanb002.server;

import java.net.Socket;



public class Client extends Thread {
    Socket clientSocket;
    Server server;
    MessageRouter messageRouter;
    OutputThread out;

    String user;
    String nick;
    
    Client(Socket client, MessageRouter messageRouter, Server server) {
        clientSocket = client;
        this.server = server;
        this.messageRouter = messageRouter;
    }

    public void respond(String message) {
        out.send(message);
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



    public void run() {
        try (
                InputThread in = new InputThread(clientSocket.getInputStream(), messageRouter, this)
        ) {
            out = new OutputThread(clientSocket.getOutputStream());

            in.start();
            out.start();
            in.join();

            out.close();
            clientSocket.close();
            server.getState().removeClient(this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

};



