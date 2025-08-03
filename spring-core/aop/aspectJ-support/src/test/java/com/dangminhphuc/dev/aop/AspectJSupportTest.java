package com.dangminhphuc.dev.aop;

import com.dangminhphuc.dev.AppConfig;
import com.dangminhphuc.dev.MyService;
import com.dangminhphuc.dev.service.LoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class AspectJSupportTest {
    @Nested
    @DisplayName("Running as main method")
    class AsMainMethod {
        @Test
        public void mainTest() {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

            MyService myService = context.getBean(MyService.class);
            myService.performAction("Hello, AspectJ!");
        }
    }

    private MyService proxiedService;
    private LoggerService mockLoggerService;

    @BeforeEach
    public void setUpStreams() {
        // 1. Create the target object
        MyService target = new MyService();

        // 2. Create the mock collaborator
        this.mockLoggerService = mock(LoggerService.class);

        // 3. Create the aspect instance, injecting the mock
        LoggingAspect aspect = new LoggingAspect(mockLoggerService);

        // 4. Create the proxy factory
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(aspect);

        // 5. Get the proxied object
        this.proxiedService = factory.getProxy();
    }

    @Test
    public void testAop() {
        this.proxiedService.performAction("TEST");

        verify(mockLoggerService).log("Logging before method execution!");
    }
}
