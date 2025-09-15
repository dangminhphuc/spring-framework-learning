package com.dangminhphuc.dev.webmvc.dispatcher.hierarchy;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the root WebApplicationContext
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);

        // Manage the lifecycle of the root WebApplicationContext
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's context
        AnnotationConfigWebApplicationContext dispatcherContext1 = new AnnotationConfigWebApplicationContext();
        dispatcherContext1.register(MvcConfig1.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher1 = servletContext.addServlet("dispatcher1", new DispatcherServlet(dispatcherContext1));
        dispatcher1.setLoadOnStartup(1);
        dispatcher1.addMapping("/app1/*");

        // Create the second dispatcher servlet's context
        AnnotationConfigWebApplicationContext dispatcherContext2 = new AnnotationConfigWebApplicationContext();
        dispatcherContext2.register(MvcConfig2.class);

        // Register and map the second dispatcher servlet
        ServletRegistration.Dynamic dispatcher2 = servletContext.addServlet("dispatcher2", new DispatcherServlet(dispatcherContext2));
        dispatcher2.setLoadOnStartup(1);
        dispatcher2.addMapping("/app2/*");
    }
}