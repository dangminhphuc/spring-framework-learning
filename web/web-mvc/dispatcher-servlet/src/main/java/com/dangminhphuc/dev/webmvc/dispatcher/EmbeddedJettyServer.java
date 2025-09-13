package com.dangminhphuc.dev.webmvc.dispatcher;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class EmbeddedJettyServer {
    private final Server server;
    private final ServletInitializer servletInitializer;

    @Autowired
    public EmbeddedJettyServer(int port, ServletInitializer servletInitializer) {
        this.server = new Server(port);
        this.servletInitializer = servletInitializer;
    }


    public void start() throws Exception {
        ContextHandler handler = servletInitializer.contextHandler();
        server.setHandler(handler);
        server.start();
        System.out.println("=====" + this.servletInitializer);
    }

    public void stop() throws Exception {
        server.stop();
    }

    public int getPort() {
        return server.getURI().getPort();
    }

}
