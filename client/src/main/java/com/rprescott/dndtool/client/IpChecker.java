package com.rprescott.dndtool.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IpChecker {

    public static String getIp() {
        BufferedReader input = null;
        String ip = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            input = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = input.readLine();
        }
        catch (Exception e) {
            
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