package com.dndtool.server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.WebApplicationInitializer;

import com.dndtool.server.security.SecurityInitializer;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

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
        Server server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setConfigurations(new Configuration[] {
            new AnnotationConfiguration() {
                @Override
                public void preConfigure(WebAppContext context) {
                    ClassInheritanceMap map = new ClassInheritanceMap();
                    map.put(WebApplicationInitializer.class.getName(),
                        new ConcurrentHashSet<String>() {{
                            add(SecurityInitializer.class.getName());
                            add(SpringServletInitializer.class.getName());
                        }});
                    context.setAttribute(CLASS_INHERITANCE_MAP, map);
                    _classInheritanceHandler = new ClassInheritanceHandler(map);
                }
            }
        });
        server.setHandler(context);
        server.start();
        server.join();
    }
}