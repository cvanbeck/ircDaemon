package com.github.cvanb002;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler {
    Socket clientSocket = null;
    MessageCentre messages;

    ClientHandler(Socket client, MessageCentre messages) {
        clientSocket = client;
        this.messages = messages;
    }

    public void run() {
        try (
                InputThread in = new InputThread(clientSocket.getInputStream(), messages);
                OutputThread out = new OutputThread(clientSocket.getOutputStream(), messages);
        ) {
            in.start();
            out.start();
            while (in.getState() != Thread.State.TERMINATED) {
                continue;
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }


    private class InputThread extends Thread implements AutoCloseable {
        BufferedReader in = null;
        MessageCentre messageCentre;

        InputThread(InputStream in, MessageCentre messageCentre) {
            super("InputThread");
            this.in = new BufferedReader(new InputStreamReader(in));
            this.messageCentre = messageCentre;
        }

        public void run() {
            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {
                    messageCentre.addMessage(inputLine);
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

    private class OutputThread extends Thread implements AutoCloseable {
        PrintWriter out;
        MessageCentre messageCentre;

        OutputThread(OutputStream out, MessageCentre messageCentre) {
            super("OutputThread");
            this.out = new PrintWriter(out, true);
            this.messageCentre = messageCentre;
        }

        public void run() {
            while (connected) {
                out.println(message);
            }
        }

        private void difference(ArrayList<String> oldMessages, ArrayList<String> newMessages) {
            return;
        }

        @Override
        public void close() throws IOException {
            out.close();
        }
    }
}

