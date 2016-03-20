package com.rprescott.dndtool.server;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/rprescott/dndtool/server/config/application-context.xml");
        Server server = new Server();
        try {
            server.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
