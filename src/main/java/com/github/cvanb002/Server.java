package com.github.cvanb002;

import java.io.IOException;
import java.net.*;


public class Server {
    public static void main(String[] args) throws IOException {
        int port = 6665; // ports 6665-6669 are often reserved for IRC

        try(ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null));){
            while(true){
                new ServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
}