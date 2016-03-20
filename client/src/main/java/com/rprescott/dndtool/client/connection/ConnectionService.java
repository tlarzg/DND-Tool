package com.rprescott.dndtool.client.connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionService {
    
    private static Socket clientSocket;
    
    public static Socket obtainConnection(String hostName, int port) throws UnknownHostException, IOException {
        Socket ret = null;
        if (clientSocket == null) {
            System.out.println("Attempting to make a connection to Hostname: " + hostName + " on port: " + port);
            clientSocket = new Socket(IpChecker.getIp(), port);
            // We'll wait up to a second before a timeout occurs.
            clientSocket.setSoTimeout(1000);
        }
        else {
            System.out.println("Connection already exists. Returning cached connection.");
            ret = clientSocket;
        }
        return ret;
    }
    
    public static Socket obtainClientSocket() {
        return clientSocket;
    }
    
    public static void closeConnection() {
        try {
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        clientSocket = null;
    }
}
