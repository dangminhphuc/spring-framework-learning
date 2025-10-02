package com.dangminhphuc.dev.web;

import com.dangminhphuc.dev.web.config.AppConfig;
import com.dangminhphuc.dev.web.config.InterceptorConfig;
import com.dangminhphuc.dev.web.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class HierarchyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/app/*"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class, InterceptorConfig.class};
    }
}
