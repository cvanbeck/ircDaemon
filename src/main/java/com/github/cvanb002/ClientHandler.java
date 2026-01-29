package com.github.cvanb002;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;


public class ClientHandler extends Thread {
    Socket clientSocket = null;
    MessageCentre messages;
    OutputThread out;

    ClientHandler(Socket client, MessageCentre messages) {
        clientSocket = client;
        this.messages = messages;
    }

    public void respond(String message) {
        out.send(message);
    }

    public void run() {
        try (
                InputThread in = new InputThread(clientSocket.getInputStream(), messages);
        ) {
            out = new OutputThread(clientSocket.getOutputStream());
            in.start();
            out.start();
            in.join();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}

 class InputThread extends Thread implements AutoCloseable {
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
                messageCentre.sendMessage(inputLine);
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


