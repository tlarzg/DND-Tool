package com.dndtool.server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dndtool.server.security.CredentialsService;
import com.dndtool.server.security.SecurityInitializer;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {

    private static final int DEFAULT_PORT = 8080;

    /**
     * Entry point to the application. Accepts the following following arguments:
     * <li>--port (port number)</li>
     * <li>--init (True/False)</li>
     * <li>--user (userName) </li>
     * <li>--password (password) </li>
     *
     * Setting the port attribute will define what port the server is listening on. If the initialization flag is
     * set, you can specify a user name and password to be inserted into the database as a valid user.
     *
     * @param args - The arguments passed in.
     * @throws Exception If something bad happens.
     */
    public static void main(String[] args) throws Exception {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> portOpt =
            parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
        OptionSpec<Boolean> initOpt =
            parser.accepts("init").withOptionalArg().ofType(Boolean.class).defaultsTo(false);
        OptionSpec<String> initUserOpt =
            parser.accepts("user").availableIf(initOpt).withRequiredArg().ofType(String.class);
        OptionSpec<String> initPasswordOpt =
            parser.accepts("password").availableIf(initOpt).withRequiredArg().ofType(String.class);


        OptionSet parsedOpts = parser.parse(args);
        int port = portOpt.value(parsedOpts);
        boolean init = initOpt.value(parsedOpts) || parsedOpts.has(initOpt);

        startServer(port, init, (init ? new InitializationOptions(
                initUserOpt.value(parsedOpts),
                initPasswordOpt.value(parsedOpts))
            : null));
    }

    private static void startServer(int port, boolean init, InitializationOptions initOptions) throws Exception {
        Server server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setResourceBase("src/main/webapp");
        context.setConfigurations(new Configuration[] {
            new AnnotationConfiguration() {
                @Override
                public void preConfigure(WebAppContext context) {
                    ClassInheritanceMap map = new ClassInheritanceMap();
                    map.put(WebApplicationInitializer.class.getName(),
                        new ConcurrentHashSet<String>() {{
                            add(SecurityInitializer.class.getName());
                            add(ApiServletInitializer.class.getName());
                        }});
                    context.setAttribute(CLASS_INHERITANCE_MAP, map);
                    _classInheritanceHandler = new ClassInheritanceHandler(map);
                }
            }
        });
        context.addServlet(new ServletHolder(new DefaultServlet()), "/");

        server.setHandler(context);
        server.start();

        if (init) {
            initApplication(
                WebApplicationContextUtils.getWebApplicationContext(context.getServletContext()), initOptions);
        }
        server.join();
    }

    private static void initApplication(WebApplicationContext applicationContext, InitializationOptions initOptions) {
        try {
            applicationContext.getBean(CredentialsService.class).registerUser(
                initOptions.getUsername(), initOptions.getPassword());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class InitializationOptions {
        private final String username;
        private final String password;

        InitializationOptions(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("InitializationOptions [username=");
            builder.append(username);
            builder.append(", password=");
            builder.append(password);
            builder.append("]");
            return builder.toString();
        }
    }
}