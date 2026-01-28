package com.github.cvanb002;

import java.io.*;
import java.net.Socket;


public class ServerThread extends Thread{
    Socket clientSocket = null;

    ServerThread(Socket client){
        super("ServerThread");
        clientSocket = client;
    }

    public void run(){
        try(
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            while((inputLine = in.readLine()) != null){
                out.println(inputLine);
            }
            clientSocket.close();
        } catch(IOException e){
            e.printStackTrace(System.out);
        }
    }
}
