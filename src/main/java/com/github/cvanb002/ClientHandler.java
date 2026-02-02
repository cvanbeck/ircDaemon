package com.github.cvanb002;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;


public class ClientHandler extends Thread {
    Socket clientSocket = null;
    MessageCentre messageCentre;
    OutputThread out;
    
    ClientHandler(Socket client, MessageCentre messageCentre) {
        clientSocket = client;
        this.messageCentre = messageCentre;
    }

    public void respond(String message) {
        out.send(message);
    }

    public void run() {
        try (
                InputThread in = new InputThread(clientSocket.getInputStream(), messageCentre, this);
        ) {
            out = new OutputThread(clientSocket.getOutputStream());

            in.start();
            out.start();
            in.join();

            out.close();
            clientSocket.close();
            Server.removeClient(this);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}

 class InputThread extends Thread implements AutoCloseable {
    BufferedReader in = null;
    MessageCentre messageCentre;
    ClientHandler sender;

    InputThread(InputStream in, MessageCentre messageCentre, ClientHandler sender) {
        super("InputThread");
        this.in = new BufferedReader(new InputStreamReader(in));
        this.messageCentre = messageCentre;
        this.sender = sender;
    }

    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                messageCentre.sendMessage(inputLine, sender);
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

class OutputThread extends Thread implements AutoCloseable {
    PrintWriter out;
    LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    OutputThread(OutputStream out) {
        super("OutputThread");
        this.out = new PrintWriter(out, true);
    }

    public void run() {
        try {
            while (true) {
                String message = queue.take();
                out.println(message);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message){
        queue.offer(message);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}


