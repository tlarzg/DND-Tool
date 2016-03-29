package com.dndtool.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

@Configuration
@EnableWebMvc
@Import(ContextConfiguration.class)
public class Main {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> portOpt =
            parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);

        OptionSet parsedOpts = parser.parse(args);
        int port = portOpt.value(parsedOpts);

        startServer(port);
    }

    private static void startServer(int port) throws Exception {
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.setConfigLocation(Main.class.getName());

        ContextHandlerCollection contexts = new ContextHandlerCollection();

        ServletContextHandler springHandler = new ServletContextHandler();
        springHandler.setContextPath("/api");
        springHandler.addEventListener(new ContextLoaderListener(springContext));
        springHandler.addServlet(new ServletHolder(new DispatcherServlet(springContext)), "/*");
        contexts.addHandler(springHandler);

        ContextHandler resourceContext = new ContextHandler();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/webapp/");
        resourceContext.setHandler(resourceHandler);
        resourceContext.setContextPath("/");
        contexts.addHandler(resourceContext);

        Server server = new Server(port);
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}