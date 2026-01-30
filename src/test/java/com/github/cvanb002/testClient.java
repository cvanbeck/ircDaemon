package com.github.cvanb002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class testClient {
    public static void main(String[] args){
        int port = 6665;
        String name = String.valueOf(new Random().nextInt(9999));
        String host = "127.0.0.1";
        try(
            Socket socket = new Socket(host, port);
            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        ) {
            String userInput;
            System.out.println("Succesfully Joined Server");
            new Thread(new Runnable() {
                @Override
                public void run(){
                String response;
                    try {
                        while(true) {
                            if ((response = in.readLine()) != null) {
                                System.out.println(response);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            while ((userInput = stdIn.readLine()) != null){
                out.println(name + ": " + userInput);
                //System.out.println(in.readLine());
            }
        } catch (IOException e){
            return;
        }
    }
}
