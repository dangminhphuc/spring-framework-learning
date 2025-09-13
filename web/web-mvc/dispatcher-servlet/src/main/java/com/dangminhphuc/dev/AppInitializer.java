package com.dangminhphuc.dev;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer {
    public static ServletContextHandler createHandler() {
        // Spring context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class); // config của bạn

        // DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(context);

        // Jetty handler
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(servlet), "/app/*");

        return handler;
    }
}
