package com.dangminhphuc.dev.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.DispatcherServlet;

public class App {
    public static void main(String[] args) throws Exception {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(WebConfig.class);
//
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
//
//
//        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        handler.setContextPath("/");
//        handler.addServlet(new ServletHolder("dispatcher", dispatcherServlet), "/*");

        Server server = new Server(8080);

        Handler handler = createHandler();
        server.setHandler(handler);

        // Start server
        server.start();
        System.out.println("Server started at http://localhost:8080/");
        server.join();
    }

    public static Handler createHandler() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);


        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder(dispatcherServlet), "/*");
        return handler;
    }

}
