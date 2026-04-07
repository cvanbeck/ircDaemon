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
            createIO(socket);

            int currentByte;
            StringBuilder inputLine = new StringBuilder();

            while ((currentByte = in.read()) != -1) {
                inputLine.append((char) currentByte);

                if(inputLine.toString().contains("\r\n")){
                    String input = inputLine.toString();
                    logInput(input);
                    processInput(input);

                    inputLine.setLength(0); // Resets input
                }
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + e.getMessage());
        } finally {
            close();
        }
    }

    public synchronized void send(String message){
        try {
            logOutput(message);
            out.write(message + IRC.Constants.CRLF);
            out.flush();
        } catch (Exception e){
            System.out.println("Error sending: " + e.getMessage());
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {}
    }

    private void processInput(String inputLine) {
        try {
            Message message = parser.parse(inputLine);
            commandHandler.handle(message, client);
        } catch (Exception e) {
            // TODO: Update with correct numeric response
            send("Invalid message");
        }
    }

    private void createIO(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void logInput(String input) {
        System.out.println(client.getNick() + ": " + input);
    }

    private void logOutput(String output) {
        System.out.println(">> " + output);
    }




}
