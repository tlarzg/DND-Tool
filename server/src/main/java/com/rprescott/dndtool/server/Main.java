package com.rprescott.dndtool.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/rprescott/dndtool/server/config/application-context.xml");
        Server server = new Server();
        server.run();
    }
}
