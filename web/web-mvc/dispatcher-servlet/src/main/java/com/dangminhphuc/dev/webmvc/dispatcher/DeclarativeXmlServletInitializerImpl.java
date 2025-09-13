package com.dangminhphuc.dev.webmvc.dispatcher;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Component("xmlServletInitializer")
public class DeclarativeXmlServletInitializerImpl implements ServletInitializer {
    @Override
    public ContextHandler contextHandler() {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:app-config.xml");

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder("dispatcher", new DispatcherServlet(context)), "/app/*");

        return handler;
    }
}
