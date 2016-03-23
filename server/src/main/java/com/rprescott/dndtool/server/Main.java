package com.rprescott.dndtool.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nimbusds.srp6.SRP6Exception;

public class Main implements DisposableBean {

    private static ApplicationContext context;

    /**
     * Entry point to the application. Spawns the application context and starts the server.
     *
     * @param args - All arguments are currently being ignored.
     */
    public static void main(String[] args) throws SRP6Exception {
        context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    }

    @Override
    public void destroy() throws Exception {
        if (context != null) {
            ((ConfigurableApplicationContext) context).close();
            context = null;
        }
    }
}
