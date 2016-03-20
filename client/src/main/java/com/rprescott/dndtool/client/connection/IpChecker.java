package com.rprescott.dndtool.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Utility to obtain the external IPv4 address of the current machine. 
 */
public class IpChecker {

    /**
     * Obtains the external IPv4 address of the current machine.
     * 
     * @return The IPv4 address or <b>null</b> if the service is offline.
     */
    public static String getIp() {
        BufferedReader input = null;
        String ip = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            input = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = input.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ip;
    }
}