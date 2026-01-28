package com.github.cvanb002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class testClient {
    public static void main(String[] args){
        int port = 6665;
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
            while ((userInput = stdIn.readLine()) != null){
                out.println(userInput);
                System.out.println("Echo: " + in.readLine());
            }
        } catch (IOException e){
            return;
        }

    }
}
