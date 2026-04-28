package com.github.cvanb002.server;

import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Message;

import java.io.*;
import java.net.Socket;

// Class responsible for handling input/output for each client. Implements runnable so that
// each connection can be run on its own thread.

public class Connection implements Runnable {
    private Socket socket;
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

    // Used for testing only
    public Connection(BufferedWriter out, BufferedReader in , Parser parser, CommandHandler commandHandler, Client client){
        this.out = out;
        this.in = in;
        this.parser = parser;
        this.commandHandler = commandHandler;
        this.client = client;
    }

    public void run() {
        try {
            createIO(socket);

            int currentByte;
            StringBuilder inputLine = new StringBuilder();
            // Continuously listens to incoming bytes from socket
            while ((currentByte = in.read()) != -1) {
                inputLine.append((char) currentByte);
                // Bytes are read until CLRF is received as defined in IRC docs
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
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {}
    }

    private void processInput(String inputLine) {
        // Function responsible for parsing incoming message into a message type, and then handling whatever command has
        // been sent. This function does not require a list of commands to do this, instead it just needs to know about
        // the commandHandler class which is in turn responsible for calling the correct Handler object.
        try {
            Message message = parser.parse(inputLine);
            commandHandler.handle(message, client);
        } catch (Exception e) {
            // TODO: Update with correct numeric response
            send("Invalid message");
        }
    }

    // Sets up reader/writers on socket in/out
    private void createIO(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void logInput(String input) {

        System.out.print(client.getNick() + ": " + input);
    }

    private void logOutput(String output) {
        System.out.println(">> " + output);
    }
}
