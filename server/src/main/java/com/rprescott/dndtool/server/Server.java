package com.rprescott.dndtool.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    
    public void run() {
        while (true) {
            ServerSocket serverSocket = null;
            try {
                // Creating a new ServerSocket. The argument is an integer which is the port number to accept
                // requests on.
                serverSocket = new ServerSocket(1337);
                
                // Now, we will wait for a client to connect. We will not move past the following call until the 
                // server receives a connection.
                Socket clientSocket = serverSocket.accept();
                
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                // Just convert the first line of the input into a String and print it out onto the server console.
                String clientInputString = input.readLine();
                System.out.println("Received message from client: " + clientInputString);
                
                // The client is probably waiting for a server response. Let's send a message to the client to inform it
                // that we've received it's message.
                PrintStream responseStream = new PrintStream(clientSocket.getOutputStream());
                responseStream.println("I got your message. It said: " + clientInputString);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            
            // Now that we've responded, we can close our connection to this socket gracefully.
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}