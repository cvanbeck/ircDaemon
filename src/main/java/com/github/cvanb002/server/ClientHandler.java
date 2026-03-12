package com.github.cvanb002.server;

import java.io.*;
import java.net.Socket;



public class ClientHandler extends Thread {
    Socket clientSocket;
    Server server;
    MessageCentre messageCentre;
    OutputThread out;
    
    ClientHandler(Socket client, MessageCentre messageCentre, Server server) {
        clientSocket = client;
        this.messageCentre = messageCentre;
    }

    public void respond(String message) {
        out.send(message);
    }

    public void run() {
        try (
                InputThread in = new InputThread(clientSocket.getInputStream(), messageCentre, this);
        ) {
            out = new OutputThread(clientSocket.getOutputStream());

            in.start();
            out.start();
            in.join();

            out.close();
            clientSocket.close();
            server.removeClient(this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

};



