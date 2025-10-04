package com.dangminhphuc.dev.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class ExceptionConfig {

    @Bean
    public HandlerExceptionResolver exceptionResolver() {
        CustomMappingExceptionResolver resolver = new CustomMappingExceptionResolver();

        resolver.setExceptionMappings(new Properties() {{
            put("java.lang.Exception", "exception/500");
            put("java.lang.RuntimeException", "exception/error");
        }});
        resolver.setDefaultErrorView("exception/error"); // View name for error page
        resolver.setExceptionAttribute("ex"); // Attribute name to access the exception in the view
        return resolver;
    }

    private static class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
        @Override
        protected ModelAndView doResolveException(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Object handler,
                                                  Exception ex) {
            System.out.println("[CustomMappingExceptionResolver] Handling exception: " + ex.getMessage());
            ModelAndView mav = super.doResolveException(request, response, handler, ex);

            // You can add additional attributes to the ModelAndView if needed
            if (mav != null) {
                mav.addObject("exceptionMessage", ex.getMessage());
            }
            return mav;
        }
    }
}
