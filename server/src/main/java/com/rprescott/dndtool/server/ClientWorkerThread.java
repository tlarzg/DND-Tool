package com.rprescott.dndtool.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientWorkerThread implements Runnable {

    private Socket clientSocket;

    public ClientWorkerThread(Socket clientSocket) {
        Thread.currentThread().setName("Client Worker Thread " + clientSocket.getInetAddress().getHostName() +  "  " + this.hashCode());
        this.clientSocket = clientSocket;
        try {
            clientSocket.setKeepAlive(true);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Just convert the first line of the input into a String and print it out onto the server console.
            while (true) {
                System.out.println("Executing on thread: " + Thread.currentThread().getName());
                String clientInputString = input.readLine();
                System.out.println("Received message from client: " + clientInputString);

                // The client is probably waiting for a server response. Let's send a message to the client to inform it
                // that we've received it's message.
                PrintStream responseStream = new PrintStream(clientSocket.getOutputStream());
                responseStream.println("I got your message. It said: " + clientInputString);
                responseStream.println("Here, have another message");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
