package com.dangminhphuc.dev.webmvc.dispatcher;

import com.dangminhphuc.dev.webmvc.dispatcher.config.ProgrammaticConfig;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Component
public class ProgrammaticServletInitializerImpl implements ServletInitializer {
    @Override
    public ContextHandler contextHandler() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ProgrammaticConfig.class);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder("dispatcher", new DispatcherServlet(context)), "/app/*");
        return handler;
    }
}
