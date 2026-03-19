package com.github.cvanb002.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputThread extends Thread implements AutoCloseable {
    BufferedReader in;
    MessageRouter messageRouter;
    ClientHandler sender;

    InputThread(InputStream in, MessageRouter messageRouter, ClientHandler sender) {
        super("InputThread");
        this.in = new BufferedReader(new InputStreamReader(in));
        this.messageRouter = messageRouter;
        this.sender = sender;
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                messageRouter.sendMessage(inputLine, sender);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}