package com.rprescott.dndtool.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.rprescott.dndtool.client.connection.IpChecker;

public class Client {
    
    private Socket clientSocket;

	public void run() {
        try {
            clientSocket = new Socket(IpChecker.getIp(), 1337);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	    while(true) {
	        
	        PrintStream printStream = null;
	        // If we successfully initialized the socket connection...
	        if (clientSocket != null) {
	            printStream = createOutputStream();
	        }
	        
	        // Now, let's print a line of text to the Stream. This gets placed into the output stream and will be
	        // sent to the server.
	        if (printStream != null) {
	            System.err.println("Sending message to server...");
	            printStream.println("Sending message to server.");
	        }
	        
	        // Now, let's check the server response. Note that the following lines will wait forever until the 
	        // server responds. If the server does not respond, your program will freeze here. Most client code will implement
	        // a timeout function to only wait a certain amount of time. If a response is not received, the client will disconnect.
	        try {
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            String responseFromServer = bufferedReader.readLine();
	            System.out.println("Response From Server: " + responseFromServer);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

    private PrintStream createOutputStream() {
        PrintStream clientPrintStream = null;
        try {
            clientPrintStream = new PrintStream(clientSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return clientPrintStream;
    }
}
