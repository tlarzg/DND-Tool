package com.rprescott.dndtool.client;

import com.rprescott.dndtool.client.ui.DndClient;
import com.rprescott.dndtool.client.ui.login.LoginScreen;

public class Main {
    
    public static void main(String[] args) {
        String version = Main.class.getPackage().getImplementationVersion();
        Client client = new Client();
        new LoginScreen(version);
        //new DndClient();
        //client.run();
    }

}
