package com.github.cvanb002.server;

import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Message;

import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {
    private final Socket socket;
    private final Parser parser;
    private final CommandHandler commandHandler;
    private final Client client;

    private BufferedWriter out;
    private BufferedReader in;

    public Connection(Socket socket, Parser parser, CommandHandler commandHandler, Client client){
        this.socket = socket;
        this.parser = parser;
        this.commandHandler = commandHandler;
        this.client = client;

    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            int currentByte;
            String inputLine = "";

            // Reads a byte at a time until CRLF is sent
            while ((currentByte = in.read()) != -1) {
                inputLine += (char) currentByte;

                if(inputLine.contains("\r\n")){
                    System.out.println(client.getNick() + ": " + inputLine);
                    try {
                        Message message = parser.parse(inputLine);
                        commandHandler.handle(message, client);
                    } catch (Exception e) {
                        // TODO: Update with correct numeric response
                        send("Invalid message");
                    }
                inputLine = "";
                }
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    public synchronized void send(String message){
        try {
            System.out.println(">> " + message);
            out.write(message + IRC.Constants.CRLF);
            out.flush();
        } catch (Exception e){
            System.out.println("Error sending: " + e.getMessage());
        }
    }
}
