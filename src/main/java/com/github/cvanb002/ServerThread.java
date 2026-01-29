    package com.github.cvanb002;

    import java.io.*;
    import java.net.Socket;


    public class ServerThread extends Thread{
        Socket clientSocket = null;

        ServerThread(Socket client){
            super("ServerThread");
            clientSocket = client;
        }

        public void run(){
            try(
                InputThread in = new InputThread(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                in.start();
                while(in.getState() != Thread.State.TERMINATED){
                    continue;
                }
                clientSocket.close();
            } catch(IOException e){
                e.printStackTrace(System.out);
            }
        }
    }

    class InputThread extends Thread implements AutoCloseable {
        BufferedReader in = null;

        InputThread(InputStream in){
            super("InputThread");
            this.in = new BufferedReader(new InputStreamReader(in));
        }

        public void run(){
            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException e){
                e.printStackTrace(System.out);
            }
        }

        @Override
        public void close() throws IOException {
            in.close();
        }
    }

    class OutputThread extends Thread implements AutoCloseable{
        PrintWriter out;

        OutputThread(OutputStream out){
            super("OutputThread");
            this.out = new PrintWriter(out, true);
        }

        @Override
        public void close() throws IOException {
            out.close();
        }
    }

    class messageHolder {

    }

