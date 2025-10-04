package com.dangminhphuc.dev;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);

        //
        DispatcherServlet servlet = new DispatcherServlet(webContext);

        // Jetty server
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addEventListener(new ContextLoaderListener(rootContext));
        handler.addServlet(new ServletHolder(servlet), "/");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}