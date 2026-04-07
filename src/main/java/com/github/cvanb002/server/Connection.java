package com.github.cvanb002.server;

import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.mediator.CommandHandler;
import com.github.cvanb002.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
    private final Socket socket;
    private final Scanner scanner;
    private final CommandHandler commandHandler;
    private final Client client;

    private PrintWriter out;

    public Connection(Socket socket, Scanner scanner, CommandHandler commandHandler, Client client){
        this.socket = socket;
        this.scanner = scanner;
        this.commandHandler = commandHandler;
        this.client = client;

    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(client.getNick() + inputLine);

                try {
                    Message message = scanner.parse(inputLine);
                    commandHandler.handle(message, client);

                } catch (Exception e) {
                    // TODO: Update with correct numeric response
                    send("Invalid message");
                }
            }
        } catch (IOException e) {
            System.out.println("Client failed to connect: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    public synchronized void send(String message){
        try {
            out.println(message);
        } catch (Exception e){
            System.out.println("Error sending: " + e.getMessage());
        }
    }
}
