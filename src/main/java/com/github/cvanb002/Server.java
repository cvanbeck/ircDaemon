package com.github.cvanb002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 6665; // ports 6665-6669 are often reserved for IRC

        try(
            ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null));
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            )
        ){
            String inputLine;
            while((inputLine = in.readLine()) != null){
                out.println(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

    }
}
