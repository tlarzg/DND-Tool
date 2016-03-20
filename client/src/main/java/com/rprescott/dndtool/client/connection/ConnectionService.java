package com.rprescott.dndtool.client.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionService {
    
    private static Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    
    /**
     * Obtains a connection to the supplied hostname on the supplied port. If this client is already connected
     * to a server, a cached socket is returned instead of starting a new connection.
     * 
     * @param hostName - The hostname / IP to connect to.
     * @param port - The port to connect to.
     * @return A Socket representing a connection to the server.
     */
    public static Socket obtainConnection(String hostName, int port) throws UnknownHostException, IOException {
        Socket ret = null;
        if (clientSocket == null) {
            System.out.println("Attempting to make a connection to Hostname: " + hostName + " on port: " + port);
            clientSocket = new Socket(IpChecker.getIp(), port);
            // We'll wait up to 10 seconds before a timeout occurs.
            clientSocket.setSoTimeout(10000);
        }
        else {
            System.out.println("Connection already exists. Returning cached connection.");
            ret = clientSocket;
        }
        return ret;
    }
    
    /**
     * Obtains the OutputStream for the socket. A socket may only have one InputStream and one OutputStream.
     * This will create the OutputStream if it doesn't already exist. If it does, the cached stream is returned.
     * 
     * @return The OutputStream for the socket.
     */
    public static ObjectOutputStream obtainClientOutputStream() {
        if (outputStream == null) {
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream;
    }
    
    /**
     * Obtains the InputStream for the socket. A socket may only have one InputStream and one OutputStream.
     * This will create the InputStream if it doesn't already exist. If it does, the cached stream is returned.
     * 
     * @return The InputStream for the socket.
     */
    public static ObjectInputStream obtainClientInputStream() {
        if (inputStream == null) {
            try {
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }
    
    /**
     * Gracefully closes the connection.
     */
    public static void closeConnection() {
        try {
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        outputStream = null;
        inputStream = null;
        clientSocket = null;
    }
}
