package com.rprescott.dndtool.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionService {
    
    private static Socket clientSocket;
    
    public static Socket obtainConnection(String hostName, int port) {
        Socket ret = null;
        if (clientSocket == null) {
            try {
                clientSocket = new Socket(IpChecker.getIp(), 1337);
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            ret = clientSocket;
        }
        return ret;
    }
}
