package com.github.cvanb002.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

public class OutputThread extends Thread implements AutoCloseable {
    PrintWriter out;
    LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    boolean isOpen ;

    OutputThread(OutputStream out) {
        super("OutputThread");
        this.out = new PrintWriter(out, true);
        isOpen = true;
    }

    public void run() {
        try {
            while (isOpen) {
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
        isOpen = false;
        out.close();
    }
}